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

import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;

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

    private List<Webapp> webapps = new ArrayList<>();

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

    private Tomcat startTomcat() throws LifecycleException, ServletException {
        final Tomcat startingTomcat = new Tomcat();
        startingTomcat.setPort(configuredPort);
        for (final Webapp webapp : webapps) {
            startingTomcat.addWebapp(webapp.path, webapp.root);
        }
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

    public TomcatRule addWebapp(final String webappRoot, final String contextPath) {
        webapps.add(new Webapp(webappRoot, contextPath));
        return this;
    }

    private class Webapp {

        final String root;
        final String path;

        private Webapp(String root, String path) {
            this.root = root;
            this.path = path;
        }
    }
}
