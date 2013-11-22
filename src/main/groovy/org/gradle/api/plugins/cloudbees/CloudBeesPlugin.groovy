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

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.WarPlugin
import org.gradle.api.plugins.cloudbees.tasks.CloudBeesTask
import org.gradle.api.plugins.cloudbees.tasks.app.*
import org.gradle.api.plugins.cloudbees.tasks.db.CloudBeesDbCreate
import org.gradle.api.plugins.cloudbees.tasks.db.CloudBeesDbDrop
import org.gradle.api.plugins.cloudbees.tasks.db.CloudBeesDbInfo
import org.gradle.api.plugins.cloudbees.tasks.db.CloudBeesDbList
import org.gradle.plugins.ear.EarPlugin

/**
 * CloudBees plugin for managing applications and databases on CloudBees RUN@cloud platform. The plugin hides the
 * complexity of having to deal with the communication between client and backend through CloudBees API. API-related
 * properties can be configured through extensions.
 *
 * @author Benjamin Muschko
 */
class CloudBeesPlugin implements Plugin<Project> {
    static final String EXTENSION_NAME = 'cloudBees'

    @Override
    void apply(Project project) {
        project.extensions.create(EXTENSION_NAME, CloudBeesPluginExtension)
        addTasks(project)
    }

    /**
     * Adds all tasks.
     *
     * @param project Project
     */
    private void addTasks(Project project) {
        configureParentTask(project)
        addAppTasks(project)
        addDbTasks(project)
    }

    /**
     * Configures parent task.
     *
     * @param project Project
     */
    private void configureParentTask(Project project) {
        project.tasks.withType(CloudBeesTask).whenTaskAdded { CloudBeesTask task ->
            def extension = project.extensions.findByName(EXTENSION_NAME)
            task.conventionMapping.apiFormat = { extension.apiFormat }
            task.conventionMapping.apiVersion = { extension.apiVersion }
            task.conventionMapping.apiUrl = { extension.apiUrl }
            task.conventionMapping.apiKey = { getApiKey(project) }
            task.conventionMapping.apiSecret = { getApiSecret(project) }
        }
    }

    /**
     * Adds application tasks.
     *
     * @param project Project
     */
    private void addAppTasks(Project project) {
        project.tasks.withType(CloudBeesAppChecksums).whenTaskAdded { task ->
            task.conventionMapping.appId = { getAppId(project) }
        }

        project.task('cloudBeesAppChecksums', type: CloudBeesAppChecksums)

        project.tasks.withType(CloudBeesAppDelete).whenTaskAdded { task ->
            task.conventionMapping.appId = { getAppId(project) }
        }

        project.task('cloudBeesAppDelete', type: CloudBeesAppDelete)

        project.tasks.withType(CloudBeesAppDeployEar).whenTaskAdded { task ->
            task.conventionMapping.appId = { getAppId(project) }
            task.conventionMapping.environment = { project.hasProperty('environment') ? project.environment : null }
            task.conventionMapping.message = { project.hasProperty('message') ? project.message : null }
            task.conventionMapping.earFile = { getEarFile(project) }
        }

        project.task('cloudBeesAppDeployEar', type: CloudBeesAppDeployEar)

        project.tasks.withType(CloudBeesAppDeployWar).whenTaskAdded { task ->
            task.conventionMapping.appId = { getAppId(project) }
            task.conventionMapping.environment = { project.hasProperty('environment') ? project.environment : null }
            task.conventionMapping.message = { project.hasProperty('message') ? project.message : null }
            task.conventionMapping.warFile = { getWarFile(project) }
        }

        project.task('cloudBeesAppDeployWar', type: CloudBeesAppDeployWar)

        project.tasks.withType(CloudBeesAppDeployArchive).whenTaskAdded { task ->
            task.conventionMapping.appId = { getAppId(project) }
            task.conventionMapping.environment = { project.hasProperty('environment') ? project.environment : null }
            task.conventionMapping.message = { project.hasProperty('message') ? project.message : null }
            task.conventionMapping.archiveFile = { getArchiveFile(project) }
            task.conventionMapping.archiveType = { getArchiveType(project) }
            task.conventionMapping.deltaDeploy = { getDeltaDeploy(project) }
            task.conventionMapping.parameters = { getParameters(project) }
        }

        project.task('cloudBeesAppDeployArchive', type: CloudBeesAppDeployArchive)

        project.tasks.withType(CloudBeesAppInfo).whenTaskAdded { task ->
            task.conventionMapping.appId = { getAppId(project) }
        }

        project.task('cloudBeesAppInfo', type: CloudBeesAppInfo)
        project.task('cloudBeesAppList', type: CloudBeesAppList)

        project.tasks.withType(CloudBeesAppRestart).whenTaskAdded { task ->
            task.conventionMapping.appId = { getAppId(project) }
        }

        project.task('cloudBeesAppRestart', type: CloudBeesAppRestart)

        project.tasks.withType(CloudBeesAppStart).whenTaskAdded { task ->
            task.conventionMapping.appId = { getAppId(project) }
        }

        project.task('cloudBeesAppStart', type: CloudBeesAppStart)

        project.tasks.withType(CloudBeesAppStop).whenTaskAdded { task ->
            task.conventionMapping.appId = { getAppId(project) }
        }

        project.task('cloudBeesAppStop', type: CloudBeesAppStop)

        project.tasks.withType(CloudBeesAppTail).whenTaskAdded { task ->
            task.conventionMapping.appId = { getAppId(project) }
            task.conventionMapping.log = { project.hasProperty('log') ? project.log : null }
        }

        project.task('cloudBeesAppTail', type: CloudBeesAppTail)
    }

