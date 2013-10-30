package de.elicis.lom.champions;

import org.bukkit.entity.Player;

public class Garen extends Champion {
	public Garen(Player player2) {
		name = "Garen";
		player = player2.getName();
		level = getPlayer().getLevel();
		health = (double) (455 + (level * 96) + itemhealth);
		mana = 0 + itemmana;
		manaregen = 0 + itemmanaregen;
		damage = (int) (52 + (level * 3.5) + itemdamage);
		abilityPower = 0 * level + itemabilityPower;
		armor = (int) (19 + (level * 2.7) + itemarmor);
		magicResist = (int) (30 + (level * 1.25) + itemmagicResist);
		speed = 1 + itemspeed;
		getPlayer().setMaxHealth(health);
		getPlayer().setHealth(health);
		getPlayer().setHealthScaled(true);

	}

	@Override
	public void updateChamp() {
		level = getPlayer().getLevel();
		health = (double) (455 + (level * 96) + itemhealth);
		mana = 0 + itemmana;
		manaregen = 0 + itemmanaregen;
		damage = (int) (52 + (level * 3.5) + itemdamage);
		abilityPower = 0 * level + itemabilityPower;
		armor = (int) (19 + (level * 2.7) + itemarmor);
		magicResist = (int) (30 + (level * 1.25) + itemmagicResist);
		speed = 1 + itemspeed;
	}
}
