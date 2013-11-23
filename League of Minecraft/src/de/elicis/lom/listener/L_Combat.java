package de.elicis.lom.listener;

import java.util.ArrayList;

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
import de.elicis.lom.champions.skills.MageBasicAttack;
import de.elicis.lom.data.Arena;
import de.elicis.lom.data.Nexus;
import de.elicis.lom.sign.LoM_Sign;
import de.elicis.lom.sign.LoM_SignType;
import de.elicis.lom.sign.LoM_TowerSign;
import de.elicis.lom.tower.Tower;

public class L_Combat implements Listener {
	ArrayList<Material> weapons = new ArrayList<Material>();
	public L_Combat() {
		addweapons();
	}

	//TODO: Make improvements to combat system
	
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		
		//TODO: Fiddle with metadata to customise arrows.
		
		if(event.getDamager() instanceof Arrow){
			Arrow arrow = (Arrow) event.getDamager();
			if(event.getDamager().hasMetadata("Hawkshot")){
				if(arrow.getShooter() instanceof Player){
					Player player = (Player) arrow.getShooter();
					if(LoM_API.getArenaP(player).getTeam(player) == "blue"){
						for(Entity e: arrow.getNearbyEntities(3, 3, 3)){
							if(e instanceof Player){
								Player p2 = (Player) e;
								if(LoM_API.getArenaP(player).getTeamRed().contains(p2)){
									p2.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 1));
								}
							}
						}
					}
					if(LoM_API.getArenaP(player).getTeam(player) == "red"){
						for(Entity e: arrow.getNearbyEntities(3, 3, 3)){
							if(e instanceof Player){
								Player p2 = (Player) e;
								if(LoM_API.getArenaP(player).getTeamBlue().contains(p2)){
									p2.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 1));
								}
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
					if (weapons.contains(player1.getItemInHand().getData().getItemType())) {
						Player player2 = (Player) event.getDamager();
						if (LoM_API.isInArena(player2)) {
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
							if (LoM_API.isInArena(player2)) {
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
							if (LoM_API.isInArena(player2)) {
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
		
		if(event.getAction() == Action.LEFT_CLICK_BLOCK){
			if(event.getClickedBlock().getType() == Material.SIGN
					|| event.getClickedBlock().getType() == Material.SIGN_POST){
				Sign sign = (Sign) event.getClickedBlock();
				if(LoM_API.isLoM_TowerSign(sign)){
					LoM_TowerSign lsign = LoM_API.getLoM_TowerSign(sign);
						if(LoM_API.getArenaW(sign.getWorld()) != null){
								if(LoM_API.isInArena(player)){
										Arena a = LoM_API.getArenaW(sign.getWorld());
									if(a.getChamps().get(player.getName()) != null){
										Tower t = lsign.getTower();
										Champion c = a.getChamps().get(player.getName());
										t.setHealth(t.getHealth()-(c.getDamage()* (100/100 + t.getArmor())));
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
										nex = a.getNexusBlue();;
									}
									String winner;
									if(team.equalsIgnoreCase("red")){
										winner = "blue";
									}else{
										winner = "red";
									}
								if(nex.getHealth() - damage <= 0){
									a.endGame(winner);
								}else{
									nex.setHealth(nex.getHealth() - damage);
								}
							}
						}
					}
				}
			}
		}
	}
	
	/*
	 * Will Improve this system at a later time!
	 */
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
		if(event.getDamager() instanceof Snowball){
			Snowball snowball = (Snowball) event.getDamager();
			if(snowball.getShooter() instanceof Player){
				Player shooter = (Player) snowball.getShooter();
				if(event.getEntity() instanceof Player){
					Player target = (Player) event.getEntity();
					if(LoM_API.isInArena(shooter) && LoM_API.isInArena(target)){
						if(LoM_API.getArenaP(shooter) != null && LoM_API.getArenaP(target) != null){
							if(LoM_API.getArenaP(shooter).getChamps().get(shooter).getBasicAttack() instanceof MageBasicAttack){
								event.setDamage(2);
							}
						}
					}
				}
			}
		}
	}
	
	public void addweapons() {
		weapons.add(Material.IRON_HOE);
		weapons.add(Material.IRON_SWORD);
		weapons.add(Material.WOOD_SWORD);
		weapons.add(Material.BONE);
		weapons.add(Material.GOLD_SWORD);
		weapons.add(Material.DIAMOND_SWORD);
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
