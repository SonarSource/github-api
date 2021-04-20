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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Utility class for creating and retrieving webhooks; removes duplication between GHOrganization and GHRepository
 * functionality
 */
class GHHooks {
    static abstract class Context extends GitHubInteractiveObject {

        private Context(GitHub root) {
            this.root = root;
        }

        /**
         * Gets hooks.
         *
         * @return the hooks
         * @throws IOException
         *             the io exception
         */
        public List<GHHook> getHooks() throws IOException {

            GHHook[] hookArray = root.createRequest().withUrlPath(collection()).fetch(collectionClass()); // jdk/eclipse
                                                                                                          // bug
            // requires this
            // to be on separate line
            List<GHHook> list = new ArrayList<GHHook>(Arrays.asList(hookArray));
            for (GHHook h : list)
                wrap(h);
            return list;
        }

        /**
         * Gets hook.
         *
         * @param id
         *            the id
         * @return the hook
         * @throws IOException
         *             the io exception
         */
        public GHHook getHook(int id) throws IOException {
            GHHook hook = root.createRequest().withUrlPath(collection() + "/" + id).fetch(clazz());
            return wrap(hook);
        }

        /**
         * Create hook gh hook.
         *
         * @param name
         *            the name
         * @param config
         *            the config
         * @param events
         *            the events
         * @param active
         *            the active
         * @return the gh hook
         * @throws IOException
         *             the io exception
         */
        public GHHook createHook(String name, Map<String, String> config, Collection<GHEvent> events, boolean active)
                throws IOException {
            List<String> ea = null;
            if (events != null) {
                ea = new ArrayList<String>();
                for (GHEvent e : events)
                    ea.add(e.symbol());
            }

            GHHook hook = root.createRequest()
                    .method("POST")
                    .with("name", name)
                    .with("active", active)
                    .with("config", config)
                    .with("events", ea)
                    .withUrlPath(collection())
                    .fetch(clazz());

            return wrap(hook);
        }

        abstract String collection();

        abstract Class<? extends GHHook[]> collectionClass();

        abstract Class<? extends GHHook> clazz();

        abstract GHHook wrap(GHHook hook);
    }

    private static class RepoContext extends Context {
        private final GHRepository repository;
        private final GHUser owner;

        private RepoContext(GHRepository repository, GHUser owner) {
            super(repository.root);
            this.repository = repository;
            this.owner = owner;
        }

        @Override
        String collection() {
            return String.format("/repos/%s/%s/hooks", owner.getLogin(), repository.getName());
        }

        @Override
        Class<? extends GHHook[]> collectionClass() {
            return GHRepoHook[].class;
        }

        @Override
        Class<? extends GHHook> clazz() {
            return GHRepoHook.class;
        }

        @Override
        GHHook wrap(GHHook hook) {
            return ((GHRepoHook) hook).wrap(repository);
        }
    }

    private static class OrgContext extends Context {
        private final GHOrganization organization;

        private OrgContext(GHOrganization organization) {
            super(organization.root);
            this.organization = organization;
        }

        @Override
        String collection() {
            return String.format("/orgs/%s/hooks", organization.getLogin());
        }

        @Override
        Class<? extends GHHook[]> collectionClass() {
            return GHOrgHook[].class;
        }

        @Override
        Class<? extends GHHook> clazz() {
            return GHOrgHook.class;
        }

        @Override
        GHHook wrap(GHHook hook) {
            return ((GHOrgHook) hook).wrap(organization);
        }
    }

    static Context repoContext(GHRepository repository, GHUser owner) {
        return new RepoContext(repository, owner);
    }

    static Context orgContext(GHOrganization organization) {
        return new OrgContext(organization);
    }
}
