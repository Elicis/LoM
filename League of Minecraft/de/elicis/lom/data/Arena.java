package de.elicis.lom.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import de.elicis.lom.Main;
import de.elicis.lom.champions.Alistar;
import de.elicis.lom.champions.Ashe;
import de.elicis.lom.champions.Champion;
import de.elicis.lom.events.GameStartEvent;
import de.elicis.lom.tower.Tower;
import de.elicis.lom.tower.TowerType;

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
	Boolean countdownStarted = false;
	public ArrayList<String> Players = new ArrayList<String>();
	private ArrayList<String> TeamRed = new ArrayList<String>();
	private ArrayList<String> TeamBlue = new ArrayList<String>();
	HashMap<String, Champion> Champs = new HashMap<String, Champion>();
	ArrayList<String> ChampsRed = new ArrayList<String>();
	ArrayList<String> ChampsBlue = new ArrayList<String>();
	int minChamps;
	Nexus nexusred, nexusblue;
	Tower t_red_1, t_red_2,t_red_3,t_red_4,t_red_5,t_red_6,t_red_7,t_red_8,t_red_9,t_red_10,t_red_11;
	Tower t_blue_1,t_blue_2,t_blue_3,t_blue_4,t_blue_5,t_blue_6,t_blue_7,t_blue_8,t_blue_9,t_blue_10,t_blue_11;
	
	public int gameCountdownTimer;
	
	

	public Arena(String name2) {
		world = name2;
		name = name2;
		minChamps = 1; // As a test
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
		for(String str: TeamRed){
			if(str.equalsIgnoreCase(player.getName())){
				return "red";
			}
		}
		for(String str: TeamBlue){
			if(str.equalsIgnoreCase(player.getName())){
				return "blue";
			}
		}
		return null;
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
		if(isActive()){
			if(isRedTeamEmpty()){
				endGame("blue");
			}
			if(isBlueTeamEmpty()){
				endGame("red");
			}
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
		
		System.out.println("Champs Size: " + Champs.size());
		
		if(Champs.size() >= minChamps){
			startCountdown();
			countdownStarted = true;
			System.out.println("Test");
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
		if(Champs.size() < minChamps){
			if(countdownStarted){
				stopCountdown();
			}
		}
	}
	
	public boolean isRedTeamEmpty(){
		if(TeamRed.size() <= 0){
			return true;
		}
		return false;
	}
	
	public boolean isBlueTeamEmpty(){
		if(TeamBlue.size() <= 0){
			return true;
		}
		return false;
	}

	public void setSpawnRed(Location loc) {
		SpawnRed = new LoMLocation(loc);
	}

	public void setSpawnBlue(Location loc) {
		SpawnBlue = new LoMLocation(loc);
	}

	public void startCountdown(){
		
		gameCountdownTimer = Main.getPlugin().getServer().getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable(){
			
			int countdown = 61;
			
			public void run(){
				countdown --;
				if(countdown == 60){
					for(Player player: getPlayers()){
						player.sendMessage(ChatColor.GREEN + "Game starts in " + ChatColor.GOLD + "1 minute");
					}
				}
				else if(countdown == 30){
					for(Player player: getPlayers()){
						player.sendMessage(ChatColor.GREEN + "Game starts in " + ChatColor.GOLD + "30 seconds");
					}
				}
				
				else if(countdown == 10){
					for(Player player: getPlayers()){
						player.sendMessage(ChatColor.GREEN + "Game starts in " + ChatColor.GOLD + "10 seconds");
					}
				}
				
				else if(countdown == 9){
					for(Player player: getPlayers()){
						player.sendMessage(ChatColor.GREEN + "Game starts in " + ChatColor.GOLD + "9 seconds");
					}
				}
				
				else if(countdown == 8){
					for(Player player: getPlayers()){
						player.sendMessage(ChatColor.GREEN + "Game starts in " + ChatColor.GOLD + "8 seconds");
					}
				}
				
				else if(countdown == 7){
					for(Player player: getPlayers()){
						player.sendMessage(ChatColor.GREEN + "Game starts in " + ChatColor.GOLD + "7 seconds");
					}
				}
				
				else if(countdown == 6){
					for(Player player: getPlayers()){
						player.sendMessage(ChatColor.GREEN + "Game starts in " + ChatColor.GOLD + "6 seconds");
					}
				}
				
				else if(countdown == 5){
					for(Player player: getPlayers()){
						player.sendMessage(ChatColor.GREEN + "Game starts in " + ChatColor.GOLD + "5 seconds");
					}
				}
				
				else if(countdown == 4){
					for(Player player: getPlayers()){
						player.sendMessage(ChatColor.GREEN + "Game starts in " + ChatColor.GOLD + "4 seconds");
					}
				}
				
				else if(countdown == 3){
					for(Player player: getPlayers()){
						player.sendMessage(ChatColor.GREEN + "Game starts in " + ChatColor.GOLD + "3 seconds");
					}
				}
				
				else if(countdown == 2){
					for(Player player: getPlayers()){
						player.sendMessage(ChatColor.GREEN + "Game starts in " + ChatColor.GOLD + "2 seconds");
					}
				}
				
				else if(countdown == 1){
					for(Player player: getPlayers()){
						player.sendMessage(ChatColor.GREEN + "Game starts in " + ChatColor.GOLD + "1 second");
					}
				}
				
				else if(countdown == 0){
					for(String player: Players){
						if(!getChamps().containsKey(player)){
							Random rand = new Random();
							// TODO: Edit when more champions are added
							int randChamp = rand.nextInt(2);
							if(randChamp <= 1){
								addChamp(Main.getPlugin().getServer().getPlayer(player), new Ashe(Main.getPlugin().getServer().getPlayer(player)));
							}
							if(randChamp == 2){
								addChamp(Main.getPlugin().getServer().getPlayer(player), new Alistar(Main.getPlugin().getServer().getPlayer(player)));
							}
						}
					}
					stopCountdown();
					startGame();
				}
			}
		}, 0, 20L);
	}
	
	public void stopCountdown(){
		Main.getPlugin().getServer().getScheduler().cancelTask(gameCountdownTimer);
		countdownStarted = false;
	}
	
	public void startGame() {
		GameStartEvent event = new GameStartEvent(this);
		if(!event.isCancelled()){
			active = true;
			for (Player player : getPlayers()) {
				if (getTeam(player).equalsIgnoreCase("red")) {
					player.teleport(SpawnRed.getLocation());
				} else if (getTeam(player).equalsIgnoreCase("blue")) {
					player.teleport(SpawnBlue.getLocation());
				}
				player.sendMessage(ChatColor.GREEN
						+ "The game has started. Go get them " + ChatColor.GOLD
						+ getChamps().get(player.getName()).getName());
				continue;
			}
			
			if(isRedTeamEmpty()){
				endGame("blue");
			}
			
			if(isBlueTeamEmpty()){
				endGame("red");
			}
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
	public ArrayList<Tower> getTowers(){
		ArrayList<Tower> list = new ArrayList<Tower>();
		try{
			list.add(t_blue_1);
			list.add(t_blue_2);
			list.add(t_blue_3);
			list.add(t_blue_4);
			list.add(t_blue_5);
			list.add(t_blue_6);
			list.add(t_blue_7);
			list.add(t_blue_8);
			list.add(t_blue_9);
			list.add(t_blue_10);
			list.add(t_blue_11);
			list.add(t_red_1);
			list.add(t_red_2);
			list.add(t_red_3);
			list.add(t_red_4);
			list.add(t_red_5);
			list.add(t_red_6);
			list.add(t_red_7);
			list.add(t_red_8);
			list.add(t_red_9);
			list.add(t_red_10);
			list.add(t_red_11);
		} catch(Exception e){
		}
		return list;
	}
	public Tower getTowerByType(TowerType type){
		for(Tower t : getTowers()){
			if(t.getType() == type){
				return t;
			}
		}
		return null;
	}

	public Nexus getNexusRed() {
		return nexusred;
	}
	
	public Nexus getNexusBlue() {
		return nexusblue;
	}
	public Tower getNextInLineBotBlue(){
		if(!this.getT_blue_outer_bot().isDestroyed()){
			return this.getT_blue_outer_bot();
		}else if(!this.getT_blue_inner_bot().isDestroyed()){
			return this.getT_blue_inner_bot();
		}else if(!this.getT_blue_inhib_bot().isDestroyed()){
			return this.getT_blue_inhib_bot();
		}else if(!this.getT_blue_nexus_bot().isDestroyed()){
			return this.getT_blue_nexus_bot();
		}else if(!this.getT_blue_nexus_top().isDestroyed()){
			return this.getT_blue_nexus_top();
		}else{
			return null;
		}
	}
	public Tower getNextInLineMidBlue(){
		if(!this.getT_blue_outer_mid().isDestroyed()){
			return this.getT_blue_outer_mid();
		}else if(!this.getT_blue_inner_mid().isDestroyed()){
			return this.getT_blue_inner_mid();
		}else if(!this.getT_blue_inhib_mid().isDestroyed()){
			return this.getT_blue_inhib_mid();
		}else if(!this.getT_blue_nexus_bot().isDestroyed()){
			return this.getT_blue_nexus_bot();
		}else if(!this.getT_blue_nexus_top().isDestroyed()){
			return this.getT_blue_nexus_top();
		}else{
			return null;
		}
	}
	public Tower getNextInLineTopBlue(){
		if(!this.getT_blue_outer_top().isDestroyed()){
			return this.getT_blue_outer_top();
		}else if(!this.getT_blue_inner_top().isDestroyed()){
			return this.getT_blue_inner_top();
		}else if(!this.getT_blue_inhib_top().isDestroyed()){
			return this.getT_blue_inhib_top();
		}else if(!this.getT_blue_nexus_bot().isDestroyed()){
			return this.getT_blue_nexus_bot();
		}else if(!this.getT_blue_nexus_top().isDestroyed()){
			return this.getT_blue_nexus_top();
		}else{
			return null;
		}
	}
	public Tower getNextInLineBotRed(){
		if(!this.getT_red_outer_bot().isDestroyed()){
			return this.getT_red_outer_bot();
		}else if(!this.getT_red_inner_bot().isDestroyed()){
			return this.getT_red_inner_bot();
		}else if(!this.getT_red_inhib_bot().isDestroyed()){
			return this.getT_red_inhib_bot();
		}else if(!this.getT_red_nexus_bot().isDestroyed()){
			return this.getT_red_nexus_bot();
		}else if(!this.getT_red_nexus_top().isDestroyed()){
			return this.getT_red_nexus_top();
		}else{
			return null;
		}
	}
	public Tower getNextInLineMidRed(){
		if(!this.getT_red_outer_mid().isDestroyed()){
			return this.getT_red_outer_mid();
		}else if(!this.getT_red_inner_mid().isDestroyed()){
			return this.getT_red_inner_mid();
		}else if(!this.getT_red_inhib_mid().isDestroyed()){
			return this.getT_red_inhib_mid();
		}else if(!this.getT_red_nexus_bot().isDestroyed()){
			return this.getT_red_nexus_bot();
		}else if(!this.getT_red_nexus_top().isDestroyed()){
			return this.getT_red_nexus_top();
		}else{
			return null;
		}
	}
	public Tower getNextInLineTopRed(){
		if(!this.getT_red_outer_top().isDestroyed()){
			return this.getT_red_outer_top();
		}else if(!this.getT_red_inner_top().isDestroyed()){
			return this.getT_red_inner_top();
		}else if(!this.getT_red_inhib_top().isDestroyed()){
			return this.getT_red_inhib_top();
		}else if(!this.getT_red_nexus_bot().isDestroyed()){
			return this.getT_red_nexus_bot();
		}else if(!this.getT_red_nexus_top().isDestroyed()){
			return this.getT_red_nexus_top();
		}else{
			return null;
		}
	}
	public void setNexusRed(Nexus nexus) {
		this.nexusred = nexus;
	}
	public void setNexusBlue(Nexus nexus){
		this.nexusblue = nexus;
	}
	public void endGame(String winner){
		for(Player player: getPlayers()){
			player.setMaxHealth(20);
			player.setHealth(player.getMaxHealth());
			player.setFoodLevel(20);
			player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
			InvSave.reloadInventory(player);
			player.setGameMode(GameMode.SURVIVAL);
		}
		if(winner.equalsIgnoreCase("red")){
			for(Player player : getTeamRed()){
				player.sendMessage(ChatColor.GOLD + "Victory!");
			}
			for(Player player : getTeamBlue()){
				player.sendMessage(ChatColor.GOLD + "Defeat!");
			}
		}else{
			for(Player player : getTeamBlue()){
				player.sendMessage(ChatColor.GOLD + "Victory!");
			}
			for(Player player : getTeamRed()){
				player.sendMessage(ChatColor.GOLD + "Defeat!");
			}
		}
		Players.clear();
		TeamRed.clear();
		TeamBlue.clear();
		Champs.clear();
		ChampsRed.clear();
		ChampsBlue.clear();
		if(getTowers().size() > 0){
			for(Tower t : getTowers()){
				if(t != null){
					t.setHealth(t.getMaxHealth());
				}
			}
		}
		if(nexusred != null){
			nexusred.setHealth(4000);
		}else{
			for(Player player: Bukkit.getOnlinePlayers()){
				if(player.hasPermission("lom.info")){
					player.sendMessage(ChatColor.GOLD+"[LoM]"+ ChatColor.WHITE + "The red Nexus in the Arena:" +
					ChatColor.BLUE + this.getName()+ ChatColor.WHITE + "isn't there anymore!");
					
				}
			}
		}
		if(nexusblue != null){
			nexusblue.setHealth(4000);
		}else{
			for(Player player: Bukkit.getOnlinePlayers()){
				if(player.hasPermission("lom.info")){
					player.sendMessage(ChatColor.GOLD+"[LoM]"+ ChatColor.WHITE + "The blue Nexus in the Arena:" +
					ChatColor.BLUE + this.getName()+ ChatColor.WHITE + "isn't there anymore!");
					
				}
			}
		}
		active = false;
		
	}
	
	public boolean isEveryTowerSetUp(){
		boolean isSetUp = true;
		for(Tower t: getTowers()){
			if(t == null){
				isSetUp = false;
			}
		}
		return isSetUp;
	}

}
