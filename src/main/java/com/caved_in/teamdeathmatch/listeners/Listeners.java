package com.caved_in.teamdeathmatch.listeners;

//Commons Imports

import com.caved_in.commons.chat.ChatHandler;
import com.caved_in.commons.items.ItemHandler;
import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.commons.time.Cooldown;
import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.TeamType;
import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.Team;
import com.caved_in.teamdeathmatch.fakeboard.fPlayer;
import com.caved_in.teamdeathmatch.gamehandler.GameSetupHandler;
import com.caved_in.teamdeathmatch.gamehandler.KillstreakHandler;
import com.caved_in.teamdeathmatch.runnables.AssistAggregator;
import com.caved_in.teamdeathmatch.runnables.RestoreInventory;
import com.caved_in.teamdeathmatch.assists.*;
import com.mysql.jdbc.StringUtils;
import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

//Team Deathmatch Imports
//Crackshot Import
//Bukkit Imports


public class Listeners implements Listener {
	private Cooldown playerCooldown = new Cooldown(2);
	private Cooldown moveCooldown = new Cooldown(3);
	private Cooldown respawnInvincibilityCooldown = new Cooldown(6);

	public Listeners(TDMGame Plugin) {
		Plugin.getServer().getPluginManager().registerEvents(this, Plugin);
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent Event) {
		fPlayer fPlayer = FakeboardHandler.getPlayer(Event.getPlayer());
		String playerName = fPlayer.getPlayerName();

		if (!moveCooldown.isOnCooldown(playerName)) {
			if (GameSetupHandler.isGameInProgress()) {
				if (fPlayer != null && fPlayer.isAfk()) //TODO prevent the null check from being needed
				{
					fPlayer.setAfk(false);
				}
			}
			moveCooldown.setOnCooldown(playerName);
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		fPlayer fPlayer = FakeboardHandler.getPlayer(event.getPlayer());
		String playerName = fPlayer.getPlayerName();

		if (GameSetupHandler.isGameInProgress()) {
			if (!playerCooldown.isOnCooldown(playerName)) {
				if (fPlayer.isAfk()) {
					fPlayer.setAfk(false);
				}
				playerCooldown.setOnCooldown(playerName);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDied(final PlayerDeathEvent event) {
		//Get the player who died
		Player player = event.getEntity();
		//Check if there's a game in profeess
		if (GameSetupHandler.isGameInProgress()) {
			//Get the fPlayer data for the player who was killed, and set their inventory contents
			fPlayer fPlayerKilled = FakeboardHandler.getPlayer(player);
			fPlayerKilled.setDeathInventory(player.getInventory().getContents());
			//Get the player who killed this player
			Player playerKiller = player.getKiller();
			if (playerKiller != null) {
				fPlayer killingPlayer = FakeboardHandler.getPlayer(playerKiller);
				if (killingPlayer != null && fPlayerKilled != null) {
					//Get the team of the killing player
					Team killerTeam = FakeboardHandler.getTeam(killingPlayer.getTeam());
					//Add score to the killing team
					killerTeam.addTeamScore(1);
					//Add a death to the player who was killed
					fPlayerKilled.addDeath();
					//Add +1 score to the player who killed
					killingPlayer.addScore(1);
					//Add killstreak progress to the player who killed
					killingPlayer.addKillstreak(1);
					KillstreakHandler.HandleKillStreak(killingPlayer);
					fPlayerKilled.resetKillstreak();

					final String killerName = killingPlayer.getPlayerName();
					final String killedName = fPlayerKilled.getPlayerName();

					TDMGame.runnableManager.runTaskLater(new AssistAggregator(killedName,killerName), 5);
				}
			}
			PlayerHandler.clearInventory(player);
			player.setScoreboard(fPlayerKilled.getPlayerScoreboard().getScoreboard());
		}
		event.getDrops().clear();
	}


	@EventHandler
	public void onWeaponDamageEntity(WeaponDamageEntityEvent event) {
		if (event.getVictim() instanceof Player) {
			fPlayer playerShooter = FakeboardHandler.getPlayer(event.getPlayer());
			fPlayer playerShot = FakeboardHandler.getPlayer((Player) event.getVictim());
			String shooterName = playerShooter.getPlayerName();
			String shotName = playerShot.getPlayerName();
			if (playerShooter.getTeam().equalsIgnoreCase(playerShot.getTeam()) || this.respawnInvincibilityCooldown.isOnCooldown(shooterName) || this.respawnInvincibilityCooldown.isOnCooldown(shotName) || playerShooter.isAfk() || playerShot.isAfk()) {
				event.setCancelled(true);
				return;
			} else {
				AssistManager.addData(shotName, shooterName);
			}
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		if (!GameSetupHandler.isGameInProgress()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void forceRespawn(final ForceRespawnEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		fPlayer fPlayer = FakeboardHandler.getPlayer(playerName);
		if (TDMGame.gameInProgress) {
			event.setForcedRespawn(true);
			if (fPlayer.getTeam().equalsIgnoreCase("T")) {
				player.teleport(new SpawnpointConfig(player.getWorld().getName(), TeamType.Terrorist).getSpawn());
			} else {
				event.getPlayer().teleport(new SpawnpointConfig(player.getWorld().getName(), TeamType.CounterTerrorist).getSpawn());
			}
			TDMGame.runnableManager.runTaskLater(new RestoreInventory(playerName), 20);
		}
		this.respawnInvincibilityCooldown.setOnCooldown(event.getPlayer().getName());

		if (fPlayer.isAfk()) {
			fPlayer.setAfk(false);
		}
	}

	@EventHandler
	public void RespawnEvent(PlayerRespawnEvent Event) {
		if (TDMGame.gameInProgress) {
			if (FakeboardHandler.getPlayer(Event.getPlayer()).getTeam().equalsIgnoreCase("T")) {
				Event.setRespawnLocation(new SpawnpointConfig(Event.getPlayer().getWorld().getName(), TeamType.Terrorist).getSpawn());
				Event.getPlayer().getInventory().setArmorContents(new ItemStack[]{ItemHandler.makeLeatherItemStack(Material.LEATHER_HELMET, Color.BLUE),
						ItemHandler.makeLeatherItemStack(Material.LEATHER_CHESTPLATE, Color.BLUE), ItemHandler.makeLeatherItemStack(Material.LEATHER_LEGGINGS,
						Color.BLUE), ItemHandler.makeLeatherItemStack(Material.LEATHER_BOOTS, Color.BLUE)});
			} else {
				Event.setRespawnLocation(new SpawnpointConfig(Event.getPlayer().getWorld().getName(), TeamType.CounterTerrorist).getSpawn());
				Event.getPlayer().getInventory().setArmorContents(new ItemStack[]{ItemHandler.makeLeatherItemStack(Material.LEATHER_HELMET, Color.RED),
						ItemHandler.makeLeatherItemStack(Material.LEATHER_CHESTPLATE, Color.RED), ItemHandler.makeLeatherItemStack(Material.LEATHER_LEGGINGS,
						Color.RED), ItemHandler.makeLeatherItemStack(Material.LEATHER_BOOTS, Color.RED)});
			}
		}
		//this.respawnInvincibilityCooldown.setOnCooldown(Event.getPlayer().getName());
		//Event.getPlayer().setScoreboard(FakeboardHandler.getPlayer(Event.getPlayer()).getPlayerScoreboard().getScoreboard());
	}

	@EventHandler
	public void AsynchChat(AsyncPlayerChatEvent Event) {
		String chatMessage = Event.getMessage();
		Player playerCastingVote = Event.getPlayer();
		String playerCastingName = playerCastingVote.getName();
		if (ChatHandler.startsWith(chatMessage, "!kick")) {
			if (!ChatHandler.isActiveVoteKick()) {
				if (ChatHandler.canCastVote(playerCastingName)) {
					if (chatMessage.contains(" ")) {
						String[] Split = chatMessage.split(" ");
						String PlayerName = Split[1];
						String Reason = "";
						for (int I = 2; I < Split.length; I++) {
							Reason += Split[I] + " ";
						}
						if (!StringUtils.isEmptyOrWhitespaceOnly(Reason)) {
							if (!StringUtils.isEmptyOrWhitespaceOnly(PlayerName)) {
								if (Bukkit.getPlayer(PlayerName) != null) {
									ChatHandler.newVoteKick(playerCastingVote, Bukkit.getPlayer(PlayerName), Reason);
									TDMGame.SendMessageToAll(ChatColor.YELLOW + playerCastingName + ChatColor.WHITE + " wants to kick " + ChatColor.YELLOW +
											PlayerName + ChatColor.WHITE + " for '" + ChatColor.AQUA + Reason);
									TDMGame.SendMessageToAll("Type " + ChatColor.YELLOW + "!yes" + ChatColor.WHITE + " in chat to vote yes, " +
											"or " + ChatColor.YELLOW + "!no" + ChatColor.WHITE + " to vote no");
									TDMGame.runnableManager.runTaskLater(new Runnable() {

										@Override
										public void run() {
											ChatHandler.handleActiveVoteKick();
										}
									}, (20 * 25));
								} else {
									playerCastingVote.sendMessage(ChatColor.RED + "This player isn't online, or simply doesn't exist.");
									Event.setCancelled(true);
								}
							} else {
								playerCastingVote.sendMessage(ChatColor.RED + "You need to provide a player name to kick");
								Event.setCancelled(true);
							}
						} else {
							playerCastingVote.sendMessage(ChatColor.RED + "You must provide a message for why you want to kick a player");
							Event.setCancelled(true);
						}
					}
				} else {
					playerCastingVote.sendMessage(ChatColor.RED + "You need to wait 5 minutes before casting another vote...");
					Event.setCancelled(true);
				}
			} else {
				playerCastingVote.sendMessage(ChatColor.RED + "There's currently a vote being casted, please wait until it's over...");
				Event.setCancelled(true);
			}
		} else if (ChatHandler.startsWith(chatMessage, "!yes") || ChatHandler.startsWith(chatMessage, "!no")) {
			if (ChatHandler.isActiveVoteKick()) {
				if (!ChatHandler.hasVoted(playerCastingName)) {
					if (ChatHandler.startsWith(chatMessage, "!yes")) {
						ChatHandler.addVote(playerCastingVote, ChatHandler.VoteType.Yes);
					} else {
						ChatHandler.addVote(playerCastingVote, ChatHandler.VoteType.No);
					}
				} else {
					playerCastingVote.sendMessage(ChatColor.YELLOW + "You've already casted your vote..");
					Event.setCancelled(true);
				}
			} else {
				playerCastingVote.sendMessage(ChatColor.YELLOW + "There's no vote currently active");
				Event.setCancelled(true);
			}
		} else {
			String playerPrefix = "";
			if (!playerCastingVote.isOp()) {
				if (PlayerHandler.isPremium(playerCastingName)) {
					playerPrefix += ChatColor.GOLD + "[P]" + ChatColor.RESET;
				}
			} else {
				playerPrefix += ChatColor.AQUA + "[Owner]" + ChatColor.RESET;
			}

			if (TDMGame.gameInProgress) {
				String playerTeam = FakeboardHandler.getPlayer(Event.getPlayer()).getTeam();
				if (playerTeam.equalsIgnoreCase("t")) {
					playerPrefix += ChatColor.GRAY + "[T] " + ChatColor.RESET;
				} else {
					playerPrefix += ChatColor.GRAY + "[CT] " + ChatColor.RESET;
				}
			}

			Event.setFormat(playerPrefix + "%1$s - %2$s");
		}
	}

	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		final String playerName = player.getName();

		PlayerHandler.removePotionEffects(player);
		TDMGame.runnableManager.runTaskAsynch(new Runnable() //TODO See if it works better as async, or not
		{
			@Override
			public void run() {
				FakeboardHandler.loadPlayer(playerName);
			}
		});

		if (!player.getWorld().getName().equalsIgnoreCase(TDMGame.gameMap)) {
			player.teleport(Bukkit.getWorld(TDMGame.gameMap).getSpawnLocation());
			//TDMGame.Console(event.getPlayer().getName() + " joined game and wasn't in world [" + TDMGame.gameMap + "] --> Teleported to current map");
		}
		try {
			TDMGame.runnableManager.runTaskLater(new Runnable() {
				@Override
				public void run() {
					try {
						if (TDMGame.gameInProgress) {
							TDMGame.setupHandler.assignPlayerTeam(player);
							String Team = FakeboardHandler.getPlayer(player).getTeam();
							//TDMGame.Console(event.getPlayer().getName() + " joined game --> Assigned to [" + Team + "]");
							if (Team.equalsIgnoreCase("T")) {
								player.teleport(new SpawnpointConfig(player.getWorld().getName(), TeamType.Terrorist).getSpawn());
							} else {
								player.teleport(new SpawnpointConfig(player.getWorld().getName(), TeamType.CounterTerrorist).getSpawn());
							}
							player.chat("/kit");
							player.sendMessage(ChatColor.GREEN + "To select a loadout, use /kit");

						} else {
							PlayerHandler.clearInventory(player);
						}
					} catch (Exception Ex) {
						Ex.printStackTrace();
						player.kickPlayer(ChatColor.YELLOW + "Please Re-Log; There was an error loading your data.");
					}
				}
			}, 40);
		} catch (Exception Ex) {
			Ex.printStackTrace();
			player.kickPlayer(ChatColor.YELLOW + "Please Re-Log; There was an error loading your data.");
		}
		player.setFoodLevel(20);
		//player.setWalkSpeed(0.2F);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onReceiveName(PlayerReceiveNameTagEvent Event) {
		Player playerReceiving = Event.getPlayer();
		Player playerSending = Event.getNamedPlayer();
		String sendingName = playerSending.getName();
		if (TDMGame.gameInProgress) {
			Team sendingPlayerTeam = FakeboardHandler.getTeamByPlayer(playerSending);
			Team receivingPlayerTeam = FakeboardHandler.getTeamByPlayer(playerReceiving);
			if (sendingPlayerTeam != null && receivingPlayerTeam != null) {
				if (sendingPlayerTeam.getName().equalsIgnoreCase(receivingPlayerTeam.getName())) {
					Event.setTag(ChatColor.GREEN + sendingName);
				} else {
					Event.setTag(ChatColor.RED + sendingName);
				}
			} else {
				Event.setTag(ChatColor.WHITE + sendingName);
			}
		} else {
			Event.setTag(ChatColor.WHITE + sendingName);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerQuit(PlayerQuitEvent Event) {
		Player player = Event.getPlayer();
		PlayerHandler.clearInventory(player);
		FakeboardHandler.removePlayer(player);
	}
}
