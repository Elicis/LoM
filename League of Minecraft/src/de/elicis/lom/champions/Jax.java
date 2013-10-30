package de.elicis.lom.champions;

import org.bukkit.entity.Player;

public class Jax extends Champion {
	public Jax(Player player2) {
		name = "Jax";
		player = player2.getName();
		level = getPlayer().getLevel();
		health = (double) (463 + (level * 98) + itemhealth);
		mana = 230 + (35 * level) + itemmana;
		manaregen = 6.4 + (0.7 * level) + itemmanaregen;
		damage = (int) (56.3 + (level * 3.375) + itemdamage);
		abilityPower = 0 * level + itemabilityPower;
		armor = (int) (18 + (level * 3.5) + itemarmor);
		magicResist = (int) (30 + (level * 1.25) + itemmagicResist);
		speed = 1 + itemspeed;
		getPlayer().setMaxHealth(health);
		getPlayer().setHealth(health);
		getPlayer().setHealthScaled(true);
	}

	@Override
	public void updateChamp() {
		level = getPlayer().getLevel();
		health = (double) (463 + (level * 98) + itemhealth);
		mana = 230 + (35 * level) + itemmana;
		manaregen = 6.4 + (0.7 * level) + itemmanaregen;
		damage = (int) (56.3 + (level * 3.375) + itemdamage);
		abilityPower = 0 * level + itemabilityPower;
		armor = (int) (18 + (level * 3.5) + itemarmor);
		magicResist = (int) (30 + (level * 1.25) + itemmagicResist);
		speed = 1 + itemspeed;
	}
}
