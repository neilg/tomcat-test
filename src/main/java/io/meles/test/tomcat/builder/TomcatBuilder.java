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

package io.meles.test.tomcat.builder;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;

import org.apache.catalina.startup.Tomcat;

public class TomcatBuilder {

    public static final int DEFAULT_HTTP_PORT = 8080;

    private int port = DEFAULT_HTTP_PORT;

    private List<Webapp> webapps = new ArrayList<>();

    public static TomcatBuilder builder() {
        return new TomcatBuilder();
    }

    public static TomcatBuilder withTomcat() {
        return builder();
    }

    public TomcatBuilder onPort(final int port) {
        this.port = port;
        return this;
    }

    public TomcatBuilder run(final WebappBuilder webappBuilder) {
        return run(webappBuilder.build());
    }

    public TomcatBuilder run(final Webapp webapp) {
        webapps.add(webapp);
        return this;
    }

    public Tomcat build() throws ServletException {
        final Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);
        for (final Webapp webapp : webapps) {
            tomcat.addWebapp(webapp.getContextPath(), webapp.getBase());
        }
        tomcat.getEngine();
        return tomcat;
    }
}
