package spleefnk.events;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.Config;
import spleefnk.Language;
import spleefnk.arena.Arena;
import spleefnk.managers.GameManager;

public class FormListener implements Listener {

    private GameManager gameManager;
    private Language language;

    private Config cfg;

    public FormListener(GameManager gameManager) {
        this.gameManager = gameManager;
        this.language = gameManager.getPlugin().getLanguage();
    }

    @EventHandler
    public void onFormReceive(PlayerFormRespondedEvent event) {

        Arena arena;

        if (event.getWindow() instanceof FormWindowCustom) {
            FormWindowCustom fw = (FormWindowCustom) event.getWindow();
            if (!(fw.getTitle().equals(this.language.translateString("wizardFormTitle")))) return;
            if (fw.getResponse().getInputResponse(0) == null) {
                gameManager.getSetupWizardManager().endWizard(event.getPlayer());
                event.getPlayer().sendMessage(this.language.translateString("setupWizardCanceld"));
                return;
            }
            int minPlayers = Integer.parseInt(fw.getResponse().getInputResponse(0));
            int maxPlayers = Integer.parseInt(fw.getResponse().getInputResponse(1));
            if (minPlayers < 2) {
                event.getPlayer().sendMessage(this.language.translateString("minPlayersMoreThan2"));
                gameManager.getSetupWizardManager().endWizard(event.getPlayer());
            } else if (maxPlayers < 2) {
                event.getPlayer().sendMessage(this.language.translateString("maxPlayersMoreThan2"));
                gameManager.getSetupWizardManager().endWizard(event.getPlayer());
            } else {
                gameManager.getSetupWizardManager().writePlayers(minPlayers, maxPlayers, event.getPlayer());
            }
        } else if (event.getWindow() instanceof FormWindowSimple) {
            FormWindowSimple fw = (FormWindowSimple) event.getWindow();
            if (fw.getResponse() == null) return;
            StringBuilder sb = new StringBuilder(fw.getResponse().getClickedButton().getText());
            arena = gameManager.getArenaManager().getArenaByName(sb.delete(0, 2).toString());

            if (fw.getTitle().equals(this.language.translateString("joinFormTitle"))) {
                arena.addPlayer(event.getPlayer());
            } else if (fw.getTitle().equals(this.language.translateString("disableFormTitle"))){
                gameManager.getArenasConfig().set(arena.getName() + ".enabled", false);
                gameManager.saveConfig();
                event.getPlayer().sendMessage(this.language.translateString("arenaDisabled"));
            } else if (fw.getTitle().equals(this.language.translateString("enableFormTitle"))){
                gameManager.getArenasConfig().set(arena.getName() + ".enabled", true);
                gameManager.saveConfig();
                event.getPlayer().sendMessage(this.language.translateString("arenaEnabled"));

            }
        }
    }
}


