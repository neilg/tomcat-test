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

package io.meles.test.tomcat.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;

import org.apache.catalina.startup.Tomcat;

public class TomcatBuilder {

    public static final int DEFAULT_HTTP_PORT = 8080;

    private final int port;
    private final List<WebappBuilder> webapps;

    public static TomcatBuilder builder() {
        return new TomcatBuilder(DEFAULT_HTTP_PORT, Collections.<WebappBuilder>emptyList());
    }

    public static TomcatBuilder withTomcat() {
        return builder();
    }

    private TomcatBuilder(final int port, final List<WebappBuilder> webapps) {
        this.port = port;
        this.webapps = webapps;
    }

    public TomcatBuilder onPort(final int port) {
        return new TomcatBuilder(port, webapps);
    }

    public TomcatBuilder run(final WebappBuilder webapp) {
        final List<WebappBuilder> newWebapps = new ArrayList<>(webapps);
        newWebapps.add(webapp);
        return new TomcatBuilder(port, newWebapps);
    }

    public Tomcat build() throws ServletException {
        final Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);
        for (final WebappBuilder webapp : webapps) {
            tomcat.addWebapp(webapp.getContextPath(), webapp.getBase());
        }
        tomcat.getEngine();
        return tomcat;
    }
}
