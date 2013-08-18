package de.elicis.lom;

import org.bukkit.Bukkit;

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
						for (Arena arena : plugin.Arenas.values()) {
							if (arena.isActive()) {
								for (Champion champ : arena.getChamps()
										.values()) {
									champ.updateChamp();
									champ.setMoney((int) (champ.getMoney() + 1 + champ
											.getGoldRegen()));
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
								plugin.Arenas.put(arena.getName(), arena);
							}
							continue;
						}

					}
				}, 200, 40);
		Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {

			@Override
			public void run() {
				for (LoM_Sign sign : plugin.Signs) {
					sign.updateSign();
				}

			}

		}, 200, 40);
	}
}
