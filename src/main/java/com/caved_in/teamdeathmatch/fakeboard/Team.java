package com.caved_in.teamdeathmatch.fakeboard;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Team {
	private String teamName = "";

	private Map<String, fPlayer> teamMembers = new HashMap<String, fPlayer>();
	private Boolean allowFriendlyFire = false;
	private int teamScore = 0;

	public Team(String teamName) {
		this.teamName = teamName;
	}

	public String getName() {
		return teamName;
	}

	public void setName(String teamName) {
		this.teamName = teamName;
	}

	@Override
	public String toString() {
		return teamName;
	}

	public boolean hasPlayer(String playerName) {
		return teamMembers.containsKey(playerName);
	}

	public boolean hasPlayer(Player player) {
		return hasPlayer(player.getName());
	}

	public boolean hasPlayer(fPlayer player) {
		return hasPlayer(player.getPlayerName());
	}

	public void removePlayer(String playerName) {
		if (hasPlayer(playerName)) {
			teamMembers.remove(playerName);
		}
	}

	public void removePlayer(Player player) {
		removePlayer(player.getName());
	}

	public void removePlayer(fPlayer player) {
		removePlayer(player.getPlayerName());
	}

	public void addPlayer(String playerName) {
		teamMembers.put(playerName, FakeboardHandler.getPlayer(playerName));
	}

	public void addPlayer(Player player) {
		addPlayer(player.getName());
	}

	public void addPlayer(fPlayer player) {
		player.setTeam(teamName);
		teamMembers.put(player.getPlayerName(), player);
	}

	public fPlayer getPlayer(String playerName) {
		return teamMembers.get(playerName);
	}

	public fPlayer getPlayer(Player player) {
		return getPlayer(player.getName());
	}

	public List<fPlayer> getTeamMembers() {
		return new ArrayList<fPlayer>(teamMembers.values());
	}

	public void setScore(Player player, int score) {
		setScore(player.getName(), score);
	}

	public void setScore(String playerName, int score) {
		getPlayer(playerName).setPlayerScore(score);
	}

	public void addScore(Player player, int incrementAmount) {
		addScore(player.getName(), incrementAmount);
	}

	public void addScore(String playerName, int incrementAmount) {
		getPlayer(playerName).addScore(incrementAmount);
	}

	public void addTeamScore(int amount) {
		this.teamScore += amount;
	}

	public int getTeamScore() {
		return teamScore;
	}

	public boolean allowFriendlyFire() {
		return allowFriendlyFire;
	}

	public void setFriendlyFire(boolean friendlyFire) {
		this.allowFriendlyFire = friendlyFire;
	}
}
