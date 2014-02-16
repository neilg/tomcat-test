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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class WebappBuilderTest {

    @Test
    public void shouldUseWebappBase() {
        final WebappBuilder webappBuilderUnderTest = WebappBuilder.webapp("the/location");

        assertThat(webappBuilderUnderTest.build(), hasProperty("base", equalTo("the/location")));
    }

    @Test
    public void shouldUseContextPath() {
        final WebappBuilder webappBuilderUnderTest = WebappBuilder.webapp("the/location").at("/the/path");

        assertThat(webappBuilderUnderTest.build(), hasProperty("contextPath", equalTo("/the/path")));
    }
}
