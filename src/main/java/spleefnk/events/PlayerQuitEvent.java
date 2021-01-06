package spleefnk.events;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import spleefnk.arena.Arena;
import spleefnk.managers.ArenaManager;


public class PlayerQuitEvent implements Listener {

    private ArenaManager arenaManager;

    public PlayerQuitEvent(ArenaManager arenaManager){
        this.arenaManager = arenaManager;
    }

    @EventHandler
    public void onPlayerQuit(cn.nukkit.event.player.PlayerQuitEvent event) {
        for (Arena arena : arenaManager.getArenas()){
            if (arena.isPlaying(event.getPlayer())){
                arena.removePlayer(event.getPlayer());
            }
        }
    }

}
