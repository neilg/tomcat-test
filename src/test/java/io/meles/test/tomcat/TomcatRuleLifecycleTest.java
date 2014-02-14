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
import static org.junit.Assert.assertThat;

import org.apache.catalina.LifecycleState;
import org.apache.catalina.startup.Tomcat;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class TomcatRuleLifecycleTest {

    @Test
    public void tomcatIsDestroyedAfterEvaluation() throws Throwable {
        final TomcatRule ruleUnderTest = new TomcatRule(0);

        final Tomcat[] tomcat = new Tomcat[1];

        final Statement base = new Statement() {
            @Override
            public void evaluate() throws Throwable {
                tomcat[0] = ruleUnderTest.getTomcat();
            }
        };
        final Statement statement = ruleUnderTest.apply(base, Description.EMPTY);
        statement.evaluate();

        assertThat(tomcat[0].getServer().getState(), is(LifecycleState.DESTROYED));
    }

}
