package spleefnk.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import spleefnk.Language;
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
                    player.sendMessage(this.language.translateString("bowSpleefCMDusage"));
                    break;
                } else {
                    String arenaName = args[1];
                    Arena arena = gameManager.getArenaManager().getArenaByName(arenaName);
                    if (arena == null){
                        player.sendMessage(this.language.translateString("arenaDoesntExsist"));
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
                    player.sendMessage(this.language.translateString("enbableCMDusage"));
                    break;
                } else {
                    String arenaName = args[1];
                    Arena arena = gameManager.getArenaManager().getArenaByName(arenaName);
                    if (arena == null){
                        player.sendMessage(this.language.translateString("arenaDoesntExsist"));
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
                        player.sendMessage(this.language.translateString("arenaDoesntExsist"));
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
                StringBuilder stringBuffer = new StringBuilder();
                for (Arena arena : gameManager.getArenaManager().getArenas()) {
                    stringBuffer.append(arena.getName()).append(" ");
                }
                player.sendMessage(this.language.translateString("listCMD")
                        .replace("%arenaName%", stringBuffer.toString()));
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
                    player.sendMessage(language.translateString("removeCMDusage"));
                    break;
                } else {
                    String arenaName = args[1];
                    Arena arena = gameManager.getArenaManager().getArenaByName(arenaName);
                    if (arena == null){
                        player.sendMessage(language.translateString("arenaDoesntExsist"));
                        break;
                    }
                    if (arena.isEnabled()) {
                        player.sendMessage(this.language.translateString("disableFirst")
                                .replace("%arenaName%", arenaName));
                        break;
                    }
                    player.sendMessage(language.translateString("arenaRemoved"));
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
                        //
                        player.sendMessage(this.language.translateString("arenaDoesntExsist"));
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
    }
}

