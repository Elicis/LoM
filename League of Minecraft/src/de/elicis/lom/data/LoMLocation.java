package de.elicis.lom.data;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LoMLocation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6098062202011548725L;

	int x;
	int y;
	int z;
	String world;

	public LoMLocation(Location loc) {
		x = loc.getBlockX();
		y = loc.getBlockY();
		z = loc.getBlockZ();
		world = loc.getWorld().getName();
	}

	public Location getLocation() {
		Location loc = new Location(Bukkit.getWorld(world), x, y, z);
		return loc;
	}
}
