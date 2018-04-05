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

/**
 * Search issues.
 *
 * @author Kohsuke Kawaguchi
 * @see GitHub#searchIssues() GitHub#searchIssues()
 */
public class GHIssueSearchBuilder extends GHSearchBuilder<GHIssue> {
    GHIssueSearchBuilder(GitHub root) {
        super(root, IssueSearchResult.class);
    }

    /**
     * Search terms.
     */
    public GHIssueSearchBuilder q(String term) {
        super.q(term);
        return this;
    }

    /**
     * Mentions gh issue search builder.
     *
     * @param u
     *            the u
     * @return the gh issue search builder
     */
    public GHIssueSearchBuilder mentions(GHUser u) {
        return mentions(u.getLogin());
    }

    /**
     * Mentions gh issue search builder.
     *
     * @param login
     *            the login
     * @return the gh issue search builder
     */
    public GHIssueSearchBuilder mentions(String login) {
        return q("mentions:" + login);
    }

    /**
     * Is open gh issue search builder.
     *
     * @return the gh issue search builder
     */
    public GHIssueSearchBuilder isOpen() {
        return q("is:open");
    }

    /**
     * Is closed gh issue search builder.
     *
     * @return the gh issue search builder
     */
    public GHIssueSearchBuilder isClosed() {
        return q("is:closed");
    }

    /**
     * Is merged gh issue search builder.
     *
     * @return the gh issue search builder
     */
    public GHIssueSearchBuilder isMerged() {
        return q("is:merged");
    }

    /**
     * Order gh issue search builder.
     *
     * @param v
     *            the v
     * @return the gh issue search builder
     */
    public GHIssueSearchBuilder order(GHDirection v) {
        req.with("order", v);
        return this;
    }

    /**
     * Sort gh issue search builder.
     *
     * @param sort
     *            the sort
     * @return the gh issue search builder
     */
    public GHIssueSearchBuilder sort(Sort sort) {
        req.with("sort", sort);
        return this;
    }

    /**
     * The enum Sort.
     */
    public enum Sort {
        COMMENTS, CREATED, UPDATED
    }

    private static class IssueSearchResult extends SearchResult<GHIssue> {
        private GHIssue[] items;

        @Override
        GHIssue[] getItems(GitHub root) {
            for (GHIssue i : items)
                i.wrap(root);
            return items;
        }
    }

    @Override
    protected String getApiUrl() {
        return "/search/issues";
    }
}
