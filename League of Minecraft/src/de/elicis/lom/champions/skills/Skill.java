package de.elicis.lom.champions.skills;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.elicis.lom.api.LoM_API;

public abstract class Skill {
	Player player;
	int manaCost;
	ItemStack iconItem;
	int slot;
	int cooldown;

	// TODO: Implement way of showing player their current mana
	
	public Skill(Player player2, int mana, ItemStack iconItem, int slot, int cooldown) {
		player = player2;
		manaCost = mana;
		this.iconItem = iconItem;
		this.slot = slot;
		if(this.slot > 8){
			this.slot = 8;
		}
		if(this.slot < 0){
			this.slot = 0;
		}
		this.cooldown = cooldown;
	}

	public abstract void useSkill();
	
	public boolean hasMana(){
		if(LoM_API.isInArena(player)){
			if(LoM_API.getArenaP(player).getChamps().get(player.getName()).getMana() >= manaCost){
				return true;
			}
		}
		return false;
	}
	
	public int getSlot(){
		return slot;
	}
	
	public int getCooldown(){
		return cooldown;
	}
	
	public ItemStack getIconItem(){
		return iconItem;
	}
}
