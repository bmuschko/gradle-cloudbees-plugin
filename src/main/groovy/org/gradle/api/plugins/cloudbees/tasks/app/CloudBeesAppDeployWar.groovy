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
import com.cloudbees.api.BeesClient
import org.gradle.api.plugins.cloudbees.tasks.CloudBeesTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Optional

/**
 * Deploys a new version of an application using a WAR file.
 *
 * @author Benjamin Muschko
 */
class CloudBeesAppDeployWar extends CloudBeesTask {
	@Input String appId
	@Input @Optional String message
	@InputFile File warFile

	CloudBeesAppDeployWar() {
		super('Deploys a new version of an application using a WAR file.') 
	}

	@Override
	void executeAction(BeesClient client) {
		logger.quiet "Deploying WAR '${getWarFile()}' to application ID '${getAppId()}' with message '${getMessage()}'"
		ApplicationDeployArchiveResponse response = client.applicationDeployWar(getAppId(), null, getMessage(), getWarFile(), null, null)
		logger.quiet "Application uploaded successfully to: $response.url"
	}
}
