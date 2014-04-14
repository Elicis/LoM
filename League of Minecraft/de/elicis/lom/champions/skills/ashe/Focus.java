package de.elicis.lom.champions.skills.ashe;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.elicis.lom.champions.skills.Skill;

public class Focus extends Skill {

	public Focus(Player player2, int mana, int slot, int cooldown) {
		super(player2, mana, new ItemStack(Material.BOOK), slot, cooldown);
		setItemSlot("Focus");
	}
	
	@Override
	public void useSkill() {
		/*
		 * This is a passive ability and so not much will happen
		 * The message is mostly just a test.
		 */
		getPlayer().sendMessage(ChatColor.GOLD + "Focus " + ChatColor.RED + "is a passive skill");
		setItemSlot("Focus");
	}

}
