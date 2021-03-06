/*
 * Build system for LensKit, and open-source recommender systems toolkit.
 * Copyright 2010-2014 Regents of the University of Minnesota and contributors
 * Work on LensKit has been funded by the National Science Foundation under
 * grants IIS 05-34939, 08-08692, 08-12148, and 10-17697.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the
 *   distribution.
 *
 * - Neither the name of the University of Minnesota nor the names of
 *   its contributors may be used to endorse or promote products derived
 *   from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.grouplens.lenskit.build

import org.gradle.util.ConfigureUtil

import java.util.regex.Pattern

/**
 * Extension object providing Travis environment utilities.
 */
class TravisExtension {
    boolean isActive() {
        return System.getenv('CI') == 'true'
    }

    String getRepo() {
        return System.getenv('TRAVIS_REPO_SLUG')
    }

    String getBranch() {
        return System.getenv('TRAVIS_BRANCH')
    }

    String getPullRequest() {
        def pr = System.getenv('TRAVIS_PULL_REQUEST')
        return pr == 'false' ? null : pr
    }

    String getActiveJdk() {
        return System.getenv('TRAVIS_JDK_VERSION')
    }

    Integer getBuildNumber() {
        return System.getenv('TRAVIS_BUILD_NUMBER')?.toInteger()
    }

    boolean isReleaseBuild() {
        if (!isActive()) {
            return false
        }
        if (pullRequest != null) {
            return false
        }
        return branch =~ /^(master|release\/[0-9.]+(?:-[A-Z0-9.+]?))$/
    }

    boolean isMasterBuild() {
        return isReleaseBuild() && branch == 'master'
    }

    void call(Closure block) {
        ConfigureUtil.configure(block, this)
    }
}
