package de.elicis.lom.champions.skills;

import org.bukkit.entity.Player;

public class Skill {
	Player player;
	int manaCost;

	public Skill(Player player2, int mana) {
		player = player2;
		manaCost = mana;
	}

	public void useSkill() {
	}
}
