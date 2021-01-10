package spleefnk.Tasks;

import cn.nukkit.Player;
import cn.nukkit.block.BlockID;
import cn.nukkit.scheduler.NukkitRunnable;
import cn.nukkit.scheduler.PluginTask;
import spleefnk.SpleefPlugin;
import spleefnk.arena.Arena;
import spleefnk.arena.GameState;
import spleefnk.managers.GameManager;

public class ActiveGameTask extends PluginTask<SpleefPlugin> {

    private Arena arena;
    private GameManager gameManager;

    public ActiveGameTask(Arena arena, GameManager gameManager) {
        super(gameManager.getPlugin());
        this.arena = arena;
        this.gameManager = gameManager;
    }

    @Override
    public void onRun(int i) {
        if (arena.getPlayers().size() <=0) {
            if (arena.getPlayers().isEmpty()) {
                arena.sendMessage("§6Someone won a game!");
            } else {
                arena.sendMessage(arena.getPlayers().get(0).getName() + "§cWon the game!");
            }
            arena.setGameState(GameState.RESTARTING);
            return;
        }
        for (Player player : arena.getPlayers()) {
            if (player.getLocation().getLevelBlock().getId() == BlockID.LAVA || player.getLocation().getLevelBlock().getId() == BlockID.STILL_LAVA) {
                arena.removePlayer(player);
            }
        }

    }
}
