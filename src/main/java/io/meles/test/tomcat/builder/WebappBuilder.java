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

public class WebappBuilder {

    private final String contextPath;
    private final String base;

    public static WebappBuilder webapp(final String base) {
        return new WebappBuilder(null, base);
    }

    private WebappBuilder(final String contextPath, final String base) {
        this.contextPath = contextPath;
        this.base = base;
    }

    public WebappBuilder at(final String contextPath) {
        return new WebappBuilder(contextPath, base);
    }

    public String getBase() {
        return base;
    }

    public String getContextPath() {
        return contextPath;
    }

    public Webapp build() {
        return new Webapp(contextPath, base);
    }
}
