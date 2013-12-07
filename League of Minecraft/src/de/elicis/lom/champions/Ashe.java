package de.elicis.lom.champions;

import org.bukkit.entity.Player;

import de.elicis.lom.champions.skills.RangedBasicAttack;
import de.elicis.lom.champions.skills.ashe.EnchantedCrystalArrow;
import de.elicis.lom.champions.skills.ashe.Focus;
import de.elicis.lom.champions.skills.ashe.Hawkshot;
import de.elicis.lom.champions.skills.ashe.Volley;

public class Ashe extends Champion {
	public Ashe(Player player2) {
		name = "Ashe";
		player = player2.getName();
		level = getPlayer().getLevel();
		health = (double) (395 + (level * 79) + itemhealth);
		mana = 173 + (35 * level) + itemmana;
		manaregen = 6.3 + (0.4 * level) + itemmanaregen;
		damage = (int) (46.3 + (level * 2.85) + itemdamage);
		abilityPower = 0 * level + itemabilityPower;
		armor = (int) (11.5 + (level * 3.4) + itemarmor);
		magicResist = 30 + itemmagicResist;
		goldregen = 1.0D; // Test
		speed = 1 + itemspeed;
		//TODO: Finish rest of ashes skills
		basicAttack = new RangedBasicAttack(player2, 0, 7);
		skills.add(new Focus(player2, 0, 0, 0)); // Focus (Passive)
		skills.add(new Volley(player2, 60, 1, 16)); // Volley
		skills.add(new Hawkshot(player2, 0, 2, 60)); // Hawk Shot
		skills.add(new EnchantedCrystalArrow(player2, 100, 4, 100)); // Enchanted Crystal Arrow
		addSkills(player2);
		getPlayer().setMaxHealth(health);
		getPlayer().setHealth(health);
		getPlayer().setHealthScaled(true);
	}

	@Override
	public void updateChamp() {
		level = getPlayer().getLevel();
		health = (double) (395 + (level * 79) + itemhealth);
		mana = 173 + (35 * level) + itemmana;
		manaregen = 6.3 + (0.4 * level) + itemmanaregen;
		damage = (int) (46.3 + (level * 2.85) + itemdamage);
		abilityPower = 0 * level + itemabilityPower;
		armor = (int) (11.5 + (level * 3.4) + itemarmor);
		magicResist = 30 + itemmagicResist;
		speed = 1 + itemspeed;
	}
}
