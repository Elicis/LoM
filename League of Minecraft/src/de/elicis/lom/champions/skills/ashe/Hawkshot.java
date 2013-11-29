package de.elicis.lom.champions.skills.ashe;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import de.elicis.lom.Main;
import de.elicis.lom.champions.skills.Skill;

public class Hawkshot extends Skill {
	
	public Hawkshot(Player player2, int mana, int slot, int cooldown) {
		super(player2, mana, new ItemStack(Material.POTION), slot, cooldown);
		setItemSlot();
	}
	
	public void setItemSlot(){
		ItemMeta im = getIconItem().getItemMeta();
		im.setDisplayName("Hawkshot");
		getIconItem().setItemMeta(im);
		getPlayer().getInventory().setItem(getSlot(), getIconItem());
	}

	@Override
	public void useSkill() {
		Arrow arrow = (Arrow)getPlayer().launchProjectile(Arrow.class);
		arrow.setMetadata("Hawkshot", new FixedMetadataValue(Main.instance, "Hawkshot"));
		arrow.getWorld().playEffect(arrow.getLocation(), Effect.SMOKE, 1);
		setItemSlot();
	}

}
