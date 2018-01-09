/*
 * GitHub API for Java
 * Copyright (C) 2009-2018 SonarSource SA
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

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.io.IOException;
import java.net.URL;

@SuppressFBWarnings(value = {"UWF_UNWRITTEN_FIELD"}, justification = "JSON API")
public abstract class GHPullRequestReviewAbstract extends GHObject {
    protected GHPullRequest owner;

    protected String body;
    private GHUser user;
    private String commit_id;

    public abstract GHPullRequestReviewState getState();

    /**
     * Gets the pull request to which this review is associated.
     */
    public GHPullRequest getParent() {
        return owner;
    }

    /**
     * The comment itself.
     */
    public String getBody() {
        return body;
    }

    /**
     * Gets the user who posted this review.
     */
    public GHUser getUser() throws IOException {
        return owner.root.getUser(user.getLogin());
    }

    public String getCommitId() {
        return commit_id;
    }

    @Override
    public URL getHtmlUrl() {
        return null;
    }

    String getApiRoute() {
        return owner.getApiRoute() + "/reviews/" + id;
    }

    /**
     * Deletes this review.
     */
    public void delete() throws IOException {
        new Requester(owner.root).method("DELETE")
                .to(getApiRoute());
    }

    /**
     * Obtains all the review comments associated with this pull request review.
     */
    public PagedIterable<GHPullRequestReviewComment> listReviewComments() {
        return new PagedIterable<GHPullRequestReviewComment>() {
            public PagedIterator<GHPullRequestReviewComment> _iterator(int pageSize) {
                return new PagedIterator<GHPullRequestReviewComment>(
                        owner.root.retrieve()
                                .asIterator(getApiRoute() + "/comments",
                                        GHPullRequestReviewComment[].class, pageSize)) {
                    protected void wrapUp(GHPullRequestReviewComment[] page) {
                        for (GHPullRequestReviewComment c : page)
                            c.wrapUp(owner);
                    }
                };
            }
        };
    }
}
