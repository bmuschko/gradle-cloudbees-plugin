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
package org.gradle.api.plugins.cloudbees.tasks.db

import com.cloudbees.api.DatabaseInfo
import com.cloudbees.api.DatabaseListResponse
import org.gradle.api.plugins.cloudbees.client.CloudBeesClient
import org.gradle.api.plugins.cloudbees.tasks.CloudBeesTask

/**
 * Returns a list of all the databases associated with your account.
 *
 * @author Benjamin Muschko
 */
class CloudBeesDbList extends CloudBeesTask {
    CloudBeesDbList() {
        super('Returns a list of all the databases associated with your account.')
    }

    @Override
    void executeAction(CloudBeesClient client) {
        DatabaseListResponse response = client.databaseList()
        List<DatabaseInfo> infos = response.databases

        infos.each { info ->
            logger.quiet "    $info.name ($info.status)"
        }

        if(!infos) {
            logger.quiet "    No Databases found."
        }
    }
}
