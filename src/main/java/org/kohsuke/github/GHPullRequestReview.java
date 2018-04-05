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

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.io.IOException;
import java.net.URL;
import java.util.Date;

import javax.annotation.CheckForNull;

/**
 * Review to a pull request.
 *
 * @see GHPullRequest#listReviews() GHPullRequest#listReviews()
 * @see GHPullRequestReviewBuilder
 */
@SuppressFBWarnings(value = { "UWF_UNWRITTEN_FIELD" }, justification = "JSON API")
public class GHPullRequestReview extends GHObject {
    GHPullRequest owner;

    private String body;
    private GHUser user;
    private String commit_id;
    private GHPullRequestReviewState state;
    private String submitted_at;
    private String html_url;

    GHPullRequestReview wrapUp(GHPullRequest owner) {
        this.owner = owner;
        return this;
    }

    /**
     * Gets the pull request to which this review is associated.
     *
     * @return the parent
     */
    public GHPullRequest getParent() {
        return owner;
    }

    /**
     * The comment itself.
     *
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * Gets the user who posted this review.
     *
     * @return the user
     * @throws IOException
     *             the io exception
     */
    public GHUser getUser() throws IOException {
        return owner.root.getUser(user.getLogin());
    }

    /**
     * Gets commit id.
     *
     * @return the commit id
     */
    public String getCommitId() {
        return commit_id;
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    @CheckForNull
    public GHPullRequestReviewState getState() {
        return state;
    }

    @Override
    public URL getHtmlUrl() {
        return GitHubClient.parseURL(html_url);
    }

    /**
     * Gets api route.
     *
     * @return the api route
     */
    protected String getApiRoute() {
        return owner.getApiRoute() + "/reviews/" + getId();
    }

    /**
     * When was this resource created?
     *
     * @return the submitted at
     * @throws IOException
     *             the io exception
     */
    public Date getSubmittedAt() throws IOException {
        return GitHubClient.parseDate(submitted_at);
    }

    /**
     * Since this method does not exist, we forward this value.
     */
    @Override
    public Date getCreatedAt() throws IOException {
        return getSubmittedAt();
    }

    /**
     * Submit.
     *
     * @param body
     *            the body
     * @param state
     *            the state
     * @throws IOException
     *             the io exception
     * @deprecated Former preview method that changed when it got public. Left here for backward compatibility. Use
     *             {@link #submit(String, GHPullRequestReviewEvent)}
     */
    @Deprecated
    public void submit(String body, GHPullRequestReviewState state) throws IOException {
        submit(body, state.toEvent());
    }

    /**
     * Updates the comment.
     *
     * @param body
     *            the body
     * @param event
     *            the event
     * @throws IOException
     *             the io exception
     */
    public void submit(String body, GHPullRequestReviewEvent event) throws IOException {
        owner.root.createRequest()
                .method("POST")
                .with("body", body)
                .with("event", event.action())
                .withUrlPath(getApiRoute() + "/events")
                .fetchInto(this);
        this.body = body;
        this.state = event.toState();
    }

    /**
     * Deletes this review.
     *
     * @throws IOException
     *             the io exception
     */
    public void delete() throws IOException {
        owner.root.createRequest().method("DELETE").withUrlPath(getApiRoute()).send();
    }

    /**
     * Dismisses this review.
     *
     * @param message
     *            the message
     * @throws IOException
     *             the io exception
     */
    public void dismiss(String message) throws IOException {
        owner.root.createRequest()
                .method("PUT")
                .with("message", message)
                .withUrlPath(getApiRoute() + "/dismissals")
                .send();
        state = GHPullRequestReviewState.DISMISSED;
    }

    /**
     * Obtains all the review comments associated with this pull request review.
     *
     * @return the paged iterable
     * @throws IOException
     *             the io exception
     */
    public PagedIterable<GHPullRequestReviewComment> listReviewComments() throws IOException {
        return owner.root.createRequest()
                .withUrlPath(getApiRoute() + "/comments")
                .toIterable(GHPullRequestReviewComment[].class, item -> item.wrapUp(owner));
    }
}
