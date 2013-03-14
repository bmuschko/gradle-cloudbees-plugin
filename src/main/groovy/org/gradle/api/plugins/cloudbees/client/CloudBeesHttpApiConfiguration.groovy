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
package org.gradle.api.plugins.cloudbees.client

/**
 * CloudBees Http API configuration.
 *
 * @author benjamin
 */
class CloudBeesHttpApiConfiguration {
    final String apiUrl
    final String apiKey
    final String apiSecret
    final String apiFormat
    final String apiVersion

    CloudBeesHttpApiConfiguration(String apiUrl, String apiKey, String apiSecret, String apiFormat, String apiVersion) {
        this.apiUrl = apiUrl
        this.apiKey = apiKey
        this.apiSecret = apiSecret
        this.apiFormat = apiFormat
        this.apiVersion = apiVersion
    }
}