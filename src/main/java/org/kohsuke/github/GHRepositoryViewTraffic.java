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

import java.util.List;

/**
 * Repository view statistics.
 *
 * @see GHRepository#getViewTraffic() GHRepository#getViewTraffic()
 */
public class GHRepositoryViewTraffic extends GHRepositoryTraffic {
    private List<DailyInfo> views;

    GHRepositoryViewTraffic() {
    }

    GHRepositoryViewTraffic(int count, int uniques, List<DailyInfo> views) {
        super(count, uniques);
        this.views = views;
    }

    /**
     * Gets views.
     *
     * @return the views
     */
    public List<DailyInfo> getViews() {
        return views;
    }

    public List<DailyInfo> getDailyInfo() {
        return getViews();
    }

    /**
     * The type DailyInfo.
     */
    public static class DailyInfo extends GHRepositoryTraffic.DailyInfo {
        DailyInfo() {
        }

        DailyInfo(String timestamp, int count, int uniques) {
            super(timestamp, count, uniques);
        }
    }
}
