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
 * Repository clone statistics.
 *
 * @see GHRepository#getCloneTraffic() GHRepository#getCloneTraffic()
 */
public class GHRepositoryCloneTraffic extends GHRepositoryTraffic {
    private List<DailyInfo> clones;

    GHRepositoryCloneTraffic() {
    }

    GHRepositoryCloneTraffic(Integer count, Integer uniques, List<DailyInfo> clones) {
        super(count, uniques);
        this.clones = clones;
    }

    /**
     * Gets clones.
     *
     * @return the clones
     */
    public List<DailyInfo> getClones() {
        return clones;
    }

    public List<DailyInfo> getDailyInfo() {
        return getClones();
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
