package spleefnk.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import spleefnk.arena.Arena;
import spleefnk.managers.GameManager;

public class SpleefCommand extends Command {

    private GameManager gameManager;

    public SpleefCommand(GameManager gameManager) {
        super("spleef", "Main spleef command");
        this.gameManager = gameManager;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only use these commands as a player");
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
                    player.sendMessage("§cYou dont have permission to this command");
                    break;
                }
                if (args.length == 1) {
                    player.sendMessage("Please do /spleef create <arena name>");
                    break;
                } else if (args.length > 2) {
                    player.sendMessage("Please do /spleef create <arena name>");
                    break;
                } else {
                    String arenaName = args[1];
                    Arena arena = new Arena(arenaName, gameManager);
                    gameManager.getSetupWizardManager().startWizard(player, arena, false);
                    break;
                }
            case "join":
                if (!player.hasPermission("spleefNK.user")) {
                    player.sendMessage("§cYou dont have permission to this command");
                    break;
                }
                if (args.length == 1) {
                    gameManager.getFormManager().sendJoinForm(player);
                    break;
                } else if (args.length > 2) {
                    player.sendMessage("Please do /spleef list for a list of all arenas");
                    player.sendMessage("Please do /spleef join <arena name> to join a arena");
                    break;
                } else {
                    String arenaName = args[1];
                    Arena arena = gameManager.getArenaManager().getArenaByName(arenaName);
                    if (arena == null) {
                        player.sendMessage("§cThis arena doesnt exsist... do /spleef list to see a list of arenas");
                        break;
                    }
                    arena.addPlayer(player);
                    break;
                }
            case "bowspleef":
                if (!player.hasPermission("spleefNK.admin")) {
                    player.sendMessage("§cYou dont have permission to this command");
                    break;
                }
                if (args.length == 1) {
                    player.sendMessage("Please do /spleef list for a list of all arenas");
                    player.sendMessage("Please do /spleef bowspleef <arena name> to enable bowspleef for a arena");
                    break;
                } else if (args.length > 2) {
                    player.sendMessage("Please do /spleef list for a list of all arenas");
                    player.sendMessage("Please do /spleef bowspleef <arena name> to join a arena");
                    break;
                } else {
                    String arenaName = args[1];
                    Arena arena = gameManager.getArenaManager().getArenaByName(arenaName);
                    if (arena == null){
                        player.sendMessage("That arena doesn't exist");
                        break;
                    }
                    if (gameManager.getArenasConfig().getBoolean(arenaName + ".bowSpleef")) {
                        arena.setBowSpleefEnabled(false);
                        player.sendMessage("Bow spleef is now disabled for arena " + arenaName);
                        break;
                    }
                    arena.setBowSpleefEnabled(true);
                    player.sendMessage("Bow spleef is now enabled for arena : " + arenaName);
                    break;
                }

            case "enable":
                if (!player.hasPermission("spleefNK.admin")) {
                    player.sendMessage("§cYou dont have permission to this command");
                    break;
                }
                if (args.length == 1) {
                    gameManager.getFormManager().sendEnableForm(player);
                    break;
                } else if (args.length > 2) {
                    player.sendMessage("Please do /spleef list for a list of all arenas");
                    player.sendMessage("Please do /spleef enable <arena name> to join a arena");
                    break;
                } else {
                    String arenaName = args[1];
                    Arena arena = gameManager.getArenaManager().getArenaByName(arenaName);
                    if (arena == null){
                        player.sendMessage("That arena doesn't exist");
                        break;
                    }
                    player.sendMessage("Arena enabled");
                    arena.setEnabled(true);
                    break;
                }
            case "disable":
                if (!player.hasPermission("spleefNK.admin")) {
                    player.sendMessage("§cYou dont have permission to this command");
                    break;
                }
                if (args.length == 1) {
                    gameManager.getFormManager().sendDisableForm(player);
                    break;
                } else if (args.length > 2) {
                    player.sendMessage("Please do /spleef list for a list of all arenas");
                    player.sendMessage("Please do /spleef disable <arena name> to disable a arena");
                    break;
                } else {
                    String arenaName = args[1];
                    Arena arena = gameManager.getArenaManager().getArenaByName(arenaName);
                    if (arena == null){
                        player.sendMessage("That arena doesn't exist");
                        break;
                    }
                    player.sendMessage("Arena disabled");
                    arena.setEnabled(false);
                    break;
                }
            case "list":
                if (!player.hasPermission("spleefNK.user")) {
                    player.sendMessage("§cYou dont have permission to this command");
                    break;
                }
                if (gameManager.getArenaManager().getArenas().isEmpty()) {
                    player.sendMessage("§cNo arenas have been created yet!");
                }
                for (Arena arena : gameManager.getArenaManager().getArenas()) {
                    player.sendMessage("§a- " + arena.getName());
                }
                break;
            case "help":
                if (!player.hasPermission("spleefNK.user")) {
                    player.sendMessage("§cYou dont have permission to this command");
                    break;
                }
                sendHelp(player);
                break;
            case "remove":
                if (!player.hasPermission("spleefNK.admin")) {
                    player.sendMessage("§cYou dont have permission to this command");
                    break;
                }
                if (args.length == 1) {
                    player.sendMessage("Please do /spleef remove <arena name> to remove a arena");
                    break;
                } else if (args.length > 2) {
                    player.sendMessage("Please do /spleef remove <arena name> to disable a arena");
                    break;
                } else {
                    String arenaName = args[1];
                    Arena arena = gameManager.getArenaManager().getArenaByName(arenaName);
                    if (arena == null){
                        player.sendMessage("That arena doesn't exist");
                        break;
                    }
                    if (arena.isEnabled()) {
                        player.sendMessage("Please disable the arena first with /spleef disable " + arenaName);
                        break;
                    }
                    player.sendMessage("Arena removed");
                    gameManager.getArenaManager().removeArena(arena);
                    break;
                }
            case "edit":
                if (!player.hasPermission("spleefNK.admin")) {
                    player.sendMessage("§cYou dont have permission to this command");
                    break;
                }
                if (args.length == 1) {
                    player.sendMessage("Please do /spleef edit <arena name>");
                    break;
                } else if (args.length > 2) {
                    player.sendMessage("Please do /spleef edit <arena name>");
                    break;
                } else {
                    String arenaName = args[1];
                    Arena arena = gameManager.getArenaManager().getArenaByName(arenaName);
                    if (arena == null){
                        player.sendMessage("That arena doesn't exist");
                        break;
                    }
                    gameManager.getSetupWizardManager().startWizard(player, arena, true);
                    break;
                }
        }
        return false;
    }

    private void sendHelp(Player player) {
        player.sendMessage("§2----Spleef-----\n/spleef list : List all arenas\n/spleef enable/disable : Disable or enable an arena\n/spleef join : join an arena\n/spleef create : create an arena\n/spleef bowspleef : Do bow spleef on or off for a arena\n/spleef remove : remove an arena\n/spleef edit : edit an arena");
    }
}

