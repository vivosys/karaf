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
package org.apache.karaf.shell.bundles;

import java.util.List;

import org.apache.karaf.shell.commands.Command;
import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.FrameworkWiring;

@Command(scope = "bundle", name = "refresh", description = "Refresh bundles.")
public class Refresh extends BundlesCommandWithConfirmation {

    protected void doExecute(List<Bundle> bundles) throws Exception {
        FrameworkWiring wiring = getBundleContext().getBundle(0).adapt(FrameworkWiring.class);
        wiring.refreshBundles(bundles == null || bundles.isEmpty() ? null : bundles);
    }
}
