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
 * Search code for {@link GHContent}.
 *
 * @author Kohsuke Kawaguchi
 * @see GitHub#searchContent() GitHub#searchContent()
 */
public class GHContentSearchBuilder extends GHSearchBuilder<GHContent> {
    GHContentSearchBuilder(GitHub root) {
        super(root, ContentSearchResult.class);
    }

    /**
     * Search terms.
     */
    public GHContentSearchBuilder q(String term) {
        super.q(term);
        return this;
    }

    /**
     * In gh content search builder.
     *
     * @param v
     *            the v
     * @return the gh content search builder
     */
    public GHContentSearchBuilder in(String v) {
        return q("in:" + v);
    }

    /**
     * Language gh content search builder.
     *
     * @param v
     *            the v
     * @return the gh content search builder
     */
    public GHContentSearchBuilder language(String v) {
        return q("language:" + v);
    }

    /**
     * Fork gh content search builder.
     *
     * @param v
     *            the v
     * @return the gh content search builder
     */
    public GHContentSearchBuilder fork(String v) {
        return q("fork:" + v);
    }

    /**
     * Size gh content search builder.
     *
     * @param v
     *            the v
     * @return the gh content search builder
     */
    public GHContentSearchBuilder size(String v) {
        return q("size:" + v);
    }

    /**
     * Path gh content search builder.
     *
     * @param v
     *            the v
     * @return the gh content search builder
     */
    public GHContentSearchBuilder path(String v) {
        return q("path:" + v);
    }

    /**
     * Filename gh content search builder.
     *
     * @param v
     *            the v
     * @return the gh content search builder
     */
    public GHContentSearchBuilder filename(String v) {
        return q("filename:" + v);
    }

    /**
     * Extension gh content search builder.
     *
     * @param v
     *            the v
     * @return the gh content search builder
     */
    public GHContentSearchBuilder extension(String v) {
        return q("extension:" + v);
    }

    /**
     * User gh content search builder.
     *
     * @param v
     *            the v
     * @return the gh content search builder
     */
    public GHContentSearchBuilder user(String v) {
        return q("user:" + v);
    }

    /**
     * Repo gh content search builder.
     *
     * @param v
     *            the v
     * @return the gh content search builder
     */
    public GHContentSearchBuilder repo(String v) {
        return q("repo:" + v);
    }

    /**
     * Order gh content search builder.
     *
     * @param v
     *            the v
     * @return the gh content search builder
     */
    public GHContentSearchBuilder order(GHDirection v) {
        req.with("order", v);
        return this;
    }

    /**
     * Sort gh content search builder.
     *
     * @param sort
     *            the sort
     * @return the gh content search builder
     */
    public GHContentSearchBuilder sort(GHContentSearchBuilder.Sort sort) {
        if (Sort.BEST_MATCH.equals(sort)) {
            req.remove("sort");
        } else {
            req.with("sort", sort);
        }
        return this;
    }

    /**
     * The enum Sort.
     */
    public enum Sort {
        BEST_MATCH, INDEXED
    }

    private static class ContentSearchResult extends SearchResult<GHContent> {
        private GHContent[] items;

        @Override
        GHContent[] getItems(GitHub root) {
            for (GHContent item : items)
                item.wrap(root);
            return items;
        }
    }

    @Override
    protected String getApiUrl() {
        return "/search/code";
    }
}
