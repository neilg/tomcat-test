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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import javax.servlet.ServletException;

import org.apache.catalina.Globals;
import org.apache.catalina.startup.Tomcat;
import org.junit.Before;
import org.junit.Test;

public class TomcatBuilderTest {

    @Before
    public void catalinaBase() {
        System.setProperty(Globals.CATALINA_BASE_PROP, System.getProperty("java.io.tmpdir") + "/tomcat-" + getClass().getName() + "-" + System.currentTimeMillis());
    }

    @Test
    public void shouldUsePort() throws ServletException {
        final Tomcat tomcat = TomcatBuilder
                .withTomcat()
                .onPort(9876)
                .build();
        assertThat(tomcat.getConnector().getPort(), is(9876));
    }
}
