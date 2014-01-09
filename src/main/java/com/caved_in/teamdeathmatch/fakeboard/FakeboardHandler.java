package com.caved_in.teamdeathmatch.fakeboard;

import com.caved_in.commons.player.PlayerHandler;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class FakeboardHandler {
	//private TDMGame Plugin;
	private static HashMap<String, Team> activeTeams = new HashMap<String, Team>();
	private static HashMap<String, fPlayer> activePlayers = new HashMap<String, fPlayer>();

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
		activePlayers.put(playerName, new fPlayer(playerName));
	}


	public static fPlayer getPlayer(String playerName) {
		return activePlayers.get(playerName);
	}

	public static fPlayer getPlayer(Player player) {
		return getPlayer(player.getName());
	}

	public static void cleanTeams() {
		for (Entry<String, Team> Item : activeTeams.entrySet()) {
			for (Player Player : getPlayers(Item.getKey())) {
				getPlayer(Player).setTeam(null);
				resetScores(Player);
				removeFromTeam(Item.getKey(), Player);
				PlayerHandler.clearInventory(Player);
				for (PotionEffect E : Player.getActivePotionEffects()) {
					Player.removePotionEffect(E.getType());
				}
			}
		}
		activeTeams.clear();
	}

	public static void resetScores(Player player) {
		fPlayer fPlayer = getPlayer(player);
		fPlayer.setPlayerScore(0);
		fPlayer.resetDeaths();
		fPlayer.resetKillstreak();
	}

	public static Team getTeamByPlayer(String playerName) {
		fPlayer player = getPlayer(playerName);
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

	public static boolean addToTeam(String team, fPlayer player) {
		if (!activeTeams.get(team).hasPlayer(player)) {
			activeTeams.get(team).addPlayer(player);
			return true;
		}
		return false;
	}

	public static void setFriendlyFire(String teamName, boolean friendlyFire) {
		activeTeams.get(teamName).setFriendlyFire(friendlyFire);
	}

	public static List<Player> getPlayers(String teamName) {
		List<Player> Players = new ArrayList<Player>();
		for (fPlayer Player : activeTeams.get(teamName).getTeamMembers()) {
			if (Player.getPlayer() != null) {
				Players.add(Player.getPlayer());
			}
		}
		return Players;
	}

	public static Team getTeam(String TeamName) {
		return activeTeams.get(TeamName);
	}

	public static void removePlayer(Player player) {
		activePlayers.remove(player.getName());
	}

}
