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

import com.cloudbees.api.ApplicationStatusResponse
import org.gradle.api.plugins.cloudbees.api.CloudBeesClient
import org.gradle.api.plugins.cloudbees.tasks.CloudBeesTask
import org.gradle.api.tasks.Input

/**
 * Starts all deployed instances of an application.
 *
 * @author Benjamin Muschko
 */
class CloudBeesAppStart extends CloudBeesTask {
    @Input String appId

    CloudBeesAppStart() {
        super('Starts all deployed instances of an application.')
    }

    @Override
    void executeAction(CloudBeesClient client) {
        ApplicationStatusResponse response = client.applicationStart(getAppId())

        if(response.status == 'success') {
            logger.quiet "Application '${getAppId()}' was started successfully."
        }
        else {
            logger.error "Failed to start application '${getAppId()}'."
        }
    }
}
