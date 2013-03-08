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

import com.cloudbees.api.DatabaseCreateResponse
import org.gradle.api.plugins.cloudbees.client.CloudBeesClient
import org.gradle.api.plugins.cloudbees.tasks.CloudBeesTask
import org.gradle.api.tasks.Input

/**
 * Creates a new database.
 *
 * @author Benjamin Muschko
 */
class CloudBeesDbCreate extends CloudBeesTask {
    /**
     * Database identifier.
     */
    @Input
    String dbId

    /**
     * Database username.
     */
    @Input
    String username

    /**
     * Database password.
     */
    @Input
    String password

    /**
     * Account used for database creation.
     */
    @Input
    String account

    CloudBeesDbCreate() {
        super('Creates a new database.')
    }

    @Override
    void executeAction(CloudBeesClient client) {
        DatabaseCreateResponse response = client.databaseCreate(getAccount(), getDbId(), getUsername(), getPassword())

        if(response.databaseId) {
            logger.quiet "Database '${getDbId()}' was created successfully."
        }
        else {
            logger.quiet "Failed to create database '${getDbId()}'."
        }
    }
}
