package de.elicis.lom.data;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class LoM_Score implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1524030185494068646L;
	Scoreboard board;
	Objective objkills;
	Objective objlevel;
	Objective objhealth;
	Team teamblue;
	Team teamred;

	public void initScore() {
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		objkills = board.registerNewObjective("kills", "playerKillCount");
		objlevel = board.registerNewObjective("Player", "dummy");
		objhealth = board.registerNewObjective("showhealth", "health");
		objkills.setDisplaySlot(DisplaySlot.SIDEBAR);
		objlevel.setDisplaySlot(DisplaySlot.SIDEBAR);
		objhealth.setDisplaySlot(DisplaySlot.BELOW_NAME);
		objkills.setDisplayName("Kills");
		objlevel.setDisplayName("Level");
		objhealth.setDisplayName("HP");
		teamblue = board.registerNewTeam("Blue");
		teamred = board.registerNewTeam("Red");
		teamblue.setAllowFriendlyFire(false);
		teamred.setAllowFriendlyFire(false);
	}

	public void updateScoreboard() {

	}

	public void addPlayer(Player player, String team) {
		if (team.equalsIgnoreCase("red")) {
			teamblue.addPlayer(player);
		} else if (team.equalsIgnoreCase("blue")) {
			teamred.addPlayer(player);
		}
		player.setScoreboard(board);
	}

	public void removePlayer(Player player) {
		try {
			teamblue.removePlayer(player);
			teamred.removePlayer(player);
		} catch (Exception e) {

		}
		player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}

	public void resetScore() {
		for (OfflinePlayer player : teamred.getPlayers()) {
			teamred.removePlayer(player);
		}
		for (OfflinePlayer player : teamblue.getPlayers()) {
			teamblue.removePlayer(player);
		}

	}

}
