package de.elicis.lom.listener;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.elicis.lom.api.LoM_API;
import de.elicis.lom.champions.Champion;
import de.elicis.lom.data.Arena;
import de.elicis.lom.data.Nexus;
import de.elicis.lom.events.NexusDamageEvent;
import de.elicis.lom.events.NexusDestroyEvent;
import de.elicis.lom.events.TowerDamageEvent;
import de.elicis.lom.events.TowerDestroyEvent;
import de.elicis.lom.sign.LoM_Sign;
import de.elicis.lom.sign.LoM_SignType;
import de.elicis.lom.sign.LoM_TowerSign;

public class L_Combat implements Listener {
	
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Arrow){
			Arrow arrow = (Arrow) event.getDamager();
			if(event.getDamager().hasMetadata("Hawkshot")){
				if(arrow.getShooter() instanceof Player){
					Player player = (Player) arrow.getShooter();
					if(LoM_API.getArenaP(player).getTeam(player).equalsIgnoreCase("blue")){
						for(Entity e: arrow.getNearbyEntities(3, 3, 3)){
							if(e instanceof Player){
								Player p2 = (Player) e;
								if(LoM_API.getArenaP(player).getTeamRed().contains(p2)){
									p2.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 3, 1));
								}
							}
						}
					}
					if(LoM_API.getArenaP(player).getTeam(player).equalsIgnoreCase("red")){
						for(Entity e: arrow.getNearbyEntities(3, 3, 3)){
							if(e instanceof Player){
								Player p2 = (Player) e;
								if(LoM_API.getArenaP(player).getTeamBlue().contains(p2)){
									p2.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 3, 1));
								}
							}
						}
					}
				}
			}
			if(event.getDamager().hasMetadata("EnchantedCrystalArrow")){
				if(arrow.getShooter() instanceof Player){
					Player player = (Player)arrow.getShooter();
					if(LoM_API.getArenaP(player).getTeam(player).equalsIgnoreCase("blue")){
						if(event.getEntity() instanceof Player){
							Player damaged = (Player)event.getEntity();
							if(LoM_API.getArenaP(damaged).getTeam(damaged).equalsIgnoreCase("red")){
								damaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5, 2));
								damaged.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 2, 1));
								Champion champdamaged = LoM_API.getArenaP(damaged).getChamps().get(damaged.getName());
								Champion champshoother = LoM_API.getArenaP(player).getChamps().get(player.getName());
								Double Damage = (double) (425 + champshoother.getabilityPower() * (100/(100 + champdamaged.getitemMagicResist())));
								for(Entity e: damaged.getNearbyEntities(3, 3, 3)){
									if(e instanceof Player){
										Player p2 = (Player) e;
										if(LoM_API.getArenaP(p2).getTeam(p2).equalsIgnoreCase("red")){
											damaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2, 1));
											p2.setHealth(p2.getHealth() - (Damage/2));
											
										}
									}
								}
								damaged.setHealth(damaged.getHealth() - Damage);
							}
						}
					}
					if(LoM_API.getArenaP(player).getTeam(player).equalsIgnoreCase("red")){
						if(event.getEntity() instanceof Player){
							Player damaged = (Player)event.getEntity();
							if(LoM_API.getArenaP(damaged).getTeam(damaged).equalsIgnoreCase("blue")){
								damaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5, 2));
								damaged.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 2, 1));
								Champion champdamaged = LoM_API.getArenaP(damaged).getChamps().get(damaged.getName());
								Champion champshoother = LoM_API.getArenaP(player).getChamps().get(player.getName());
								Double Damage = (double) (425 + champshoother.getabilityPower() * (100/(100 + champdamaged.getitemMagicResist())));
								for(Entity e: damaged.getNearbyEntities(3, 3, 3)){
									if(e instanceof Player){
										Player p2 = (Player) e;
										if(LoM_API.getArenaP(p2).getTeam(p2).equalsIgnoreCase("blue")){
											damaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2, 1));
											p2.setHealth(p2.getHealth() - (Damage/2));
										}
									}
								}
								damaged.setHealth(damaged.getHealth() - Damage);
							}
						}
					}
				}
			}
		}
		
		if (event.getEntityType() == (EntityType.PLAYER)) {
			Player player1 = (Player) event.getEntity();
			if (LoM_API.isInArena(player1)) {
				Arena arena = LoM_API.getArenaP(player1);
				
				if(event.getDamager() instanceof Player){
					Player damager = (Player)event.getDamager();
					if(damager.getInventory().getItemInHand().getType() == Material.BLAZE_ROD){
						event.setCancelled(true);
					}
				}

				if (event.getDamager().getType() == (EntityType.PLAYER)) {
						Player player2 = (Player) event.getDamager();
						if (LoM_API.isInArena(player2)) {
							if (arena.isActive()) {
								if (!arena.getTeam(player1).equalsIgnoreCase(arena
										.getTeam(player2))) {
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
				if (event.getCause() == DamageCause.PROJECTILE) {
					if (event.getDamager().getType() == (EntityType.ARROW)) {
						Arrow a = (Arrow) event.getDamager();
						if (a.getShooter() instanceof Player) {
							Player player2 = (Player) a.getShooter();
							if (LoM_API.isInArena(player2)) {
								if (arena.isActive()) {
									if (!arena.getTeam(player1).equalsIgnoreCase(arena
											.getTeam(player2))) {
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
							if (LoM_API.isInArena(player2)) {
								if (arena.isActive()) {
									if (!arena.getTeam(player1).equalsIgnoreCase(arena
											.getTeam(player2))) {
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
		
		if(event.getAction() == Action.LEFT_CLICK_BLOCK){
			if(event.getClickedBlock().getType() == Material.SIGN
					|| event.getClickedBlock().getType() == Material.SIGN_POST){
				Sign sign = (Sign) event.getClickedBlock();
				if(LoM_API.isLoM_TowerSign(sign)){
					LoM_TowerSign lsign = LoM_API.getLoM_TowerSign(sign);
						if(LoM_API.getArenaW(sign.getWorld()) != null){
								if(LoM_API.isInArena(player)){
									if(LoM_API.getArenaW(sign.getWorld()).isActive()){
											Arena a = LoM_API.getArenaW(sign.getWorld());
										if(a.getChamps().get(player.getName()) != null){
											Champion c = a.getChamps().get(player.getName());
											int damage = (c.getDamage()* (100/100 + lsign.getTower().getArmor()));
											if(lsign.getTower().getHealth() - damage > 0){
												TowerDamageEvent event1 = new TowerDamageEvent(lsign.getTower(),a, damage, player);
												if(!event1.isCancelled()){
													lsign.getTower().setHealth(lsign.getTower().getHealth()- event1.getAmount());
												}
											}else{
												TowerDestroyEvent event1 = new TowerDestroyEvent(lsign.getTower(), a, player);
												if(!event1.isCancelled()){
													lsign.getTower().setHealth(0);
												}
											}
										}
									}
								
							}
								
						
					}
				}
				if(LoM_API.isLoM_Sign(sign)){
					LoM_Sign lomSign = LoM_API.getLoM_Sign(sign);
					if(LoM_API.isInArena(player)){
						Arena a = LoM_API.getArenaP(player);
						if(a.isActive()){
							Champion c = a.getChamps().get(player.getName());
							if(lomSign.getType().getType()
									.equalsIgnoreCase(LoM_SignType.NEXUS.getType())){
									String team = sign.getLine(1);
									int damage = c.getDamage() * (100/100 + 50);
									Nexus nex;
									if(team.equalsIgnoreCase("red")){
										nex = a.getNexusRed();
									}else{
										nex = a.getNexusBlue();
									}
									String winner;
									if(team.equalsIgnoreCase("red")){
										winner = "blue";
									}else{
										winner = "red";
									}
								if(nex.getHealth() - damage <= 0){
									NexusDestroyEvent event1 = new NexusDestroyEvent(a, player, nex);
									if(!event1.isCancelled()){
										a.endGame(winner);
									}
								}else{
									NexusDamageEvent event1 = new NexusDamageEvent(a, damage, player, nex);
									if(!event1.isCancelled()){
										nex.setHealth(nex.getHealth() - event1.getAmount());
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
	public void onEntityExplode(EntityExplodeEvent event) {
		Entity ent = event.getEntity();
		
		if (ent instanceof Fireball) {
			event.setCancelled(true); //Removes block damage
		}
	}
	
	@EventHandler
	public void onExplosionPrime(ExplosionPrimeEvent event) {
		event.setFire(false); //Only really needed for fireballs
		
		Entity ent = event.getEntity();
		if (ent instanceof Fireball)
			event.setRadius(2); //Increased from default(1), since the fireball now don't cause fire
	}
}
