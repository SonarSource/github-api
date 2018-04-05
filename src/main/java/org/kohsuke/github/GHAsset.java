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

/**
 * Asset in a release.
 *
 * @see GHRelease#getAssets() GHRelease#getAssets()
 */
public class GHAsset extends GHObject {
    GHRepository owner;
    private String name;
    private String label;
    private String state;
    private String content_type;
    private long size;
    private long download_count;
    private String browser_download_url;

    /**
     * Gets content type.
     *
     * @return the content type
     */
    public String getContentType() {
        return content_type;
    }

    /**
     * Sets content type.
     *
     * @param contentType
     *            the content type
     * @throws IOException
     *             the io exception
     */
    public void setContentType(String contentType) throws IOException {
        edit("content_type", contentType);
        this.content_type = contentType;
    }

    /**
     * Gets download count.
     *
     * @return the download count
     */
    public long getDownloadCount() {
        return download_count;
    }

    /**
     * Gets label.
     *
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets label.
     *
     * @param label
     *            the label
     * @throws IOException
     *             the io exception
     */
    public void setLabel(String label) throws IOException {
        edit("label", label);
        this.label = label;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
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
     * Gets size.
     *
     * @return the size
     */
    public long getSize() {
        return size;
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @deprecated This object has no HTML URL.
     */
    @Override
    public URL getHtmlUrl() {
        return null;
    }

    /**
     * Gets browser download url.
     *
     * @return the browser download url
     */
    public String getBrowserDownloadUrl() {
        return browser_download_url;
    }

    private void edit(String key, Object value) throws IOException {
        root.createRequest().with(key, value).method("PATCH").withUrlPath(getApiRoute()).send();
    }

    /**
     * Delete.
     *
     * @throws IOException
     *             the io exception
     */
    public void delete() throws IOException {
        root.createRequest().method("DELETE").withUrlPath(getApiRoute()).send();
    }

    private String getApiRoute() {
        return "/repos/" + owner.getOwnerName() + "/" + owner.getName() + "/releases/assets/" + getId();
    }

    GHAsset wrap(GHRelease release) {
        this.owner = release.getOwner();
        this.root = owner.root;
        return this;
    }

    /**
     * Wrap gh asset [ ].
     *
     * @param assets
     *            the assets
     * @param release
     *            the release
     * @return the gh asset [ ]
     */
    public static GHAsset[] wrap(GHAsset[] assets, GHRelease release) {
        for (GHAsset aTo : assets) {
            aTo.wrap(release);
        }
        return assets;
    }
}
