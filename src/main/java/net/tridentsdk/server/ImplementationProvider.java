/*
 * Trident - A Multithreaded Server Alternative
 * Copyright 2016 The TridentSDK Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.tridentsdk.server;

import net.tridentsdk.Impl;
import net.tridentsdk.Server;
import net.tridentsdk.command.logger.Logger;
import net.tridentsdk.config.Config;
import net.tridentsdk.server.command.InfoLogger;
import net.tridentsdk.server.config.TridentConfig;

import java.nio.file.Path;
import java.util.concurrent.ExecutionException;

/**
 * This class is the bridge between the server and the API,
 * and provides the implementation classes for the API via
 * {@link Impl}.
 */
public class ImplementationProvider implements Impl.ImplementationProvider {
    // class is initialized after server is created
    // safe to call this method
    private static final TridentServer inst = TridentServer.instance();

    @Override
    public Server svr() {
        return inst;
    }

    @Override
    public Config newCfg(Path p) {
        return TridentConfig.load(p);
    }

    @Override
    public Logger newLogger(String s) {
        try {
            return InfoLogger.get(inst.logger(), s);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}