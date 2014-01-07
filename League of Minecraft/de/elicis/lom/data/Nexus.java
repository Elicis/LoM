package de.elicis.lom.data;

import java.io.Serializable;

import org.bukkit.Location;

public class Nexus implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5459151405120872792L;
	int health;
	String team;
	LoMLocation loc;
	public Nexus(String team, Location loc) {
		super();
		this.team = team;
		health = 4000;
		this.loc = new LoMLocation(loc);
	}
	public LoMLocation getLoc() {
		return loc;
	}
	public void setLoc(LoMLocation loc) {
		this.loc = loc;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	
}
