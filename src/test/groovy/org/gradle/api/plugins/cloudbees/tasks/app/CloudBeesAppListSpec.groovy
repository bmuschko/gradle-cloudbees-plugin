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

import com.cloudbees.api.ApplicationInfo
import com.cloudbees.api.ApplicationListResponse
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.cloudbees.client.CloudBeesClient
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * Specification for CloudBees application list task.
 *
 * @author Benjamin Muschko
 */
class CloudBeesAppListSpec extends Specification {
    static final TASK_NAME = 'cloudBeesAppList'
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
            Task task = project.task(TASK_NAME, type: CloudBeesAppList) {
                apiKey = 'myKey'
                secret = 'mySecret'
            }

            task.client = mockClient
            task.start()
        then:
            project.tasks.findByName(TASK_NAME) != null
            mockClient.applicationList() >> { throw new RuntimeException() }
            thrown(GradleException)
    }

    def "Executes task for success"() {
        expect:
            project.tasks.findByName(TASK_NAME) == null
        when:
            Task task = project.task(TASK_NAME, type: CloudBeesAppList) {
                apiKey = 'myKey'
                secret = 'mySecret'
            }

            task.client = mockClient
            task.start()
        then:
            project.tasks.findByName(TASK_NAME) != null
            1 * mockClient.applicationList() >> new ApplicationListResponse([new ApplicationInfo('123', 'MyApp', new Date(), 'hibernate', ['http://cloudbees.gradle-in-action.com/todo'] as String[])])
    }
}