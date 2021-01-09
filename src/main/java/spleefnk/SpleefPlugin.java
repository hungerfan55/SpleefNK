package spleefnk;

import cn.nukkit.command.SimpleCommandMap;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginManager;
import spleefnk.arena.Arena;
import spleefnk.commands.SpleefCommand;
import spleefnk.events.*;
import spleefnk.managers.GameManager;

public class SpleefPlugin extends PluginBase {

    private GameManager gameManager;

    @Override
    public void onEnable() {
        super.onEnable();
        saveDefaultConfig();
        gameManager = new GameManager(this);
        gameManager.getArenaManager().loadArenas();
        registerCommands();
        registerEvents();
    }

    @Override
    public void onDisable() {
        gameManager.getArenaManager().getArenas().clear();
    }

    public void registerCommands() {
        SimpleCommandMap scm = getServer().getCommandMap();

        scm.register("spleef", new SpleefCommand(gameManager));
    }

    public void registerEvents() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(gameManager.getSetupWizardManager(), this);

        pm.registerEvents(new PlayerQuitEvent(gameManager.getArenaManager()), this);
        pm.registerEvents(new BlockBreakEvent(gameManager.getArenaManager()), this);
        pm.registerEvents(new BlockPlaceEvent(gameManager.getArenaManager()), this);
        pm.registerEvents(new FoodLevelChangeEvent(gameManager.getArenaManager()), this);
        pm.registerEvents(new ItemDropEvent(gameManager.getArenaManager()), this);
        pm.registerEvents(new PlayerDamageEvent(gameManager.getArenaManager()), this);
        pm.registerEvents(new PlayerInteractEvent(gameManager.getArenaManager()), this);
        pm.registerEvents(new ProjectileHitEvent(gameManager.getArenaManager()), this);
        pm.registerEvents(new FormListener(gameManager), this);
    }
}
