package de.elicis.lom.champions.skills.Alistar;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import de.elicis.lom.api.LoM_API;
import de.elicis.lom.champions.skills.Skill;

public class Pulverize extends Skill {

	public Pulverize(Player player2, int mana, int slot, int cooldown) {
		super(player2, mana, new ItemStack(new Potion(PotionType.STRENGTH).toItemStack(1)), slot, cooldown);
		setItemSlot();
	}
	
	public void setItemSlot(){
		ItemMeta im = getIconItem().getItemMeta();
		im.setDisplayName("Pulverize");
		getIconItem().setItemMeta(im);
		getPlayer().getInventory().setItem(getSlot(), getIconItem());
	}

	@Override
	public void useSkill() {
		getPlayer().playSound(getPlayer().getLocation(), Sound.EXPLODE, 10, 1);
		for(Entity e: getPlayer().getNearbyEntities(5, 5, 5)){
			if(e instanceof Player){
				Player player = (Player) e;
				// TODO: Test Pulverize
				if(LoM_API.getArenaP(player).getTeam(player) != LoM_API.getArenaP(getPlayer()).getTeam(getPlayer())){
					player.setVelocity(player.getVelocity().setY(5.0));
					player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1, 2));
				}
			}
		}
		setItemSlot();
	}

}
