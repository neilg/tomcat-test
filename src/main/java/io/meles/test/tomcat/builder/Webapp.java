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

public class Webapp {

    private final String contextPath;
    private final String base;

    public Webapp(String contextPath, String base) {
        this.contextPath = contextPath;
        this.base = base;
    }

    public String getContextPath() {
        return contextPath;
    }

    public String getBase() {
        return base;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Webapp{");
        sb
                .append("contextPath='")
                .append(contextPath)
                .append('\'');

        sb
                .append(", ");

        sb
                .append("base='")
                .append(base)
                .append('\'');

        sb.append('}');
        return sb.toString();
    }
}
