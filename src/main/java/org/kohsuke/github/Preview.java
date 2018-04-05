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

import org.kohsuke.github.internal.Previews;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Indicates that the method/class/etc marked maps to GitHub API in the preview period.
 * <p>
 * These APIs are subject to change and not a part of the backward compatibility commitment. Always used in conjunction
 * with 'deprecated' to raise awareness to clients. In addition, it's advised to update the targets documentation to
 * signify that the deprecation is required until preview feature being used is promoted to stable.
 *
 * @author Kohsuke Kawaguchi
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Preview {

    /**
     * An optional field defining what API media types must be set inorder to support the usage of this annotations
     * target.
     * <p>
     * This value must be set using the existing constants defined in {@link Previews}
     *
     * @return The API preview media type.
     */
    public Previews[] value();

}
