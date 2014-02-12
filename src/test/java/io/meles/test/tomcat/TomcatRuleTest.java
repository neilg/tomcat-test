package io.meles.test.tomcat;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

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
}
