package de.elicis.lom.champions;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Veigar extends Champion {
	public Veigar(Player player2) {
		name = "Veigar";
		player = player2.getName();
		level = getPlayer().getLevel();
		health = (double) (355 + (level * 82) + itemhealth);
		mana = 230 + (35 * level) + itemmana;
		manaregen = 6.4 + (0.7 * level) + itemmanaregen;
		damage = (int) (48.3 + (level * 2.625) + itemdamage);
		abilityPower = 0 * level + itemabilityPower;
		armor = (int) (12.25 + (level * 3.75) + itemarmor);
		magicResist = 30 + (level * 0) + itemmagicResist;
		speed = 1 + itemspeed;
		weapon = new ItemStack(Material.GOLD_HOE, 1);
		weapon.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		getPlayer().getInventory().addItem(weapon);
		getPlayer().setMaxHealth(health);
		getPlayer().setHealth(health);
		getPlayer().setHealthScaled(true);
	}

	@Override
	public void updateChamp() {
		level = getPlayer().getLevel();
		health = (double) (355 + (level * 82) + itemhealth);
		damage = (int) (48.3 + (level * 2.625) + itemdamage);
		abilityPower = 0 * level + itemabilityPower;
		armor = (int) (12.25 + (level * 3.75) + itemarmor);
		magicResist = 30 + (level * 0) + itemmagicResist;
		speed = 1 + itemspeed;
	}

}
