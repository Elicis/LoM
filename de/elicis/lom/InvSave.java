package de.elicis.lom;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InvSave {
	private static Main plugin;

	public InvSave(Main t) {
		plugin = t;
	}

	public static void saveInventory(Player player) {
		plugin.inventorys.reloadConfig("inventorys.yml");
		plugin.inventorys.getConfig("inventorys.yml").set(
				player.getName() + ".inventory",
				player.getInventory().getContents());
		plugin.inventorys.getConfig("inventorys.yml").set(
				player.getName() + ".armor",
				player.getInventory().getArmorContents());
		plugin.inventorys.getConfig("inventorys.yml").set(
				player.getName() + ".level", player.getLevel());
		plugin.inventorys.saveCustomConfig();
	}

	public static void reloadInventory(Player player) {
		plugin.inventorys.reloadConfig("inventorys.yml");
		if (plugin.inventorys.getConfig("inventorys.yml").get(
				player.getName() + ".inventory") != null
				&& plugin.inventorys.getConfig("inventorys.yml").get(
						player.getName() + ".armor") != null
				&& plugin.inventorys.getConfig("inventorys.yml").get(
						player.getName() + ".level") != null) {
			player.getInventory().setArmorContents(
					configToItemStack("inventorys.yml", player, ".armor"));
			player.getInventory().setContents(
					configToItemStack("inventorys.yml", player, ".inventory"));
			player.setLevel((int) plugin.inventorys.getConfig("inventorys.yml")
					.get(player.getName() + ".level"));
		}
	}

	private static ItemStack[] configToItemStack(String yml, Player player,
			String section) {
		@SuppressWarnings("unchecked")
		List<ItemStack> list = (List<ItemStack>) plugin.inventorys.getConfig(
				yml).get(player.getName() + section);
		ItemStack[] stack = list.toArray(new ItemStack[0]);
		return stack;
	}

}
