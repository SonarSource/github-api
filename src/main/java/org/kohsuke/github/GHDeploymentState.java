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

/**
 * Represents the state of deployment
 */
public enum GHDeploymentState {
    PENDING,
    SUCCESS,
    ERROR,
    FAILURE,

    /**
     * The state of the deployment currently reflects it's in progress.
     *
     * @deprecated until preview feature has graduated to stable
     */
    @Deprecated
    @Preview(Previews.FLASH)
    IN_PROGRESS,

    /**
     * The state of the deployment currently reflects it's queued up for processing.
     *
     * @deprecated until preview feature has graduated to stable
     */
    @Deprecated
    @Preview(Previews.FLASH)
    QUEUED,

    /**
     * The state of the deployment currently reflects it's no longer active.
     *
     * @deprecated until preview feature has graduated to stable
     */
    @Deprecated
    @Preview(Previews.ANT_MAN)
    INACTIVE
}
