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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;

/**
 * The type GHBlob.
 *
 * @author Kanstantsin Shautsou
 * @author Kohsuke Kawaguchi
 * @see GHTreeEntry#asBlob() GHTreeEntry#asBlob()
 * @see GHRepository#getBlob(String) GHRepository#getBlob(String)
 * @see <a href="https://developer.github.com/v3/git/blobs/#get-a-blob">Get a blob</a>
 */
public class GHBlob {
    private String content, encoding, url, sha;
    private long size;

    /**
     * Gets url.
     *
     * @return API URL of this blob.
     */
    public URL getUrl() {
        return GitHubClient.parseURL(url);
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
     * Gets size.
     *
     * @return Number of bytes in this blob.
     */
    public long getSize() {
        return size;
    }

    /**
     * Gets encoding.
     *
     * @return the encoding
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * Gets content.
     *
     * @return Encoded content. You probably want {@link #read()}
     */
    public String getContent() {
        return content;
    }

    /**
     * Read input stream.
     *
     * @return the actual bytes of the blob.
     */
    public InputStream read() {
        if (encoding.equals("base64")) {
            try {
                Base64.Decoder decoder = Base64.getMimeDecoder();
                return new ByteArrayInputStream(decoder.decode(content));
            } catch (IllegalArgumentException e) {
                throw new AssertionError(e); // US-ASCII is mandatory
            }
        }

        throw new UnsupportedOperationException("Unrecognized encoding: " + encoding);
    }
}
