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

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

/**
 * Request/responce contains useful metadata. Custom exception allows store info for next diagnostics.
 *
 * @author Kanstantsin Shautsou
 */
public class GHIOException extends IOException {
    protected Map<String, List<String>> responseHeaderFields;

    /**
     * Instantiates a new Ghio exception.
     */
    public GHIOException() {
    }

    /**
     * Instantiates a new Ghio exception.
     *
     * @param message
     *            the message
     */
    public GHIOException(String message) {
        super(message);
    }

    /**
     * Constructs a {@code GHIOException} with the specified detail message and cause.
     *
     * @param message
     *            The detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     *
     * @param cause
     *            The cause (which is saved for later retrieval by the {@link #getCause()} method). (A null value is
     *            permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public GHIOException(String message, Throwable cause) {
        super(message, cause);
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

    GHIOException withResponseHeaderFields(@Nonnull Map<String, List<String>> headerFields) {
        this.responseHeaderFields = headerFields;
        return this;
    }
}
