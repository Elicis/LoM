package de.elicis.lom.listener;

import java.util.ArrayList;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;

import de.elicis.lom.Arena;
import de.elicis.lom.Main;
import de.elicis.lom.champions.Champion;

public class L_Combat implements Listener {
	ArrayList<Integer> weapons = new ArrayList<Integer>();

	public L_Combat() {
		addweapons();
	}

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		if (event.getEntityType() == (EntityType.PLAYER)) {
			Player player1 = (Player) event.getEntity();
			if (isInArena(player1)) {
				Arena arena = getArenaP(player1);

				if (event.getDamager().getType() == (EntityType.PLAYER)) {
					if (weapons.contains(player1.getItemInHand().getTypeId())) {
						Player player2 = (Player) event.getDamager();
						if (isInArena(player2)) {
							if (arena.isActive()) {
								if (arena.getTeam(player1) != arena
										.getTeam(player2)) {
									Champion champPlayer1 = arena.getChamps()
											.get(player1.getName());
									Champion champPlayer2 = arena.getChamps()
											.get(player2.getName());
									event.setDamage((float) champPlayer1.getDamage()
											* (100 / (100 + champPlayer2
													.getArmor())));
								}
							}
						}
					}
				}
				if (event.getCause() == DamageCause.PROJECTILE) {
					if (event.getDamager().getType() == (EntityType.ARROW)) {
						Arrow a = (Arrow) event.getDamager();
						if (a.getShooter() instanceof Player) {
							Player player2 = (Player) a.getShooter();
							if (isInArena(player2)) {
								if (arena.isActive()) {
									if (arena.getTeam(player1) != arena
											.getTeam(player2)) {
										Champion champPlayer1 = arena
												.getChamps().get(
														player1.getName());
										Champion champPlayer2 = arena
												.getChamps().get(
														player2.getName());
										event.setDamage((float) champPlayer1
												.getDamage()
												* (100 / (100 + champPlayer2
														.getArmor())));
									}
								}
							}
						}
					}
					if (event.getDamager().getType() == (EntityType.SNOWBALL)) {
						Snowball a = (Snowball) event.getDamager();
						if (a.getShooter() instanceof Player) {
							Player player2 = (Player) a.getShooter();
							if (isInArena(player2)) {
								if (arena.isActive()) {
									if (arena.getTeam(player1) != arena
											.getTeam(player2)) {
										Champion champPlayer1 = arena
												.getChamps().get(
														player1.getName());
										Champion champPlayer2 = arena
												.getChamps().get(
														player2.getName());
										event.setDamage((float) champPlayer1
												.getDamage()
												* (100 / (100 + champPlayer2
														.getArmor())));
									}
								}
							}
						}
					}

				}
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction() == (Action.LEFT_CLICK_AIR)
				|| event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			if (isInArena(player)) {
				if (player.getItemInHand().getTypeId() == 294) {

				}
			}
		}
	}

	/*
	 * Checks if the player is already in an arena
	 */
	public boolean isInArena(Player player) {
		for (Arena arena : Main.Arenas.values()) {
			if (arena.getPlayersS().contains(player.getName())) {
				return true;
			}
			continue;
		}
		return false;
	}

	/*
	 * Return an Arena for a specific Player
	 */
	public Arena getArenaP(Player player) {
		for (Arena arena : Main.Arenas.values()) {
			if (arena.getPlayers().contains(player)) {
				return arena;
			}
			continue;
		}
		return null;
	}

	public void addweapons() {
		weapons.add(290);
		weapons.add(267);
		weapons.add(268);
		weapons.add(352);
		weapons.add(283);
		weapons.add(276);
	}
}
