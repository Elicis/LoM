package de.elicis.lom.champions.skills;

import java.util.Date;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MageBasicAttack extends Skill {
	long lastlaunched = 0;

	public MageBasicAttack(Player player2, int mana, int slot) {
		super(player2, mana, new ItemStack(Material.SNOW_BALL), slot, 2);
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
		//if (new Date().getTime() - lastlaunched <= 2000) {
			// Note to self: try spawning snowball as entity so we can change the damage of the snowball
			player.launchProjectile(Snowball.class).setShooter(player);
			lastlaunched = new Date().getTime();
			setItemSlot();
		//}
	}
}
