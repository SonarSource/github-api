/*
 * GitHub API for Java
 * Copyright (C) 2009-2017 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.kohsuke.github;

/**
 * The type GHException.
 *
 * @author Kohsuke Kawaguchi
 */
public class GHException extends RuntimeException {
    /**
     * Instantiates a new Gh exception.
     *
     * @param message
     *            the message
     */
    public GHException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Gh exception.
     *
     * @param message
     *            the message
     * @param cause
     *            the cause
     */
    public GHException(String message, Throwable cause) {
        super(message, cause);
    }
}
