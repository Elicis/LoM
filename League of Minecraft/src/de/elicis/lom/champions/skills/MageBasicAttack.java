package de.elicis.lom.champions.skills;

import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MageBasicAttack extends Skill {
	long lastlaunched = 0;

	public MageBasicAttack(Player player2, int mana, int slot) {
		super(player2, mana, new ItemStack(Material.FIREBALL), slot, 3);
		setItemSlot();
	}
	
	public void setItemSlot(){
		ItemMeta im = iconItem.getItemMeta();
		im.setDisplayName("Mage Basic Attack");
		iconItem.setItemMeta(im);
		player.getInventory().setItem(slot, iconItem);
	}

	@Override
	public void useSkill() {
		player.launchProjectile(Fireball.class).setShooter(player);
		setItemSlot();
	}
}
