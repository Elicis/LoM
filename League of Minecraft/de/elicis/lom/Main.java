package de.elicis.lom;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.elicis.lom.api.LoM_API;
import de.elicis.lom.ce.CE_LoM;
import de.elicis.lom.data.Arena;
import de.elicis.lom.data.Config;
import de.elicis.lom.data.ConfigCreator;
import de.elicis.lom.data.InvSave;
import de.elicis.lom.data.Metrics;
import de.elicis.lom.data.SL;
import de.elicis.lom.listener.L_Combat;
import de.elicis.lom.listener.L_Player;
import de.elicis.lom.shop.Shop;
import de.elicis.lom.sign.LoM_Sign;
import de.elicis.lom.sign.LoM_TowerSign;

public class Main extends JavaPlugin {
	public HashMap<String, Arena> Arenas = new HashMap<String, Arena>();
	public ArrayList<LoM_Sign> Signs = new ArrayList<LoM_Sign>();
	public ArrayList<LoM_TowerSign> towerSigns = new ArrayList<LoM_TowerSign>();
	public Shop shop = new Shop();
	public ArrayList<String> champions = new ArrayList<String>();
	private Config inventorys = new Config();
	InvSave invSave = new InvSave();
	Engine eng;
	ConfigCreator creator;
	
	@Override
	public void onEnable(){
		addChamps();
		saveDefaultConfig();
		this.getCommand("lom").setExecutor(new CE_LoM(this));
		getInventorys().reloadConfig("inventorys.yml");
		registerListeners();
		creator = new ConfigCreator();
		try {
			File verzeichnis = new File(this.getDataFolder() + "/data/");
			verzeichnis.mkdirs();
			Arenas = SL.load(this.getDataFolder().getPath() + "/data/arenas.bin");
			Signs = SL.load(this.getDataFolder().getPath() + "/data/signs.bin");
			towerSigns = SL.load(this.getDataFolder().getPath() + "/data/towers.bin");
		} catch (Exception e) {
			
		}
		loadArenas();
		try {
			Metrics metric = new Metrics(this);
			metric.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		loadEngine();
		
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){

			@Override
			public void run() {
				for(Player player: Bukkit.getOnlinePlayers()){
					if(LoM_API.isInArena(player)){
						for(int i=0; i <= 8; i++){
							if(player.getInventory().getItem(i) != null){
								if(player.getInventory().getItem(i).getAmount() > 1){
									player.getInventory().getItem(i).setAmount(player.getInventory().getItem(i).getAmount() - 1);
								}
							}
						}
					}
				}
			}
			
		}, 10, 20);
	}

	@Override
	public void onDisable() {
		getInventorys().saveCustomConfig();
		saveArenas();
		unloadArenas();
		try {
			SL.save(Arenas, this.getDataFolder().getPath() + "/data/arenas.bin");
			SL.save(Signs, this.getDataFolder().getPath() + "/data/signs.bin");
			SL.save(towerSigns, this.getDataFolder().getPath() + "/data/towers.bin");
		} catch (Exception e) {
			
		}
	}

	private void registerListeners() {
		getServer().getPluginManager().registerEvents(new L_Player(), this);
		getServer().getPluginManager().registerEvents(new L_Combat(), this);
		getServer().getPluginManager().registerEvents(shop, this);
	}

	private void loadArenas() {
		for (Arena arena : Arenas.values()) {
			WorldCreator cre = new WorldCreator(arena.getName());
			World world = cre.createWorld();
			world.setKeepSpawnInMemory(true);
			System.out.println("World: " + arena.getName() + " loaded!");
			continue;
		}
	}

	private void saveArenas() {
		for (Arena arena : Arenas.values()) {
			arena.getWorld().save();
			System.out.println("World: " + arena.getName() + " saved!");
			continue;
		}
	}

	public void loadEngine() {
		eng = new Engine();
		eng.startEngine();
	}

	public void unloadArenas() {
		for (Arena arena : Arenas.values()) {
			for (Player player : arena.getPlayers()){
				player.setMaxHealth(20);
				player.setHealth(player.getMaxHealth());
				player.setFoodLevel(20);
				player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
				InvSave.reloadInventory(player);
				player.setGameMode(GameMode.SURVIVAL);
			}
			arena.Players.clear();
			arena.getChamps().clear();
			arena.getChampsBlue().clear();
			arena.getChampsRed().clear();
			arena.clearTeams();

		}
	}
	public void addChamps(){
		champions.add("Ashe");
		champions.add("Garen");
		champions.add("Alistar");
		champions.add("Jax");
		champions.add("Veigar");
	}

	/**
	 * @return the inventorys
	 */
	public Config getInventorys() {
		return inventorys;
	}

	/**
	 * @param inventorys the inventorys to set
	 */
	public void setInventorys(Config inventorys) {
		this.inventorys = inventorys;
	}
    public static Main getPlugin() {
    	return (Main) Bukkit.getPluginManager().getPlugin("League of Minecraft");
    }

}
