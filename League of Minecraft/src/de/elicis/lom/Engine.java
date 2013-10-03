package de.elicis.lom;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.elicis.lom.champions.Champion;
import de.elicis.lom.sign.LoM_Sign;

public class Engine {
	private static Main plugin;

	public Engine(Main t) {
		plugin = t;
	}

	public void startEngine() {

		Bukkit.getScheduler().runTaskTimerAsynchronously(plugin,
				new Runnable() {

					@Override
					public void run() {
						for (Arena arena : Main.Arenas.values()) {
							if (arena.isActive()) {
								for (Champion champ : arena.getChamps()
										.values()) {
									Player player = champ.getPlayer();
									champ.updateChamp();
									champ.setMoney((int) (champ.getMoney() + 1 + champ
											.getGoldRegen()));
									if(champ.getPlayer().getInventory().getItem(44).getData().getItemType() == Material.GOLD_INGOT){
										player.getInventory().setItem(44, new ItemStack(Material.GOLD_INGOT));
									}
									ItemStack ingot = player.getInventory().getItem(44);
									ItemMeta meta = ingot.getItemMeta();
									List<String> list = new ArrayList<String>();
									list.add((String) "" + champ.getMoney());
									meta.setLore(list);
									ingot.setItemMeta(meta);
									player.getInventory().setItem(44, ingot);
									if ((champ.getMana() + champ.getManaRegen()) <= champ
											.getMaxMana()) {
										champ.setMana((int) (champ.getMana() + champ
												.getManaRegen()));
									} else {
										champ.setMana(champ.getMaxMana());
									}
									arena.Champs.put(champ.getPlayerName(),
											champ);
									continue;
								}
								Main.Arenas.put(arena.getName(), arena);
							}
							continue;
						}

					}
				}, 200, 40);
		Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {

			@Override
			public void run() {
				for (LoM_Sign sign : plugin.Signs) {
					sign.setArenas(Main.Arenas);
					sign.updateSign();
				}

			}

		}, 200, 40);
	}
}
