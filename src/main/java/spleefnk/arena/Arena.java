package spleefnk.arena;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.Location;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.Config;
import spleefnk.Tasks.ActiveGameTask;
import spleefnk.Tasks.LobbyCountDownTask;
import spleefnk.managers.GameManager;

import java.util.ArrayList;
import java.util.List;

public class Arena {

    private List<Player> players = new ArrayList<>();
    private List<Location> brokenBlocks = new ArrayList<>();

    private Location location;

    private String name;

    private boolean enabled;
    private boolean bowSpleefEnabled;

    private int minPlayers;
    private int maxPlayers;

    private GameManager gameManager;

    private Config cfg;

    private GameState gameState = GameState.WAITING;

    private LobbyCountDownTask lobbyCountDownTask;
    private ActiveGameTask activeGameTask;


    public Arena(String name, GameManager gameManager) {

        this.gameManager = gameManager;

        cfg = gameManager.getArenasConfig();
        gameManager.getArenasConfig();

        location = new Location(cfg.getDouble(name + ".spawnLocation.x"), cfg.getDouble(name + ".spawnLocation.y"), cfg.getDouble(name + ".spawnLocation.z"), cfg.getDouble(name + ".spawnLocation.yaw"), cfg.getDouble(name + ".spawnLocation.pitch"), gameManager.getPlugin().getServer().getLevelByName(cfg.getString(name + ".spawnLocation.worldName")));
        this.enabled = cfg.getBoolean(name + ".enabled");
        this.bowSpleefEnabled = cfg.getBoolean(name + ".bowSpleef");
        this.name = name;
        //TODO: make these config options with a formGUI and items to set them in the SetupWizardManager
        this.minPlayers = 2;
        this.maxPlayers = 8;
    }

    public LobbyCountDownTask getLobbyCountDownTask() {
        return lobbyCountDownTask;
    }

    public ActiveGameTask getActiveGameTask() {
        return activeGameTask;
    }

    public boolean isEnabled() {
        return cfg.getBoolean(name + "enabled");
    }

    public void setEnabled(boolean val) {
        cfg.set(this.getName() + ".enabled", val);
        gameManager.saveConfig();
    }

    public void setBowSpleefEnabled(boolean val) {
        cfg.set(this.getName() + ".bowSpleef", val);
        gameManager.saveConfig();
    }

    public void addPlayer(Player player) {
        if (isPlaying(player)) {
            player.sendMessage("§4Your already in a game!");
            return;
        }

        if (!gameManager.getArenasConfig().getBoolean(this.getName() + ".enabled")) {
            player.sendMessage("§4This arena is not enabled!");
            return;
        }

        if (players.size() >= maxPlayers) {
            player.sendMessage("§4This arena is full!");
            return;
        }

        if (this.getGameState() == GameState.ACTIVE || this.getGameState() == GameState.RESTARTING) {
            player.sendMessage("§4This arena is already active!");
            return;
        }
        players.add(player);
        player.setGamemode(0);
        player.setHealth(20);
        player.getFoodData().setLevel(player.getFoodData().getMaxLevel());
        this.sendMessage(player.getName() + "§6 has joined!");
        player.teleport(getSpawnLocation());
        giveItems(player);
        checkLobby();
    }

    public void giveItems(Player player) {
        PlayerInventory pI = player.getInventory();
        pI.clearAll();
        if (!isBowSpleef()){
            Item item = Item.get(ItemID.DIAMOND_SHOVEL);
            item.addEnchantment(Enchantment.get(Enchantment.ID_EFFICIENCY).setLevel(5));
            pI.addItem(item);
        } else {
            Item item = Item.get(ItemID.BOW);
            Item item1 = Item.get(ItemID.ARROW);
            Item item2 = Item.get(ItemID.SNOWBALL);
            item1.setCount(128);
            item2.setCount(128);

            pI.addItem(item);
            pI.addItem(item1);
            pI.addItem(item2);
        }
    }

    private void checkLobby() {
        if (this.players.size() >= this.getMinPlayers()) {
            setGameState(GameState.STARTING);
        }

        if (this.players.size() < this.getMinPlayers() && gameState == GameState.STARTING) {
            setGameState(GameState.WAITING);
            lobbyCountDownTask.cancel();
        }
    }

    public void removePlayer(Player player) {
        players.remove(player);
        player.teleport(gameManager.getPlugin().getServer().getDefaultLevel().getSpawnLocation().getLocation());
        //TODO: player rollback handler
        if (gameState == GameState.STARTING) {
            checkLobby();
        }
    }

    public List<Location> getBrokenBlocksList() {
        return brokenBlocks;
    }

    public boolean isPlaying(Player player) {
        return players.contains(player);
    }

    public List<Player> getPlayers() {
        return players;
    }


    public Location getSpawnLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;

        switch (gameState) {
            case WAITING:
                break;
            case STARTING:
                lobbyCountDownTask = new LobbyCountDownTask(this, gameManager);
                gameManager.getPlugin().getServer().getScheduler().scheduleRepeatingTask(gameManager.getPlugin(), lobbyCountDownTask, 20);
                break;
            case ACTIVE:
                lobbyCountDownTask.cancel();
                sendMessage("§6The game has started");
                activeGameTask = new ActiveGameTask(this, gameManager);
                gameManager.getPlugin().getServer().getScheduler().scheduleRepeatingTask(gameManager.getPlugin(), activeGameTask, 8);
                break;
            case RESTARTING:
                activeGameTask.cancel();
                for (Location location : brokenBlocks) {
                    Vector3 v3 = new Vector3(location.getX(), location.getY(), location.getZ());
                    location.getLevel().setBlock(v3, Block.get(Block.SNOW_BLOCK));
                    for (Player player : this.getPlayers()) {
                        this.removePlayer(player);
                    }
                    this.setGameState(GameState.WAITING);
                }
                break;
        }
    }

    public void sendMessage(String msg) {
        for (Player player : players) {
            player.sendMessage(msg);
        }

    }

    public void setSpawnLocation(Location location) {
        this.location = location;
    }

    public boolean isBowSpleef() {
        return cfg.getBoolean(name + ".bowSpleef");
    }
}
