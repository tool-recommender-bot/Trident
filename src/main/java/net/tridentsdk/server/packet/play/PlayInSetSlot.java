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
package net.tridentsdk.server.packet.play;

import io.netty.buffer.ByteBuf;
import net.tridentsdk.server.net.NetClient;
import net.tridentsdk.server.packet.PacketIn;

import javax.annotation.concurrent.Immutable;

/**
 * Sent by the client to indicate to the server that the
 * player has selected a new slot.
 */
@Immutable
public class PlayInSetSlot extends PacketIn {
    public PlayInSetSlot() {
        super(PlayInSetSlot.class);
    }

    @Override
    public void read(ByteBuf buf, NetClient client) {
        client.getPlayer().getInventory().setSelectedSlot(buf.readShort());
    }
}