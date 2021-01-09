package spleefnk.managers;

import cn.nukkit.utils.Config;
import spleefnk.SpleefPlugin;

import java.io.File;

public class GameManager {

    private SpleefPlugin plugin;
    private ArenaManager arenaManager;
    private SetupWizardManager setupWizardManager;
    private FormManager formManager;

    private  File arenasFile;
    private Config arenasConfig;

    public GameManager(SpleefPlugin plugin) {
        this.plugin = plugin;
        arenaManager = new ArenaManager(this);
        setupWizardManager = new SetupWizardManager(this);
        if (this.arenasFile == null){
            this.arenasFile = new File(plugin.getDataFolder(), "arenas.yml");
        }
        this.arenasConfig = new Config(arenasFile, 2);
        this.formManager = new FormManager(this);
    }

    public void saveConfig() {
        this.arenasConfig.save(arenasFile);
    }

    public FormManager getFormManager() {
        return formManager;
    }

    public Config getArenasConfig() {
        return arenasConfig;
    }

    public SpleefPlugin getPlugin() {
        return plugin;
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    public SetupWizardManager getSetupWizardManager() {
        return setupWizardManager;
    }
}
