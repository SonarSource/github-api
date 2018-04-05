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

/**
 * Represents an annotated tag in a {@link GHRepository}
 *
 * @see GHRepository#getTagObject(String) GHRepository#getTagObject(String)
 */
@SuppressFBWarnings(value = { "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD", "UWF_UNWRITTEN_FIELD", "NP_UNWRITTEN_FIELD" },
        justification = "JSON API")
public class GHTagObject extends GitHubInteractiveObject {
    private GHRepository owner;

    private String tag;
    private String sha;
    private String url;
    private String message;
    private GitUser tagger;
    private GHRef.GHObject object;
    private GHVerification verification;

    GHTagObject wrap(GHRepository owner) {
        this.owner = owner;
        this.root = owner.root;
        return this;
    }

    /**
     * Gets owner.
     *
     * @return the owner
     */
    public GHRepository getOwner() {
        return owner;
    }

    /**
     * Gets root.
     *
     * @return the root
     */
    public GitHub getRoot() {
        return root;
    }

    /**
     * Gets tag.
     *
     * @return the tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * Gets sha.
     *
     * @return the sha
     */
    public String getSha() {
        return sha;
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets tagger.
     *
     * @return the tagger
     */
    public GitUser getTagger() {
        return tagger;
    }

    /**
     * Gets object.
     *
     * @return the object
     */
    public GHRef.GHObject getObject() {
        return object;
    }

    /**
     * Gets Verification Status.
     *
     * @return the Verification status
     */
    public GHVerification getVerification() {
        return verification;
    }
}
