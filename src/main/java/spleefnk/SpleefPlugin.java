package spleefnk;

import cn.nukkit.command.SimpleCommandMap;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginManager;
import cn.nukkit.utils.Config;
import spleefnk.commands.SpleefCommand;
import spleefnk.events.*;
import spleefnk.managers.GameManager;

import java.io.File;

public class SpleefPlugin extends PluginBase {

    private static SpleefPlugin spleefPlugin;
    private GameManager gameManager;
    private Language language;

    public static SpleefPlugin getInstance() {
        return spleefPlugin;
    }

    @Override
    public void onLoad() {
        spleefPlugin = this;
        File file = new File(this.getDataFolder() + "/Language");

        if (!file.exists() && !file.mkdirs()) {
            this.getLogger().error("Language Folder initialization failed");
        }
        this.loadLanguage();
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
    }

    private void loadLanguage() {
        this.saveResource("Language/en_US.yml", false);
        this.saveResource("Language/en_US.yml", "Language/cache/new_en_US.yml", true);
        String lang = this.getConfig().getString("language", "en_US");
        File langFile = new File(this.getDataFolder() + "/Language/" + lang + ".yml");
        if (langFile.exists()) {
            this.getLogger().info("§aLanguage: " + lang + "loaded!");
        } else {
            this.getLogger().error("§cLanguage: " + lang + "does not exist! Load default language!");
            langFile = new File(getDataFolder() + "/Language/en_US.yml");
        }
        this.language = new Language(new Config(langFile, Config.YAML));
        this.language.update(new Config(this.getDataFolder() + "/Language/cache/new_en_US.yml"));
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

    public Language getLanguage() {
        return this.language;
    }

}
