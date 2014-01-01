package de.elicis.lom.tower;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.elicis.lom.Main;
import de.elicis.lom.api.LoM_API;
import de.elicis.lom.champions.Champion;
import de.elicis.lom.data.Arena;
import de.elicis.lom.data.LoMLocation;

public class Tower implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 741023570227318835L;
	String team;
	int range;
	int damage;
	int health;
	int maxHealth;
	int armor;
	boolean isShooting;
	TowerType type;
	LoMLocation loc;
	public Tower(String team, Location loc, TowerType type) {
		super();
		this.loc = new LoMLocation(loc);
		this.team = team;
		isShooting = false;
		damage = 150;
		range = 15;
		health = maxHealth = 1550;
		armor = 90;
		this.type = type;
		startTower();
	}
	
	

	public String getTeam() {
		return team;
	}
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public int getArmor() {
		return armor;
	}
	public void setArmor(int armor) {
		this.armor = armor;
	}
	public LoMLocation getLocation() {
		return loc;
	}
	public World getWorld(){
		return Bukkit.getWorld(getLocation().getWorld());
	}
	public boolean isShooting() {
		return isShooting;
	}
	public void setShooting(boolean isShooting) {
		this.isShooting = isShooting;
	}
	public void startTower(){
		if(LoM_API.isArena(getWorld())){
			final Arena a = LoM_API.getArenaW(getWorld());
			
			if(a.isActive()){
				Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new BukkitRunnable(){
					@Override
					public void run() {
						if(!isShooting){
							if(a.isActive()){
								for(Player p : a.getPlayers()){
									if(!a.getTeam(p).equalsIgnoreCase(team)){
										if(p.getLocation().distance(getLocation().getLocation()) < range){
											shootPlayer(p);
										}
									}
								}
							}
						}
					}
					
				}, 1000, 1000);
			}
		}
	}
	public void shootPlayer(final Player player){
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new BukkitRunnable(){
			public void run(){
				try{
					if(getLocation().getLocation().distance(player.getLocation()) > getRange()){
						isShooting = false;
						cancel();
					}else{
						if(!isDestroyed()){
						if(LoM_API.isInArena(player)){
							Arena a = LoM_API.getArenaP(player);
							if(a.isActive()){
								isShooting = true;
								Bukkit.getWorld(a.getWorldName()).playEffect(getLocation().getLocation().add(0, 10, 0), Effect.BLAZE_SHOOT, 0);
								Champion c = a.getChamps().get(player.getName());
								c.setHealth(c.getHealth() - (damage* (100 / (100 + c.getArmor()))));
								
							}
						}else{
							isShooting = false;
							cancel();
						}
						}
					}
				}catch(Exception e){
					
				}
			}
		}, 1000, 2000);
	}
	public int getHealth() {
		return health;
	}
	
	public int getMaxHealth(){
		return maxHealth;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public boolean isDestroyed(){
		if(this.health <= 0){
			return true;
		}else{
			return false;
		}
	}



	public TowerType getType() {
		return type;
	}



	public void setType(TowerType type) {
		this.type = type;
	}
	
	
}
