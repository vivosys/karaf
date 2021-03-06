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
package org.apache.karaf.admin.command;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;

@Command(scope = "admin", name = "change-rmi-server-port", description = "Changes the RMI server port (used by management layer) of an existing instance.")
public class ChangeRmiServerPortCommand extends AdminCommandSupport {

    @Argument(index = 0, name = "name", description = "The name of the container instance", required = true, multiValued = false)
    private String instance = null;

    @Argument(index = 1, name = "port", description = "The new RMI server port to set", required = true, multiValued = false)
    private int port = 0;

    protected Object doExecute() throws Exception {
        getExistingInstance(instance).changeRmiServerPort(port);
        return null;
    }

}
