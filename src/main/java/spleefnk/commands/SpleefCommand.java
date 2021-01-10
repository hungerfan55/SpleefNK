package spleefnk.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import spleefnk.Language;
import spleefnk.SpleefPlugin;
import spleefnk.arena.Arena;
import spleefnk.managers.GameManager;

public class SpleefCommand extends Command {

    private GameManager gameManager;
    private Language language;

    public SpleefCommand(GameManager gameManager) {
        super("spleef", "Main spleef command");
        this.gameManager = gameManager;
        this.language = gameManager.getPlugin().getLanguage();
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(this.language.translateString("cmdOnlyPlayer"));
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            sendHelp(player);
            return true;
        }

        switch (args[0]) {
            case "create":
                if (!player.hasPermission("spleefNK.admin")) {
                    player.sendMessage(this.language.translateString("noPerm"));
                    break;
                }
                if (args.length == 1) {
                    player.sendMessage(this.language.translateString("createCMDusage"));
                    break;
                } else if (args.length > 2) {
                    player.sendMessage(this.language.translateString("createCMDusage"));
                    break;
                } else {
                    String arenaName = args[1];
                    Arena arena = new Arena(arenaName, gameManager);
                    gameManager.getSetupWizardManager().startWizard(player, arena, false);
                    break;
                }
            case "join":
                if (!player.hasPermission("spleefNK.user")) {
                    player.sendMessage(this.language.translateString("noPerm"));
                    break;
                }
                if (args.length == 1) {
                    gameManager.getFormManager().sendJoinForm(player);
                    break;
                } else if (args.length > 2) {
                    player.sendMessage(this.language.translateString("listCMDusage"));
                    player.sendMessage(this.language.translateString("joinCMDusage"));
                    break;
                } else {
                    String arenaName = args[1];
                    Arena arena = gameManager.getArenaManager().getArenaByName(arenaName);
                    if (arena == null) {
                        player.sendMessage(this.language.translateString("arenaDoesntExsist"));
                        break;
                    }
                    arena.addPlayer(player);
                    break;
                }
            case "bowspleef":
                if (!player.hasPermission("spleefNK.admin")) {
                    player.sendMessage(this.language.translateString("noPerm"));
                    break;
                }
                if (args.length == 1) {
                    player.sendMessage(this.language.translateString("listCMDusage"));
                    player.sendMessage(this.language.translateString("bowSpleefCMDusage"));
                    break;
                } else if (args.length > 2) {
                    player.sendMessage(this.language.translateString("listCMDusage"));
                    //TODO
                    player.sendMessage("Please do /spleef bowspleef <arena name> to join a arena");
                    break;
                } else {
                    String arenaName = args[1];
                    Arena arena = gameManager.getArenaManager().getArenaByName(arenaName);
                    if (arena == null){
                        //TODO
                        player.sendMessage("That arena doesn't exist");
                        break;
                    }
                    if (gameManager.getArenasConfig().getBoolean(arenaName + ".bowSpleef")) {
                        arena.setBowSpleefEnabled(false);
                        player.sendMessage(this.language.translateString("bowSpleefDisabled")
                                .replace("%arenaName%", arenaName));
                        break;
                    }
                    arena.setBowSpleefEnabled(true);
                    player.sendMessage(this.language.translateString("bowSpleefEnabled")
                            .replace("%arenaName%", arenaName));
                    break;
                }

            case "enable":
                if (!player.hasPermission("spleefNK.admin")) {
                    player.sendMessage(this.language.translateString("noPerm"));
                    break;
                }
                if (args.length == 1) {
                    gameManager.getFormManager().sendEnableForm(player);
                    break;
                } else if (args.length > 2) {
                    player.sendMessage(this.language.translateString("listCMDusage"));
                    //TODO
                    player.sendMessage("Please do /spleef enable <arena name> to join a arena");
                    break;
                } else {
                    String arenaName = args[1];
                    Arena arena = gameManager.getArenaManager().getArenaByName(arenaName);
                    if (arena == null){
                        //TODO
                        player.sendMessage("That arena doesn't exist");
                        break;
                    }
                    player.sendMessage(this.language.translateString("arenaEnabled"));
                    arena.setEnabled(true);
                    break;
                }
            case "disable":
                if (!player.hasPermission("spleefNK.admin")) {
                    player.sendMessage(this.language.translateString("noPerm"));
                    break;
                }
                if (args.length == 1) {
                    gameManager.getFormManager().sendDisableForm(player);
                    break;
                } else if (args.length > 2) {
                    player.sendMessage(this.language.translateString("listCMDusage"));
                    player.sendMessage(this.language.translateString("disableCMDusage"));
                    break;
                } else {
                    String arenaName = args[1];
                    Arena arena = gameManager.getArenaManager().getArenaByName(arenaName);
                    if (arena == null){
                        player.sendMessage("That arena doesn't exist");
                        break;
                    }
                    player.sendMessage(this.language.translateString("arenaDisabled"));
                    arena.setEnabled(false);
                    break;
                }
            case "list":
                if (!player.hasPermission("spleefNK.user")) {
                    player.sendMessage(this.language.translateString("noPerm"));
                    break;
                }
                if (gameManager.getArenaManager().getArenas().isEmpty()) {
                    player.sendMessage(this.language.translateString("noArenasYet"));
                }
                for (Arena arena : gameManager.getArenaManager().getArenas()) {
                    player.sendMessage("§a- " + arena.getName());
                }
                break;
            case "help":
                if (!player.hasPermission("spleefNK.user")) {
                    player.sendMessage(this.language.translateString("noPerm"));
                    break;
                }
                sendHelp(player);
                break;
            case "remove":
                if (!player.hasPermission("spleefNK.admin")) {
                    player.sendMessage(this.language.translateString("noPerm"));
                    break;
                }
                if (args.length == 1) {
                    player.sendMessage(this.language.translateString("removeCMDusage"));
                    break;
                } else if (args.length > 2) {
                    //TODO
                    player.sendMessage("Please do /spleef remove <arena name> to disable a arena");
                    break;
                } else {
                    String arenaName = args[1];
                    Arena arena = gameManager.getArenaManager().getArenaByName(arenaName);
                    if (arena == null){
                        //TODO
                        player.sendMessage("That arena doesn't exist");
                        break;
                    }
                    if (arena.isEnabled()) {
                        player.sendMessage(this.language.translateString("disableFirst")
                                .replace("%arenaName%", arenaName));
                        break;
                    }
                    player.sendMessage("Arena removed");
                    gameManager.getArenaManager().removeArena(arena);
                    break;
                }
            case "edit":
                if (!player.hasPermission("spleefNK.admin")) {
                    player.sendMessage(this.language.translateString("noPerm"));
                    break;
                }
                if (args.length == 1 || args.length > 2) {
                    player.sendMessage(this.language.translateString("editCMDusage"));
                } else {
                    String arenaName = args[1];
                    Arena arena = gameManager.getArenaManager().getArenaByName(arenaName);
                    if (arena == null){
                        //TODD
                        player.sendMessage("That arena doesn't exist");
                        break;
                    }
                    gameManager.getSetupWizardManager().startWizard(player, arena, true);
                }
                break;
        }
        return false;
    }

    private void sendHelp(Player player) {
        player.sendMessage(this.language.translateString("helpMessage"));
        //player.sendMessage("§2----Spleef-----\n/spleef list : List all arenas\n/spleef enable/disable : Disable or enable an arena\n/spleef join : join an arena\n/spleef create : create an arena\n/spleef bowspleef : Do bow spleef on or off for a arena\n/spleef remove : remove an arena\n/spleef edit : edit an arena");
    }
}

