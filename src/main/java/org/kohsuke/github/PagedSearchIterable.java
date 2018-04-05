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

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.Iterator;

import javax.annotation.Nonnull;

/**
 * {@link PagedIterable} enhanced to report search result specific information.
 *
 * @param <T>
 *            the type parameter
 * @author Kohsuke Kawaguchi
 */
@SuppressFBWarnings(
        value = { "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD", "UWF_UNWRITTEN_FIELD",
                "UWF_FIELD_NOT_INITIALIZED_IN_CONSTRUCTOR" },
        justification = "Constructed by JSON API")
public class PagedSearchIterable<T> extends PagedIterable<T> {
    private final transient GitHub root;

    private final GitHubRequest request;

    private final Class<? extends SearchResult<T>> receiverType;

    /**
     * As soon as we have any result fetched, it's set here so that we can report the total count.
     */
    private SearchResult<T> result;

    PagedSearchIterable(GitHub root, GitHubRequest request, Class<? extends SearchResult<T>> receiverType) {
        this.root = root;
        this.request = request;
        this.receiverType = receiverType;
    }

    @Override
    public PagedSearchIterable<T> withPageSize(int size) {
        return (PagedSearchIterable<T>) super.withPageSize(size);
    }

    /**
     * Returns the total number of hit, including the results that's not yet fetched.
     *
     * @return the total count
     */
    public int getTotalCount() {
        populate();
        return result.total_count;
    }

    /**
     * Is incomplete boolean.
     *
     * @return the boolean
     */
    public boolean isIncomplete() {
        populate();
        return result.incomplete_results;
    }

    private void populate() {
        if (result == null)
            iterator().hasNext();
    }

    @Nonnull
    @Override
    public PagedIterator<T> _iterator(int pageSize) {
        final Iterator<T[]> adapter = adapt(
                GitHubPageIterator.create(root.getClient(), receiverType, request, pageSize));
        return new PagedIterator<T>(adapter, null);
    }

    /**
     * Adapts {@link Iterator}.
     *
     * @param base
     *            the base
     * @return the iterator
     */
    protected Iterator<T[]> adapt(final Iterator<? extends SearchResult<T>> base) {
        return new Iterator<T[]>() {
            public boolean hasNext() {
                return base.hasNext();
            }

            public T[] next() {
                SearchResult<T> v = base.next();
                if (result == null)
                    result = v;
                return v.getItems(root);
            }
        };
    }
}
