package spleefnk.managers;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import spleefnk.arena.Arena;

import java.util.ArrayList;
import java.util.List;

public class ArenaManager {

    private GameManager gameManager;

    private List<Arena> arenas = new ArrayList<>();

    public ArenaManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void loadArenas() {
        for (String arenaName : gameManager.getArenasConfig().getKeys(false)) {
            Arena arena = new Arena(arenaName, gameManager);
            arenas.add(arena);
        }
    }

    public List<Arena> getArenas() {
        return arenas;
    }

    public void addArena(Arena arena) {
        this.arenas.add(arena);
    }

    public void removeArena(Arena arena) {
        this.arenas.removeIf(existing -> existing.equals(arena));
        Config cfg = gameManager.getArenasConfig();

        cfg.remove(arena.getName());
        gameManager.saveConfig();
    }

    public Arena getArenaByName(String arenaName) {

        for (Arena arena : arenas) {
            if (arena.getName().equals(arenaName)) {
                return arena;
            }
        }
        return null;
    }

    public Arena arenaForPlayer(Player player) {
        for (Arena arena : arenas){
            for (Player player1 : arena.getPlayers()){
                if (player.equals(player1)){
                    return arena;
                }
            }
        }
        return null;
    }
}
