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

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.IOException;

/**
 * The type GHDeployKey.
 */
public class GHDeployKey {

    protected String url, key, title;
    protected boolean verified;
    protected long id;
    private GHRepository owner;

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Gets key.
     *
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
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
     * Is verified boolean.
     *
     * @return the boolean
     */
    public boolean isVerified() {
        return verified;
    }

    /**
     * Wrap gh deploy key.
     *
     * @param repo
     *            the repo
     * @return the gh deploy key
     */
    public GHDeployKey wrap(GHRepository repo) {
        this.owner = repo;
        return this;
    }

    public String toString() {
        return new ToStringBuilder(this).append("title", title).append("id", id).append("key", key).toString();
    }

    /**
     * Delete.
     *
     * @throws IOException
     *             the io exception
     */
    public void delete() throws IOException {
        owner.root.createRequest()
                .method("DELETE")
                .withUrlPath(String.format("/repos/%s/%s/keys/%d", owner.getOwnerName(), owner.getName(), id))
                .send();
    }
}
