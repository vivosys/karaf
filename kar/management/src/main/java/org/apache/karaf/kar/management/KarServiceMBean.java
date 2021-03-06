/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.karaf.kar.management;

import java.util.List;

public interface KarServiceMBean {

    /**
     * List the installed KAR files.
     *
     * @return the list of KAR files.
     * @throws Exception in case of listing failure.
     */
    List<String> getKars() throws Exception;

    /**
     * Install a KAR file from the given URL.
     *
     * @param url the JAR URL.
     * @throws Exception in case of installation failure.
     */
    void install(String url) throws Exception;

    /**
     * Uninstall a KAR file.
     * 
     * @param name the name of the KAR file.
     * @throws Exception in case of uninstall failure.
     */
    void uninstall(String name) throws Exception;

    /**
     * Uninstall a KAR file and clean the system repository with the KAR content.
     *
     * @param name the KAR name.
     * @param clean true to cleanup the system repository, false else.
     * @throws Exception in case of uninstall failure.
     */
    void uninstall(String name, boolean clean) throws Exception;

}
