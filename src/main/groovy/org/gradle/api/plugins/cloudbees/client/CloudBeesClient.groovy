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
package org.gradle.api.plugins.cloudbees.client

import com.cloudbees.api.*

/**
 * Interface for CloudBees client delegate.
 *
 * @author benjamin
 */
interface CloudBeesClient {
    ApplicationCheckSumsResponse applicationCheckSums(String appId)
    ApplicationDeleteResponse applicationDelete(String appId)
    ApplicationDeployArchiveResponse applicationDeployWar(String appId, String environment, String description, File warFile, File srcFile, UploadProgress progress)
    ApplicationInfo applicationInfo(String appId)
    ApplicationListResponse applicationList()
    ApplicationRestartResponse applicationRestart(String appId)
    ApplicationStatusResponse applicationStart(String appId)
    ApplicationStatusResponse applicationStop(String appId)
    void tailLog(String appId, String logName, OutputStream out)
    DatabaseCreateResponse databaseCreate(String domain, String dbId, String username, String password)
    DatabaseDeleteResponse databaseDelete(String dbId)
    DatabaseInfo databaseInfo(String dbId, boolean fetchPassword)
    DatabaseListResponse databaseList()
}