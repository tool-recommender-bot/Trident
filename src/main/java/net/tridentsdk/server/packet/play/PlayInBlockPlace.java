package net.tridentsdk.server.packet.play;

import io.netty.buffer.ByteBuf;
import net.tridentsdk.base.Block;
import net.tridentsdk.base.Position;
import net.tridentsdk.base.Vector;
import net.tridentsdk.doc.Debug;
import net.tridentsdk.event.player.BlockPlaceEvent;
import net.tridentsdk.inventory.Item;
import net.tridentsdk.inventory.PlayerInventory;
import net.tridentsdk.server.TridentServer;
import net.tridentsdk.server.net.NetClient;
import net.tridentsdk.server.packet.PacketIn;
import net.tridentsdk.server.player.TridentPlayer;
import net.tridentsdk.world.opt.GameMode;

import javax.annotation.concurrent.Immutable;

import static net.tridentsdk.server.net.NetData.rvec;
import static net.tridentsdk.server.net.NetData.rvint;

/**
 * Sent by the client when it attempts to place a block.
 */
@Immutable
public class PlayInBlockPlace extends PacketIn {
    public PlayInBlockPlace() {
        super(PlayInBlockPlace.class);
    }

    @Debug
    @Override
    public void read(ByteBuf buf, NetClient client) {
        TridentPlayer player = client.getPlayer();
        Vector vector = rvec(buf);
        int faceIdx = rvint(buf);
        int hand = rvint(buf);

        float cX = buf.readFloat();
        float cY = buf.readFloat();
        float cZ = buf.readFloat();

        if (cX == -1 && cY == -1 && cZ == -1) {
            System.out.println("FACE: " + faceIdx);
            // special eat/bucket action
        } else {
            PlayerInventory inv = player.getInventory();
            Item item = hand == 0 ? inv.getHeldItem() : inv.getOffHeldItem();

            if (!item.getSubstance().isBlock()) {
                return;
            }

            Position playerPos = player.getPosition();
            Position blockPos = vector.toPosition(player.getWorld());

            if (blockPos.distanceSquared(playerPos) > PlayInPlayerDig.MAX_DIST_SQ) {
                TridentServer.getInstance().getLogger().warn(
                        "Suspicious place @ " + blockPos + " by " + player.getName() + " (" + player.getUuid() + ')');
                return;
            }

            PlayInPlayerDig.DigFace face = PlayInPlayerDig.DigFace.get(faceIdx);
            switch (face) {
                case BOTTOM:
                    blockPos.subtract(0, 1, 0);
                    break;
                case TOP:
                    blockPos.add(0, 1, 0);
                    break;
                case NORTH:
                    blockPos.subtract(0, 0, 1);
                    break;
                case SOUTH:
                    blockPos.add(0, 0 , 1);
                    break;
                case WEST:
                    blockPos.subtract(1, 0, 0);
                    break;
                case EAST:
                    blockPos.add(1, 0 , 0);
                    break;
                case SPECIAL:
                    throw new RuntimeException("Invalid item placement");
            }

            Block b = blockPos.getBlock();

            TridentServer.getInstance().getEventController().dispatch(new BlockPlaceEvent(player, b, item), e -> {
                if (!e.isCancelled()) {
                    b.setSubstance(item.getSubstance());
                    b.setData(item.getDamage());

                    if (player.getGameMode() != GameMode.CREATIVE) {
                        if (hand == 0) {
                            inv.remove(36 + inv.getSelectedSlot(), 1);
                        } else {
                            inv.remove(45, 1);
                        }
                    }

                    // TODO handle item NBT for skulls, also block states
                    // TODO send chunk
                }
            });
        }
    }
}