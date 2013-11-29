package de.elicis.lom.champions.skills.ashe;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import de.elicis.lom.Main;
import de.elicis.lom.champions.skills.Skill;

public class EnchantedCrystalArrow extends Skill{

	public EnchantedCrystalArrow(Player player2, int mana, int slot, int cooldown) {
		super(player2, mana, new ItemStack(Material.SNOW_BALL), slot, cooldown);
		setItemSlot();
	}
	
	public void setItemSlot(){
		ItemMeta im = getIconItem().getItemMeta();
		im.setDisplayName("Enchanted Crystal Arrow");
		im.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
		getIconItem().setItemMeta(im);
		getPlayer().getInventory().setItem(getSlot(), getIconItem());
	}

	@Override
	public void useSkill() {
		Arrow arrow1 = (Arrow)getPlayer().launchProjectile(Arrow.class);
		arrow1.setVelocity(arrow1.getVelocity().multiply(3));
		arrow1.setMetadata("EnchantedCrystalArrow", new FixedMetadataValue(Main.getPlugin(), "EnchantedCrystalArrow"));
	}

}
