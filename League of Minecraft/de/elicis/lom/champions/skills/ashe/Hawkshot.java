package de.elicis.lom.champions.skills.ashe;

import org.bukkit.Effect;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import de.elicis.lom.Main;
import de.elicis.lom.champions.skills.Skill;

public class Hawkshot extends Skill {
	
	public Hawkshot(Player player2, int mana, int slot, int cooldown) {
		super(player2, mana, new ItemStack(new Potion(PotionType.SLOWNESS).toItemStack(1)), slot, cooldown);
		setItemSlot("Hawkshot");
	}

	@Override
	public void useSkill() {
		Arrow arrow = (Arrow)getPlayer().launchProjectile(Arrow.class);
		arrow.setMetadata("Hawkshot", new FixedMetadataValue(Main.getPlugin(), "Hawkshot"));
		arrow.getWorld().playEffect(arrow.getLocation(), Effect.SMOKE, 1);
		setItemSlot("Hawkshot");
	}

}
