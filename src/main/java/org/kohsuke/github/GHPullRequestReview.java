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
import java.util.List;

/**
 * Review to the pull request
 *
 * @see GHPullRequest#listReviews()
 * @see GHPullRequest#createReview(String, String, GHPullRequestReviewEvent, List)
 */
public class GHPullRequestReview extends GHPullRequestReviewAbstract {
    private GHPullRequestReviewState state;

    @Override
    public GHPullRequestReviewState getState() {
        return state;
    }

    GHPullRequestReview wrapUp(GHPullRequest owner) {
        this.owner = owner;
        return this;
    }

    /**
     * Dismisses this review.
     */
    public void dismiss(String message) throws IOException {
        new Requester(owner.root).method("PUT")
                .with("message", message)
                .to(getApiRoute() + "/dismissals");
        state = GHPullRequestReviewState.DISMISSED;
    }
}
