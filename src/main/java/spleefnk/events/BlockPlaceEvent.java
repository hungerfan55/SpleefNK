package spleefnk.events;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import spleefnk.arena.Arena;
import spleefnk.arena.GameState;
import spleefnk.managers.ArenaManager;

public class BlockPlaceEvent implements Listener {
    private ArenaManager arenaManager;

    public BlockPlaceEvent(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }

    @EventHandler
    public void onBlockPlace(cn.nukkit.event.block.BlockPlaceEvent event) {

        Player player = event.getPlayer();

        for (Arena arena : arenaManager.getArenas()) {
            if (arena.isPlaying(player)) {
                if (arena.getGameState() == GameState.STARTING || arena.getGameState() == GameState.WAITING || arena.getGameState() == GameState.ACTIVE ) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
