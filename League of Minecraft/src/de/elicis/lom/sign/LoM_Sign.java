package de.elicis.lom.sign;

import java.io.Serializable;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import de.elicis.lom.Main;
import de.elicis.lom.data.Arena;
import de.elicis.lom.data.LoMLocation;

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
	
	//Just for SignType.Arena
	int maxplayer;

	public LoM_Sign(String name2, LoM_SignType type, Location location) {
		this.type = type;
		Arena = Main.getPlugin().Arenas;
		maxplayer = Main.getPlugin().getConfig().getInt("arena.maxPlayer");
			name = name2;
			loc = new LoMLocation(location);
			if (loc.getLocation().getBlock().getType() == Material.SIGN
					|| loc.getLocation().getBlock().getType() == Material.SIGN_POST) {
				if (type == LoM_SignType.ARENA) {
					if (Arena.containsKey(name2)) {
						Arena a = Arena.get(name);
					String state;
					if (a.isActive()) {
						state =  "Running";
					} else {
						state =  "Ready";
					}
					line1 = "[Arena]";
					line2 = name;
					line3 = "[" + a.getPlayers().size() + "/"+ maxplayer +"]" + "[" + state
							+ ChatColor.BLACK + "]";
					}
				} else if (type == LoM_SignType.CHAMPION) {
					line1 = "[Champion]";
					line2 = name;
					line3 = "";
				} else if (type == LoM_SignType.NEXUS){
					line1 = "[Nexus]";
					line2 = getSign().getLine(1);
					line3 = "";
				}
					
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
				line3 = "";
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
					line3 = "[" + a.getPlayers().size() + "/"+ maxplayer + "]" + "[" + state + "]";
				}

			}
			if(type.getType().equalsIgnoreCase(LoM_SignType.NEXUS.getType())){
				line1 = "[Nexus]";
				line2 = getSign().getLine(1);
				line3 = "";
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

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public String getLine3() {
		return line3;
	}

	public void setLine3(String line3) {
		this.line3 = line3;
	}
	public Sign getSign(){
		if (loc.getLocation().getBlock().getType() == Material.SIGN
				|| loc.getLocation().getBlock().getType() == Material.SIGN_POST) {
			return (Sign) loc.getLocation().getBlock();
		}
		return null;
	}
}
