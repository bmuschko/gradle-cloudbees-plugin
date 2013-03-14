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
import org.gradle.api.plugins.cloudbees.client.CloudBeesClient
import org.gradle.api.plugins.cloudbees.client.CloudBeesHttpApiClient
import org.gradle.api.plugins.cloudbees.client.CloudBeesHttpApiConfiguration
import org.gradle.api.plugins.cloudbees.client.DefaultHttpApiConfig
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

/**
 * Base CloudBees task which exposes the essential properties for API communication and the actual instantiation
 * and exception handling for it.
 *
 * @author Benjamin Muschko
 */
abstract class CloudBeesTask extends DefaultTask {
    /**
     * CloudBees API format. Defaults to "xml" if not set.
     */
    @Input
    String apiFormat = DefaultHttpApiConfig.FORMAT.value

    /**
     * CloudBees API version. Defaults to "1.0" if not set.
     */
    @Input
    String apiVersion = DefaultHttpApiConfig.VERSION.value

    /**
     * CloudBees API URL. Defaults to "https://api.cloudbees.com/api" if not set.
     */
    @Input
    String apiUrl = DefaultHttpApiConfig.URL.value

    /**
     * CloudBees API key.
     */
    @Input
    String apiKey

    /**
     * CloudBees API secret.
     */
    @Input
    String apiSecret

    CloudBeesClient client

    CloudBeesTask(String description) {
        this.description = description
        group = 'CloudBees'
        client = new CloudBeesHttpApiClient()
    }

    @TaskAction
    void start() {
        withExceptionHandling {
            client.setConfiguration(new CloudBeesHttpApiConfiguration(getApiUrl(), getApiKey(), getApiSecret(), getApiFormat(), getApiVersion()))
            executeAction(client)
        }
    }

    private void withExceptionHandling(Closure c) {
        try {
            c()
        }
        catch(Exception e) {
            throw new GradleException('Failed to execute CloudBees task', e)
        }
    }

    abstract void executeAction(CloudBeesClient client)
}