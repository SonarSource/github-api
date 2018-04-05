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

import java.net.URL;
import java.util.Locale;

/**
 * The type GHDeploymentStatus.
 */
public class GHDeploymentStatus extends GHObject {
    private GHRepository owner;
    protected GHUser creator;
    protected String state;
    protected String description;
    protected String target_url;
    protected String log_url;
    protected String deployment_url;
    protected String repository_url;
    protected String environment_url;

    /**
     * Wrap gh deployment status.
     *
     * @param owner
     *            the owner
     *
     * @return the gh deployment status
     */
    public GHDeploymentStatus wrap(GHRepository owner) {
        this.owner = owner;
        this.root = owner.root;
        if (creator != null)
            creator.wrapUp(root);
        return this;
    }

    /**
     * Gets target url.
     *
     * @deprecated Target url is deprecated in favor of {@link #getLogUrl() getLogUrl}
     *
     * @return the target url
     */
    @Deprecated
    public URL getTargetUrl() {
        return GitHubClient.parseURL(target_url);
    }

    /**
     * Gets target url.
     * <p>
     * This method replaces {@link #getTargetUrl() getTargetUrl}}.
     *
     * @deprecated until preview feature has graduated to stable
     *
     * @return the target url
     */
    @Deprecated
    @Preview(Previews.ANT_MAN)
    public URL getLogUrl() {
        return GitHubClient.parseURL(log_url);
    }

    /**
     * Gets deployment url.
     *
     * @return the deployment url
     */
    public URL getDeploymentUrl() {
        return GitHubClient.parseURL(deployment_url);
    }

    /**
     * Gets deployment environment url.
     *
     * @deprecated until preview feature has graduated to stable
     *
     * @return the deployment environment url
     */
    @Deprecated
    @Preview(Previews.ANT_MAN)
    public URL getEnvironmentUrl() {
        return GitHubClient.parseURL(environment_url);
    }

    /**
     * Gets repository url.
     *
     * @return the repository url
     */
    public URL getRepositoryUrl() {
        return GitHubClient.parseURL(repository_url);
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    public GHDeploymentState getState() {
        return GHDeploymentState.valueOf(state.toUpperCase(Locale.ENGLISH));
    }

    /**
     * @deprecated This object has no HTML URL.
     */
    @Override
    public URL getHtmlUrl() {
        return null;
    }
}
