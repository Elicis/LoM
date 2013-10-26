package de.elicis.lom.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import de.elicis.lom.champions.Champion;

public class Arena implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 946226928291619094L;
	String name;
	String world;
	LoMLocation LobbyRed;
	LoMLocation LobbyBlue;
	LoMLocation SpawnRed;
	LoMLocation SpawnBlue;
	Boolean active = false;
	public ArrayList<String> Players = new ArrayList<String>();
	private ArrayList<String> TeamRed = new ArrayList<String>();
	private ArrayList<String> TeamBlue = new ArrayList<String>();
	HashMap<String, Champion> Champs = new HashMap<String, Champion>();
	ArrayList<String> ChampsRed = new ArrayList<String>();
	ArrayList<String> ChampsBlue = new ArrayList<String>();
	Tower t_red_1, t_red_2,t_red_3,t_red_4,t_red_5,t_red_6,t_red_7,t_red_8,t_red_9,t_red_10,t_red_11;
	Tower t_blue_1,t_blue_2,t_blue_3,t_blue_4,t_blue_5,t_blue_6,t_blue_7,t_blue_8,t_blue_9,t_blue_10,t_blue_11;
	
	

	public Arena(String name2) {
		world = name2;
		name = name2;
	}

	public String getName() {
		return name;
	}

	public World getWorld() {
		if (Bukkit.getWorld(world) != null) {
			return Bukkit.getWorld(world);
		}
		return null;
	}

	public String getWorldName() {
		return world;
	}

	public LoMLocation getLobbyRed() {
		if (LobbyRed != null) {
			return LobbyRed;
		}
		return null;
	}

	public LoMLocation getLobbyBlue() {
		if (LobbyBlue != null) {
			return LobbyBlue;
		}
		return null;
	}

	public boolean isActive() {
		return active;
	}

	public ArrayList<Player> getTeamRed() {
		ArrayList<Player> Player = new ArrayList<Player>();
		for (String string : TeamRed) {
			Player.add(Bukkit.getPlayer(string));
		}
		return Player;
	}

	public ArrayList<Player> getTeamBlue() {
		ArrayList<Player> Player = new ArrayList<Player>();
		for (String string : TeamBlue) {
			Player.add(Bukkit.getPlayer(string));
		}
		return Player;
	}

	public HashMap<String, Champion> getChamps() {
		return Champs;
	}

	public ArrayList<String> getChampNames() {
		ArrayList<String> names = new ArrayList<String>();
		for (Champion champ : Champs.values()) {
			names.add(champ.getName().toLowerCase());
		}
		return names;
	}

	public ArrayList<String> getChampsRed() {
		return ChampsRed;
	}

	public ArrayList<String> getChampNamesRed() {
		ArrayList<String> names = ChampsRed;
		return names;
	}

	public ArrayList<String> getChampsBlue() {
		return ChampsBlue;
	}

	public ArrayList<String> getChampNamesBlue() {
		ArrayList<String> names = ChampsBlue;
		return names;
	}

	public String getTeam(Player player) {
		if (TeamRed.contains(player.getName())) {
			return "red";
		}
		else if (TeamBlue.contains(player.getName())) {
			return "blue";
		}
		return "";
	}

	public ArrayList<Player> getPlayers() {
		ArrayList<Player> Player = new ArrayList<Player>();
		for (String string : Players) {
			Player.add(Bukkit.getPlayer(string));
			continue;
		}
		return Player;
	}

	public ArrayList<String> getPlayersS() {
		return Players;
	}

	public LoMLocation getSpawnRed() {
		if (SpawnRed != null) {
			return SpawnRed;
		}
		return null;
	}

	public LoMLocation getSpawnBlue() {
		if (SpawnBlue != null) {
			return SpawnBlue;
		}
		return null;
	}

	public void setWorld(World world2) {
		world = world2.getName();
	}

	public void setLobbyRed(Location loc2) {
		LoMLocation loc = new LoMLocation(loc2);
		LobbyRed = loc;
	}

	public void setLobbyBlue(Location loc2) {
		LoMLocation loc = new LoMLocation(loc2);
		LobbyBlue = loc;
	}

	public void setActive(boolean bol) {
		active = bol;
	}

	public void addPlayerRed(Player player) {
		Players.add(player.getName());
		TeamRed.add(player.getName());
	}

	private void removePlayerRed(Player player) {
		if (TeamRed.contains(player.getName())) {
			TeamRed.remove(player.getName());
		}
	}

	public void addPlayerBlue(Player player) {
		Players.add(player.getName());
		TeamBlue.add(player.getName());
	}

	private void removePlayerBlue(Player player) {
		if (TeamBlue.contains(player.getName())) {
			TeamBlue.remove(player.getName());
		}
	}

	public void removePlayer(Player player) {
		if (Players.contains(player.getName())) {
			Players.remove(player.getName());
			removePlayerRed(player);
			removePlayerBlue(player);
			removeChamp(player);
		}
	}

	public void addChamp(Player player, Champion champ) {
		if (getPlayersS().contains(player.getName())) {
			Champs.put(player.getName(), champ);
			if (getTeam(player) != null) {
				String team = getTeam(player);
				if (team.equalsIgnoreCase("red")) {
					ChampsRed.add(champ.getName());
				} else if (team.equalsIgnoreCase("blue")) {
					ChampsBlue.add(champ.getName());
				}
			}
		}
	}

	public void removeChamp(Player player) {
		if (Champs.containsKey(player.getName())) {
			if (getTeam(player).equalsIgnoreCase("red")) {
				ChampsRed.remove(Champs.get(player.getName()).getName());
			} else if (getTeam(player).equalsIgnoreCase("blue")) {
				ChampsBlue.remove(Champs.get(player.getName()).getName());
			}
			Champs.remove(player.getName());
		}
	}

	public void setSpawnRed(Location loc) {
		SpawnRed = new LoMLocation(loc);
	}

	public void setSpawnBlue(Location loc) {
		SpawnBlue = new LoMLocation(loc);
	}

	public void startGame() {
		active = true;
		for (Player player : getPlayers()) {
			if (getTeam(player).equalsIgnoreCase("red")) {
				player.teleport(SpawnRed.getLocation());
			} else if (getTeam(player).equalsIgnoreCase("blau")) {
				player.teleport(SpawnBlue.getLocation());
			}
			player.sendMessage(ChatColor.GREEN
					+ "The game has started. Go get them " + ChatColor.GOLD
					+ getChamps().get(player.getName()).getName());
			continue;
		}
	}
	public void clearTeams(){
		TeamRed.clear();
		TeamBlue.clear();
	}
	public Tower getT_red_nexus_top() {
		return t_red_1;
	}

	public void setT_red_nexus_top(Tower t_red_1) {
		this.t_red_1 = t_red_1;
	}

	public Tower getT_red_nexus_bot() {
		return t_red_2;
	}

	public void setT_red_nexus_bot(Tower t_red_2) {
		this.t_red_2 = t_red_2;
	}

	public Tower getT_red_inhib_top() {
		return t_red_3;
	}

	public void setT_red_inhib_top(Tower t_red_3) {
		this.t_red_3 = t_red_3;
	}

	public Tower getT_red_inhib_mid() {
		return t_red_4;
	}

	public void setT_red_inhib_mid(Tower t_red_4) {
		this.t_red_4 = t_red_4;
	}

	public Tower getT_red_inhib_bot() {
		return t_red_5;
	}

	public void setT_red_inhib_bot(Tower t_red_5) {
		this.t_red_5 = t_red_5;
	}

	public Tower getT_red_inner_top() {
		return t_red_6;
	}

	public void setT_red_inner_top(Tower t_red_6) {
		this.t_red_6 = t_red_6;
	}

	public Tower getT_red_inner_mid() {
		return t_red_7;
	}

	public void setT_red_inner_mid(Tower t_red_7) {
		this.t_red_7 = t_red_7;
	}

	public Tower getT_red_inner_bot() {
		return t_red_8;
	}

	public void setT_red_inner_bot(Tower t_red_8) {
		this.t_red_8 = t_red_8;
	}

	public Tower getT_red_outer_top() {
		return t_red_9;
	}

	public void setT_red_outer_top(Tower t_red_9) {
		this.t_red_9 = t_red_9;
	}

	public Tower getT_red_outer_mid() {
		return t_red_10;
	}

	public void setT_red_outer_mid(Tower t_red_10) {
		this.t_red_10 = t_red_10;
	}

	public Tower getT_red_outer_bot() {
		return t_red_11;
	}

	public void setT_red_outer_bot(Tower t_red_11) {
		this.t_red_11 = t_red_11;
	}

	public Tower getT_blue_nexus_top() {
		return t_blue_1;
	}

	public void setT_blue_nexus_top(Tower t_blue_1) {
		this.t_blue_1 = t_blue_1;
	}

	public Tower getT_blue_nexus_bot() {
		return t_blue_2;
	}

	public void setT_blue_nexus_bot(Tower t_blue_2) {
		this.t_blue_2 = t_blue_2;
	}

	public Tower getT_blue_inhib_top() {
		return t_blue_3;
	}

	public void setT_blue_inhib_top(Tower t_blue_3) {
		this.t_blue_3 = t_blue_3;
	}

	public Tower getT_blue_inhib_mid() {
		return t_blue_4;
	}

	public void setT_blue_inhib_mid(Tower t_blue_4) {
		this.t_blue_4 = t_blue_4;
	}

	public Tower getT_blue_inhib_bot() {
		return t_blue_5;
	}

	public void setT_blue_inhib_bot(Tower t_blue_5) {
		this.t_blue_5 = t_blue_5;
	}

	public Tower getT_blue_inner_top() {
		return t_blue_6;
	}

	public void setT_blue_inner_top(Tower t_blue_6) {
		this.t_blue_6 = t_blue_6;
	}

	public Tower getT_blue_inner_mid() {
		return t_blue_7;
	}

	public void setT_blue_inner_mid(Tower t_blue_7) {
		this.t_blue_7 = t_blue_7;
	}

	public Tower getT_blue_inner_bot() {
		return t_blue_8;
	}

	public void setT_blue_inner_bot(Tower t_blue_8) {
		this.t_blue_8 = t_blue_8;
	}

	public Tower getT_blue_outer_top() {
		return t_blue_9;
	}

	public void setT_blue_outer_top(Tower t_blue_9) {
		this.t_blue_9 = t_blue_9;
	}

	public Tower getT_blue_outer_mid() {
		return t_blue_10;
	}

	public void setT_blue_outer_mid(Tower t_blue_10) {
		this.t_blue_10 = t_blue_10;
	}

	public Tower getT_blue_outer_bot() {
		return t_blue_11;
	}

	public void setT_blue_outer_bot(Tower t_blue_11) {
		this.t_blue_11 = t_blue_11;
	}

}