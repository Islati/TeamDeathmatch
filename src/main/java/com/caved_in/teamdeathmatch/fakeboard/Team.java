package com.caved_in.teamdeathmatch.fakeboard;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Team {
	private String teamName;

	private Map<String, GamePlayer> teamMembers = new HashMap<>();
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

	public boolean hasPlayer(GamePlayer player) {
		return hasPlayer(player.getName());
	}

	public boolean removePlayer(String playerName) {
		return teamMembers.remove(playerName) != null;
	}

	public boolean removePlayer(Player player) {
		return removePlayer(player.getName());
	}

	public void addPlayer(String playerName) {
		teamMembers.put(playerName, FakeboardHandler.getPlayer(playerName));
	}

	public void addPlayer(Player player) {
		addPlayer(player.getName());
	}

	public void addPlayer(GamePlayer player) {
		player.setTeam(teamName);
		teamMembers.put(player.getName(), player);
	}

	public GamePlayer getPlayer(String playerName) {
		return teamMembers.get(playerName);
	}

	public GamePlayer getPlayer(Player player) {
		return getPlayer(player.getName());
	}

	public Collection<GamePlayer> getTeamMembers() {
		return teamMembers.values();
	}

	public int getTeamSize() {
		return teamMembers.size();
	}

	public void setScore(String playerName, int score) {
		getPlayer(playerName).setPlayerScore(score);
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
