package de.elicis.lom.champions.skills;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class Volley extends Skill{

	public Volley(Player player2, int mana, int slot, int cooldown) {
		super(player2, mana, new ItemStack(Material.ARROW), slot, cooldown);
	}

	public void setItemSlot(){
		ItemMeta im = iconItem.getItemMeta();
		im.setDisplayName("Volley");
		iconItem.setItemMeta(im);
		player.getInventory().setItem(slot, iconItem);
	}

	
	@Override
	public void useSkill() {
		Arrow snow1 = ((Arrow)player.launchProjectile(Arrow.class));
		Arrow snow2 = ((Arrow)player.launchProjectile(Arrow.class));
		Arrow snow3 = ((Arrow)player.launchProjectile(Arrow.class));
		Arrow snow4 = ((Arrow)player.launchProjectile(Arrow.class));
		Arrow snow5 = ((Arrow)player.launchProjectile(Arrow.class));
		Arrow snow6 = ((Arrow)player.launchProjectile(Arrow.class));
		Arrow snow7 = ((Arrow)player.launchProjectile(Arrow.class));
		snow1.setBounce(true);
		snow2.setBounce(true);
		snow3.setBounce(true);
		snow4.setBounce(true);
		snow5.setBounce(true);
		snow6.setBounce(true);
		snow7.setBounce(true);
		snow1.setVelocity(snow1.getVelocity().multiply(new Vector(2, 0.7, 1.0)));
		snow2.setVelocity(snow2.getVelocity().multiply(new Vector(1, 1.3, 1.0)));
		snow3.setVelocity(snow3.getVelocity().multiply(new Vector(0, 1.03, 1.0)));
		snow4.setVelocity(snow4.getVelocity().multiply(new Vector(-1, 0.65, 1.0)));
		snow5.setVelocity(snow5.getVelocity().multiply(new Vector(-2, 0.8, 1.0)));
		snow6.setVelocity(snow6.getVelocity().multiply(new Vector(3, 0.6, 1.0)));
		snow7.setVelocity(snow7.getVelocity().multiply(new Vector(-3, 0.6, 1.0)));
		if(snow1.doesBounce()){
			System.out.println("Test");
		}
		setItemSlot();
	}

	
	
}
