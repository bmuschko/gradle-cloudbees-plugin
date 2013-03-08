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
package org.gradle.api.plugins.cloudbees

import org.gradle.api.plugins.cloudbees.client.DefaultHttpApiConfig

/**
 * CloudBees plugin extension model.
 *
 * @author Benjamin Muschko
 */
class CloudBeesPluginExtension {
    /**
     * CloudBees API format. Defaults to "xml" if not set.
     */
    String apiFormat = DefaultHttpApiConfig.FORMAT.value

    /**
     * CloudBees API version. Defaults to "1.0" if not set.
     */
    String apiVersion = DefaultHttpApiConfig.VERSION.value

    /**
     * CloudBees API URL. Defaults to "https://api.cloudbees.com/api" if not set.
     */
    String apiUrl = DefaultHttpApiConfig.URL.value

    /**
     * CloudBees API key.
     */
    String apiKey

    /**
     * CloudBees API secret.
     */
    String apiSecret

    /**
     * Application identifier.
     */
    String appId

    /**
     * Database identifier.
     */
    String dbId
}