    /**
     * Adds database tasks.
     *
     * @param project Project
     */
    private void addDbTasks(Project project) {
        project.tasks.withType(CloudBeesDbInfo).whenTaskAdded { task ->
            task.conventionMapping.dbId = { getDbId(project) }
        }

        project.task('cloudBeesDbInfo', type: CloudBeesDbInfo)
        project.task('cloudBeesDbList', type: CloudBeesDbList)

        project.tasks.withType(CloudBeesDbDrop).whenTaskAdded { task ->
            task.conventionMapping.dbId = { getDbId(project) }
        }

        project.task('cloudBeesDbDrop', type: CloudBeesDbDrop)

        project.tasks.withType(CloudBeesDbCreate).whenTaskAdded { task ->
            task.conventionMapping.dbId = { getDbId(project) }
            task.conventionMapping.account = { project.hasProperty('account') ? project.account : null }
            task.conventionMapping.username = { project.hasProperty('username') ? project.username : null }
            task.conventionMapping.password = { project.hasProperty('password') ? project.password : null }
        }

        project.task('cloudBeesDbCreate', type: CloudBeesDbCreate)
    }

    /**
     * Gets API key from project property "cloudbees.api.key". If the property doesn't exist
     * the extension's value is used.
     *
     * @param project Project
     * @return API key
     */
    private String getApiKey(Project project) {
        project.hasProperty('cloudbees.api.key') ? project.property('cloudbees.api.key') : project.extensions.findByName(EXTENSION_NAME).apiKey
    }

    /**
     * Gets API secret from project property "cloudbees.api.secret". If the property doesn't exist
     * the extension's value is used.
     *
     * @param project Project
     * @return API key
     */
    private String getApiSecret(Project project) {
        project.hasProperty('cloudbees.api.secret') ? project.property('cloudbees.api.secret') : project.extensions.findByName(EXTENSION_NAME).apiSecret
    }

    /**
     * Gets application ID from project property "appId". If the property doesn't exist
     * the extension's value is used.
     *
     * @param project Project
     * @return Application ID
     */
    private String getAppId(Project project) {
        project.hasProperty('appId') ? project.appId : project.extensions.findByName(EXTENSION_NAME).appId
    }

    /**
     * Gets database ID from project property "dbId". If the property doesn't exist
     * the extension's value is used.
     *
     * @param project Project
     * @return Database ID
     */
    private String getDbId(Project project) {
        project.hasProperty('dbId') ? project.dbId : project.extensions.findByName(EXTENSION_NAME).dbId
    }

    /**
     * Gets EAR archive path from project property "earFile". If the property doesn't exist
     * the web module's archive path is used.
     *
     * @param project
     * @return EAR file
     */
    private File getEarFile(Project project) {
        project.hasProperty('earFile') ? new File(project.property('earFile')) : getEarPluginArchive(project)
    }

    /**
     * Gets the project's EAR file if War plugin is applied; otherwise return null.
     *
     * @param project Project
     * @return EAR file
     */
    private File getEarPluginArchive(Project project) {
        project.plugins.hasPlugin(EarPlugin) ? project.tasks.getByName(EarPlugin.EAR_TASK_NAME).archivePath : null
    }

    /**
     * Gets WAR archive path from project property "warFile". If the property doesn't exist
     * the web module's archive path is used.
     *
     * @param project
     * @return WAR file
     */
    private File getWarFile(Project project) {
        project.hasProperty('warFile') ? new File(project.property('warFile')) : getWarPluginArchive(project)
    }

    /**
     * Gets the project's WAR file if War plugin is applied; otherwise return null.
     *
     * @param project Project
     * @return WAR file
     */
    private File getWarPluginArchive(Project project) {
         project.plugins.hasPlugin(WarPlugin) ? project.tasks.getByName(WarPlugin.WAR_TASK_NAME).archivePath : null
    }

    /**
     * Gets archive path from project property "archiveFile". If the property doesn't exist
     * the extension's value is used.
     *
     * @param project
     * @return Archive file
     */
    private File getArchiveFile(Project project) {
        project.hasProperty('archiveFile') ? new File(project.property('archiveFile')) : project.extensions.findByName(EXTENSION_NAME).archiveFile
    }

    /**
     * Gets archive type from the extension.
     *
     * @param project
     * @return Archive type
     */
    private String getArchiveType(Project project) {
        project.extensions.findByName(EXTENSION_NAME).archiveType
    }

    /**
     * Gets parameters from extension
     *
     * @param project
     * @return Archive type
     */
    private boolean getDeltaDeploy(Project project) {
        project.extensions.findByName(EXTENSION_NAME).deltaDeploy
    }

    /**
     * Gets parameters from extension
     *
     * @param project
     * @return Archive type
     */
    private Map<String, String> getParameters(Project project) {
        project.extensions.findByName(EXTENSION_NAME).parameters
    }
}
