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

import de.elicis.lom.ce.CE_LoM;
import de.elicis.lom.listener.L_Combat;
import de.elicis.lom.listener.L_Player;
import de.elicis.lom.shop.Shop;
import de.elicis.lom.sign.LoM_Sign;

public class Main extends JavaPlugin {
	public static HashMap<String, Arena> Arenas = new HashMap<String, Arena>();
	public ArrayList<LoM_Sign> Signs = new ArrayList<LoM_Sign>();
	public Shop shop = new Shop();
	Config inventorys = new Config(this);
	InvSave invSave = new InvSave(this);
	Engine eng;

	@Override
	public void onEnable() {
		this.getCommand("lom").setExecutor(new CE_LoM(this));
		inventorys.reloadConfig("inventorys.yml");
		registerListeners();
		try {
			File verzeichnis = new File(this.getDataFolder() + "/data/");
			verzeichnis.mkdirs();
			Arenas = SL.load(this.getDataFolder().getPath() + "/data/arenas.bin");
			Signs = SL.load(this.getDataFolder().getPath() + "/data/signs.bin");
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		loadArenas();
		try {
			Metrics metric = new Metrics(this);
			metric.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loadEngine();
	}

	@Override
	public void onDisable() {
		inventorys.saveCustomConfig();
		saveArenas();
		unloadArenas();
		try {
			SL.save(Arenas, this.getDataFolder().getPath() + "/data/arenas.bin");
			SL.save(Signs, this.getDataFolder().getPath() + "/data/signs.bin");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void registerListeners() {
		getServer().getPluginManager().registerEvents(new L_Player(this), this);
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
		eng = new Engine(this);
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
			arena.Champs.clear();
			arena.ChampsBlue.clear();
			arena.ChampsRed.clear();
			arena.TeamBlue.clear();
			arena.TeamRed.clear();

		}
	}

}
