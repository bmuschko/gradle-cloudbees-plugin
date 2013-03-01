/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.api.plugins.cloudbees.tasks.app

import com.cloudbees.api.ApplicationListResponse
import com.cloudbees.api.BeesClient
import org.gradle.api.plugins.cloudbees.tasks.CloudBeesTask

/**
 * Returns the list of applications available to your account.
 *
 * @author Benjamin Muschko
 */
class CloudBeesAppList extends CloudBeesTask {
    CloudBeesAppList() {
        super('Returns the list of applications available to your account.')
    }

    @Override
    void executeAction(BeesClient client) {
        ApplicationListResponse response = client.applicationList()
        def infos = response.applications

        if(infos.size() > 0) {
            logger.quiet 'Application List:'

            infos.each { info ->
                logger.quiet "    $info.title ($info.status)"
            }
        }
        else {
            logger.quiet 'No applications found.'
        }
    }
}