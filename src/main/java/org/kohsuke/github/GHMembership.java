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
import java.net.URL;
import java.util.Locale;

/**
 * Represents a membership of a user in an organization.
 *
 * @author Kohsuke Kawaguchi
 * @see GHMyself#listOrgMemberships() GHMyself#listOrgMemberships()
 */
public class GHMembership extends GitHubInteractiveObject {
    String url;
    String state;
    String role;
    GHUser user;
    GHOrganization organization;

    /**
     * Gets url.
     *
     * @return the url
     */
    public URL getUrl() {
        return GitHubClient.parseURL(url);
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    public State getState() {
        return Enum.valueOf(State.class, state.toUpperCase(Locale.ENGLISH));
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public Role getRole() {
        return Enum.valueOf(Role.class, role.toUpperCase(Locale.ENGLISH));
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public GHUser getUser() {
        return user;
    }

    /**
     * Gets organization.
     *
     * @return the organization
     */
    public GHOrganization getOrganization() {
        return organization;
    }

    /**
     * Accepts a pending invitation to an organization.
     *
     * @throws IOException
     *             the io exception
     * @see GHMyself#getMembership(GHOrganization) GHMyself#getMembership(GHOrganization)
     */
    public void activate() throws IOException {
        root.createRequest().method("PATCH").with("state", State.ACTIVE).withUrlPath(url).fetchInto(this);
    }

    GHMembership wrap(GitHub root) {
        this.root = root;
        if (user != null)
            user = root.getUser(user.wrapUp(root));
        if (organization != null)
            organization.wrapUp(root);
        return this;
    }

    /**
     * Role of a user in an organization.
     */
    public enum Role {
        /**
         * Organization owner.
         */
        ADMIN,
        /**
         * Non-owner organization member.
         */
        MEMBER;
    }

    /**
     * Whether a role is currently active or waiting for acceptance (pending)
     */
    public enum State {
        ACTIVE, PENDING;
    }
}
