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
import java.net.URL;

/**
 * Represents a status of a commit.
 *
 * @author Kohsuke Kawaguchi
 * @see GHRepository#getLastCommitStatus(String) GHRepository#getLastCommitStatus(String)
 * @see GHCommit#getLastStatus() GHCommit#getLastStatus()
 * @see GHRepository#createCommitStatus(String, GHCommitState, String, String) GHRepository#createCommitStatus(String,
 *      GHCommitState, String, String)
 */
public class GHCommitStatus extends GHObject {
    String state;
    String target_url, description;
    String context;
    GHUser creator;

    GHCommitStatus wrapUp(GitHub root) {
        if (creator != null)
            creator.wrapUp(root);
        this.root = root;
        return this;
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    public GHCommitState getState() {
        for (GHCommitState s : GHCommitState.values()) {
            if (s.name().equalsIgnoreCase(state))
                return s;
        }
        throw new IllegalStateException("Unexpected state: " + state);
    }

    /**
     * The URL that this status is linked to.
     * <p>
     * This is the URL specified when creating a commit status.
     *
     * @return the target url
     */
    public String getTargetUrl() {
        return target_url;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets creator.
     *
     * @return the creator
     * @throws IOException
     *             the io exception
     */
    public GHUser getCreator() throws IOException {
        return root.intern(creator);
    }

    /**
     * Gets context.
     *
     * @return the context
     */
    public String getContext() {
        return context;
    }

    /**
     * @deprecated This object has no HTML URL.
     */
    @Override
    public URL getHtmlUrl() {
        return null;
    }
}
