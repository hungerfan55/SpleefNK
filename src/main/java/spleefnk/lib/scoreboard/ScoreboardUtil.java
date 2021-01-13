package spleefnk.lib.scoreboard;

import cn.nukkit.Server;
import spleefnk.lib.scoreboard.base.IScoreboard;

/**
 * @author lt_name
 */
public class ScoreboardUtil {

    private static IScoreboard scoreboard;

    private ScoreboardUtil() {

    }

    public synchronized static IScoreboard getScoreboard() {
        if (scoreboard == null) {
            try {
                Class.forName("gt.creeperface.nukkit.scoreboardapi.ScoreboardAPI");
                if (Server.getInstance().getPluginManager().getPlugin("ScoreboardAPI").isDisabled()) {
                    throw new Exception("Not Loaded");
                }
                scoreboard = new spleefnk.lib.scoreboard.creeperface.SimpleScoreboard();
            } catch (Exception e) {
                try {
                    Class.forName("de.theamychan.scoreboard.ScoreboardPlugin");
                    if (Server.getInstance().getPluginManager().getPlugin("ScoreboardPlugin").isDisabled()) {
                        throw new Exception("Not Loaded");
                    }
                    scoreboard = new spleefnk.lib.scoreboard.theamychan.SimpleScoreboard();
                } catch (Exception e1) {
                    scoreboard = new spleefnk.lib.scoreboard.theamychan.SimpleScoreboard();
                }
            }
        }
        return scoreboard;
    }

}
