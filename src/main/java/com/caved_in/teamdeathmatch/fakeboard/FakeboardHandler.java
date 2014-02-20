package com.caved_in.teamdeathmatch.fakeboard;

import com.caved_in.commons.player.PlayerHandler;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

public class FakeboardHandler {
	private static HashMap<String, Team> activeTeams = new HashMap<String, Team>();
	private static HashMap<String, GamePlayer> activePlayers = new HashMap<String, GamePlayer>();

	/**
	 * Register a new team with the specified name
	 *
	 * @param teamName
	 */
	public static void registerTeam(String teamName, boolean friendlyFire) {
		Team newTeam = new Team(teamName);
		newTeam.setFriendlyFire(friendlyFire);
		activeTeams.put(teamName, newTeam);
	}

	public static void loadPlayer(String playerName) {
		activePlayers.put(playerName, new GamePlayer(playerName));
	}


	public static GamePlayer getPlayer(String playerName) {
		return activePlayers.get(playerName);
	}

	public static GamePlayer getPlayer(Player player) {
		return getPlayer(player.getName());
	}

	//Todo: Optimize this method, and clean this up
	public static void cleanTeams() {
		for (Entry<String, Team> teamEntry : activeTeams.entrySet()) {
			for (Player player : getPlayers(teamEntry.getKey())) {
				getPlayer(player).setTeam(null);
				resetScores(player);
				removeFromTeam(teamEntry.getKey(), player);
				PlayerHandler.clearInventory(player);
				PlayerHandler.removePotionEffects(player);
			}
		}
		activeTeams.clear();
	}

	public static void resetScores(Player player) {
		GamePlayer GamePlayer = getPlayer(player);
		GamePlayer.setPlayerScore(0);
		GamePlayer.resetDeaths();
		GamePlayer.resetKillstreak();
	}

	public static Team getTeamByPlayer(String playerName) {
		GamePlayer player = getPlayer(playerName);
		if (player != null) {
			return getTeam(player.getTeam());
		}
		return null;
	}

	public static Team getTeamByPlayer(Player player) {
		return getTeamByPlayer(player.getName());
	}

	/**
	 * Checks if whether a team exists with the given name or not
	 *
	 * @param teamName Name of team to check
	 * @return true if a team with that name exists, false otherwise
	 */
	public static boolean doesTeamExist(String teamName) {
		return activeTeams.containsKey(teamName);
	}

	/**
	 * Removes a player from a team
	 *
	 * @param team   Team to remove player from
	 * @param player Player to remove
	 * @return True if they were removed, false otherwise
	 */
	public static boolean removeFromTeam(String team, Player player) {
		return activeTeams.get(team).removePlayer(player);
	}

	public static boolean isOnTeam(String team, Player player) {
		return activeTeams.get(team).hasPlayer(player);
	}

	/**
	 * Adds a player to a team
	 *
	 * @param team
	 * @param player
	 * @return true if they were added, false otherwise
	 */
	public static boolean addToTeam(String team, Player player) {
		if (!activeTeams.get(team).hasPlayer(player)) {
			activeTeams.get(team).addPlayer(player);
			return true;
		}
		return false;
	}

	public static boolean addToTeam(String team, GamePlayer player) {
		if (!activeTeams.get(team).hasPlayer(player)) {
			activeTeams.get(team).addPlayer(player);
			return true;
		}
		return false;
	}

	public static void setFriendlyFire(String teamName, boolean friendlyFire) {
		activeTeams.get(teamName).setFriendlyFire(friendlyFire);
	}

	public static Set<Player> getPlayers(String teamName) {
		Set<Player> players = new HashSet<Player>();
		for (GamePlayer teamPlayer : activeTeams.get(teamName).getTeamMembers()) {
			Player player = teamPlayer.getPlayer();
			if (player != null) {
				players.add(player);
			}
		}
		return players;
	}

	public static Team getTeam(String teamName) {
		return activeTeams.get(teamName);
	}

	public static void removePlayer(Player player) {
		activePlayers.remove(player.getName());
	}

}
