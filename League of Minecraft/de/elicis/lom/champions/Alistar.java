package de.elicis.lom.champions;

import org.bukkit.entity.Player;

import de.elicis.lom.champions.skills.MageBasicAttack;
import de.elicis.lom.champions.skills.Alistar.Pulverize;

public class Alistar extends Champion {

	public Alistar(Player player2) {
		name = "Alistar";
		player = player2.getName();
		level = getPlayer().getLevel();
		health = (double) (442 + (level * 102) + itemhealth);
		mana = 215 + (38 * level) + itemmana;
		manaregen = 6.45 + (0.45 * level) + itemmanaregen;
		damage = (int) (55.03 + (level * 6.62) + itemdamage);
		abilityPower = 0 * level + itemabilityPower;
		armor = (int) (14.5 + (level * 3.5) + itemarmor);
		magicResist = (int) (30 + (level * 1.25) + itemmagicResist);
		speed = 1 + itemspeed;
		basicAttack = new MageBasicAttack(player2, 0, 7); // TODO: Create Mele Basic Attack
		skills.add(new Pulverize(player2, 70, 0, 17));
		getPlayer().setMaxHealth(health);
		getPlayer().setHealth(health);
		getPlayer().setHealthScaled(true);
	}

	@Override
	public void updateChamp() {
		level = getPlayer().getLevel();
		health = (double) (442 + (level * 102) + itemhealth);
		mana = 215 + (38 * level) + itemmana;
		manaregen = 6.45 + (0.45 * level) + itemmanaregen;
		damage = (int) (55.03 + (level * 6.62) + itemdamage);
		abilityPower = 0 * level + itemabilityPower;
		armor = (int) (14.5 + (level * 3.5) + itemarmor);
		magicResist = (int) (30 + (level * 1.25) + itemmagicResist);
		speed = 1 + itemspeed;
	}

}
