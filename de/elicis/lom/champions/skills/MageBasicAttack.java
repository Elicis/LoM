package de.elicis.lom.champions.skills;

import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;

public class MageBasicAttack extends Skill {
	long lastlaunched = 0;

	public MageBasicAttack(Player player2, int mana) {
		super(player2, mana);
	}

	@Override
	public void useSkill() {
		if (new Date().getTime() - lastlaunched <= 2000) {
			player.launchProjectile(Snowball.class).setShooter(player);
			lastlaunched = new Date().getTime();
		}
	}

}
