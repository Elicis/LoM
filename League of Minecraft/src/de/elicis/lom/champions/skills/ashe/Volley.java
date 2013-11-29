package de.elicis.lom.champions.skills.ashe;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import de.elicis.lom.champions.skills.Skill;

public class Volley extends Skill{

	public Volley(Player player2, int mana, int slot, int cooldown) {
		super(player2, mana, new ItemStack(Material.ARROW), slot, cooldown);
		setItemSlot();
	}

	public void setItemSlot(){
		ItemMeta im = getIconItem().getItemMeta();
		im.setDisplayName("Volley");
		getIconItem().setItemMeta(im);
		getPlayer().getInventory().setItem(getSlot(), getIconItem());
	}

	
	@Override
	public void useSkill() {
		Arrow snow1 = ((Arrow)getPlayer().launchProjectile(Arrow.class));
		Arrow snow2 = ((Arrow)getPlayer().launchProjectile(Arrow.class));
		Arrow snow3 = ((Arrow)getPlayer().launchProjectile(Arrow.class));
		Arrow snow4 = ((Arrow)getPlayer().launchProjectile(Arrow.class));
		Arrow snow5 = ((Arrow)getPlayer().launchProjectile(Arrow.class));
		Arrow snow6 = ((Arrow)getPlayer().launchProjectile(Arrow.class));
		Arrow snow7 = ((Arrow)getPlayer().launchProjectile(Arrow.class));
		snow1.setVelocity(snow1.getVelocity().multiply(new Vector(2, 0.7, 1.0)));
		snow2.setVelocity(snow2.getVelocity().multiply(new Vector(1, 1.3, 1.0)));
		snow3.setVelocity(snow3.getVelocity().multiply(new Vector(0, 1.03, 1.0)));
		snow4.setVelocity(snow4.getVelocity().multiply(new Vector(-1, 0.65, 1.0)));
		snow5.setVelocity(snow5.getVelocity().multiply(new Vector(-2, 0.8, 1.0)));
		snow6.setVelocity(snow6.getVelocity().multiply(new Vector(3, 0.6, 1.0)));
		snow7.setVelocity(snow7.getVelocity().multiply(new Vector(-3, 0.6, 1.0)));
		setItemSlot();
	}

	
	
}
