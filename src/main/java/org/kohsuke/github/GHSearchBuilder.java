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

import org.apache.commons.lang3.StringUtils;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Base class for various search builders.
 *
 * @param <T>
 *            the type parameter
 * @author Kohsuke Kawaguchi
 */
public abstract class GHSearchBuilder<T> extends GHQueryBuilder<T> {
    protected final List<String> terms = new ArrayList<String>();

    /**
     * Data transfer object that receives the result of search.
     */
    private final Class<? extends SearchResult<T>> receiverType;

    GHSearchBuilder(GitHub root, Class<? extends SearchResult<T>> receiverType) {
        super(root);
        this.receiverType = receiverType;
        req.withUrlPath(getApiUrl());
        req.rateLimit(RateLimitTarget.SEARCH);
    }

    /**
     * Search terms.
     *
     * @param term
     *            the term
     * @return the gh query builder
     */
    public GHQueryBuilder<T> q(String term) {
        terms.add(term);
        return this;
    }

    /**
     * Performs the search.
     */
    @Override
    public PagedSearchIterable<T> list() {

        req.set("q", StringUtils.join(terms, " "));
        try {
            return new PagedSearchIterable<>(root, req.build(), receiverType);
        } catch (MalformedURLException e) {
            throw new GHException("", e);
        }
    }

    /**
     * Gets api url.
     *
     * @return the api url
     */
    protected abstract String getApiUrl();
}
