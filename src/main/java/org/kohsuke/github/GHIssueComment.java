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

import static org.kohsuke.github.internal.Previews.SQUIRREL_GIRL;

/**
 * Comment to the issue
 *
 * @author Kohsuke Kawaguchi
 * @see GHIssue#comment(String) GHIssue#comment(String)
 * @see GHIssue#listComments() GHIssue#listComments()
 */
public class GHIssueComment extends GHObject implements Reactable {
    GHIssue owner;

    private String body, gravatar_id, html_url, author_association;
    private GHUser user; // not fully populated. beware.

    GHIssueComment wrapUp(GHIssue owner) {
        this.owner = owner;
        return this;
    }

    /**
     * Gets the issue to which this comment is associated.
     *
     * @return the parent
     */
    public GHIssue getParent() {
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
     * Gets the ID of the user who posted this comment.
     *
     * @return the user name
     */
    @Deprecated
    public String getUserName() {
        return user.getLogin();
    }

    /**
     * Gets the user who posted this comment.
     *
     * @return the user
     * @throws IOException
     *             the io exception
     */
    public GHUser getUser() throws IOException {
        return owner == null || owner.root.isOffline() ? user : owner.root.getUser(user.getLogin());
    }

    @Override
    public URL getHtmlUrl() {
        return GitHubClient.parseURL(html_url);
    }

    /**
     * Gets author association.
     *
     * @return the author association
     */
    public GHCommentAuthorAssociation getAuthorAssociation() {
        return GHCommentAuthorAssociation.valueOf(author_association);
    }

    /**
     * Updates the body of the issue comment.
     *
     * @param body
     *            the body
     * @throws IOException
     *             the io exception
     */
    public void update(String body) throws IOException {
        owner.root.createRequest()
                .method("PATCH")
                .with("body", body)
                .withUrlPath(getApiRoute())
                .fetch(GHIssueComment.class);
        this.body = body;
    }

    /**
     * Deletes this issue comment.
     *
     * @throws IOException
     *             the io exception
     */
    public void delete() throws IOException {
        owner.root.createRequest().method("DELETE").withUrlPath(getApiRoute()).send();
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

    private String getApiRoute() {
        return "/repos/" + owner.getRepository().getOwnerName() + "/" + owner.getRepository().getName()
                + "/issues/comments/" + getId();
    }
}
