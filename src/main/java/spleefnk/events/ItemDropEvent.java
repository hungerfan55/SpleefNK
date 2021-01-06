package spleefnk.events;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerDropItemEvent;
import spleefnk.arena.Arena;
import spleefnk.managers.ArenaManager;

public class ItemDropEvent implements Listener {

    private ArenaManager arenaManager;

    public ItemDropEvent(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        for (Arena arena : arenaManager.getArenas()) {
            if (arena.isPlaying(event.getPlayer())) {
                event.setCancelled(true);
            }
        }
    }
}
