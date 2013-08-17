package de.elicis.lom.shop;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class Shop {
	int pages;
	ArrayList<Inventory> sites = new ArrayList<Inventory>();

	public Shop() {

	}

	public void createShop() {
		createMainPage();
	}

	public void createMainPage() {
		Inventory inv1 = Bukkit.createInventory(null, 54);
		// Redstoneblock
		ItemStack health = new ItemStack(152);
		health.getItemMeta().setDisplayName(ChatColor.GOLD + "Health");
		inv1.setItem(10, health);
		// Ironarmor
		ItemStack armor = new ItemStack(307);
		health.getItemMeta().setDisplayName(ChatColor.GOLD + "Armor");
		inv1.setItem(12, armor);
		// Leather tunic (blue)
		ItemStack magicresist = new ItemStack(299);
		LeatherArmorMeta meta = (LeatherArmorMeta) magicresist.getItemMeta();
		meta.setColor(Color.BLUE);
		magicresist.setItemMeta(meta);
		health.getItemMeta().setDisplayName(ChatColor.GOLD + "Magigresistance");
		inv1.setItem(14, magicresist);
		// Iron Sword
		ItemStack damage = new ItemStack(267);
		health.getItemMeta().setDisplayName(ChatColor.GOLD + "Damage");
		inv1.setItem(29, damage);
		// Diamond hoe
		ItemStack ability = new ItemStack(293);
		health.getItemMeta().setDisplayName(ChatColor.GOLD + "Abilitypower");
		inv1.setItem(33, ability);
		// Lapisblock
		ItemStack mana = new ItemStack(22);
		health.getItemMeta().setDisplayName(ChatColor.GOLD + "Mana");
		inv1.setItem(16, mana);
		// Health potion
		ItemStack consume = new ItemStack(373, 1, (short) 8261);
		health.getItemMeta().setDisplayName(ChatColor.GOLD + "Consume");
		inv1.setItem(31, consume);
		sites.add(inv1);
	}

	public int getNumPages() {
		pages = sites.size();
		return pages;
	}
}
