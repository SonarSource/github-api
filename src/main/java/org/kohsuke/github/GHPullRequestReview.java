/*
 * The MIT License
 *
 * Copyright (c) 2017, CloudBees, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
