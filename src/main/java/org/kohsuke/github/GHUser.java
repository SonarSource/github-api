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

import com.infradna.tool.bridge_method_injector.WithBridgeMethods;

import java.io.IOException;
import java.util.*;

/**
 * Represents an user of GitHub.
 *
 * @author Kohsuke Kawaguchi
 */
public class GHUser extends GHPerson {

    /**
     * Gets keys.
     *
     * @return the keys
     * @throws IOException
     *             the io exception
     */
    public List<GHKey> getKeys() throws IOException {
        return root.createRequest().withUrlPath(getApiTailUrl("keys")).toIterable(GHKey[].class, null).toList();
    }

    /**
     * Follow this user.
     *
     * @throws IOException
     *             the io exception
     */
    public void follow() throws IOException {
        root.createRequest().method("PUT").withUrlPath("/user/following/" + login).send();
    }

    /**
     * Unfollow this user.
     *
     * @throws IOException
     *             the io exception
     */
    public void unfollow() throws IOException {
        root.createRequest().method("DELETE").withUrlPath("/user/following/" + login).send();
    }

    /**
     * Lists the users that this user is following
     *
     * @return the follows
     * @throws IOException
     *             the io exception
     */
    @WithBridgeMethods(Set.class)
    public GHPersonSet<GHUser> getFollows() throws IOException {
        return new GHPersonSet<GHUser>(listFollows().toList());
    }

    /**
     * Lists the users that this user is following
     *
     * @return the paged iterable
     */
    public PagedIterable<GHUser> listFollows() {
        return listUser("following");
    }

    /**
     * Lists the users who are following this user.
     *
     * @return the followers
     * @throws IOException
     *             the io exception
     */
    @WithBridgeMethods(Set.class)
    public GHPersonSet<GHUser> getFollowers() throws IOException {
        return new GHPersonSet<GHUser>(listFollowers().toList());
    }

    /**
     * Lists the users who are following this user.
     *
     * @return the paged iterable
     */
    public PagedIterable<GHUser> listFollowers() {
        return listUser("followers");
    }

    private PagedIterable<GHUser> listUser(final String suffix) {
        return root.createRequest()
                .withUrlPath(getApiTailUrl(suffix))
                .toIterable(GHUser[].class, item -> item.wrapUp(root));
    }

    /**
     * Lists all the subscribed (aka watched) repositories.
     * <p>
     * https://developer.github.com/v3/activity/watching/
     *
     * @return the paged iterable
     */
    public PagedIterable<GHRepository> listSubscriptions() {
        return listRepositories("subscriptions");
    }

    /**
     * Lists all the repositories that this user has starred.
     *
     * @return the paged iterable
     */
    public PagedIterable<GHRepository> listStarredRepositories() {
        return listRepositories("starred");
    }

    private PagedIterable<GHRepository> listRepositories(final String suffix) {
        return root.createRequest()
                .withUrlPath(getApiTailUrl(suffix))
                .toIterable(GHRepository[].class, item -> item.wrap(root));
    }

    /**
     * Returns true if this user belongs to the specified organization.
     *
     * @param org
     *            the org
     * @return the boolean
     */
    public boolean isMemberOf(GHOrganization org) {
        return org.hasMember(this);
    }

    /**
     * Returns true if this user belongs to the specified team.
     *
     * @param team
     *            the team
     * @return the boolean
     */
    public boolean isMemberOf(GHTeam team) {
        return team.hasMember(this);
    }

    /**
     * Returns true if this user belongs to the specified organization as a public member.
     *
     * @param org
     *            the org
     * @return the boolean
     */
    public boolean isPublicMemberOf(GHOrganization org) {
        return org.hasPublicMember(this);
    }

    /**
     * Returns true if this user is marked as hireable, false otherwise
     *
     * @return if the user is marked as hireable
     */
    public boolean isHireable() {
        return hireable;
    }

    public String getBio() {
        return bio;
    }

    static GHUser[] wrap(GHUser[] users, GitHub root) {
        for (GHUser f : users)
            f.root = root;
        return users;
    }

    /**
     * Gets the organization that this user belongs to publicly.
     *
     * @return the organizations
     * @throws IOException
     *             the io exception
     */
    @WithBridgeMethods(Set.class)
    public GHPersonSet<GHOrganization> getOrganizations() throws IOException {
        GHPersonSet<GHOrganization> orgs = new GHPersonSet<GHOrganization>();
        Set<String> names = new HashSet<String>();
        for (GHOrganization o : root.createRequest()
                .withUrlPath("/users/" + login + "/orgs")
                .toIterable(GHOrganization[].class, null)
                .toArray()) {
            if (names.add(o.getLogin())) // I've seen some duplicates in the data
                orgs.add(root.getOrganization(o.getLogin()));
        }
        return orgs;
    }

    /**
     * Lists events performed by a user (this includes private events if the caller is authenticated.
     */
    public PagedIterable<GHEventInfo> listEvents() throws IOException {
        return root.createRequest()
                .withUrlPath(String.format("/users/%s/events", login))
                .toIterable(GHEventInfo[].class, item -> item.wrapUp(root));
    }

    /**
     * Lists Gists created by this user.
     *
     * @return the paged iterable
     * @throws IOException
     *             the io exception
     */
    public PagedIterable<GHGist> listGists() throws IOException {
        return root.createRequest()
                .withUrlPath(String.format("/users/%s/gists", login))
                .toIterable(GHGist[].class, null);
    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GHUser) {
            GHUser that = (GHUser) obj;
            return this.login.equals(that.login);
        }
        return false;
    }

    String getApiTailUrl(String tail) {
        if (tail.length() > 0 && !tail.startsWith("/"))
            tail = '/' + tail;
        return "/users/" + login + tail;
    }

    GHUser wrapUp(GitHub root) {
        super.wrapUp(root);
        return this;
    }
}
