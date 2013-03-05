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

import com.cloudbees.api.ApplicationStatusResponse
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.cloudbees.api.CloudBeesClient
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * Specification for CloudBees application start task.
 *
 * @author Benjamin Muschko
 */
class CloudBeesAppStartSpec extends Specification {
    static final TASK_NAME = 'cloudBeesAppStart'
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
            Task task = project.task(TASK_NAME, type: CloudBeesAppStart) {
                appId = 'gradle-in-action/todo'
                apiKey = 'myKey'
                secret = 'mySecret'
            }

            task.client = mockClient
            task.start()
        then:
            project.tasks.findByName(TASK_NAME) != null
            mockClient.applicationStart('gradle-in-action/todo') >> { throw new RuntimeException() }
            thrown(GradleException)
    }

    def "Executes task for success with database restart"() {
        expect:
            project.tasks.findByName(TASK_NAME) == null
        when:
            Task task = project.task(TASK_NAME, type: CloudBeesAppStart) {
                appId = 'gradle-in-action/todo'
                apiKey = 'myKey'
                secret = 'mySecret'
            }

            task.client = mockClient
            task.start()
        then:
            project.tasks.findByName(TASK_NAME) != null
            1 * mockClient.applicationStart('gradle-in-action/todo') >> new ApplicationStatusResponse('success')
    }

    def "Executes task for success with failed database restart"() {
        expect:
            project.tasks.findByName(TASK_NAME) == null
        when:
            Task task = project.task(TASK_NAME, type: CloudBeesAppStart) {
                appId = 'gradle-in-action/todo'
                apiKey = 'myKey'
                secret = 'mySecret'
            }

            task.client = mockClient
            task.start()
        then:
            project.tasks.findByName(TASK_NAME) != null
            1 * mockClient.applicationStart('gradle-in-action/todo') >> new ApplicationStatusResponse('failure')
    }
}