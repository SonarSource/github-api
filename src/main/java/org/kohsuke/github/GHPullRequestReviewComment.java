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

import javax.annotation.CheckForNull;

import static org.kohsuke.github.internal.Previews.SQUIRREL_GIRL;

/**
 * Review comment to the pull request
 *
 * @author Julien Henry
 * @see GHPullRequest#listReviewComments() GHPullRequest#listReviewComments()
 * @see GHPullRequest#createReviewComment(String, String, String, int) GHPullRequest#createReviewComment(String, String,
 *      String, int)
 */
public class GHPullRequestReviewComment extends GHObject implements Reactable {
    GHPullRequest owner;

    private String body;
    private GHUser user;
    private String path;
    private String html_url;
    private int position = -1;
    private int original_position = -1;
    private long in_reply_to_id = -1L;

    /**
     * Draft gh pull request review comment.
     *
     * @param body
     *            the body
     * @param path
     *            the path
     * @param position
     *            the position
     * @return the gh pull request review comment
     * @deprecated You should be using {@link GHPullRequestReviewBuilder#comment(String, String, int)}
     */
    @Deprecated
    public static GHPullRequestReviewComment draft(String body, String path, int position) {
        GHPullRequestReviewComment result = new GHPullRequestReviewComment();
        result.body = body;
        result.path = path;
        result.position = position;
        return result;
    }

    GHPullRequestReviewComment wrapUp(GHPullRequest owner) {
        this.owner = owner;
        return this;
    }

    /**
     * Gets the pull request to which this review comment is associated.
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
     * Gets the user who posted this comment.
     *
     * @return the user
     * @throws IOException
     *             the io exception
     */
    public GHUser getUser() throws IOException {
        return owner.root.getUser(user.getLogin());
    }

    /**
     * Gets path.
     *
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * Gets position.
     *
     * @return the position
     */
    @CheckForNull
    public int getPosition() {
        return position;
    }

    /**
     * Gets original position.
     *
     * @return the original position
     */
    public int getOriginalPosition() {
        return original_position;
    }

    /**
     * Gets in reply to id.
     *
     * @return the in reply to id
     */
    @CheckForNull
    public long getInReplyToId() {
        return in_reply_to_id;
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
        return getApiRoute(false);
    }

    /**
     * Gets api route.
     *
     * @param includePullNumber
     *            if true, includes the owning pull request's number in the route.
     *
     * @return the api route
     */
    protected String getApiRoute(boolean includePullNumber) {
        return "/repos/" + owner.getRepository().getFullName() + "/pulls"
                + (includePullNumber ? "/" + owner.getNumber() : "") + "/comments/" + getId();
    }

    /**
     * Updates the comment.
     *
     * @param body
     *            the body
     * @throws IOException
     *             the io exception
     */
    public void update(String body) throws IOException {
        owner.root.createRequest().method("PATCH").with("body", body).withUrlPath(getApiRoute()).fetchInto(this);
        this.body = body;
    }

    /**
     * Deletes this review comment.
     *
     * @throws IOException
     *             the io exception
     */
    public void delete() throws IOException {
        owner.root.createRequest().method("DELETE").withUrlPath(getApiRoute()).send();
    }

    /**
     * Create a new comment that replies to this comment.
     *
     * @param body
     *            the body
     * @return the gh pull request review comment
     * @throws IOException
     *             the io exception
     */
    public GHPullRequestReviewComment reply(String body) throws IOException {
        return owner.root.createRequest()
                .method("POST")
                .with("body", body)
                .withUrlPath(getApiRoute(true) + "/replies")
                .fetch(GHPullRequestReviewComment.class)
                .wrapUp(owner);
    }

    @Preview(SQUIRREL_GIRL)
    @Deprecated
    public GHReaction createReaction(ReactionContent content) throws IOException {
        return owner.root.createRequest()
                .method("POST")
                .withPreview(SQUIRREL_GIRL)
                .with("content", content.getContent())
                .withUrlPath(getApiRoute() + "/reactions")
                .fetch(GHReaction.class)
                .wrap(owner.root);
    }

    @Preview(SQUIRREL_GIRL)
    @Deprecated
    public PagedIterable<GHReaction> listReactions() {
        return owner.root.createRequest()
                .withPreview(SQUIRREL_GIRL)
                .withUrlPath(getApiRoute() + "/reactions")
                .toIterable(GHReaction[].class, item -> item.wrap(owner.root));
    }
}
