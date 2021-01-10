package spleefnk.managers;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import spleefnk.arena.Arena;
import spleefnk.arena.GameState;

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
        FormWindowSimple fw = new FormWindowSimple("Join arena", "arenas");
        for (Arena arena : gameManager.getArenaManager().getArenas()) {
            if (!(arena.getGameState() == GameState.RESTARTING) || !(arena.getGameState() == GameState.ACTIVE) || !(arena.getPlayers().size() >= arena.getMaxPlayers())) {
                if (gameManager.getArenasConfig().getBoolean(arena.getName() + ".enabled")){
                    fw.addButton(new ElementButton("§a" + arena.getName(), new ElementButtonImageData("path", "textures/items/snowball")));
                }
            }
        }
        player.showFormWindow(fw);
    }

    public void sendEnableForm(Player player) {
        FormWindowSimple fw = new FormWindowSimple("enable arenas", "enable/disable arenas");
        for (Arena arena : gameManager.getArenaManager().getArenas()) {
            if (!gameManager.getArenasConfig().getBoolean(arena.getName() + ".enabled")) {
                fw.addButton(new ElementButton("§4" + arena.getName(), new ElementButtonImageData("path", "textures/ui/redX1")));
            }
            player.showFormWindow(fw);
        }
    }

    public void sendDisableForm(Player player) {
        FormWindowSimple fw = new FormWindowSimple("disable arenas", "enable/disable arenas");
        for (Arena arena : gameManager.getArenaManager().getArenas()) {
            if (gameManager.getArenasConfig().getBoolean(arena.getName() + ".enabled")) {
                fw.addButton(new ElementButton("§4" + arena.getName(), new ElementButtonImageData("path", "textures/ui/check")));
            }
            player.showFormWindow(fw);
        }
    }
}
