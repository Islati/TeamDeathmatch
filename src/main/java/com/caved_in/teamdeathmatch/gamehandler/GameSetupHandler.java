package com.caved_in.teamdeathmatch.gamehandler;

//Commons imports

import com.caved_in.commons.Commons;
import com.caved_in.commons.items.ItemHandler;
import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.TeamType;
import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.GamePlayer;
import com.caved_in.teamdeathmatch.fakeboard.Team;
import com.caved_in.teamdeathmatch.menus.loadoutselector.LoadoutActionMenu;
import com.caved_in.teamdeathmatch.menus.loadoutselector.LoadoutCreationMenu;
import com.caved_in.teamdeathmatch.menus.loadoutselector.LoadoutSelectionMenu;
import com.caved_in.teamdeathmatch.runnables.PlayerOpenKits;
import com.caved_in.teamdeathmatch.runnables.TeleportCT;
import com.caved_in.teamdeathmatch.runnables.TeleportTerrorist;
import com.caved_in.worldmanager.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.kitteh.tag.TagAPI;

import java.util.Random;

//Team Deathmatch imports
//World Manager Imports
//Bukkit imports

public class GameSetupHandler {
	//Initials for both of the teams
	private static String teamCounterTerrorist = TeamType.COUNTER_TERRORIST.toString();
	private static String teamTerrorist = TeamType.TERRORIST.toString();
	//If a game is currently in progress
	private static boolean gameInProgress = false;
	//Whether or not to reset the last map
	private static boolean resetLastMap = false;
	//What map needs to be reset, if any
	private static String mapToReset = "";

	private static boolean isForceMap = false;

	//Threads to handle game setup
	private static Runnable teleportTerrorists = new TeleportTerrorist();
	private static Runnable teleportCounterTerrorists = new TeleportCT();
	private static Runnable playerOpenKits = new PlayerOpenKits();

	//Blue and Red team armors
	private static ItemStack[] blueTeamArmor, redTeamArmor;

	static {
		blueTeamArmor = new ItemStack[]{
				ItemHandler.makeLeatherItemStack(Material.LEATHER_HELMET, Color.BLUE),
				ItemHandler.makeLeatherItemStack(Material.LEATHER_CHESTPLATE, Color.BLUE),
				ItemHandler.makeLeatherItemStack(Material.LEATHER_LEGGINGS, Color.BLUE),
				ItemHandler.makeLeatherItemStack(Material.LEATHER_BOOTS, Color.BLUE),
		};
		redTeamArmor = new ItemStack[]{
				ItemHandler.makeLeatherItemStack(Material.LEATHER_HELMET, Color.RED),
				ItemHandler.makeLeatherItemStack(Material.LEATHER_CHESTPLATE, Color.RED),
				ItemHandler.makeLeatherItemStack(Material.LEATHER_LEGGINGS, Color.RED),
				ItemHandler.makeLeatherItemStack(Material.LEATHER_BOOTS, Color.RED),
		};

	}

	public static ItemStack[] getBlueTeamArmor() {
		return blueTeamArmor;
	}

	public static ItemStack[] getRedTeamArmor() {
		return redTeamArmor;
	}

	public static void doSetup() {
		//Teleport players to the spawn
//		teleportPlayersToSpawn();
		//Set a game in progress
		setGameInProgress(true);
		//Make all the teams
		makeGameTeams();
		//Teleport terrorists and Counter Terrorists
		TDMGame.runnableManager.runTaskNow(teleportTerrorists);
		TDMGame.runnableManager.runTaskNow(teleportCounterTerrorists);
		//Make all the players open their "kits"
		TDMGame.runnableManager.runTaskLater(playerOpenKits, 10L);
		//If we want to reset the last map
		if (isResetLastMap()) {
			//Get the maps name
			String mapToReset = getMapToReset();
			//Call our worldhandler to reset it
			WorldManager.rollbackMap(mapToReset);
			Commons.messageConsole("Rolled back the map [" + mapToReset + "]");
			setResetLastMap(false);
			setMapToReset("");
		}
	}

	public static void givePlayerLoadoutGem(Player player) {
		if (!ItemHandler.playerHasItem(player, Material.EMERALD, "Select & Edit Loadouts")) {
			player.getInventory().setItem(8, ItemHandler.makeItemStack(Material.EMERALD, ChatColor.GREEN + "Select & Edit Loadouts"));
		}
	}

