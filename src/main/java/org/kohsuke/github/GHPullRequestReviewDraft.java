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

import java.io.IOException;

/**
 * Draft of a Pull Request review.
 *
 * @see GHPullRequest#newDraftReview(String, String, GHPullRequestReviewComment...)
 */
public class GHPullRequestReviewDraft extends GHPullRequestReviewAbstract {

    GHPullRequestReviewDraft wrapUp(GHPullRequest owner) {
        this.owner = owner;
        return this;
    }

    @Override
    public GHPullRequestReviewState getState() {
        return GHPullRequestReviewState.PENDING;
    }

    public void submit(String body, GHPullRequestReviewEvent event) throws IOException {
        new Requester(owner.root).method("POST")
                .with("body", body)
                .with("event", event.action())
                .to(getApiRoute() + "/events", this);
        this.body = body;
    }

}
