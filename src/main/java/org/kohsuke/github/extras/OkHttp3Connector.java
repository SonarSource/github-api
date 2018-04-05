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
package org.kohsuke.github.extras;

import okhttp3.OkHttpClient;
import okhttp3.OkUrlFactory;
import org.kohsuke.github.HttpConnector;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * {@link HttpConnector} for {@link OkHttpClient}.
 * <p>
 * Unlike {@link #DEFAULT}, OkHttp does response caching. Making a conditional request against GitHubAPI and receiving a
 * 304 response does not count against the rate limit. See http://developer.github.com/v3/#conditional-requests
 *
 * @author Roberto Tyley
 * @author Kohsuke Kawaguchi
 * @see org.kohsuke.github.extras.okhttp3.OkHttpConnector
 */
@Deprecated
public class OkHttp3Connector implements HttpConnector {
    private final OkUrlFactory urlFactory;

    /**
     * Instantiates a new Ok http 3 connector.
     *
     * @param urlFactory
     *            the url factory
     */
    /*
     * @see org.kohsuke.github.extras.okhttp3.OkHttpConnector
     */
    @Deprecated
    public OkHttp3Connector(OkUrlFactory urlFactory) {
        this.urlFactory = urlFactory;
    }

    public HttpURLConnection connect(URL url) throws IOException {
        return urlFactory.open(url);
    }
}
