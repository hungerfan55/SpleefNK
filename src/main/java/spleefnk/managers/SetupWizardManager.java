package spleefnk.managers;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import cn.nukkit.level.Location;
import cn.nukkit.utils.Config;
import spleefnk.Language;
import spleefnk.SpleefPlugin;
import spleefnk.arena.Arena;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetupWizardManager implements Listener {

    private GameManager gameManager;

    private Map<Player, Arena> inWizard = new HashMap<>();

    private Language language;

    private boolean edit;

    private final String SET_SPAWN_LOCATION_ITEM_NAME = "§aSet the spawn location §7(long / right click)";
    private final String SAVE_ARENA_ITEM_NAME = "§cSave arena";
    private final String CANCEL_SETUP_WIZARD_ITEM_NAME = "§cCancel arena creation";


    public SetupWizardManager(GameManager gameManager) {
        this.gameManager = gameManager;
        language = SpleefPlugin.getInstance().getLanguage();
    }

    public void startWizard(Player player, Arena arena, boolean edit) {
        this.edit = edit;
        inWizard.put(player, arena);

        if (arena == null) {
            player.sendMessage(this.language.translateString("somethingWentWrong"));
            return;
        }
        player.setGamemode(1);
        giveItems(player);
        gameManager.getFormManager().sendWizardForm(player);
    }

    public void writePlayers(int minPlayers, int maxPlayers, Player player) {
        Arena arena = inWizard.get(player);
        String name = arena.getName();
        gameManager.getArenasConfig().set(name + ".minPlayers", minPlayers);
        gameManager.getArenasConfig().set(name + ".maxPlayers", maxPlayers);
        player.sendMessage(this.language.translateString("minMaxHasBeenSet"));
    }

    public void giveItems(Player player) {
        player.getInventory().clearAll();
        Item item = Item.get(ItemID.BLAZE_ROD);
        item.setCustomName(SET_SPAWN_LOCATION_ITEM_NAME);
        Item item1 = Item.get(ItemID.REDSTONE_DUST);
        item1.setCustomName(CANCEL_SETUP_WIZARD_ITEM_NAME);
        Item item2 = Item.get(ItemID.EMERALD);
        item2.setCustomName(SAVE_ARENA_ITEM_NAME);

        List<Item> items = new ArrayList<>();
        items.add(item);
        items.add(item1);
        items.add(item2);

        for (Item item3 : items){
            player.getInventory().addItem(item3);
        }
    }

    public void endWizard(Player player) {
        player.getInventory().clearAll();
        inWizard.remove(player);
    }

    public boolean inWizard(Player player) {
        return inWizard.containsKey(player);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!inWizard(player)) return;
        if (player.getInventory().getItemInHand().equals(Item.AIR)) return;

        if (!event.getItem().hasCustomName()){
            return;
        }

        Arena arena = inWizard.get(player);

        String itemName = event.getItem().getCustomName();

        if (itemName.equalsIgnoreCase(SET_SPAWN_LOCATION_ITEM_NAME)) {

            player.sendMessage(this.language.translateString("spawnSet"));
            event.setCancelled(true);
            Location location = player.getLocation();
            String arenaName = arena.getName();
            gameManager.getArenasConfig().set(arenaName + ".enabled", false);
            gameManager.getArenasConfig().set(arenaName + ".bowSpleef", false);
            gameManager.getArenasConfig().set(arenaName + ".spawnLocation.x", location.getX());
            gameManager.getArenasConfig().set(arenaName + ".spawnLocation.y", location.getY());
            gameManager.getArenasConfig().set(arenaName + ".spawnLocation.z", location.getZ());
            gameManager.getArenasConfig().set(arenaName + ".spawnLocation.yaw", location.getYaw());
            gameManager.getArenasConfig().set(arenaName + ".spawnLocation.pitch", location.getPitch());
            gameManager.getArenasConfig().set(arenaName + ".spawnLocation.worldName", location.getLevel().getName());
            gameManager.saveConfig();
            /*
            Config layout

               arenaName:
                 enabled: false
                 spawnLocation:
                   x: 2
                   y: 3
                   z: 3
                   yaw: 3
                   pitch 4
                   worldName : "dfq"
             */
            arena.setSpawnLocation(location);
            return;
        } else if (itemName.equalsIgnoreCase(SAVE_ARENA_ITEM_NAME)) {
            if (edit) {
                player.sendMessage(this.language.translateString("arenaEdited"));
                event.setCancelled(true);
                endWizard(player);
                return;
            }
            player.sendMessage(this.language.translateString("arenaCreated"));
            event.setCancelled(true);
            endWizard(player);
            gameManager.getArenaManager().addArena(arena);
            return;

        } else if (itemName.equalsIgnoreCase(CANCEL_SETUP_WIZARD_ITEM_NAME)) {
            player.sendMessage(this.language.translateString("setupWizardCanceld"));
            event.setCancelled(true);
            endWizard(player);
            return;
        }
    }
}
