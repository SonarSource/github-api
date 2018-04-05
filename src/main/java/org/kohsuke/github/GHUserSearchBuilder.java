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
 * Search users.
 *
 * @author Kohsuke Kawaguchi
 * @see GitHub#searchUsers() GitHub#searchUsers()
 */
public class GHUserSearchBuilder extends GHSearchBuilder<GHUser> {
    GHUserSearchBuilder(GitHub root) {
        super(root, UserSearchResult.class);
    }

    /**
     * Search terms.
     */
    public GHUserSearchBuilder q(String term) {
        super.q(term);
        return this;
    }

    /**
     * Type gh user search builder.
     *
     * @param v
     *            the v
     * @return the gh user search builder
     */
    public GHUserSearchBuilder type(String v) {
        return q("type:" + v);
    }

    /**
     * In gh user search builder.
     *
     * @param v
     *            the v
     * @return the gh user search builder
     */
    public GHUserSearchBuilder in(String v) {
        return q("in:" + v);
    }

    /**
     * Repos gh user search builder.
     *
     * @param v
     *            the v
     * @return the gh user search builder
     */
    public GHUserSearchBuilder repos(String v) {
        return q("repos:" + v);
    }

    /**
     * Location gh user search builder.
     *
     * @param v
     *            the v
     * @return the gh user search builder
     */
    public GHUserSearchBuilder location(String v) {
        return q("location:" + v);
    }

    /**
     * Language gh user search builder.
     *
     * @param v
     *            the v
     * @return the gh user search builder
     */
    public GHUserSearchBuilder language(String v) {
        return q("language:" + v);
    }

    /**
     * Created gh user search builder.
     *
     * @param v
     *            the v
     * @return the gh user search builder
     */
    public GHUserSearchBuilder created(String v) {
        return q("created:" + v);
    }

    /**
     * Followers gh user search builder.
     *
     * @param v
     *            the v
     * @return the gh user search builder
     */
    public GHUserSearchBuilder followers(String v) {
        return q("followers:" + v);
    }

    /**
     * Order gh user search builder.
     *
     * @param v
     *            the v
     * @return the gh user search builder
     */
    public GHUserSearchBuilder order(GHDirection v) {
        req.with("order", v);
        return this;
    }

    /**
     * Sort gh user search builder.
     *
     * @param sort
     *            the sort
     * @return the gh user search builder
     */
    public GHUserSearchBuilder sort(Sort sort) {
        req.with("sort", sort);
        return this;
    }

    /**
     * The enum Sort.
     */
    public enum Sort {
        FOLLOWERS, REPOSITORIES, JOINED
    }

    private static class UserSearchResult extends SearchResult<GHUser> {
        private GHUser[] items;

        @Override
        GHUser[] getItems(GitHub root) {
            return GHUser.wrap(items, root);
        }
    }

    @Override
    protected String getApiUrl() {
        return "/search/users";
    }
}
