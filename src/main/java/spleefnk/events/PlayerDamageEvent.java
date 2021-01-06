package spleefnk.events;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageEvent;
import spleefnk.arena.Arena;
import spleefnk.arena.GameState;
import spleefnk.managers.ArenaManager;

public class PlayerDamageEvent implements Listener {
    private ArenaManager arenaManager;

    public PlayerDamageEvent(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            for (Arena arena : arenaManager.getArenas()) {
                if (arena.isPlaying(player)) {
                    if (arena.getGameState() == GameState.STARTING || arena.getGameState() == GameState.WAITING) {
                        event.setCancelled(true);
                    } else if (arena.getGameState() == GameState.ACTIVE) {
                        //Makes it so they can still hit each other nut dont hurt them maybe config option??
                        event.setDamage(0f);
                    }
                }
            }
        }
    }
}
