package de.elicis.lom.sign;

import java.io.Serializable;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import de.elicis.lom.Arena;
import de.elicis.lom.LoMLocation;

public class LoM_Sign implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6659308713687725185L;
	LoMLocation loc;
	LoM_SignType type;
	HashMap<String, Arena> Arena;
	String name;
	String line1;
	String line2;
	String line3;

	public LoM_Sign(String name2, LoM_SignType type2, Location location, HashMap<String, Arena> Arena2) {
		
		Arena = Arena2;
		if (Arena.containsKey(name2)) {
			name = name2;
			Arena a = Arena.get(name);
			loc = new LoMLocation(location);
			if (loc.getLocation().getBlock().getType() == Material.SIGN
					|| loc.getLocation().getBlock().getType() == Material.SIGN_POST) {
				if (type == LoM_SignType.ARENA) {
					String state;
					if (a.isActive()) {
						state =  "Running";
					} else {
						state =  "Ready";
					}
					line1 = "[Arena]";
					line2 = name;
					line3 = "[" + a.getPlayers().size() + "/10]" + "[" + state
							+ ChatColor.BLACK + "]";
				} else if (type == LoM_SignType.CHAMPION) {
					line1 = "[Champion]";
					line2 = name;
					line3 = "";
				}
			}
			type = type2;
		}
	}

	public void updateSign() {
		Block b = loc.getLocation().getBlock();
		if (b.getType() == Material.SIGN_POST
				|| b.getType() == Material.WALL_SIGN) {
			if (type.getType()
					.equalsIgnoreCase(LoM_SignType.CHAMPION.getType())) {

				line1 = "[Champion]";
				line2 = name;

			}
			if (type.getType().equalsIgnoreCase(LoM_SignType.ARENA.getType())) {
				if (Arena.containsKey(name)) {
					Arena a = Arena.get(name);
					String state;
					if (a.isActive()) {
						state =  "Running";
					} else {
						state =  "Ready";
					}
					line1 = "[Arena]";
					line2 = name;
					line3 = "[" + a.getPlayers().size() + "/10]" + "[" + state + "]";
				}

			}
			Sign sign = (Sign) b.getState();
			sign.setLine(0, line1);
			sign.setLine(1, line2);
			sign.setLine(2, line3);
			sign.update(true);

		}
	}
	public void setArenas(HashMap<String, Arena> Arena2){
		Arena = Arena2;
	}

	public LoMLocation getLocation() {
		return loc;
	}

	public String getName() {
		return name;
	}

	public LoM_SignType getType() {
		return type;
	}
}
