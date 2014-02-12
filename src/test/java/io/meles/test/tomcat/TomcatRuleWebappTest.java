/*
 * Meles Tomcat Test
 * Copyright (C) 2014 Neil Green
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.meles.test.tomcat;

import static io.meles.test.tomcat.TomcatRule.withTomcat;
import static io.meles.test.tomcat.WebappBuilder.webapp;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class TomcatRuleWebappTest {

    private static final String WEBAPP_ROOT = TomcatRuleWebappTest.class.getResource("/a_webapp").getFile();

    @Test
    public void canRunWebappFromFilesystem() throws Throwable {

        final TomcatRule ruleUnderTest =
                withTomcat()
                        .run(webapp(WEBAPP_ROOT).at("/"))
                        .onFreePort();

        final Statement base = new Statement() {
            @Override
            public void evaluate() throws Throwable {
                int tomcatPort = ruleUnderTest.getLocalPort();
                final HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:" + tomcatPort + "/index.jsp").openConnection();
                final InputStreamReader in = new InputStreamReader(connection.getInputStream());
                final StringBuilder body = new StringBuilder();
                final char[] buffer = new char[512];
                for (int charsRead = in.read(buffer); charsRead != -1; charsRead = in.read(buffer)) {
                    body.append(buffer, 0, charsRead);
                }
                assertThat(body.toString(), is("This is the webapp"));
            }
        };
        final Statement statement = ruleUnderTest.apply(base, Description.EMPTY);
        statement.evaluate();
    }
}
