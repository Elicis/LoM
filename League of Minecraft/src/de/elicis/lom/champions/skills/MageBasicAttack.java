package de.elicis.lom.champions.skills;

import java.util.Date;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;

public class MageBasicAttack extends Skill {
	long lastlaunched = 0;

	public MageBasicAttack(Player player2, int mana, int slot) {
		super(player2, mana, new ItemStack(Material.SNOW_BALL), slot);
		setItemSlot();
	}
	
	public void setItemSlot(){
		player.getInventory().setItem(slot, iconItem);
		player.getInventory().getItem(slot).getItemMeta().setDisplayName("Mage Basic Attack");
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
