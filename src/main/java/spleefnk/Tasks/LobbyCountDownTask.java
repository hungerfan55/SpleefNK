package spleefnk.Tasks;

import cn.nukkit.scheduler.NukkitRunnable;
import cn.nukkit.scheduler.PluginTask;
import spleefnk.SpleefPlugin;
import spleefnk.arena.Arena;
import spleefnk.arena.GameState;
import spleefnk.managers.GameManager;

public class LobbyCountDownTask extends PluginTask<SpleefPlugin> {

    private Arena arena;
    private int secondsSinceStart;
    private int timeUntilStart = 60;
    private GameManager gameManager;

    public LobbyCountDownTask(Arena arena, GameManager gameManager) {
        super(gameManager.getPlugin());
        this.arena = arena;
        this.gameManager = gameManager;
    }

    @Override
    public void onRun(int i) {
        if (arena.getPlayers().size() < arena.getMinPlayers()){
            arena.sendMessage("§4The countdown has been canceled");
        }

        if (timeUntilStart == 60 || timeUntilStart == 30 || timeUntilStart == 10) {
            arena.sendMessage("§6Seconds till start : " + timeUntilStart);
        } else if (timeUntilStart <= 0) {
            if (arena.getGameState() == GameState.ACTIVE){
                System.out.println("fuck");
            } else {
                arena.setGameState(GameState.ACTIVE);
            }
        }

        timeUntilStart--;
        secondsSinceStart++;
    }



}
