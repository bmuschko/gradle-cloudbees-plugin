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
package org.gradle.api.plugins.cloudbees.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.plugins.cloudbees.api.CloudBeesClient
import org.gradle.api.plugins.cloudbees.api.CloudBeesHttpApiClient
import org.gradle.api.plugins.cloudbees.api.DefaultApiConfig
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

/**
 * Base CloudBees task which exposes the essential properties for API communication and the actual instantiation
 * and exception handling for it.
 *
 * @author Benjamin Muschko
 */
abstract class CloudBeesTask extends DefaultTask {
    @Input String apiFormat = DefaultApiConfig.FORMAT.value
    @Input String apiVersion = DefaultApiConfig.VERSION.value
    @Input String apiUrl = DefaultApiConfig.URL.value
    @Input String apiKey
    @Input String secret
    CloudBeesClient client

    CloudBeesTask(String description) {
        this.description = description
        group = 'CloudBees'
        client = new CloudBeesHttpApiClient(getApiUrl(), getApiKey(), getSecret(), getApiFormat(), getApiVersion())
    }

    @TaskAction
    void start() {
        withExceptionHandling {
            executeAction(client)
        }
    }

    private void withExceptionHandling(Closure c) {
        try {
            c()
        }
        catch(Exception e) {
            throw new GradleException('Failed to executed CloudBees task', e)
        }
    }

    abstract void executeAction(CloudBeesClient client)
}