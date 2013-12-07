package de.elicis.lom.champions.skills.ashe;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.elicis.lom.champions.skills.Skill;

public class Focus extends Skill {

	public Focus(Player player2, int mana, int slot, int cooldown) {
		super(player2, mana, new ItemStack(Material.BOOK), slot, cooldown);
		setItemSlot();
	}

	public void setItemSlot(){
		ItemMeta im = getIconItem().getItemMeta();
		im.setDisplayName("Focus");
		getIconItem().setItemMeta(im);
		getPlayer().getInventory().setItem(getSlot(), getIconItem());
	}
	
	@Override
	public void useSkill() {
		/*
		 * This is a passive ability and so not much will happen
		 * The message is mostly just a test.
		 */
		getPlayer().sendMessage(ChatColor.GOLD + "Focus " + ChatColor.RED + "is a passive skill");
		setItemSlot();
	}

}
