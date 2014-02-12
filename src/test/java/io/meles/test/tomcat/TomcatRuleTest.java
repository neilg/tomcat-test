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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.catalina.LifecycleState;
import org.apache.catalina.startup.Tomcat;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class TomcatRuleTest {

    @Test
    public void canBindToFreePort() throws Throwable {
        final TomcatRule ruleUnderTest = new TomcatRule(0);

        final Statement base = new Statement() {
            @Override
            public void evaluate() throws Throwable {
                final int localPort = ruleUnderTest.getLocalPort();
                assertThat(localPort, is(not(0)));
            }
        };

        final Statement statement = ruleUnderTest.apply(base, Description.EMPTY);
        statement.evaluate();
    }

    @Test
    public void bindsToSpecifiedPort() throws Throwable {
        final int portToBind = 7654;
        final TomcatRule ruleUnderTest = new TomcatRule(portToBind);

        final Statement base = new Statement() {
            @Override
            public void evaluate() throws Throwable {
                final int localPort = ruleUnderTest.getLocalPort();
                assertThat(localPort, is(portToBind));
            }
        };

        final Statement statement = ruleUnderTest.apply(base, Description.EMPTY);
        statement.evaluate();
    }

    @Test
    public void tomcatIsDestroyedAfterEvaluation() throws Throwable {
        final TomcatRule ruleUnderTest = new TomcatRule(0);

        final Tomcat[] tomcats = new Tomcat[1];

        final Statement base = new Statement() {
            @Override
            public void evaluate() throws Throwable {
                tomcats[0] = ruleUnderTest.getTomcat();
            }
        };
        final Statement statement = ruleUnderTest.apply(base, Description.EMPTY);
        statement.evaluate();

        assertThat(tomcats[0].getServer().getState(), is(LifecycleState.DESTROYED));
    }

    @Test
    public void canRunWebappFromFilesystem() throws Throwable {

        final TomcatRule ruleUnderTest = new TomcatRule(0);
        ruleUnderTest.addWebapp(getClass().getResource("/a_webapp").getFile(), "/");

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
