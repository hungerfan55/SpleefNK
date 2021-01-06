package spleefnk.events;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import spleefnk.arena.Arena;
import spleefnk.managers.ArenaManager;

public class PlayerInteractEvent implements Listener {

    private ArenaManager arenaManager;

    public PlayerInteractEvent(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }


    @EventHandler
    public void onInteract(cn.nukkit.event.player.PlayerInteractEvent event) {
        for (Arena arena : arenaManager.getArenas()) {
            if (arena.isPlaying(event.getPlayer())) {
                if (event.getAction() == cn.nukkit.event.player.PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                    if (event.getBlock().getId() == BlockID.SNOW_BLOCK) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
