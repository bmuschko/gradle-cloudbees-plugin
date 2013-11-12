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
import com.cloudbees.api.UploadProgress
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.cloudbees.client.CloudBeesClient
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * Specification for CloudBees application Archive deployment task.
 *
 * @author M. Sean Gilligan
 * @author Benjamin Muschko
 */
class CloudBeesAppDeployArchiveSpec extends Specification {
    static final TASK_NAME = 'cloudBeesAppDeployArchive'
    Project project
    CloudBeesClient mockClient

    def setup() {
        project = ProjectBuilder.builder().build()
        mockClient = Mock(CloudBeesClient)
    }

    def "Executes task for thrown exception"() {
        expect:
            project.tasks.findByName(TASK_NAME) == null
        when:
            Task task = project.task(TASK_NAME, type: CloudBeesAppDeployArchive) {
                appId = 'gradle-in-action/todo'
                environment = 'prod'
                archiveFile = new File('todo.zip')
                archiveType = 'zip'
                parameters = [containerType: 'java']
                apiKey = 'myKey'
                secret = 'mySecret'
                parameters = [containerType: 'java']
            }

            task.client = mockClient
            task.start()
        then:
            project.tasks.findByName(TASK_NAME) != null
            mockClient.applicationDeployArchive('gradle-in-action/todo', 'prod', null, new File('todo.zip'), null, 'zip', false, [containerType: 'java'], _ as UploadProgress) >> { throw new RuntimeException() }
            thrown(GradleException)
    }

    def "Executes task for success"() {
        expect:
            project.tasks.findByName(TASK_NAME) == null
        when:
            Task task = project.task(TASK_NAME, type: CloudBeesAppDeployArchive) {
                appId = 'gradle-in-action/todo'
                environment = 'prod'
                archiveFile = new File('todo.zip')
                archiveType = 'zip'
                parameters = [containerType: 'java']
                apiKey = 'myKey'
                secret = 'mySecret'
            }

            task.client = mockClient
            task.start()
        then:
            project.tasks.findByName(TASK_NAME) != null
            1 * mockClient.applicationDeployArchive('gradle-in-action/todo', 'prod', null, new File('todo.zip'), null, 'zip', false, [containerType: 'java'], _ as UploadProgress) >> new ApplicationDeployArchiveResponse()
    }
}
