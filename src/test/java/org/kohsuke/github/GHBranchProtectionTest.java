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

import org.junit.Before;
import org.junit.Test;
import org.kohsuke.github.GHBranchProtection.EnforceAdmins;
import org.kohsuke.github.GHBranchProtection.RequiredReviews;
import org.kohsuke.github.GHBranchProtection.RequiredStatusChecks;

import static org.hamcrest.CoreMatchers.*;

public class GHBranchProtectionTest extends AbstractGitHubWireMockTest {
    private static final String BRANCH = "main";
    private static final String BRANCH_REF = "heads/" + BRANCH;

    private GHBranch branch;

    private GHRepository repo;

    @Before
    public void setUp() throws Exception {
        repo = getTempRepository();
        branch = repo.getBranch(BRANCH);
    }

    @Test
    public void testEnableBranchProtections() throws Exception {
        // team/user restrictions require an organization repo to test against
        GHBranchProtection protection = branch.enableProtection()
                .addRequiredChecks("test-status-check")
                .requireBranchIsUpToDate()
                .requireCodeOwnReviews()
                .dismissStaleReviews()
                .requiredReviewers(2)
                .includeAdmins()
                .enable();

        verifyBranchProtection(protection);

        // Get goes through a different code path. Make sure it also gets the correct data.
        protection = branch.getProtection();
        verifyBranchProtection(protection);
    }

    private void verifyBranchProtection(GHBranchProtection protection) {
        RequiredStatusChecks statusChecks = protection.getRequiredStatusChecks();
        assertNotNull(statusChecks);
        assertTrue(statusChecks.isRequiresBranchUpToDate());
        assertTrue(statusChecks.getContexts().contains("test-status-check"));

        RequiredReviews requiredReviews = protection.getRequiredReviews();
        assertNotNull(requiredReviews);
        assertTrue(requiredReviews.isDismissStaleReviews());
        assertTrue(requiredReviews.isRequireCodeOwnerReviews());
        assertEquals(2, requiredReviews.getRequiredReviewers());

        EnforceAdmins enforceAdmins = protection.getEnforceAdmins();
        assertNotNull(enforceAdmins);
        assertTrue(enforceAdmins.isEnabled());
    }

    @Test
    public void testEnableProtectionOnly() throws Exception {
        branch.enableProtection().enable();
        assertTrue(repo.getBranch(BRANCH).isProtected());
    }

    @Test
    public void testDisableProtectionOnly() throws Exception {
        GHBranchProtection protection = branch.enableProtection().enable();
        assertTrue(repo.getBranch(BRANCH).isProtected());
        branch.disableProtection();
        assertFalse(repo.getBranch(BRANCH).isProtected());
    }

    @Test
    public void testEnableRequireReviewsOnly() throws Exception {
        GHBranchProtection protection = branch.enableProtection().requireReviews().enable();

        RequiredReviews requiredReviews = protection.getRequiredReviews();
        assertNotNull(protection.getRequiredReviews());
        assertFalse(requiredReviews.isDismissStaleReviews());
        assertFalse(requiredReviews.isRequireCodeOwnerReviews());
        assertThat(protection.getRequiredReviews().getRequiredReviewers(), equalTo(1));

        // Get goes through a different code path. Make sure it also gets the correct data.
        protection = branch.getProtection();
        requiredReviews = protection.getRequiredReviews();

        assertNotNull(protection.getRequiredReviews());
        assertFalse(requiredReviews.isDismissStaleReviews());
        assertFalse(requiredReviews.isRequireCodeOwnerReviews());
        assertThat(protection.getRequiredReviews().getRequiredReviewers(), equalTo(1));
    }

    @Test
    public void testSignedCommits() throws Exception {
        GHBranchProtection protection = branch.enableProtection().enable();

        assertFalse(protection.getRequiredSignatures());

        protection.enabledSignedCommits();
        assertTrue(protection.getRequiredSignatures());

        protection.disableSignedCommits();
        assertFalse(protection.getRequiredSignatures());
    }

    @Test
    public void testGetProtection() throws Exception {
        GHBranchProtection protection = branch.enableProtection().enable();
        GHBranchProtection protectionTest = repo.getBranch(BRANCH).getProtection();
        assertTrue(protectionTest instanceof GHBranchProtection);
        assertTrue(repo.getBranch(BRANCH).isProtected());
    }
}
