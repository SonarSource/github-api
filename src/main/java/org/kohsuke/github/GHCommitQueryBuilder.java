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

import java.util.Date;

/**
 * Builds up query for listing commits.
 *
 * <p>
 * Call various methods that set the filter criteria, then {@link #list()} method to actually list up the commit.
 *
 * <pre>
 * GHRepository r = ...;
 * for (GHCommit c : r.queryCommits().since(x).until(y).author("kohsuke")) {
 *     ...
 * }
 * </pre>
 *
 * @author Kohsuke Kawaguchi
 * @see GHRepository#queryCommits() GHRepository#queryCommits()
 */
public class GHCommitQueryBuilder {
    private final Requester req;
    private final GHRepository repo;

    GHCommitQueryBuilder(GHRepository repo) {
        this.repo = repo;
        this.req = repo.root.createRequest(); // requester to build up
    }

    /**
     * GItHub login or email address by which to filter by commit author.
     *
     * @param author
     *            the author
     * @return the gh commit query builder
     */
    public GHCommitQueryBuilder author(String author) {
        req.with("author", author);
        return this;
    }

    /**
     * Only commits containing this file path will be returned.
     *
     * @param path
     *            the path
     * @return the gh commit query builder
     */
    public GHCommitQueryBuilder path(String path) {
        req.with("path", path);
        return this;
    }

    /**
     * Specifies the SHA1 commit / tag / branch / etc to start listing commits from.
     *
     * @param ref
     *            the ref
     * @return the gh commit query builder
     */
    public GHCommitQueryBuilder from(String ref) {
        req.with("sha", ref);
        return this;
    }

    /**
     * Page size gh commit query builder.
     *
     * @param pageSize
     *            the page size
     * @return the gh commit query builder
     */
    public GHCommitQueryBuilder pageSize(int pageSize) {
        req.with("per_page", pageSize);
        return this;
    }

    /**
     * Only commits after this date will be returned
     *
     * @param dt
     *            the dt
     * @return the gh commit query builder
     */
    public GHCommitQueryBuilder since(Date dt) {
        req.with("since", GitHubClient.printDate(dt));
        return this;
    }

    /**
     * Only commits after this date will be returned
     *
     * @param timestamp
     *            the timestamp
     * @return the gh commit query builder
     */
    public GHCommitQueryBuilder since(long timestamp) {
        return since(new Date(timestamp));
    }

    /**
     * Only commits before this date will be returned
     *
     * @param dt
     *            the dt
     * @return the gh commit query builder
     */
    public GHCommitQueryBuilder until(Date dt) {
        req.with("until", GitHubClient.printDate(dt));
        return this;
    }

    /**
     * Only commits before this date will be returned
     *
     * @param timestamp
     *            the timestamp
     * @return the gh commit query builder
     */
    public GHCommitQueryBuilder until(long timestamp) {
        return until(new Date(timestamp));
    }

    /**
     * Lists up the commits with the criteria built so far.
     *
     * @return the paged iterable
     */
    public PagedIterable<GHCommit> list() {
        return req.withUrlPath(repo.getApiTailUrl("commits")).toIterable(GHCommit[].class, item -> item.wrapUp(repo));
    }
}
