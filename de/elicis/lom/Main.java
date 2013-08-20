package de.elicis.lom;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

import de.elicis.lom.ce.CE_LoM;
import de.elicis.lom.listener.L_Combat;
import de.elicis.lom.listener.L_Player;
import de.elicis.lom.shop.Shop;
import de.elicis.lom.sign.LoM_Sign;

// Test

public class Main extends JavaPlugin {
	public static HashMap<String, Arena> Arenas = new HashMap<String, Arena>();
	public HashMap<String, Shop> Shops = new HashMap<String, Shop>();
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
			File verzeichnis = new File("plugins/League of Minecraft");
			verzeichnis.mkdirs();
			Arenas = SL.load("plugins/League of Minecraft/arenas.bin");
			Signs = SL.load("plugins/League of Minecraft/signs.bin");
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
			SL.save(Arenas, "plugins/League of Minecraft/arenas.bin");
			SL.save(Signs, "plugins/League of Minecraft/signs.bin");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void registerListeners() {
		getServer().getPluginManager().registerEvents(new L_Player(this), this);
		getServer().getPluginManager().registerEvents(new L_Combat(), this);
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
			arena.Players.clear();
			arena.Champs.clear();
			arena.ChampsBlue.clear();
			arena.ChampsRed.clear();
			arena.TeamBlue.clear();
			arena.TeamRed.clear();

		}
	}

}
