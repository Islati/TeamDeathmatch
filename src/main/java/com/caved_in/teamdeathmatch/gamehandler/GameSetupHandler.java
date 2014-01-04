package com.caved_in.teamdeathmatch.gamehandler;

//Commons imports
import com.caved_in.commons.Commons;
import com.caved_in.commons.items.ItemHandler;
//Team Deathmatch imports
import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.TeamType;
import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.Team;
import com.caved_in.teamdeathmatch.fakeboard.fPlayer;
import com.caved_in.teamdeathmatch.runnables.PlayerOpenKits;
import com.caved_in.teamdeathmatch.runnables.TeleportCT;
import com.caved_in.teamdeathmatch.runnables.TeleportTerrorist;
//World Manager Imports
import com.caved_in.worldmanager.WorldManager;
//Bukkit imports
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.kitteh.tag.TagAPI;

import java.util.Random;

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

	//Variables related to running the threads above
	private static long playerOpenKitDelay = 10L;

	//Blue and Red team armors
	private static ItemStack[] blueTeamArmor, redTeamArmor;

	static {
		blueTeamArmor = new ItemStack[] {
			ItemHandler.makeLeatherItemStack(Material.LEATHER_HELMET, Color.BLUE),
			ItemHandler.makeLeatherItemStack(Material.LEATHER_CHESTPLATE, Color.BLUE),
			ItemHandler.makeLeatherItemStack(Material.LEATHER_LEGGINGS, Color.BLUE),
			ItemHandler.makeLeatherItemStack(Material.LEATHER_BOOTS, Color.BLUE),
		};
		redTeamArmor = new ItemStack[] {
			ItemHandler.makeLeatherItemStack(Material.LEATHER_HELMET, Color.RED),
			ItemHandler.makeLeatherItemStack(Material.LEATHER_CHESTPLATE, Color.RED),
			ItemHandler.makeLeatherItemStack(Material.LEATHER_LEGGINGS, Color.RED),
			ItemHandler.makeLeatherItemStack(Material.LEATHER_BOOTS, Color.RED),
		};

	}

	public static void doSetup() {
		//Teleport players to the spawn
		teleportPlayersToSpawn();
		//Set a game in progress
		setGameInProgress(true);
		//Make all the teams
		makeGameTeams();
		//Teleport terrorists and Counter Terrorists
		TDMGame.runnableManager.runTaskNow(teleportTerrorists);
		TDMGame.runnableManager.runTaskNow(teleportCounterTerrorists);

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

		//Make all the players open their "kits"
		TDMGame.runnableManager.runTaskLater(playerOpenKits,playerOpenKitDelay);

	}

	private static void makeGameTeams() {
		FakeboardHandler.registerTeam(teamTerrorist, false);
		FakeboardHandler.registerTeam(teamCounterTerrorist, false);
		for (Player P : Bukkit.getOnlinePlayers()) {
			assignPlayerTeam(P);
		}
	}

	public static void assignPlayerTeam(Player Player) {
		//Our two teams, the terrorists and counter terrorists
		Team terroristTeam = FakeboardHandler.getTeam(teamTerrorist);
		Team counterTerroristTeam = FakeboardHandler.getTeam(teamCounterTerrorist);
		//The size of each team
		int terroristCount = terroristTeam.get
		int counterTerroristCount = FakeboardHandler.getTeam("CT").getTeamMembers().size();
		fPlayer fPlayer = FakeboardHandler.getPlayer(Player);
		if (terroristCount != counterTerroristCount) {
			if (terroristCount > counterTerroristCount) {
				FakeboardHandler.addToTeam("CT", fPlayer);
				fPlayer.setTeam("CT");
				Player.getInventory().setArmorContents(new ItemStack[]{ItemHandler.makeLeatherItemStack(Material.LEATHER_HELMET, Color.RED),
						ItemHandler.makeLeatherItemStack(Material.LEATHER_CHESTPLATE, Color.RED), ItemHandler.makeLeatherItemStack(Material.LEATHER_LEGGINGS,
						Color.RED), ItemHandler.makeLeatherItemStack(Material.LEATHER_BOOTS, Color.RED)});
			} else {
				FakeboardHandler.addToTeam("T", Player);
				fPlayer.setTeam("T");
				Player.getInventory().setArmorContents(new ItemStack[]{ItemHandler.makeLeatherItemStack(Material.LEATHER_HELMET, Color.BLUE),
						ItemHandler.makeLeatherItemStack(Material.LEATHER_CHESTPLATE, Color.BLUE), ItemHandler.makeLeatherItemStack(Material.LEATHER_LEGGINGS,
						Color.BLUE), ItemHandler.makeLeatherItemStack(Material.LEATHER_BOOTS, Color.BLUE)});
			}
		} else {
			switch (new Random().nextInt(2)) {
				case 0:
					FakeboardHandler.addToTeam("CT", Player);
					fPlayer.setTeam("CT");
					break;
				case 1:
					FakeboardHandler.addToTeam("T", Player);
					fPlayer.setTeam("T");
					break;
				default:
					break;
			}
		}
		TagAPI.refreshPlayer(Player);
	}

	public static void teleportPlayersToSpawn() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.teleport(WorldHandler.WorldHandler.getWorldSpawn(TDMGame.gameMap));
		}
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

	public boolean canSetup() {
		return Bukkit.getOnlinePlayers().length >= 2;
	}

	public void awardEndgamePoints(String winningTeam, double winningCash, double losingCash) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			fPlayer fPlayer = FakeboardHandler.getPlayer(player);
			if (fPlayer.getTeam() != null) {
				if (fPlayer.getTeam().equalsIgnoreCase(winningTeam)) {
					TDMGame.givePlayerTunnelsXP(player, winningCash);
				} else {
					TDMGame.givePlayerTunnelsXP(player, losingCash);
				}
			}
		}
	}
}
