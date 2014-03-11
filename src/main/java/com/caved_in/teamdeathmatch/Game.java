package com.caved_in.teamdeathmatch;

import com.caved_in.commons.file.DataHandler;
import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.commons.player.PlayerWrapper;
import com.caved_in.commons.threading.RunnableManager;
import com.caved_in.commons.time.Cooldown;
import com.caved_in.teamdeathmatch.commands.CommandRegister;
import com.caved_in.teamdeathmatch.config.*;
import com.caved_in.teamdeathmatch.config.GunShopConfiguration;
import com.caved_in.teamdeathmatch.config.SpawnConfiguration;
import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.GamePlayer;
import com.caved_in.teamdeathmatch.gamehandler.GameSetupHandler;
import com.caved_in.teamdeathmatch.guns.GunHandler;
import com.caved_in.teamdeathmatch.listeners.BukkitListeners;
import com.caved_in.teamdeathmatch.perks.PerkHandler;
import com.caved_in.teamdeathmatch.runnables.MessageRunnable;
import com.caved_in.teamdeathmatch.runnables.ScoreboardRunnable;
import com.caved_in.teamdeathmatch.runnables.StartCheckRunnable;
import com.google.common.collect.Iterables;
import com.shampaggon.crackshot.CSUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */
public class Game extends JavaPlugin {
	public static GameSetupHandler setupHandler;
	public static RunnableManager runnableManager;

	public static int gameStartTime = 30;
	public static DataHandler worldList;
	public static String gameMap = "";
	public static GunHandler gunHandler;

	public static CSUtility crackShotAPI;

	public static PerkHandler perkHandler;

	public static LoadoutSQL loadoutSQL;
	public static GunsSQL gunsSQL;
	public static PerksSQL perksSQL;

	public static Cooldown afkCooldown = new Cooldown(10);
	public static List<String> messages = new ArrayList<String>();

	public static String DATA_FOLDER;

	public static String SPAWN_CONFIG_FILE;

	public static String GUN_CONFIG_FILE;

	public static String SQL_CONFIG_FILE;

	public static Configuration configuration;

	private static Serializer serializer = new Persister();

	@Override
	public void onEnable() {
		if (!getDataFolder().exists()) {
			getDataFolder().mkdirs();
		}

		//Get the location of our data folder
		DATA_FOLDER = this.getDataFolder() + File.separator;
		//Gun config file
		GUN_CONFIG_FILE = DATA_FOLDER + "GunConfig.xml";
		//Spawn config file
		SPAWN_CONFIG_FILE = DATA_FOLDER + "SpawnConfig.xml";
		SQL_CONFIG_FILE = DATA_FOLDER + "Database.xml";
		//Init our config
		initConfig();
		//Initialize the configuration
		configuration = new Configuration();
		SqlConfiguration sqlConfiguration = configuration.getSqlConfiguration();
		//Load out sql shit
		loadoutSQL = new LoadoutSQL(sqlConfiguration);
		gunsSQL = new GunsSQL(sqlConfiguration);
		perksSQL = new PerksSQL(sqlConfiguration);
		//Init the Handlers and apis'
		perkHandler = new PerkHandler();
		gunHandler = new GunHandler();
		crackShotAPI = new CSUtility();
		worldList = new DataHandler("plugins/Team-Deathmatch/Worldlist.txt");
		//(Re)load our messages
		reloadMessages();
		setupHandler = new GameSetupHandler();
		runnableManager = new RunnableManager(this);
		rotateMap(false);
		new CommandRegister(this);
		new BukkitListeners(this);
		//new Voting(this);
		runnableManager.registerSynchRepeatTask("MessageReminder", new MessageRunnable(), 6000, 6000);
		//runnableManager.RegisterSynchRepeatTask("SpeedBoost", new SpeedRunnable(), 20L, 40L);
		runnableManager.registerSynchRepeatTask("ScoreboardRunnable", new ScoreboardRunnable(), 400, 40);

		for (Player player : Bukkit.getOnlinePlayers()) {
			final String playerName = player.getName();
			FakeboardHandler.loadPlayer(playerName);
			if (!player.getWorld().getName().equalsIgnoreCase(Game.gameMap)) {
				player.teleport(Bukkit.getWorld(Game.gameMap).getSpawnLocation());
				//TDMGame.Console(event.getPlayer().getName() + " joined game and wasn't in world [" + TDMGame.gameMap + "] --> Teleported to current map");
			}
		}
	}

