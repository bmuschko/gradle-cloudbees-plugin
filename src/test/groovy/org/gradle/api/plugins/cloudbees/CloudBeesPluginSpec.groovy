package org.gradle.api.plugins.cloudbees

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.WarPlugin
import org.gradle.api.plugins.cloudbees.CloudBeesPlugin
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class CloudBeesPluginSpec extends Specification {
    static final List<String> APP_TASK_NAMES
    static final List<String> DB_TASK_NAMES
    static final List<String> ALL_TASK_NAMES
    Project project

    static {
        APP_TASK_NAMES = ['cloudBeesAppChecksums', 'cloudBeesAppDelete', 'cloudBeesAppDeployWar', 'cloudBeesAppInfo',
                          'cloudBeesAppList', 'cloudBeesAppRestart', 'cloudBeesAppStart', 'cloudBeesAppStop',
                          'cloudBeesAppTail'].asImmutable()
        DB_TASK_NAMES = ['cloudBeesDbInfo', 'cloudBeesDbList', 'cloudBeesDbDrop', 'cloudBeesDbCreate'].asImmutable()
        ALL_TASK_NAMES = APP_TASK_NAMES + DB_TASK_NAMES
    }

    def setup() {
        project = ProjectBuilder.builder().build()
    }

    def "Applies plugin and checks created tasks"() {
        expect:
            ALL_TASK_NAMES.each {
                project.tasks.findByName(it) == null
            }
        when:
            project.apply plugin: 'cloudbees'
        then:
            project.plugins.hasPlugin(WarPlugin)
            project.extensions.findByName(CloudBeesPlugin.EXTENSION_NAME) != null

            ALL_TASK_NAMES.each {
                project.tasks.findByName(it) != null
            }
    }

    def "Applies plugin for sample task without custom extension configuration"() {
        expect:
            ALL_TASK_NAMES.each {
                project.tasks.findByName(it) == null
            }
        when:
            project.apply plugin: 'cloudbees'
        then:
            project.plugins.hasPlugin(WarPlugin)
            project.extensions.findByName(CloudBeesPlugin.EXTENSION_NAME) != null

            Task task = project.tasks.findByName('cloudBeesAppList')
            task != null
            task.apiKey ==  null
            task.secret == null
            task.apiUrl == 'https://api.cloudbees.com/api'
            task.apiFormat == 'xml'
            task.apiVersion == '1.0'
    }

    def "Applies plugin and configures sample task through extension"() {
        expect:
            ALL_TASK_NAMES.each {
                project.tasks.findByName(it) == null
            }
        when:
            project.apply plugin: 'cloudbees'

            project.cloudBees {
                apiKey = 'myKey'
                secret = 'mySecret'
                apiUrl = 'http://myawesome.api.com'
                apiFormat = 'json'
                apiVersion = '2.2'
            }
        then:
            project.plugins.hasPlugin(WarPlugin)
            project.extensions.findByName(CloudBeesPlugin.EXTENSION_NAME) != null

            Task task = project.tasks.findByName('cloudBeesAppList')
            task != null
            task.apiKey == 'myKey'
            task.secret == 'mySecret'
            task.apiUrl == 'http://myawesome.api.com'
            task.apiFormat == 'json'
            task.apiVersion == '2.2'
    }

    def "Applies plugin and configures cloudBeesAppChecksums task"() {
        when:
            project.apply plugin: 'cloudbees'

            project.cloudBees {
                appId = 'gradle-in-action/todo'
            }
        then:
            project.plugins.hasPlugin(WarPlugin)
            project.extensions.findByName(CloudBeesPlugin.EXTENSION_NAME) != null

            Task task = project.tasks.findByName('cloudBeesAppChecksums')
            task != null
            task.apiKey ==  null
            task.secret == null
            task.apiUrl == 'https://api.cloudbees.com/api'
            task.apiFormat == 'xml'
            task.apiVersion == '1.0'
            task.appId == 'gradle-in-action/todo'
    }

    def "Applies plugin and configures cloudBeesAppDelete task"() {
        when:
            project.apply plugin: 'cloudbees'

            project.cloudBees {
                appId = 'gradle-in-action/todo'
            }
        then:
            project.plugins.hasPlugin(WarPlugin)
            project.extensions.findByName(CloudBeesPlugin.EXTENSION_NAME) != null

            Task task = project.tasks.findByName('cloudBeesAppDelete')
            task != null
            task.apiKey ==  null
            task.secret == null
            task.apiUrl == 'https://api.cloudbees.com/api'
            task.apiFormat == 'xml'
            task.apiVersion == '1.0'
            task.appId == 'gradle-in-action/todo'
    }

    def "Applies plugin and configures cloudBeesAppDeployWar task for external WAR file"() {
        when:
            project.apply plugin: 'cloudbees'

            project.cloudBees {
                appId = 'gradle-in-action/todo'
            }

            project.ext.message = 'v0.1'
            project.ext.warFile = 'todo.war'
        then:
            project.plugins.hasPlugin(WarPlugin)
            project.extensions.findByName(CloudBeesPlugin.EXTENSION_NAME) != null

            Task task = project.tasks.findByName('cloudBeesAppDeployWar')
            task != null
            task.apiKey ==  null
            task.secret == null
            task.apiUrl == 'https://api.cloudbees.com/api'
            task.apiFormat == 'xml'
            task.apiVersion == '1.0'
            task.appId == 'gradle-in-action/todo'
            task.message == 'v0.1'
            task.warFile == new File('todo.war')
    }

    def "Applies plugin and configures cloudBeesAppDeployWar task for produced project WAR file"() {
        when:
            project.apply plugin: 'cloudbees'

            project.cloudBees {
                appId = 'gradle-in-action/todo'
            }

            project.ext.message = 'v0.1'
        then:
            project.plugins.hasPlugin(WarPlugin)
            project.extensions.findByName(CloudBeesPlugin.EXTENSION_NAME) != null

            Task task = project.tasks.findByName('cloudBeesAppDeployWar')
            task != null
            task.apiKey ==  null
            task.secret == null
            task.apiUrl == 'https://api.cloudbees.com/api'
            task.apiFormat == 'xml'
            task.apiVersion == '1.0'
            task.appId == 'gradle-in-action/todo'
            task.message == 'v0.1'
            task.warFile == new File("$project.buildDir/libs", 'test.war')
    }

    def "Applies plugin and configures cloudBeesAppInfo task"() {
        when:
            project.apply plugin: 'cloudbees'

            project.cloudBees {
                appId = 'gradle-in-action/todo'
            }
        then:
            project.plugins.hasPlugin(WarPlugin)
            project.extensions.findByName(CloudBeesPlugin.EXTENSION_NAME) != null

            Task task = project.tasks.findByName('cloudBeesAppInfo')
            task != null
            task.apiKey ==  null
            task.secret == null
            task.apiUrl == 'https://api.cloudbees.com/api'
            task.apiFormat == 'xml'
            task.apiVersion == '1.0'
            task.appId == 'gradle-in-action/todo'
    }

    def "Applies plugin and configures cloudBeesAppRestart task"() {
        when:
            project.apply plugin: 'cloudbees'

            project.cloudBees {
                appId = 'gradle-in-action/todo'
            }
        then:
            project.plugins.hasPlugin(WarPlugin)
            project.extensions.findByName(CloudBeesPlugin.EXTENSION_NAME) != null

            Task task = project.tasks.findByName('cloudBeesAppRestart')
            task != null
            task.apiKey ==  null
            task.secret == null
            task.apiUrl == 'https://api.cloudbees.com/api'
            task.apiFormat == 'xml'
            task.apiVersion == '1.0'
            task.appId == 'gradle-in-action/todo'
    }

    def "Applies plugin and configures cloudBeesAppStart task"() {
        when:
            project.apply plugin: 'cloudbees'

            project.cloudBees {
                appId = 'gradle-in-action/todo'
            }
        then:
            project.plugins.hasPlugin(WarPlugin)
            project.extensions.findByName(CloudBeesPlugin.EXTENSION_NAME) != null

            Task task = project.tasks.findByName('cloudBeesAppStart')
            task != null
            task.apiKey ==  null
            task.secret == null
            task.apiUrl == 'https://api.cloudbees.com/api'
            task.apiFormat == 'xml'
            task.apiVersion == '1.0'
            task.appId == 'gradle-in-action/todo'
    }

    def "Applies plugin and configures cloudBeesAppStop task"() {
        when:
            project.apply plugin: 'cloudbees'

            project.cloudBees {
                appId = 'gradle-in-action/todo'
            }
        then:
            project.plugins.hasPlugin(WarPlugin)
            project.extensions.findByName(CloudBeesPlugin.EXTENSION_NAME) != null

            Task task = project.tasks.findByName('cloudBeesAppStop')
            task != null
            task.apiKey ==  null
            task.secret == null
            task.apiUrl == 'https://api.cloudbees.com/api'
            task.apiFormat == 'xml'
            task.apiVersion == '1.0'
            task.appId == 'gradle-in-action/todo'
    }

    def "Applies plugin and configures cloudBeesAppTail task"() {
        when:
            project.apply plugin: 'cloudbees'

            project.cloudBees {
                appId = 'gradle-in-action/todo'
            }

            project.ext.log = 'hello'
        then:
            project.plugins.hasPlugin(WarPlugin)
            project.extensions.findByName(CloudBeesPlugin.EXTENSION_NAME) != null

            Task task = project.tasks.findByName('cloudBeesAppTail')
            task != null
            task.apiKey ==  null
            task.secret == null
            task.apiUrl == 'https://api.cloudbees.com/api'
            task.apiFormat == 'xml'
            task.apiVersion == '1.0'
            task.appId == 'gradle-in-action/todo'
            task.log == 'hello'
    }

    def "Applies plugin and configures cloudBeesDbInfo task"() {
        when:
            project.apply plugin: 'cloudbees'

            project.cloudBees {
                dbId = 'gradle-in-action/db'
            }
        then:
            project.plugins.hasPlugin(WarPlugin)
            project.extensions.findByName(CloudBeesPlugin.EXTENSION_NAME) != null

            Task task = project.tasks.findByName('cloudBeesDbInfo')
            task != null
            task.apiKey ==  null
            task.secret == null
            task.apiUrl == 'https://api.cloudbees.com/api'
            task.apiFormat == 'xml'
            task.apiVersion == '1.0'
            task.dbId == 'gradle-in-action/db'
    }

    def "Applies plugin and configures cloudBeesDbList task"() {
        when:
            project.apply plugin: 'cloudbees'
        then:
            project.plugins.hasPlugin(WarPlugin)
            project.extensions.findByName(CloudBeesPlugin.EXTENSION_NAME) != null

            Task task = project.tasks.findByName('cloudBeesDbList')
            task != null
            task.apiKey ==  null
            task.secret == null
            task.apiUrl == 'https://api.cloudbees.com/api'
            task.apiFormat == 'xml'
            task.apiVersion == '1.0'
    }

    def "Applies plugin and configures cloudBeesDbDrop task"() {
        when:
            project.apply plugin: 'cloudbees'

            project.cloudBees {
                dbId = 'gradle-in-action/db'
            }
        then:
            project.plugins.hasPlugin(WarPlugin)
            project.extensions.findByName(CloudBeesPlugin.EXTENSION_NAME) != null

            Task task = project.tasks.findByName('cloudBeesDbDrop')
            task != null
            task.apiKey ==  null
            task.secret == null
            task.apiUrl == 'https://api.cloudbees.com/api'
            task.apiFormat == 'xml'
            task.apiVersion == '1.0'
            task.dbId == 'gradle-in-action/db'
    }

    def "Applies plugin and configures cloudBeesDbCreate task"() {
        when:
            project.apply plugin: 'cloudbees'

            project.cloudBees {
                dbId = 'gradle-in-action/db'
            }

            project.ext.account = 'myAccount'
            project.ext.username = 'myUser'
            project.ext.password = 'myPassword'
        then:
            project.plugins.hasPlugin(WarPlugin)
            project.extensions.findByName(CloudBeesPlugin.EXTENSION_NAME) != null

            Task task = project.tasks.findByName('cloudBeesDbCreate')
            task != null
            task.apiKey ==  null
            task.secret == null
            task.apiUrl == 'https://api.cloudbees.com/api'
            task.apiFormat == 'xml'
            task.apiVersion == '1.0'
            task.dbId == 'gradle-in-action/db'
            task.account == 'myAccount'
            task.username == 'myUser'
            task.password == 'myPassword'
    }
}