/*
 * Trident - A Multithreaded Server Alternative
 * Copyright 2017 The TridentSDK Team
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
package net.tridentsdk.server.net;

import javax.annotation.concurrent.Immutable;

/**
 * This class handles the network connections for the
 * server
 * and manages the netty channels, packets, pipelines, etc.
 */
@Immutable
public abstract class NetServer {
    /**
     * The server IP
     */
    private final String ip;
    /**
     * The server port
     */
    private final int port;

    /**
     * The net server superconstructor
     */
    NetServer(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * Initializer code for server startup.
     *
     * @param ip the IP address of the interface to bind
     * @param port the port which to bind the interface
     * @param useNative whether to use native epoll
     * @return the new server net handler
     */
    public static NetServer init(String ip, int port, boolean useNative) {
        boolean nativeCompat = System.getProperty("os.name").toLowerCase().contains("linux");

        return nativeCompat && useNative ?
                new NetEpollServer(ip, port) : new NetNioServer(ip, port);
    }

    /**
     * Sets up the server.
     */
    public abstract void setup();

    /**
     * Shuts down the server.
     *
     * @throws InterruptedException no
     */
    public abstract void shutdown() throws InterruptedException;

    /**
     * Obtains the address used to initialize the server.
     *
     * @return the server address
     */
    public String ip() {
        return this.ip;
    }

    /**
     * Obtains the port used to initialize the server.
     *
     * @return the server port
     */
    public int port() {
        return this.port;
    }
}