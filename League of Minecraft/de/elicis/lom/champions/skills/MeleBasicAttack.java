package de.elicis.lom.champions.skills;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class MeleBasicAttack extends Skill{

	public MeleBasicAttack(Player player2, int mana, ItemStack iconItem, int slot, int cooldown) {
		super(player2, mana, iconItem, slot, cooldown);
		setItemSlot("Mele Basic Attack");
	}

	@Override
	public void useSkill() {
		
	}
	
	

}
