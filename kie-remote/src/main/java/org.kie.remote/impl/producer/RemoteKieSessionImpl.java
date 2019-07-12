/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
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
package org.kie.remote.impl.producer;

import java.io.Closeable;
import java.util.Properties;

import org.kie.remote.RemoteEntryPoint;
import org.kie.remote.RemoteKieSession;
import org.kie.remote.TopicsConfig;

public class RemoteKieSessionImpl extends RemoteEntryPointImpl implements Closeable, RemoteKieSession {

    public static final String DEFAULT_ENTRY_POINT = "DEFAULT"; // EntryPointId.DEFAULT.getEntryPointId();
    private TopicsConfig topicsConfig;

    public RemoteKieSessionImpl( Properties configuration, TopicsConfig envConfig ) {
        super(new Sender(configuration), DEFAULT_ENTRY_POINT, envConfig);
        this.topicsConfig = envConfig;
        sender.start();
    }

    @Override
    public void close() {
        sender.stop();
    }

    @Override
    public RemoteEntryPoint getEntryPoint( String name ) {
        return new RemoteEntryPointImpl(sender, name, topicsConfig);
    }
}