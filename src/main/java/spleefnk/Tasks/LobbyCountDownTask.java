package spleefnk.Tasks;

import cn.nukkit.scheduler.PluginTask;
import spleefnk.Language;
import spleefnk.SpleefPlugin;
import spleefnk.arena.Arena;
import spleefnk.arena.GameState;
import spleefnk.managers.GameManager;

public class LobbyCountDownTask extends PluginTask<SpleefPlugin> {

    private Arena arena;
    private int secondsSinceStart;
    private int timeUntilStart = 60;
    private GameManager gameManager;
    Language language;

    public LobbyCountDownTask(Arena arena, GameManager gameManager) {
        super(gameManager.getPlugin());
        this.arena = arena;
        this.gameManager = gameManager;
        this.language = getOwner().getLanguage();
    }

    @Override
    public void onRun(int i) {
        if (arena.getPlayers().size() < arena.getMinPlayers()){
            arena.sendMessage(this.language.translateString("countDownCanceld"));
        }

        if (timeUntilStart == 60 || timeUntilStart == 30 || timeUntilStart == 10) {
            arena.sendMessage(this.language.translateString("secondsTillStart")
                    .replace("%time%", timeUntilStart + ""));
        } else if (timeUntilStart <= 0) {
            if (arena.getGameState() == GameState.ACTIVE){
                System.out.println("fuck");
            } else {
                arena.setGameState(GameState.ACTIVE);
            }
        }
            //
        timeUntilStart--;
        secondsSinceStart++;
    }



}
