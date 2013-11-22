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

import com.cloudbees.api.ApplicationDeployArchiveResponse
import com.cloudbees.api.HashWriteProgress
import org.gradle.api.plugins.cloudbees.client.CloudBeesClient
import org.gradle.api.plugins.cloudbees.tasks.CloudBeesTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Optional

/**
 * Deploys a new version of an application using an Archive file of specified type.
 * (e.g. 'zip' or 'jar')
 *
 * @author M. Sean Gilligan
 * @author Benjamin Muschko
 */
class CloudBeesAppDeployArchive extends CloudBeesTask {
    /**
     * Application identifier.
     */
    @Input
    String appId

    /**
     * CloudBees environment to deploy Archive file to.
     */
    @Input
    @Optional
    String environment

    /**
     * Deployment message e.g. version number.
     */
    @Input
    @Optional
    String message

    /**
     * Archive type.  (e.g. 'zip' or 'jar').
     */
    @Input
    String archiveType

    /**
     * Archive file to deploy.
     */
    @InputFile
    File archiveFile

    /**
     * Source file.
     */
    @InputFile
    @Optional
    File srcFile

    /**
     * deltaDeploy
     */
    @Input
    boolean deltaDeploy

    /**
     * Parameters (e.g. [containerType: "java"])
     */
    @Input
    @Optional
    Map<String, String> parameters

    CloudBeesAppDeployArchive() {
        super('Deploys a new version of an application using an Archive file.')
    }

    @Override
    void executeAction(CloudBeesClient client) {
        logger.quiet "Deploying Archive '${getArchiveFile()}' (${getArchiveType()}, delta=${getDeltaDeploy()}) to application ID '${getAppId()}' with parameters ${getParameters()} and message '${getMessage()}'"
//        String archiveType = "zip";
//        boolean deltaDeploy = false;
//        Map<String, String> parameters = [containerType: "java"];
        ApplicationDeployArchiveResponse response = client.applicationDeployArchive(getAppId(), getEnvironment(), getMessage(), getArchiveFile(), getSrcFile(), getArchiveType(), getDeltaDeploy(), getParameters(), new HashWriteProgress())
        logger.quiet "Application uploaded successfully to: $response.url"
    }
}
