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

import java.net.URL;
import java.util.Collection;
import java.util.List;

/**
 * Generated OAuth token
 *
 * @author janinko
 * @see GitHub#createToken(Collection, String, String) GitHub#createToken(Collection, String, String)
 * @see <a href="http://developer.github.com/v3/oauth/#create-a-new-authorization">API documentation</a>
 */
public class GHAuthorization extends GHObject {
    public static final String USER = "user";
    public static final String USER_EMAIL = "user:email";
    public static final String USER_FOLLOW = "user:follow";
    public static final String PUBLIC_REPO = "public_repo";
    public static final String REPO = "repo";
    public static final String REPO_STATUS = "repo:status";
    public static final String DELETE_REPO = "delete_repo";
    public static final String NOTIFICATIONS = "notifications";
    public static final String GIST = "gist";
    public static final String READ_HOOK = "read:repo_hook";
    public static final String WRITE_HOOK = "write:repo_hook";
    public static final String AMIN_HOOK = "admin:repo_hook";
    public static final String READ_ORG = "read:org";
    public static final String WRITE_ORG = "write:org";
    public static final String ADMIN_ORG = "admin:org";
    public static final String READ_KEY = "read:public_key";
    public static final String WRITE_KEY = "write:public_key";
    public static final String ADMIN_KEY = "admin:public_key";

    private List<String> scopes;
    private String token;
    private String token_last_eight;
    private String hashed_token;
    private App app;
    private String note;
    private String note_url;
    private String fingerprint;
    // TODO add some user class for https://developer.github.com/v3/oauth_authorizations/#check-an-authorization ?
    // private GHUser user;

    /**
     * Gets root.
     *
     * @return the root
     */
    public GitHub getRoot() {
        return root;
    }

    /**
     * Gets scopes.
     *
     * @return the scopes
     */
    public List<String> getScopes() {
        return scopes;
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Gets token last eight.
     *
     * @return the token last eight
     */
    public String getTokenLastEight() {
        return token_last_eight;
    }

    /**
     * Gets hashed token.
     *
     * @return the hashed token
     */
    public String getHashedToken() {
        return hashed_token;
    }

    /**
     * Gets app url.
     *
     * @return the app url
     */
    public URL getAppUrl() {
        return GitHubClient.parseURL(app.url);
    }

    /**
     * Gets app name.
     *
     * @return the app name
     */
    public String getAppName() {
        return app.name;
    }

    /**
     * Gets api url.
     *
     * @return the api url
     * @deprecated use {@link #getUrl()}
     */
    @Deprecated
    @SuppressFBWarnings(value = "NM_CONFUSING", justification = "It's a part of the library API, cannot be changed")
    public URL getApiURL() {
        return getUrl();
    }

    /**
     * @deprecated This object has no HTML URL.
     */
    @Override
    public URL getHtmlUrl() {
        return null;
    }

    /**
     * Gets note.
     *
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * Gets note url.
     *
     * @return the note url
     */
    public URL getNoteUrl() {
        return GitHubClient.parseURL(note_url);
    }

    /**
     * Gets fingerprint.
     *
     * @return the fingerprint
     */
    public String getFingerprint() {
        return fingerprint;
    }

    GHAuthorization wrap(GitHub root) {
        this.root = root;
        return this;
    }

    @SuppressFBWarnings(value = { "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD", "UWF_UNWRITTEN_FIELD" },
            justification = "JSON API")
    private static class App {
        private String url;
        private String name;
        // private String client_id; not yet used
    }
}
