package io.meles.test.tomcat;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.net.HttpURLConnection;
import java.net.URL;

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
                final HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:" + localPort).openConnection();
                connection.getResponseCode();
            }
        };

        final Statement statement = ruleUnderTest.apply(base, Description.EMPTY);
        statement.evaluate();
    }
}