	private static void makeGameTeams() {
		FakeboardHandler.registerTeam(teamTerrorist, false);
		FakeboardHandler.registerTeam(teamCounterTerrorist, false);
		for (Player P : Bukkit.getOnlinePlayers()) {
			assignPlayerTeam(P);
		}
	}

	//TODO Optimize the fuck out of this
	public static void assignPlayerTeam(final Player player) {
		//Our two teams, the terrorists and counter terrorists
		Team terroristTeam = FakeboardHandler.getTeam(teamTerrorist);
		Team counterTerroristTeam = FakeboardHandler.getTeam(teamCounterTerrorist);
		//The size of each team
		int terroristCount = terroristTeam.getTeamSize();
		int counterTerroristCount = counterTerroristTeam.getTeamSize();
		//Get our GamePlayer
		GamePlayer GamePlayer = FakeboardHandler.getPlayer(player);
		if (terroristCount != counterTerroristCount) {
			if (terroristCount > counterTerroristCount) {
				//Add the player to CT
				FakeboardHandler.addToTeam(teamCounterTerrorist, GamePlayer);
				//Set their team to CT
				GamePlayer.setTeam(teamCounterTerrorist);
				player.getInventory().setArmorContents(redTeamArmor);
			} else {
				FakeboardHandler.addToTeam(teamTerrorist, player);
				GamePlayer.setTeam(teamTerrorist);
				player.getInventory().setArmorContents(blueTeamArmor);
			}
		} else {
			switch (new Random().nextInt(2)) {
				case 0:
					FakeboardHandler.addToTeam(teamCounterTerrorist, player);
					GamePlayer.setTeam(teamCounterTerrorist);
					player.getInventory().setArmorContents(redTeamArmor);
					break;
				case 1:
					FakeboardHandler.addToTeam(teamTerrorist, player);
					GamePlayer.setTeam(teamTerrorist);
					player.getInventory().setArmorContents(blueTeamArmor);
					break;
				default:
					break;
			}
		}
		TDMGame.runnableManager.runTaskOneTickLater(new Runnable() {
			@Override
			public void run() {
				TagAPI.refreshPlayer(player);
			}
		});
	}

	public static boolean isGameInProgress() {
		return gameInProgress;
	}

	public static void setGameInProgress(boolean gameInProgress) {
		GameSetupHandler.gameInProgress = gameInProgress;
	}

	public static boolean isResetLastMap() {
		return resetLastMap;
	}

	public static void setResetLastMap(boolean resetLastMap) {
		GameSetupHandler.resetLastMap = resetLastMap;
	}

	public static String getMapToReset() {
		return mapToReset;
	}

	public static void setMapToReset(String mapToReset) {
		GameSetupHandler.mapToReset = mapToReset;
	}

	public static boolean isForceMap() {
		return isForceMap;
	}

	public static void setForceMap(boolean isForceMap) {
		GameSetupHandler.isForceMap = isForceMap;
	}

	public static boolean canSetup() {
		return Bukkit.getOnlinePlayers().length >= 2;
	}

	public static void awardEndgamePoints(String winningTeam, double winningCash, double losingCash) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			GamePlayer GamePlayer = FakeboardHandler.getPlayer(player);
			if (GamePlayer.getTeam() != null) {
				TDMGame.givePlayerTunnelsXP(player, GamePlayer.getTeam().equalsIgnoreCase(winningTeam) ? winningCash : losingCash);
			}
		}
	}

	public static void openCreationMenu(Player player, boolean isAfk) {
		GamePlayer GamePlayer = FakeboardHandler.getPlayer(player);
		GamePlayer.setAfk(isAfk);
		new LoadoutCreationMenu(player);
	}

	public static void openLoadoutSelectionMenu(Player player, boolean isAfk) {
		GamePlayer GamePlayer = FakeboardHandler.getPlayer(player);
		GamePlayer.setAfk(isAfk, false);
		new LoadoutSelectionMenu(player);
	}

	public static void openLoadoutOptionMenu(Player player, boolean isAfk) {
		GamePlayer GamePlayer = FakeboardHandler.getPlayer(player);
		GamePlayer.setAfk(isAfk, false);
		new LoadoutActionMenu(player);

	}

	public static void openLoadoutOptionMenu(Player player) {
		openLoadoutOptionMenu(player, true);
	}
}
