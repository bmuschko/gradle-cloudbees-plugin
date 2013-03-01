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

import com.cloudbees.api.BeesClient
import com.cloudbees.api.DatabaseInfo
import org.gradle.api.plugins.cloudbees.tasks.CloudBeesTask
import org.gradle.api.tasks.Input

/**
 * Returns information about connecting to a database.
 *
 * @author Benjamin Muschko
 */
class CloudBeesDbInfo extends CloudBeesTask {
    @Input String dbId

    CloudBeesDbInfo() {
        super('Returns information about connecting to a database.')
    }

    @Override
    void executeAction(BeesClient client) {
        DatabaseInfo info = client.databaseInfo(getDbId(), true)
        logger.quiet "Database Name: $info.name"
        logger.quiet "      created: $info.created"
        logger.quiet "        owner: $info.owner"
        logger.quiet "     username: $info.username"
        logger.quiet "     password: $info.password"
        logger.quiet "       master: $info.master"
        logger.quiet "         port: $info.port"
        logger.quiet "       slaves: $info.slaves"
        logger.quiet "       status: $info.status"
    }
}
