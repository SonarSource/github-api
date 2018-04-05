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

import com.fasterxml.jackson.annotation.JsonSetter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.io.IOException;
import java.io.Reader;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Base type for types used in databinding of the event payload.
 *
 * @see GitHub#parseEventPayload(Reader, Class) GitHub#parseEventPayload(Reader, Class)
 * @see GHEventInfo#getPayload(Class) GHEventInfo#getPayload(Class)
 * @see <a href="https://docs.github.com/en/developers/webhooks-and-events/webhook-events-and-payloads">Webhook events
 *      and payloads</a>
 */
@SuppressWarnings("UnusedDeclaration")
@SuppressFBWarnings("UWF_UNWRITTEN_FIELD")
public class GHEventPayload extends GitHubInteractiveObject {
    // https://docs.github.com/en/free-pro-team@latest/developers/webhooks-and-events/webhook-events-and-payloads#webhook-payload-object-common-properties
    // Webhook payload object common properties: action, sender, repository, organization, installation
    private String action;
    private GHUser sender;
    private GHRepository repository;
    private GHOrganization organization;
    private GHAppInstallation installation;

    GHEventPayload() {
    }

    /**
     * Gets the action for the triggered event. Most but not all webhook payloads contain an action property that
     * contains the specific activity that triggered the event.
     *
     * @return event action
     */
    public String getAction() {
        return action;
    }

    /**
     * Gets the sender or {@code null} if accessed via the events API.
     *
     * @return the sender or {@code null} if accessed via the events API.
     */
    public GHUser getSender() {
        return sender;
    }

    /**
     * Sets sender.
     *
     * @param sender
     *            the sender
     */
    public void setSender(GHUser sender) {
        this.sender = sender;
    }

    /**
     * Gets repository.
     *
     * @return the repository
     */
    public GHRepository getRepository() {
        return repository;
    }

