package spleefnk.Tasks;

import cn.nukkit.Player;
import cn.nukkit.block.BlockID;
import cn.nukkit.scheduler.PluginTask;
import spleefnk.Language;
import spleefnk.SpleefPlugin;
import spleefnk.arena.Arena;
import spleefnk.arena.GameState;
import spleefnk.managers.GameManager;

public class ActiveGameTask extends PluginTask<SpleefPlugin> {

    private Arena arena;
    private GameManager gameManager;
    private Language language;

    public ActiveGameTask(Arena arena, GameManager gameManager) {
        super(gameManager.getPlugin());
        this.arena = arena;
        this.gameManager = gameManager;
        this.language = gameManager.getPlugin().getLanguage();
    }

    @Override
    public void onRun(int i) {
        if (arena.getPlayers().size() <= 1) {
            if (arena.getPlayers().isEmpty()) {
                arena.sendMessage(this.language.translateString("someoneWon"));
            } else {
                arena.sendMessage(this.language.translateString("playerWon")
                        .replace("%playerName%", arena.getPlayers().get(0).getName()));
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
