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

import static org.kohsuke.github.internal.Previews.SQUIRREL_GIRL;

/**
 * Those {@link GHObject}s that can have {@linkplain GHReaction reactions}.
 *
 * @author Kohsuke Kawaguchi
 */
@Preview(SQUIRREL_GIRL)
@Deprecated
public interface Reactable {
    /**
     * List all the reactions left to this object.
     *
     * @return the paged iterable
     */
    @Preview(SQUIRREL_GIRL)
    @Deprecated
    PagedIterable<GHReaction> listReactions();

    /**
     * Leaves a reaction to this object.
     *
     * @param content
     *            the content
     * @return the gh reaction
     * @throws IOException
     *             the io exception
     */
    @Preview(SQUIRREL_GIRL)
    @Deprecated
    GHReaction createReaction(ReactionContent content) throws IOException;
}
