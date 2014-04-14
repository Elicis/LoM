package de.elicis.lom.champions.skills.Alistar;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.elicis.lom.champions.skills.Skill;

public class Headbutt extends Skill{

	public Headbutt(Player player2, int mana, ItemStack iconItem, int slot, int cooldown) {
		super(player2, mana, iconItem, slot, cooldown);
		setItemSlot("Headbutt");
	}
	
	@Override
	public void useSkill() {
		getPlayer().playSound(getPlayer().getLocation(), Sound.BLAZE_DEATH, 10, 1);
	}
}
