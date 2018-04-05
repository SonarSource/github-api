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

import java.io.IOException;

/**
 * Creates a new deployment status.
 *
 * @see GHDeployment#createStatus(GHDeploymentState) GHDeployment#createStatus(GHDeploymentState)
 */
public class GHDeploymentStatusBuilder {
    private final Requester builder;
    private GHRepository repo;
    private long deploymentId;

    /**
     * Instantiates a new Gh deployment status builder.
     *
     * @param repo
     *            the repo
     * @param deploymentId
     *            the deployment id
     * @param state
     *            the state
     *
     * @deprecated Use {@link GHDeployment#createStatus(GHDeploymentState)}
     */
    @Deprecated
    public GHDeploymentStatusBuilder(GHRepository repo, int deploymentId, GHDeploymentState state) {
        this(repo, (long) deploymentId, state);
    }

    GHDeploymentStatusBuilder(GHRepository repo, long deploymentId, GHDeploymentState state) {
        this.repo = repo;
        this.deploymentId = deploymentId;
        this.builder = repo.root.createRequest()
                .withPreview(Previews.ANT_MAN)
                .withPreview(Previews.FLASH)
                .method("POST");

        this.builder.with("state", state);
    }

    /**
     * Add an inactive status to all prior non-transient, non-production environment deployments with the same
     * repository and environment name as the created status's deployment.
     *
     * @deprecated until preview feature has graduated to stable
     *
     * @param autoInactive
     *            Add inactive status flag
     *
     * @return the gh deployment status builder
     */
    @Deprecated
    @Preview({ Previews.ANT_MAN, Previews.FLASH })
    public GHDeploymentStatusBuilder autoInactive(boolean autoInactive) {
        this.builder.with("auto_inactive", autoInactive);
        return this;
    }

    /**
     * Description gh deployment status builder.
     *
     * @param description
     *            the description
     *
     * @return the gh deployment status builder
     */
    public GHDeploymentStatusBuilder description(String description) {
        this.builder.with("description", description);
        return this;
    }

    /**
     * Name for the target deployment environment, which can be changed when setting a deploy status.
     *
     * @deprecated until preview feature has graduated to stable
     *
     * @param environment
     *            the environment name
     *
     * @return the gh deployment status builder
     */
    @Deprecated
    @Preview(Previews.FLASH)
    public GHDeploymentStatusBuilder environment(String environment) {
        this.builder.with("environment", environment);
        return this;
    }

    /**
     * The URL for accessing the environment
     *
     * @deprecated until preview feature has graduated to stable
     *
     * @param environmentUrl
     *            the environment url
     *
     * @return the gh deployment status builder
     */
    @Deprecated
    @Preview(Previews.ANT_MAN)
    public GHDeploymentStatusBuilder environmentUrl(String environmentUrl) {
        this.builder.with("environment_url", environmentUrl);
        return this;
    }

    /**
     * The full URL of the deployment's output.
     * <p>
     * This method replaces {@link #targetUrl(String) targetUrl}.
     *
     * @deprecated until preview feature has graduated to stable
     *
     * @param logUrl
     *            the deployment output url
     *
     * @return the gh deployment status builder
     */
    @Deprecated
    @Preview(Previews.ANT_MAN)
    public GHDeploymentStatusBuilder logUrl(String logUrl) {
        this.builder.with("log_url", logUrl);
        return this;
    }

    /**
     * Target url gh deployment status builder.
     *
     * @deprecated Target url is deprecated in favor of {@link #logUrl(String) logUrl}
     *
     * @param targetUrl
     *            the target url
     *
     * @return the gh deployment status builder
     */
    @Deprecated
    public GHDeploymentStatusBuilder targetUrl(String targetUrl) {
        this.builder.with("target_url", targetUrl);
        return this;
    }

    /**
     * Create gh deployment status.
     *
     * @return the gh deployment status
     *
     * @throws IOException
     *             the io exception
     */
    public GHDeploymentStatus create() throws IOException {
        return builder.withUrlPath(repo.getApiTailUrl("deployments/" + deploymentId + "/statuses"))
                .fetch(GHDeploymentStatus.class)
                .wrap(repo);
    }
}
