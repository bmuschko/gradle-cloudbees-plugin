# Gradle CloudBees plugin

![CloudBees Logo](https://jenkins-ci.org/sites/default/files/images/CloudBees-logo.thumbnail.png)

The plugin provides support for managing applications and databases on  [CloudBees RUN@cloud](http://www.cloudbees.com/run.cb). Under covers the plugin communicates with the CloudBees backend via the [CloudBees API client](https://github.com/cloudbees/cloudbees-api-client). The code of the plugin is discussed in chapter 8 of the book ["Gradle in Action"](http://www.manning.com/muschko) published by Manning.

## Usage

To use the CloudBees plugin, include in your build script:

    apply plugin: 'cloudbees'

The plugin JAR needs to be defined in the classpath of your build script. It is directly available on
[Maven Central](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22org.gradle.api.plugins%22%20AND%20a%3A%22gradle-cloudbees-plugin%22).
Alternatively, you can download it from GitHub and deploy it to your local repository. The following code snippet shows an
example on how to retrieve it from Maven Central:

    buildscript {
        repositories {
            mavenCentral()
        }

        dependencies {
            classpath 'org.gradle.api.plugins:gradle-cloudbees-plugin:0.1'
        }
    }

## Tasks

The CloudBees plugin defines the following tasks:

### Application tasks

* `cloudBeesAppChecksums`:
* `cloudBeesAppDelete`: 
* `cloudBeesAppDeployWar`:
* `cloudBeesAppInfo`:
* `cloudBeesAppList`:
* `cloudBeesAppRestart`:
* `cloudBeesAppStart`:
* `cloudBeesAppStop`:
* `cloudBeesAppTail`:

### Database tasks

* `cloudBeesDbInfo`:
* `cloudBeesDbList`: 
* `cloudBeesDbDrop`:
* `cloudBeesDbCreate`:

## Convention properties

The CloudBees plugin defines the following convention properties in the `cloudBees` closure:

* `apiFormat`: The API format (defaults to `XML`).
* `apiVersion`: The API version (defaults to `1.0`).
* `apiUrl`: The API URL (defaults to `https://api.cloudbees.com/api`).
* `apiKey`: The API key (defaults to the value of property named `cloudbees.api.key`).
* `secret`: The API secret (defaults to the value of property named `cloudbees.api.secret`).
* `appId`: The application identifier on CloudBees.
* `dbId`: The database identifier on CloudBees.

### Example

    cloudBees {
        appId = 'gradle-in-action/to-do-app'
        dbId = 'gradle-in-action/to-do-db'
    }

## Setting API credentials

If you decide to use the properties `cloudbees.api.key` and `cloudbees.api.secret` to populate your API credentials it is recommended to set them in your `~/.gradle/gradle.properties` file. The following code snippet shows an example:

    cloudbees.api.key = yourApiKey
    cloudbees.api.secret = yourApiSecret