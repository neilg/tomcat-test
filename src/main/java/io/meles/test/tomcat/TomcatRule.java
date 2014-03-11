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

import javax.servlet.ServletException;

import io.meles.test.tomcat.config.TomcatBuilder;
import org.apache.catalina.Globals;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class TomcatRule implements TestRule {

    private final TomcatBuilder tomcatBuilder;

    private Tomcat tomcat;

    public TomcatRule(final TomcatBuilder tomcatBuilder) {
        this.tomcatBuilder = tomcatBuilder;
    }

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                tomcat = startTomcat(description);
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

    private Tomcat startTomcat(final Description description) throws LifecycleException, ServletException {

        final Tomcat startingTomcat = tomcatBuilder.build();
        final String displayName = description.getDisplayName().replaceAll("[/\\\\ ]", "_");
        System.setProperty(Globals.CATALINA_BASE_PROP, System.getProperty("java.io.tmpdir") + "/tomcat-" + displayName + "-" + System.currentTimeMillis());

        startingTomcat.start();
        final Connector connector = startingTomcat.getConnector();
        if (connector.getState() != LifecycleState.STARTED) {
            throw new RuntimeException("failed to start tomcat connector on port " + tomcatBuilder.getPort());
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
