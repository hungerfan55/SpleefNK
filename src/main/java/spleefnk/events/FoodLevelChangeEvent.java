package spleefnk.events;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFoodLevelChangeEvent;
import spleefnk.arena.Arena;
import spleefnk.managers.ArenaManager;

public class FoodLevelChangeEvent implements Listener {

    private ArenaManager arenaManager;

    public FoodLevelChangeEvent(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }

    @EventHandler
    public void onFoodLevelChange(PlayerFoodLevelChangeEvent event) {
        for (Arena arena : arenaManager.getArenas()) {
            if (arena.isPlaying(event.getPlayer())) {
                event.setCancelled(true);
            }
        }
    }


}
