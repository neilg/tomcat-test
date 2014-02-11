package io.meles.test.tomcat;

import org.apache.catalina.startup.Tomcat;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class TomcatRule implements TestRule {

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                final Tomcat tomcat = new Tomcat();
                tomcat.getEngine();
                tomcat.start();
                try {
                    base.evaluate();
                } finally {
                    tomcat.stop();
                }
            }
        };
    }
}
