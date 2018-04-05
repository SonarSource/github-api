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
import java.util.Date;

/**
 * Represents your subscribing to a repository / conversation thread..
 *
 * @author Kohsuke Kawaguchi
 * @see GHRepository#getSubscription() GHRepository#getSubscription()
 * @see GHThread#getSubscription() GHThread#getSubscription()
 */
public class GHSubscription extends GitHubInteractiveObject {
    private String created_at, url, repository_url, reason;
    private boolean subscribed, ignored;

    private GHRepository repo;

    /**
     * Gets created at.
     *
     * @return the created at
     */
    public Date getCreatedAt() {
        return GitHubClient.parseDate(created_at);
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets repository url.
     *
     * @return the repository url
     */
    public String getRepositoryUrl() {
        return repository_url;
    }

    /**
     * Gets reason.
     *
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * Is subscribed boolean.
     *
     * @return the boolean
     */
    public boolean isSubscribed() {
        return subscribed;
    }

    /**
     * Is ignored boolean.
     *
     * @return the boolean
     */
    public boolean isIgnored() {
        return ignored;
    }

    /**
     * Gets repository.
     *
     * @return the repository
     */
    public GHRepository getRepository() {
        return repo;
    }

    /**
     * Removes this subscription.
     *
     * @throws IOException
     *             the io exception
     */
    public void delete() throws IOException {
        root.createRequest().method("DELETE").withUrlPath(repo.getApiTailUrl("subscription")).send();
    }

    GHSubscription wrapUp(GHRepository repo) {
        this.repo = repo;
        return wrapUp(repo.root);
    }

    GHSubscription wrapUp(GitHub root) {
        this.root = root;
        return this;
    }
}
