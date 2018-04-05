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
 * Search repositories.
 *
 * @author Kohsuke Kawaguchi
 * @see GitHub#searchRepositories() GitHub#searchRepositories()
 */
public class GHRepositorySearchBuilder extends GHSearchBuilder<GHRepository> {
    GHRepositorySearchBuilder(GitHub root) {
        super(root, RepositorySearchResult.class);
    }

    /**
     * Search terms.
     */
    public GHRepositorySearchBuilder q(String term) {
        super.q(term);
        return this;
    }

    /**
     * In gh repository search builder.
     *
     * @param v
     *            the v
     * @return the gh repository search builder
     */
    public GHRepositorySearchBuilder in(String v) {
        return q("in:" + v);
    }

    /**
     * Size gh repository search builder.
     *
     * @param v
     *            the v
     * @return the gh repository search builder
     */
    public GHRepositorySearchBuilder size(String v) {
        return q("size:" + v);
    }

    /**
     * Forks gh repository search builder.
     *
     * @param v
     *            the v
     * @return the gh repository search builder
     */
    public GHRepositorySearchBuilder forks(String v) {
        return q("forks:" + v);
    }

    /**
     * Created gh repository search builder.
     *
     * @param v
     *            the v
     * @return the gh repository search builder
     */
    public GHRepositorySearchBuilder created(String v) {
        return q("created:" + v);
    }

    /**
     * Pushed gh repository search builder.
     *
     * @param v
     *            the v
     * @return the gh repository search builder
     */
    public GHRepositorySearchBuilder pushed(String v) {
        return q("pushed:" + v);
    }

    /**
     * User gh repository search builder.
     *
     * @param v
     *            the v
     * @return the gh repository search builder
     */
    public GHRepositorySearchBuilder user(String v) {
        return q("user:" + v);
    }

    /**
     * Repo gh repository search builder.
     *
     * @param v
     *            the v
     * @return the gh repository search builder
     */
    public GHRepositorySearchBuilder repo(String v) {
        return q("repo:" + v);
    }

    /**
     * Language gh repository search builder.
     *
     * @param v
     *            the v
     * @return the gh repository search builder
     */
    public GHRepositorySearchBuilder language(String v) {
        return q("language:" + v);
    }

    /**
     * Stars gh repository search builder.
     *
     * @param v
     *            the v
     * @return the gh repository search builder
     */
    public GHRepositorySearchBuilder stars(String v) {
        return q("stars:" + v);
    }

    /**
     * Topic gh repository search builder.
     *
     * @param v
     *            the v
     * @return the gh repository search builder
     */
    public GHRepositorySearchBuilder topic(String v) {
        return q("topic:" + v);
    }

    /**
     * Order gh repository search builder.
     *
     * @param v
     *            the v
     * @return the gh repository search builder
     */
    public GHRepositorySearchBuilder order(GHDirection v) {
        req.with("order", v);
        return this;
    }

    /**
     * Sort gh repository search builder.
     *
     * @param sort
     *            the sort
     * @return the gh repository search builder
     */
    public GHRepositorySearchBuilder sort(Sort sort) {
        req.with("sort", sort);
        return this;
    }

    /**
     * The enum Sort.
     */
    public enum Sort {
        STARS, FORKS, UPDATED
    }

    private static class RepositorySearchResult extends SearchResult<GHRepository> {
        private GHRepository[] items;

        @Override
        GHRepository[] getItems(GitHub root) {
            for (GHRepository item : items)
                item.wrap(root);
            return items;
        }
    }

    @Override
    protected String getApiUrl() {
        return "/search/repositories";
    }
}
