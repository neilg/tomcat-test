package io.meles.test.tomcat;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class TomcatRule implements TestRule {

    private final int configuredPort;

    private Tomcat tomcat;

    public TomcatRule(int port) {
        this.configuredPort = port;
    }

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                tomcat = startTomcat();
                try {
                    base.evaluate();
                } finally {
                    cleanup();
                }
            }

        };
    }

    public int getLocalPort() {
        return tomcat.getConnector().getLocalPort();
    }

    private Tomcat startTomcat() throws LifecycleException {
        final Tomcat startingTomcat = new Tomcat();
        startingTomcat.setPort(configuredPort);
        startingTomcat.getEngine();
        startingTomcat.start();
        final Connector connector = startingTomcat.getConnector();
        if (connector.getState() != LifecycleState.STARTED) {
            throw new RuntimeException("failed to start tomcat connector on port " + configuredPort);
        }
        return startingTomcat;
    }

    Tomcat getTomcat() {
        return tomcat;
    }

    private void cleanup() throws LifecycleException {
        final Tomcat stoppingTomcat = tomcat;
        tomcat = null;
        if (stoppingTomcat != null) {
            try {
                stoppingTomcat.stop();
            } finally {
                stoppingTomcat.destroy();
            }
        }
    }
}
