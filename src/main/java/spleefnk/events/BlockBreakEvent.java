package spleefnk.events;

import cn.nukkit.Player;
import cn.nukkit.block.BlockID;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import spleefnk.arena.Arena;
import spleefnk.arena.GameState;
import spleefnk.managers.ArenaManager;

public class BlockBreakEvent implements Listener {

    private ArenaManager arenaManager;

    public BlockBreakEvent(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }

    @EventHandler
    public void onBlockBreak(cn.nukkit.event.block.BlockBreakEvent event) {

        Player player = event.getPlayer();

        for (Arena arena : arenaManager.getArenas()) {
            if (arena.isPlaying(player)) {
                if (arena.getGameState() == GameState.STARTING || arena.getGameState() == GameState.WAITING) {
                    event.setCancelled(true);
                } else if (arena.getGameState() == GameState.ACTIVE) {
                    if (!(event.getBlock().getId() == BlockID.SNOW_BLOCK)){
                        event.setCancelled(true);
                    }
                    arena.getBrokenBlocksList().add(event.getBlock().getLocation());
                }
            }
        }
    }
}
