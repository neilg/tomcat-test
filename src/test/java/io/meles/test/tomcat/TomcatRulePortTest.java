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

import static io.meles.test.tomcat.config.TomcatBuilder.withTomcat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.apache.catalina.Globals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class TomcatRulePortTest {

    @Before
    public void catalinaBase() {
        System.setProperty(Globals.CATALINA_BASE_PROP, System.getProperty("java.io.tmpdir") + "/tomcat-" + getClass().getName() + "-" + System.currentTimeMillis());
    }

    @Test
    public void canBindToFreePort() throws Throwable {
        final TomcatRule ruleUnderTest = new TomcatRule(withTomcat().onFreePort());

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
        final TomcatRule ruleUnderTest = new TomcatRule(withTomcat().onPort(portToBind));

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

}
