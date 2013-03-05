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

import com.cloudbees.api.ApplicationCheckSumsResponse
import org.gradle.api.plugins.cloudbees.api.CloudBeesClient
import org.gradle.api.plugins.cloudbees.tasks.CloudBeesTask
import org.gradle.api.tasks.Input

/**
 * Returns the checksums for an application.
 *
 * @author Benjamin Muschko
 */
class CloudBeesAppChecksums extends CloudBeesTask {
    @Input String appId

    CloudBeesAppChecksums() {
        super('Returns the checksums for an application.')
    }

    @Override
    void executeAction(CloudBeesClient client) {
        ApplicationCheckSumsResponse response = client.applicationCheckSums(getAppId())
        logger.quiet "Application CheckSums for ${getAppId()}:"
        Map<String, Long> checksums = response.checkSums

        checksums.each { key, value ->
            logger.quiet "    ${value.toString()?.padLeft(10)} : $key"
        }

        if(!checksums){
            logger.quiet "No checksums found. Is '${getAppId()}' a valid application?"
        }
    }
}
