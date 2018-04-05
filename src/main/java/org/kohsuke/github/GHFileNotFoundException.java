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

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

/**
 * Request/responce contains useful metadata. Custom exception allows store info for next diagnostics.
 *
 * @author Kanstantsin Shautsou
 */
public class GHFileNotFoundException extends FileNotFoundException {
    protected Map<String, List<String>> responseHeaderFields;

    /**
     * Instantiates a new Gh file not found exception.
     */
    public GHFileNotFoundException() {
    }

    /**
     * Instantiates a new Gh file not found exception.
     *
     * @param message
     *            the message
     */
    public GHFileNotFoundException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Gh file not found exception.
     *
     * @param message
     *            the message
     * @param cause
     *            the cause
     */
    public GHFileNotFoundException(String message, Throwable cause) {
        super(message);
        this.initCause(cause);
    }

    /**
     * Gets response header fields.
     *
     * @return the response header fields
     */
    @CheckForNull
    public Map<String, List<String>> getResponseHeaderFields() {
        return responseHeaderFields;
    }

    GHFileNotFoundException withResponseHeaderFields(@Nonnull Map<String, List<String>> headerFields) {
        this.responseHeaderFields = headerFields;
        return this;
    }
}