	@Override
	public void onDisable() {
		//Save config
		configuration.saveConfig();
		HandlerList.unregisterAll(this);
		Bukkit.getScheduler().cancelTasks(this);
	}

	public static Serializer getPersister() {
		return serializer;
	}

	public void initConfig() {
		File gunConfig = new File(GUN_CONFIG_FILE);
		File spawnConfig = new File(SPAWN_CONFIG_FILE);
		File sqlConfig = new File(SQL_CONFIG_FILE);
		try {
			if (!gunConfig.exists()) {
				//This saves the configurations
				getPersister().write(new GunShopConfiguration(), gunConfig);
			}

			if (!spawnConfig.exists()) {
				getPersister().write(new SpawnConfiguration(), spawnConfig);
			}

			if (!sqlConfig.exists()) {
				getPersister().write(new SqlConfiguration(), sqlConfig);
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isValidMap(String mapName) {
		return Iterables.contains(worldList.getContentsAsList(),mapName);
	}

	public static void reloadMessages() {
		messages = new DataHandler("plugins/Team-Deathmatch/Messages.txt").getContentsAsList();
	}

	public static void cleanActiveMap() {
		World World = Bukkit.getWorld(gameMap);
		if (World != null) {
			World.setPVP(true);
			World.setThundering(false);
			World.setTime(0);
			World.setStorm(false);
			World.setAutoSave(false);
		}
	}

	public static void rotateMap(boolean rollback) {
		FakeboardHandler.cleanTeams();

		if (!GameSetupHandler.isForceMap()) {
			gameMap = getGameWorld();
		}

		runnableManager.registerSynchRepeatTask("SetupCheck", new StartCheckRunnable(), 200L, 40L);

		cleanActiveMap();

		for (Player Player : Bukkit.getOnlinePlayers()) {
			GamePlayer gamePlayer = FakeboardHandler.getPlayer(Player);
			gamePlayer.clearScoreboard();
			Player.setScoreboard(gamePlayer.getPlayerScoreboard().getScoreboard());
		}
	}

	public static void givePlayerTunnelsXP(Player player, double amount) {
		givePlayerTunnelsXP(player, amount, false);
	}

	public static void givePlayerTunnelsXP(Player player, double amount, boolean isSilent) {
		givePlayerTunnelsXP(player.getName(), amount, isSilent);
	}

	public static void givePlayerTunnelsXP(String playerName, double amount, boolean isSilent) {
		PlayerWrapper playerWrapper = PlayerHandler.getData(playerName);
		double earnedXP = getXP(playerName, amount);
		playerWrapper.addCurrency(earnedXP);
		if (!isSilent) {
			if (PlayerHandler.isOnline(playerName)) {
				PlayerHandler.getPlayer(playerName).sendMessage(ChatColor.GREEN + "You've earned +" + ((int) earnedXP) + " XP!");
			}
		}
	}

	public static double getXP(String playerName, double amountAwarded) {
		double awardedXP = amountAwarded;
		if (Bukkit.getPlayer(playerName) != null && PlayerHandler.isPremium(playerName)) {
			awardedXP = (awardedXP > 20 ? awardedXP + 20 : awardedXP * 2);
		}
		return awardedXP;
	}

	public static String getGameWorld() {
		List<String> worldList = Game.worldList.getContentsAsList();
		String worldName = "";
		while (worldName.equalsIgnoreCase(gameMap)) {
			worldName = worldList.get(new Random().nextInt(worldList.size()));
		}
		return worldName;
	}

	public enum LoadoutSlot {
		Primary, Secondary, Tertiary
	}
}
