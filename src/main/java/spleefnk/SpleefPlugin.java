package spleefnk;

import cn.nukkit.command.SimpleCommandMap;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginManager;
import cn.nukkit.utils.Config;
import spleefnk.arena.Arena;
import spleefnk.commands.SpleefCommand;
import spleefnk.events.*;
import spleefnk.managers.GameManager;

import java.io.File;

public class SpleefPlugin extends PluginBase {

    private GameManager gameManager;
    private Language language;

    @Override
    public void onLoad() {
        File file = new File(this.getDataFolder() + "/Language");

        if (!file.exists() && !file.mkdirs()) {
            this.getLogger().error("Language Folder initialization failed");
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        saveDefaultConfig();
        saveConfig();
        gameManager = new GameManager(this);
        gameManager.getArenaManager().loadArenas();
        registerCommands();
        registerEvents();
        loadLanguage();
    }

    private void loadLanguage() {
        saveResource("Language/en_US.yml", false);
        String lang = getConfig().getString("language", "en_US");
        File langFile = new File(getDataFolder() + "/Language/" + lang + "yml");
        if (langFile.exists()) {
            getLogger().info("§aLanguage: " + lang + "loaded!");
            this.language = new Language(new Config(langFile, 2));
        } else {
            this.language = new Language(new Config());
        }
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
