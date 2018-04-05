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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The GitHub Preview API's license information
 *
 * @author Duncan Dickinson
 * @see GitHub#getLicense(String) GitHub#getLicense(String)
 * @see GHRepository#getLicense() GHRepository#getLicense()
 * @see <a href="https://developer.github.com/v3/licenses/">https://developer.github.com/v3/licenses/</a>
 */
@SuppressWarnings({ "UnusedDeclaration" })
@SuppressFBWarnings(value = { "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD", "UWF_UNWRITTEN_FIELD", "NP_UNWRITTEN_FIELD" },
        justification = "JSON API")
public class GHLicense extends GHObject {

    // these fields are always present, even in the short form
    protected String key, name;

    // the rest is only after populated
    protected Boolean featured;

    protected String html_url, description, category, implementation, body;

    protected List<String> required = new ArrayList<String>();
    protected List<String> permitted = new ArrayList<String>();
    protected List<String> forbidden = new ArrayList<String>();

    /**
     * Gets key.
     *
     * @return a mnemonic for the license
     */
    public String getKey() {
        return key;
    }

    /**
     * Gets name.
     *
     * @return the license name
     */
    public String getName() {
        return name;
    }

    /**
     * Featured licenses are bold in the new repository drop-down
     *
     * @return True if the license is featured, false otherwise
     * @throws IOException
     *             the io exception
     */
    public Boolean isFeatured() throws IOException {
        populate();
        return featured;
    }

    public URL getHtmlUrl() throws IOException {
        populate();
        return GitHubClient.parseURL(html_url);
    }

    /**
     * Gets description.
     *
     * @return the description
     * @throws IOException
     *             the io exception
     */
    public String getDescription() throws IOException {
        populate();
        return description;
    }

    /**
     * Gets category.
     *
     * @return the category
     * @throws IOException
     *             the io exception
     */
    public String getCategory() throws IOException {
        populate();
        return category;
    }

    /**
     * Gets implementation.
     *
     * @return the implementation
     * @throws IOException
     *             the io exception
     */
    public String getImplementation() throws IOException {
        populate();
        return implementation;
    }

    /**
     * Gets required.
     *
     * @return the required
     * @throws IOException
     *             the io exception
     */
    public List<String> getRequired() throws IOException {
        populate();
        return required;
    }

    /**
     * Gets permitted.
     *
     * @return the permitted
     * @throws IOException
     *             the io exception
     */
    public List<String> getPermitted() throws IOException {
        populate();
        return permitted;
    }

    /**
     * Gets forbidden.
     *
     * @return the forbidden
     * @throws IOException
     *             the io exception
     */
    public List<String> getForbidden() throws IOException {
        populate();
        return forbidden;
    }

    /**
     * Gets body.
     *
     * @return the body
     * @throws IOException
     *             the io exception
     */
    public String getBody() throws IOException {
        populate();
        return body;
    }

    /**
     * Fully populate the data by retrieving missing data.
     * <p>
     * Depending on the original API call where this object is created, it may not contain everything.
     *
     * @throws IOException
     *             the io exception
     */
    protected synchronized void populate() throws IOException {
        if (description != null)
            return; // already populated

        if (root == null || root.isOffline()) {
            return; // cannot populate, will have to live with what we have
        }

        URL url = getUrl();
        if (url != null) {
            root.createRequest().setRawUrlPath(url.toString()).fetchInto(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof GHLicense))
            return false;

        GHLicense that = (GHLicense) o;
        return Objects.equals(getUrl(), that.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUrl());
    }

    GHLicense wrap(GitHub root) {
        this.root = root;
        return this;
    }
}
