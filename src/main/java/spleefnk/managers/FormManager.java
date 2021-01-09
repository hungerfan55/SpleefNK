package spleefnk.managers;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.window.FormWindowCustom;

public class FormManager {

    private GameManager gameManager;

    public FormManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void sendWizardForm(Player player) {
        FormWindowCustom fw = new FormWindowCustom("Set min and max players");
        fw.addElement(new ElementInput("minimum players to start", "2", "2"));
        fw.addElement(new ElementInput("maximum players to join a game", "8", "8"));
        player.showFormWindow(fw, 1);
    }

    public void sendJoinForm(Player player) {
        //TODO: when they dont specify an arena in the /spleef join command
    }

}
