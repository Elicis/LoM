package de.elicis.lom.api;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import de.elicis.lom.data.Arena;
import de.elicis.lom.sign.LoM_Sign;

public class LoM_API {
	/*
	 * Returns true if the world is a Arena
	 */
	public static boolean isArena(World world) {
		for (Arena arena : de.elicis.lom.Main.getPlugin().Arenas.values()) {
			if (arena.getWorld().getName() == world.getName()) {
				return true;
			}
			continue;
		}
		return false;
	}

	/*
	 * Checks if the player is already in an arena
	 */
	public static boolean isInArena(Player player) {
		for (Arena arena : de.elicis.lom.Main.getPlugin().Arenas.values()) {
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
	public static Arena getArenaP(Player player) {
		for (Arena arena : de.elicis.lom.Main.getPlugin().Arenas.values()) {
			if (arena.getPlayers().contains(player)) {
				return arena;
			}
			
		}
		return null;
	}

	/*
	 * Checks if the Sign is a LoM_Sign
	 */
	public static boolean isLoM_Sign(Sign sign) {
		boolean isSign = false;
		Location loc = sign.getBlock().getLocation();
		for (LoM_Sign lomSign : de.elicis.lom.Main.getPlugin().Signs) {
			if (isSameLocation(loc, lomSign.getLocation().getLocation())) {
				isSign = true;
			}
		}
		return isSign;
	}

	/*
	 * Return the LoM_Sign for a Sign
	 */
	public static LoM_Sign getLoM_Sign(Sign sign) {
		Location loc = sign.getBlock().getLocation();
		for (LoM_Sign lomSign : de.elicis.lom.Main.getPlugin().Signs) {
			if (isSameLocation(loc, lomSign.getLocation().getLocation())) {
				return lomSign;
			}
		}
		return null;
	}
	/*
	 * Compares two Locations
	 */
	public static boolean isSameLocation(Location loc1, Location loc2){
		int x1 = loc1.getBlockX();
		int x2 = loc2.getBlockX();
		int y1 = loc1.getBlockY();
		int y2 = loc2.getBlockY();
		int z1 = loc1.getBlockZ();
		int z2 = loc2.getBlockZ();
		String world1 = loc1.getWorld().getName();
		String world2 = loc2.getWorld().getName();
		boolean isSame = false;
		if(x1 == x2 && y1 == y2 && z1 == z2 && world1.equalsIgnoreCase(world2)){
			isSame = true;
		}
		return isSame;
	}

	public static Arena getArenaW(World world) {
		for (Arena arena : de.elicis.lom.Main.getPlugin().Arenas.values()) {
			if (arena.getWorld().getName() == world.getName()) {
				return arena;
			}
			
		}
		return null;
	}
}
