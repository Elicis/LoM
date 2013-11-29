package de.elicis.lom.champions.skills.ashe;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.elicis.lom.champions.skills.Skill;

public class EnchantedCrystalArrow extends Skill{

	public EnchantedCrystalArrow(Player player2, int mana, int slot, int cooldown) {
		super(player2, mana, new ItemStack(Material.ARROW), slot, cooldown);
	}
	
	public void setItemSlot(){
		ItemMeta im = getIconItem().getItemMeta();
		im.setDisplayName("Enchanted Crystal Arrow");
		im.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
		getIconItem().setItemMeta(im);
		getIconItem().removeEnchantment(Enchantment.ARROW_DAMAGE);
		getPlayer().getInventory().setItem(getSlot(), getIconItem());
	}

	@Override
	public void useSkill() {
		
	}

}
