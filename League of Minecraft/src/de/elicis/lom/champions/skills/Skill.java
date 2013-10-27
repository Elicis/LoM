package de.elicis.lom.champions.skills;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.elicis.lom.api.LoM_API;

public abstract class Skill {
	Player player;
	int manaCost;
	ItemStack iconItem;
	int slot;

	public Skill(Player player2, int mana, ItemStack iconItem, int slot) {
		player = player2;
		manaCost = mana;
		this.iconItem = iconItem;
		this.slot = slot;
	}

	public abstract void useSkill();
	
	public boolean hasMana(){
		if(LoM_API.isInArena(player)){
			if(LoM_API.getArenaP(player).getChamps().get(player).getMana() >= manaCost){
				return true;
			}
		}
		return false;
	}
	
	public ItemStack getIconItem(){
		return iconItem;
	}
}
