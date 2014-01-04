package com.caved_in.teamdeathmatch.scoreboard;

import com.caved_in.teamdeathmatch.TeamType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.*;

public class ScoreboardHandler {
	private ScoreboardManager scoreboardManager;
	private Scoreboard scoreboard;
	private Objective objective;

	public ScoreboardHandler() {
		scoreboardManager = Bukkit.getScoreboardManager();
		scoreboard = scoreboardManager.getNewScoreboard();
		objective = scoreboard.registerNewObjective("teamkilz", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName("Team Scores");
	}

	public void clearScores() {
		scoreboard.resetScores(Bukkit.getOfflinePlayer(ChatColor.GOLD + "CTerrorist:"));
		scoreboard.resetScores(Bukkit.getOfflinePlayer(ChatColor.GOLD + "Terrorist:"));
	}

	public Scoreboard getScoreboard() {
		return this.scoreboard;
	}

	public void setScore(TeamType team, int teamScore) {
		switch (team) {
			case COUNTER_TERRORIST:
				Score counterTerroristScore = this.objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "CTerrorist:"));
				counterTerroristScore.setScore(teamScore);
				break;
			case TERRORIST:
				Score terroristScore = this.objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "Terrorist:"));
				terroristScore.setScore(teamScore);
				break;
			default:
				break;
		}
	}
}
