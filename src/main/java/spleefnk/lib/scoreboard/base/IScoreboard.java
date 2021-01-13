package spleefnk.lib.scoreboard.base;

import cn.nukkit.Player;

import java.util.List;

/**
 * @author lt_name
 */
public interface IScoreboard {

    String getScoreboardName();

    void showScoreboard(Player player, String title, List<String> message);

    void closeScoreboard(Player player);

}
