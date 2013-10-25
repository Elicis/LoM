package de.elicis.lom.data;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.elicis.lom.Main;

public class InvSave {

	public InvSave() {
	}

	public static void saveInventory(Player player) {
		Main.getPlugin().getInventorys().reloadConfig("inventorys.yml");
		Main.getPlugin().getInventorys().getConfig("inventorys.yml").set(
				player.getName() + ".inventory",
				player.getInventory().getContents());
		Main.getPlugin().getInventorys().getConfig("inventorys.yml").set(
				player.getName() + ".armor",
				player.getInventory().getArmorContents());
		Main.getPlugin().getInventorys().getConfig("inventorys.yml").set(
				player.getName() + ".level", player.getLevel());
		Main.getPlugin().getInventorys().saveCustomConfig();
	}

	public static void reloadInventory(Player player) {
		Main.getPlugin().getInventorys().reloadConfig("inventorys.yml");
		if (Main.getPlugin().getInventorys().getConfig("inventorys.yml").get(
				player.getName() + ".inventory") != null
				&& Main.getPlugin().getInventorys().getConfig("inventorys.yml").get(
						player.getName() + ".armor") != null
				&& Main.getPlugin().getInventorys().getConfig("inventorys.yml").get(
						player.getName() + ".level") != null) {
			player.getInventory().setArmorContents(
					configToItemStack("inventorys.yml", player, ".armor"));
			player.getInventory().setContents(
					configToItemStack("inventorys.yml", player, ".inventory"));
			player.setLevel((int) Main.getPlugin().getInventorys().getConfig("inventorys.yml")
					.get(player.getName() + ".level"));
		}
	}

	private static ItemStack[] configToItemStack(String yml, Player player,
			String section) {
		@SuppressWarnings("unchecked")
		List<ItemStack> list = (List<ItemStack>) Main.getPlugin().getInventorys().getConfig(
				yml).get(player.getName() + section);
		ItemStack[] stack = list.toArray(new ItemStack[0]);
		return stack;
	}

}
