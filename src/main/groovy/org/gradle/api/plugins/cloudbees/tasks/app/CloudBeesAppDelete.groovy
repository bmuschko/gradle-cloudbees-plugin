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

import com.cloudbees.api.ApplicationDeleteResponse
import com.cloudbees.api.BeesClient
import org.gradle.api.plugins.cloudbees.tasks.CloudBeesTask
import org.gradle.api.tasks.Input

/**
 * Deletes an application.
 *
 * @author Benjamin Muschko
 */
class CloudBeesAppDelete extends CloudBeesTask {
    @Input String appId

    CloudBeesAppDelete() {
        super('Deletes an application.')
    }

    @Override
    void executeAction(BeesClient client) {
        ApplicationDeleteResponse response = client.applicationDelete(getAppId())

        if(response.deleted) {
            logger.quiet "Application '${getAppId()}' was deleted successfully."
        }
        else {
            logger.quiet "Failed to delete application '${getAppId()}'."
        }
    }
}