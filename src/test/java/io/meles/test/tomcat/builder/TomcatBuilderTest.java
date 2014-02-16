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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import javax.servlet.ServletException;

import org.apache.catalina.startup.Tomcat;
import org.junit.Test;

public class TomcatBuilderTest {

    @Test
    public void shouldUsePort() throws ServletException {
        final TomcatBuilder builderUnderTest = new TomcatBuilder();
        builderUnderTest.onPort(9876);
        final Tomcat tomcat = builderUnderTest.build();
        assertThat(tomcat.getConnector().getPort(), is(9876));
    }
}
