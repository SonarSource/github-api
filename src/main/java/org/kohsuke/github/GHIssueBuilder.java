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
import java.util.List;

/**
 * The type GHIssueBuilder.
 *
 * @author Kohsuke Kawaguchi
 */
public class GHIssueBuilder {
    private final GHRepository repo;
    private final Requester builder;
    private List<String> labels = new ArrayList<String>();
    private List<String> assignees = new ArrayList<String>();

    GHIssueBuilder(GHRepository repo, String title) {
        this.repo = repo;
        this.builder = repo.root.createRequest().method("POST");
        builder.with("title", title);
    }

    /**
     * Sets the main text of an issue, which is arbitrary multi-line text.
     *
     * @param str
     *            the str
     * @return the gh issue builder
     */
    public GHIssueBuilder body(String str) {
        builder.with("body", str);
        return this;
    }

    /**
     * Assignee gh issue builder.
     *
     * @param user
     *            the user
     * @return the gh issue builder
     */
    public GHIssueBuilder assignee(GHUser user) {
        if (user != null)
            assignees.add(user.getLogin());
        return this;
    }

    /**
     * Assignee gh issue builder.
     *
     * @param user
     *            the user
     * @return the gh issue builder
     */
    public GHIssueBuilder assignee(String user) {
        if (user != null)
            assignees.add(user);
        return this;
    }

    /**
     * Milestone gh issue builder.
     *
     * @param milestone
     *            the milestone
     * @return the gh issue builder
     */
    public GHIssueBuilder milestone(GHMilestone milestone) {
        if (milestone != null)
            builder.with("milestone", milestone.getNumber());
        return this;
    }

    /**
     * Label gh issue builder.
     *
     * @param label
     *            the label
     * @return the gh issue builder
     */
    public GHIssueBuilder label(String label) {
        if (label != null)
            labels.add(label);
        return this;
    }

    /**
     * Creates a new issue.
     *
     * @return the gh issue
     * @throws IOException
     *             the io exception
     */
    public GHIssue create() throws IOException {
        return builder.with("labels", labels)
                .with("assignees", assignees)
                .withUrlPath(repo.getApiTailUrl("issues"))
                .fetch(GHIssue.class)
                .wrap(repo);
    }
}