    /**
     * Sets repository.
     *
     * @param repository
     *            the repository
     */
    public void setRepository(GHRepository repository) {
        this.repository = repository;
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
     * Sets organization.
     *
     * @param organization
     *            the organization
     */
    public void setOrganization(GHOrganization organization) {
        this.organization = organization;
    }

    /**
     * Gets installation
     *
     * @return the installation
     */
    public GHAppInstallation getInstallation() {
        return installation;
    }

    void wrapUp(GitHub root) {
        this.root = root;
        if (sender != null) {
            sender.wrapUp(root);
        }
        if (repository != null) {
            repository.wrap(root);
        }
        if (organization != null) {
            organization.wrapUp(root);
        }
        if (installation != null) {
            installation.wrapUp(root);
        }
    }

    // List of events that still need to be added:
    // ContentReferenceEvent
    // DeployKeyEvent DownloadEvent FollowEvent ForkApplyEvent GitHubAppAuthorizationEvent GistEvent GollumEvent
    // InstallationEvent InstallationRepositoriesEvent IssuesEvent LabelEvent MarketplacePurchaseEvent MemberEvent
    // MembershipEvent MetaEvent MilestoneEvent OrganizationEvent OrgBlockEvent PackageEvent PageBuildEvent
    // ProjectCardEvent ProjectColumnEvent ProjectEvent RepositoryDispatchEvent RepositoryImportEvent
    // RepositoryVulnerabilityAlertEvent SecurityAdvisoryEvent StarEvent StatusEvent TeamEvent TeamAddEvent WatchEvent

    /**
     * A check run event has been created, rerequested, completed, or has a requested_action.
     *
     * @see <a href="https://docs.github.com/en/developers/webhooks-and-events/webhook-events-and-payloads#check_run">
     *      check_run event</a>
     * @see <a href="https://docs.github.com/en/rest/reference/checks#check-runs">Check Runs</a>
     */
    public static class CheckRun extends GHEventPayload {
        private int number;
        private GHCheckRun checkRun;
        private GHRequestedAction requestedAction;

        /**
         * Gets number.
         *
         * @return the number
         */
        public int getNumber() {
            return number;
        }

        /**
         * Sets Check Run object
         *
         * @param currentCheckRun
         *            the check run object
         */
        public void setCheckRun(GHCheckRun currentCheckRun) {
            this.checkRun = currentCheckRun;
        }

        /**
         * Gets Check Run object
         *
         * @return the current checkRun object
         */
        public GHCheckRun getCheckRun() {
            return checkRun;
        }

        /**
         * Sets the Requested Action object
         *
         * @param currentRequestedAction
         *            the current action
         */
        public void setCheckRun(GHRequestedAction currentRequestedAction) {
            this.requestedAction = currentRequestedAction;
        }

        /**
         * Gets the Requested Action object
         *
         * @return the requested action
         */
        public GHRequestedAction getRequestedAction() {
            return requestedAction;
        }

        @Override
        void wrapUp(GitHub root) {
            super.wrapUp(root);
            if (checkRun == null)
                throw new IllegalStateException(
                        "Expected check_run payload, but got something else. Maybe we've got another type of event?");
            GHRepository repository = getRepository();
            if (repository != null) {
                checkRun.wrap(repository);
            } else {
                checkRun.wrap(root);
            }
        }
    }

    /**
     * A check suite event has been requested, rerequested or completed.
     *
     * @see <a href="https://docs.github.com/en/developers/webhooks-and-events/webhook-events-and-payloads#check_suite">
     *      check_suite event</a>
     * @see <a href="https://docs.github.com/en/rest/reference/checks#check-suites">Check Suites</a>
     */
    public static class CheckSuite extends GHEventPayload {
        private GHCheckSuite checkSuite;

        /**
         * Gets the Check Suite object
         *
         * @return the Check Suite object
         */
        public GHCheckSuite getCheckSuite() {
            return checkSuite;
        }

        @Override
        void wrapUp(GitHub root) {
            super.wrapUp(root);
            if (checkSuite == null)
                throw new IllegalStateException(
                        "Expected check_suite payload, but got something else. Maybe we've got another type of event?");
            GHRepository repository = getRepository();
            if (repository != null) {
                checkSuite.wrap(repository);
            } else {
                checkSuite.wrap(root);
            }
        }
    }

    /**
     * An installation has been installed, uninstalled, or its permissions have been changed.
     *
     * @see <a href=
     *      "https://docs.github.com/en/developers/webhooks-and-events/webhook-events-and-payloads#installation">
     *      installation event</a>
     * @see <a href="https://docs.github.com/en/rest/reference/apps#installations">GitHub App Installation</a>
     */
    public static class Installation extends GHEventPayload {
        private List<GHRepository> repositories;

        /**
         * Gets repositories
         *
         * @return the repositories
         */
        public List<GHRepository> getRepositories() {
            return repositories;
        };

        @Override
        void wrapUp(GitHub root) {
            super.wrapUp(root);
            if (getInstallation() == null) {
                throw new IllegalStateException(
                        "Expected check_suite payload, but got something else. Maybe we've got another type of event?");
            }

            if (repositories != null && !repositories.isEmpty()) {
                try {
                    for (GHRepository singleRepo : repositories) { // warp each of the repository
                        singleRepo.wrap(root);
                        singleRepo.populate();
                    }
                } catch (IOException e) {
                    throw new GHException("Failed to refresh repositories", e);
                }
            }
        }
    }

    /**
     * A repository has been added or removed from an installation.
     *
     * @see <a href=
     *      "https://docs.github.com/en/developers/webhooks-and-events/webhook-events-and-payloads#installation_repositories">
     *      installation_repositories event</a>
     * @see <a href="https://docs.github.com/en/rest/reference/apps#installations">GitHub App installation</a>
     */
    public static class InstallationRepositories extends GHEventPayload {
        private String repositorySelection;
        private List<GHRepository> repositoriesAdded;
        private List<GHRepository> repositoriesRemoved;

        /**
         * Gets installation selection
         *
         * @return the installation selection
         */
        public String getRepositorySelection() {
            return repositorySelection;
        }

        /**
         * Gets repositories added
         *
         * @return the repositories
         */
        public List<GHRepository> getRepositoriesAdded() {
            return repositoriesAdded;
        }

        /**
         * Gets repositories removed
         *
         * @return the repositories
         */
        public List<GHRepository> getRepositoriesRemoved() {
            return repositoriesRemoved;
        }

        @Override
        void wrapUp(GitHub root) {
            super.wrapUp(root);
            if (getInstallation() == null) {
                throw new IllegalStateException(
                        "Expected check_suite payload, but got something else. Maybe we've got another type of event?");
            }

            List<GHRepository> repositories;
            if ("added".equals(getAction()))
                repositories = repositoriesAdded;
            else // action == "removed"
                repositories = repositoriesRemoved;

            if (repositories != null && !repositories.isEmpty()) {
                try {
                    for (GHRepository singleRepo : repositories) { // warp each of the repository
                        singleRepo.wrap(root);
                        singleRepo.populate();
                    }
                } catch (IOException e) {
                    throw new GHException("Failed to refresh repositories", e);
                }
            }
        }
    }

    /**
     * A pull request status has changed.
     *
     * @see <a href=
     *      "https://docs.github.com/en/developers/webhooks-and-events/webhook-events-and-payloads#pull_request">
     *      pull_request event</a>
     * @see <a href="https://docs.github.com/en/rest/reference/pulls">Pull Requests</a>
     */
    @SuppressFBWarnings(value = { "NP_UNWRITTEN_FIELD" }, justification = "JSON API")
    public static class PullRequest extends GHEventPayload {
        private int number;
        private GHPullRequest pullRequest;
        private GHLabel label;
        private GHPullRequestChanges changes;

        /**
         * Gets number.
         *
         * @return the number
         */
        public int getNumber() {
            return number;
        }

        /**
         * Gets pull request.
         *
         * @return the pull request
         */
        public GHPullRequest getPullRequest() {
            pullRequest.root = root;
            return pullRequest;
        }

        /**
         * Gets the added or removed label for labeled/unlabeled events.
         *
         * @return label the added or removed label
         */
        public GHLabel getLabel() {
            return label;
        }

        /**
         * Get changes (for action="edited")
         *
         * @return changes
         */
        public GHPullRequestChanges getChanges() {
            return changes;
        }

        @Override
        void wrapUp(GitHub root) {
            super.wrapUp(root);
            if (pullRequest == null)
                throw new IllegalStateException(
                        "Expected pull_request payload, but got something else. Maybe we've got another type of event?");
            GHRepository repository = getRepository();
            if (repository != null) {
                pullRequest.wrapUp(repository);
            } else {
                pullRequest.wrapUp(root);
            }
        }
    }

    /**
     * A review was added to a pull request
     *
     * @see <a href=
     *      "https://docs.github.com/en/developers/webhooks-and-events/webhook-events-and-payloads#pull_request_review">
     *      pull_request_review event</a>
     * @see <a href="https://docs.github.com/en/rest/reference/pulls#reviews">Pull Request Reviews</a>
     */
    public static class PullRequestReview extends GHEventPayload {
        private GHPullRequestReview review;
        private GHPullRequest pullRequest;

        /**
         * Gets review.
         *
         * @return the review
         */
        public GHPullRequestReview getReview() {
            return review;
        }

        /**
         * Gets pull request.
         *
         * @return the pull request
         */
        public GHPullRequest getPullRequest() {
            return pullRequest;
        }

        @Override
        void wrapUp(GitHub root) {
            super.wrapUp(root);
            if (review == null)
                throw new IllegalStateException(
                        "Expected pull_request_review payload, but got something else. Maybe we've got another type of event?");

            review.wrapUp(pullRequest);

            GHRepository repository = getRepository();
            if (repository != null) {
                pullRequest.wrapUp(repository);
            } else {
                pullRequest.wrapUp(root);
            }
        }
    }

    /**
     * A review comment was added to a pull request
     *
     * @see <a href=
     *      "https://docs.github.com/en/developers/webhooks-and-events/webhook-events-and-payloads#pull_request_review_comment">
     *      pull_request_review_comment event</a>
     * @see <a href="https://docs.github.com/en/rest/reference/pulls#review-comments">Pull Request Review Comments</a>
     */
    public static class PullRequestReviewComment extends GHEventPayload {
        private GHPullRequestReviewComment comment;
        private GHPullRequest pullRequest;

        /**
         * Gets comment.
         *
         * @return the comment
         */
        public GHPullRequestReviewComment getComment() {
            return comment;
        }

        /**
         * Gets pull request.
         *
         * @return the pull request
         */
        public GHPullRequest getPullRequest() {
            return pullRequest;
        }

        @Override
        void wrapUp(GitHub root) {
            super.wrapUp(root);
            if (comment == null)
                throw new IllegalStateException(
                        "Expected pull_request_review_comment payload, but got something else. Maybe we've got another type of event?");

            comment.wrapUp(pullRequest);

            GHRepository repository = getRepository();
            if (repository != null) {
                pullRequest.wrapUp(repository);
            } else {
                pullRequest.wrapUp(root);
            }
        }
    }

    /**
     * A Issue has been assigned, unassigned, labeled, unlabeled, opened, edited, milestoned, demilestoned, closed, or
     * reopened.
     *
     * @see <a href="https://docs.github.com/en/developers/webhooks-and-events/webhook-events-and-payloads#issues">
     *      issues events</a>
     * @see <a href="https://docs.github.com/en/rest/reference/issues#comments">Issues Comments</a>
     */
    public static class Issue extends GHEventPayload {
        private GHIssue issue;

        private GHLabel label;

        private GHIssueChanges changes;

        /**
         * Gets issue.
         *
         * @return the issue
         */
        public GHIssue getIssue() {
            return issue;
        }

        /**
         * Sets issue.
         *
         * @param issue
         *            the issue
         */
        public void setIssue(GHIssue issue) {
            this.issue = issue;
        }

        /**
         * Gets the added or removed label for labeled/unlabeled events.
         *
         * @return label the added or removed label
         */
        public GHLabel getLabel() {
            return label;
        }

        /**
         * Get changes (for action="edited")
         *
         * @return changes
         */
        public GHIssueChanges getChanges() {
            return changes;
        }

        @Override
        void wrapUp(GitHub root) {
            super.wrapUp(root);
            GHRepository repository = getRepository();
            if (repository != null) {
                issue.wrap(repository);
            } else {
                issue.wrap(root);
            }
        }
    }

    /**
     * A comment was added to an issue
     *
     * @see <a href=
     *      "https://docs.github.com/en/developers/webhooks-and-events/webhook-events-and-payloads#issue_comment">
     *      issue_comment event</a>
     * @see <a href="https://docs.github.com/en/rest/reference/issues#comments">Issue Comments</a>
     */
    public static class IssueComment extends GHEventPayload {
        private GHIssueComment comment;
        private GHIssue issue;

        /**
         * Gets comment.
         *
         * @return the comment
         */
        public GHIssueComment getComment() {
            return comment;
        }

        /**
         * Sets comment.
         *
         * @param comment
         *            the comment
         */
        public void setComment(GHIssueComment comment) {
            this.comment = comment;
        }

        /**
         * Gets issue.
         *
         * @return the issue
         */
        public GHIssue getIssue() {
            return issue;
        }

        /**
         * Sets issue.
         *
         * @param issue
         *            the issue
         */
        public void setIssue(GHIssue issue) {
            this.issue = issue;
        }

        @Override
        void wrapUp(GitHub root) {
            super.wrapUp(root);
            GHRepository repository = getRepository();
            if (repository != null) {
                issue.wrap(repository);
            } else {
                issue.wrap(root);
            }
            comment.wrapUp(issue);
        }
    }

    /**
     * A comment was added to a commit
     *
     * @see <a href=
     *      "https://docs.github.com/en/developers/webhooks-and-events/webhook-events-and-payloads#commit_comment">
     *      commit comment</a>
     * @see <a href="https://docs.github.com/en/rest/reference/repos#comments">Comments</a>
     */
    public static class CommitComment extends GHEventPayload {
        private GHCommitComment comment;

        /**
         * Gets comment.
         *
         * @return the comment
         */
        public GHCommitComment getComment() {
            return comment;
        }

        /**
         * Sets comment.
         *
         * @param comment
         *            the comment
         */
        public void setComment(GHCommitComment comment) {
            this.comment = comment;
        }

        @Override
        void wrapUp(GitHub root) {
            super.wrapUp(root);
            GHRepository repository = getRepository();
            if (repository != null) {
                comment.wrap(repository);
            }
        }
    }

    /**
     * A repository, branch, or tag was created
     *
     * @see <a href="https://docs.github.com/en/developers/webhooks-and-events/webhook-events-and-payloads#create">
     *      create event</a>
     * @see <a href="https://docs.github.com/en/rest/reference/git">Git data</a>
     */
    public static class Create extends GHEventPayload {
        private String ref;
        private String refType;
        private String masterBranch;
        private String description;

        /**
         * Gets ref.
         *
         * @return the ref
         */
        @SuppressFBWarnings(value = "UWF_UNWRITTEN_FIELD", justification = "Comes from JSON deserialization")
        public String getRef() {
            return ref;
        }

        /**
         * Gets ref type.
         *
         * @return the ref type
         */
        @SuppressFBWarnings(value = "UWF_UNWRITTEN_FIELD", justification = "Comes from JSON deserialization")
        public String getRefType() {
            return refType;
        }

        /**
         * Gets default branch.
         *
         * Name is an artifact of when "master" was the most common default.
         *
         * @return the default branch
         */
        @SuppressFBWarnings(value = "UWF_UNWRITTEN_FIELD", justification = "Comes from JSON deserialization")
        public String getMasterBranch() {
            return masterBranch;
        }

        /**
         * Gets description.
         *
         * @return the description
         */
        @SuppressFBWarnings(value = "UWF_UNWRITTEN_FIELD", justification = "Comes from JSON deserialization")
        public String getDescription() {
            return description;
        }
    }

    /**
     * A branch, or tag was deleted
     *
     * @see <a href="https://docs.github.com/en/developers/webhooks-and-events/webhook-events-and-payloads#delete">
     *      delete event</a>
     * @see <a href="https://docs.github.com/en/rest/reference/git">Git data</a>
     */
    public static class Delete extends GHEventPayload {
        private String ref;
        private String refType;

        /**
         * Gets ref.
         *
         * @return the ref
         */
        @SuppressFBWarnings(value = "UWF_UNWRITTEN_FIELD", justification = "Comes from JSON deserialization")
        public String getRef() {
            return ref;
        }

        /**
         * Gets ref type.
         *
         * @return the ref type
         */
        @SuppressFBWarnings(value = "UWF_UNWRITTEN_FIELD", justification = "Comes from JSON deserialization")
        public String getRefType() {
            return refType;
        }
    }

    /**
     * A deployment
     *
     * @see <a href="https://docs.github.com/en/developers/webhooks-and-events/webhook-events-and-payloads#deployment">
     *      deployment event</a>
     * @see <a href="https://docs.github.com/en/rest/reference/repos#deployments">Deployments</a>
     */
    public static class Deployment extends GHEventPayload {
        private GHDeployment deployment;

        /**
         * Gets deployment.
         *
         * @return the deployment
         */
        public GHDeployment getDeployment() {
            return deployment;
        }

        /**
         * Sets deployment.
         *
         * @param deployment
         *            the deployment
         */
        public void setDeployment(GHDeployment deployment) {
            this.deployment = deployment;
        }

        @Override
        void wrapUp(GitHub root) {
            super.wrapUp(root);
            GHRepository repository = getRepository();
            if (repository != null) {
                deployment.wrap(repository);
            }
        }
    }

    /**
     * A deployment status
     *
     * @see <a href=
     *      "https://docs.github.com/en/developers/webhooks-and-events/webhook-events-and-payloads#deployment_status">
     *      deployment_status event</a>
     * @see <a href="https://docs.github.com/en/rest/reference/repos#deployments">Deployments</a>
     */
    public static class DeploymentStatus extends GHEventPayload {
        private GHDeploymentStatus deploymentStatus;
        private GHDeployment deployment;

        /**
         * Gets deployment status.
         *
         * @return the deployment status
         */
        public GHDeploymentStatus getDeploymentStatus() {
            return deploymentStatus;
        }

        /**
         * Sets deployment status.
         *
         * @param deploymentStatus
         *            the deployment status
         */
        public void setDeploymentStatus(GHDeploymentStatus deploymentStatus) {
            this.deploymentStatus = deploymentStatus;
        }

        /**
         * Gets deployment.
         *
         * @return the deployment
         */
        public GHDeployment getDeployment() {
            return deployment;
        }

        /**
         * Sets deployment.
         *
         * @param deployment
         *            the deployment
         */
        public void setDeployment(GHDeployment deployment) {
            this.deployment = deployment;
        }

        @Override
        void wrapUp(GitHub root) {
            super.wrapUp(root);
            GHRepository repository = getRepository();
            if (repository != null) {
                deployment.wrap(repository);
                deploymentStatus.wrap(repository);
            }
        }
    }

    /**
     * A user forked a repository
     *
     * @see <a href="https://docs.github.com/en/developers/webhooks-and-events/webhook-events-and-payloads#fork"> fork
     *      event</a>
     * @see <a href="https://docs.github.com/en/rest/reference/repos#forks">Forks</a>
     */
    public static class Fork extends GHEventPayload {
        private GHRepository forkee;

        /**
         * Gets forkee.
         *
         * @return the forkee
         */
        public GHRepository getForkee() {
            return forkee;
        }

        /**
         * Sets forkee.
         *
         * @param forkee
         *            the forkee
         */
        public void setForkee(GHRepository forkee) {
            this.forkee = forkee;
        }

        @Override
        void wrapUp(GitHub root) {
            super.wrapUp(root);
            forkee.wrap(root);
        }
    }

    /**
     * A ping.
     *
     * <a href="https://docs.github.com/en/developers/webhooks-and-events/webhook-events-and-payloads#ping"> ping
     * event</a>
     */
    public static class Ping extends GHEventPayload {

    }

    /**
     * A repository was made public.
     *
     * @see <a href="https://docs.github.com/en/developers/webhooks-and-events/webhook-events-and-payloads#public">
     *      public event</a>
     */
    public static class Public extends GHEventPayload {

    }

    /**
     * A commit was pushed.
     *
     * @see <a href="https://docs.github.com/en/developers/webhooks-and-events/webhook-events-and-payloads#push"> push
     *      event</a>
     */
    public static class Push extends GHEventPayload {
        private String head, before;
        private boolean created, deleted, forced;
        private String ref;
        private int size;
        private List<PushCommit> commits;
        private Pusher pusher;
        private String compare;

        /**
         * The SHA of the HEAD commit on the repository
         *
         * @return the head
         */
        public String getHead() {
            return head;
        }

        /**
         * This is undocumented, but it looks like this captures the commit that the ref was pointing to before the
         * push.
         *
         * @return the before
         */
        public String getBefore() {
            return before;
        }

        @JsonSetter // alias
        private void setAfter(String after) {
            head = after;
        }

        /**
         * The full Git ref that was pushed. Example: “refs/heads/main”
         *
         * @return the ref
         */
        public String getRef() {
            return ref;
        }

        /**
         * The number of commits in the push. Is this always the same as {@code getCommits().size()}?
         *
         * @return the size
         */
        public int getSize() {
            return size;
        }

        /**
         * Is created boolean.
         *
         * @return the boolean
         */
        public boolean isCreated() {
            return created;
        }

        /**
         * Is deleted boolean.
         *
         * @return the boolean
         */
        public boolean isDeleted() {
            return deleted;
        }

        /**
         * Is forced boolean.
         *
         * @return the boolean
         */
        public boolean isForced() {
            return forced;
        }

        /**
         * The list of pushed commits.
         *
         * @return the commits
         */
        public List<PushCommit> getCommits() {
            return commits;
        }

        /**
         * Gets pusher.
         *
         * @return the pusher
         */
        public Pusher getPusher() {
            return pusher;
        }

        /**
         * Sets pusher.
         *
         * @param pusher
         *            the pusher
         */
        public void setPusher(Pusher pusher) {
            this.pusher = pusher;
        }

        /**
         * Gets compare.
         *
         * @return compare
         */
        public String getCompare() {
            return compare;
        }

        /**
         * The type Pusher.
         */
        public static class Pusher {
            private String name, email;

            /**
             * Gets name.
             *
             * @return the name
             */
            public String getName() {
                return name;
            }

            /**
             * Sets name.
             *
             * @param name
             *            the name
             */
            public void setName(String name) {
                this.name = name;
            }

            /**
             * Gets email.
             *
             * @return the email
             */
            public String getEmail() {
                return email;
            }

            /**
             * Sets email.
             *
             * @param email
             *            the email
             */
            public void setEmail(String email) {
                this.email = email;
            }
        }

        /**
         * Commit in a push. Note: sha is an alias for id.
         */
        public static class PushCommit {
            private GitUser author;
            private GitUser committer;
            private String url, sha, message, timestamp;
            private boolean distinct;
            private List<String> added, removed, modified;

            /**
             * Gets author.
             *
             * @return the author
             */
            public GitUser getAuthor() {
                return author;
            }

            /**
             * Gets committer.
             *
             * @return the committer
             */
            public GitUser getCommitter() {
                return committer;
            }

            /**
             * Points to the commit API resource.
             *
             * @return the url
             */
            public String getUrl() {
                return url;
            }

            /**
             * Gets sha (id).
             *
             * @return the sha
             */
            public String getSha() {
                return sha;
            }

            @JsonSetter
            private void setId(String id) {
                sha = id;
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
             * Whether this commit is distinct from any that have been pushed before.
             *
             * @return the boolean
             */
            public boolean isDistinct() {
                return distinct;
            }

            /**
             * Gets added.
             *
             * @return the added
             */
            public List<String> getAdded() {
                return added;
            }

            /**
             * Gets removed.
             *
             * @return the removed
             */
            public List<String> getRemoved() {
                return removed;
            }

            /**
             * Gets modified.
             *
             * @return the modified
             */
            public List<String> getModified() {
                return modified;
            }

            /**
             * Obtains the timestamp of the commit
             *
             * @return the timestamp
             */
            public Date getTimestamp() {
                return GitHubClient.parseDate(timestamp);
            }
        }
    }

    /**
     * A release was added to the repo
     *
     * @see <a href="https://docs.github.com/en/developers/webhooks-and-events/webhook-events-and-payloads#release">
     *      release event</a>
     * @see <a href="https://docs.github.com/en/rest/reference/repos#releases">Releases</a>
     */
    @SuppressFBWarnings(value = { "UWF_FIELD_NOT_INITIALIZED_IN_CONSTRUCTOR", "NP_UNWRITTEN_FIELD" },
            justification = "Constructed by JSON deserialization")
    public static class Release extends GHEventPayload {
        private GHRelease release;

        /**
         * Gets release.
         *
         * @return the release
         */
        public GHRelease getRelease() {
            return release;
        }

        /**
         * Sets release.
         *
         * @param release
         *            the release
         */
        public void setRelease(GHRelease release) {
            this.release = release;
        }
    }

    /**
     * A repository was created, deleted, made public, or made private.
     *
     * @see <a href="https://docs.github.com/en/developers/webhooks-and-events/webhook-events-and-payloads#repository">
     *      repository event</a>
     * @see <a href="https://docs.github.com/en/rest/reference/repos">Repositories</a>
     */
    public static class Repository extends GHEventPayload {

    }

    /**
     * A git commit status was changed.
     *
     * @see <a href="https://docs.github.com/en/developers/webhooks-and-events/webhook-events-and-payloads#status">
     *      status event</a>
     * @see <a href="https://docs.github.com/en/rest/reference/repos#statuses">Repository Statuses</a>
     */
    public static class Status extends GHEventPayload {
        private String context;
        private String description;
        private GHCommitState state;
        private GHCommit commit;
        private String targetUrl;

        /**
         * Gets the status content.
         *
         * @return status content
         */
        public String getContext() {
            return context;
        }

        /**
         * The optional link added to the status.
         *
         * @return a url
         */
        public String getTargetUrl() {
            return targetUrl;
        }

        /**
         * Gets the status description.
         *
         * @return status description
         */
        public String getDescription() {
            return description;
        }

        /**
         * Gets the status state.
         *
         * @return status state
         */
        public GHCommitState getState() {
            return state;
        }

        /**
         * Sets the status stage.
         *
         * @param state
         *            status state
         */
        public void setState(GHCommitState state) {
            this.state = state;
        }

        /**
         * Gets the commit associated with the status event.
         *
         * @return commit
         */
        public GHCommit getCommit() {
            return commit;
        }

        /**
         * Sets the commit associated with the status event.
         *
         * @param commit
         *            commit
         */
        public void setCommit(GHCommit commit) {
            this.commit = commit;
        }

        @Override
        void wrapUp(GitHub root) {
            super.wrapUp(root);
            if (state == null) {
                throw new IllegalStateException(
                        "Expected status payload, but got something else. Maybe we've got another type of event?");
            }
            GHRepository repository = getRepository();
            if (repository != null) {
                commit.wrapUp(repository);
            }
        }
    }

    /**
     * Occurs when someone triggered a workflow run or sends a POST request to the "Create a workflow dispatch event"
     * endpoint.
     *
     * @see <a href=
     *      "https://docs.github.com/en/developers/webhooks-and-events/webhook-events-and-payloads#workflow_dispatch">
     *      workflow dispatch event</a>
     * @see <a href=
     *      "https://docs.github.com/en/actions/reference/events-that-trigger-workflows#workflow_dispatch">Events that
     *      trigger workflows</a>
     */
    public static class WorkflowDispatch extends GHEventPayload {
        private Map<String, Object> inputs;
        private String ref;
        private String workflow;

        /**
         * Gets the map of input parameters passed to the workflow.
         *
         * @return the map of input parameters
         */
        public Map<String, Object> getInputs() {
            return inputs;
        }

        /**
         * Gets the ref of the branch (e.g. refs/heads/main)
         *
         * @return the ref of the branch
         */
        public String getRef() {
            return ref;
        }

        /**
         * Gets the path of the workflow file (e.g. .github/workflows/hello-world-workflow.yml).
         *
         * @return the path of the workflow file
         */
        public String getWorkflow() {
            return workflow;
        }
    }

    /**
     * A workflow run was requested or completed.
     *
     * @see <a href=
     *      "https://docs.github.com/en/developers/webhooks-and-events/webhook-events-and-payloads#workflow_run">
     *      workflow run event</a>
     * @see <a href="https://docs.github.com/en/rest/reference/actions#workflow-runs">Actions Workflow Runs</a>
     */
    public static class WorkflowRun extends GHEventPayload {
        private GHWorkflowRun workflowRun;
        private GHWorkflow workflow;

        /**
         * Gets the workflow run.
         *
         * @return the workflow run
         */
        public GHWorkflowRun getWorkflowRun() {
            return workflowRun;
        }

        /**
         * Gets the associated workflow.
         *
         * @return the associated workflow
         */
        public GHWorkflow getWorkflow() {
            return workflow;
        }

        @Override
        void wrapUp(GitHub root) {
            super.wrapUp(root);
            if (workflowRun == null || workflow == null) {
                throw new IllegalStateException(
                        "Expected workflow and workflow_run payload, but got something else. Maybe we've got another type of event?");
            }
            GHRepository repository = getRepository();
            if (repository == null) {
                throw new IllegalStateException("Repository must not be null");
            }
            workflowRun.wrapUp(repository);
            workflow.wrapUp(repository);
        }
    }

    /**
     * A label was created, edited or deleted.
     *
     * @see <a href= "https://docs.github.com/en/developers/webhooks-and-events/webhook-events-and-payloads#label">
     *      label event</a>
     */
    public static class Label extends GHEventPayload {

        private GHLabel label;

        private GHLabelChanges changes;

        /**
         * Gets the label.
         *
         * @return the label
         */
        public GHLabel getLabel() {
            return label;
        }

        /**
         * Gets changes (for action="edited")
         *
         * @return changes
         */
        public GHLabelChanges getChanges() {
            return changes;
        }
    }
}
