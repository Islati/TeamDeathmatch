package com.caved_in.teamdeathmatch.listeners;

//Commons Imports

import com.caved_in.commons.items.ItemHandler;
import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.commons.time.Cooldown;
import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.assists.AssistHandler;
import com.caved_in.teamdeathmatch.chatvote.ChatHandler;
import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.Team;
import com.caved_in.teamdeathmatch.fakeboard.fPlayer;
import com.caved_in.teamdeathmatch.gamehandler.KillstreakHandler;
import com.caved_in.teamdeathmatch.runnables.RestoreInventory;
import com.chaseoes.forcerespawn.event.ForceRespawnEvent;
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
	private Cooldown Cooldown = new Cooldown(2);
	private Cooldown moveCooldown = new Cooldown(3);
	private Cooldown respawnInvincibilityCooldown = new Cooldown(6);

	public Listeners(TDMGame Plugin) {
		Plugin.getServer().getPluginManager().registerEvents(this, Plugin);
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent Event) {
		fPlayer fPlayer = FakeboardHandler.getPlayer(Event.getPlayer());
		String playerName = fPlayer.getPlayerName();

		if (!this.moveCooldown.isOnCooldown(playerName)) {
			if (TDMGame.gameInProgress) {
				if (fPlayer != null && fPlayer.isAfk()) //TODO prevent the null check from being needed
				{
					fPlayer.setAfk(false);
				}
			}
			this.moveCooldown.setOnCooldown(playerName);
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent Event) {
		fPlayer fPlayer = FakeboardHandler.getPlayer(Event.getPlayer());
		String playerName = fPlayer.getPlayerName();

		if (TDMGame.gameInProgress) {
			if (!this.Cooldown.isOnCooldown(playerName)) {
				if (fPlayer.isAfk()) {
					fPlayer.setAfk(false);
				}
				this.Cooldown.setOnCooldown(playerName);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void PlayerDied(final PlayerDeathEvent Event) {
		Player player = Event.getEntity();
		if (TDMGame.gameInProgress) {
			fPlayer fPlayerKilled = FakeboardHandler.getPlayer(player);
			fPlayerKilled.setDeathInventory(player.getInventory().getContents());
			if (player.getKiller() instanceof Player) {
				fPlayer Killer = FakeboardHandler.getPlayer(player.getKiller());
				if (Killer != null && fPlayerKilled != null) {
					Team KillTeam = FakeboardHandler.getTeam(Killer.getTeam());
					KillTeam.addTeamScore(1);
					fPlayerKilled.addDeath();
					Killer.addScore(1);
					
					/*
					if (KillTeam.getName().equalsIgnoreCase("T"))
					{
						TDMGame.SBMan.setScore(TeamType.Terrorist, KillTeam.getTeamScore());
					}
					else
					{
						TDMGame.SBMan.setScore(TeamType.CounterTerrorist, KillTeam.getTeamScore());
					}
					*/
					Killer.addKillstreak(1);
					KillstreakHandler.HandleKillStreak(Killer);
					fPlayerKilled.resetKillstreak();

					final String killerName = Killer.getPlayerName();
					final String killedName = fPlayerKilled.getPlayerName();

					TDMGame.runnableManager.runTaskLater(new Runnable() {
						@Override
						public void run() {
							if (AssistHandler.hasData(killedName)) {
								for (String playersWhoAssisted : AssistHandler.getData(killedName).getAttackers()) {
									if (!playersWhoAssisted.equalsIgnoreCase(killerName)) {
										if (Bukkit.getPlayer(playersWhoAssisted) != null) {
											TDMGame.givePlayerTunnelsXP(playersWhoAssisted, 1, true);
										}
									}
								}
								AssistHandler.removeData(killedName);
								TDMGame.givePlayerTunnelsXP(Bukkit.getPlayer(killerName), 4);
							}
						}
					}, 5);

					//TDMGame.Console("Added 1 Killcount to [" + Killer.getPlayerName() + "] for killing [" + fPlayerKilled.getPlayerName() + "]; Total KS ==
					// " + Killer.getKillStreak());
				}
			}
			PlayerHandler.clearInventory(player);
			player.setScoreboard(fPlayerKilled.getPlayerScoreboard().getScoreboard());
		}
		Event.getDrops().clear();
	}


	@EventHandler
	public void onWeaponDamageEntity(WeaponDamageEntityEvent event) {
		if (event.getVictim() instanceof Player) {
			fPlayer playerShooter = FakeboardHandler.getPlayer(event.getPlayer());
			fPlayer playerShot = FakeboardHandler.getPlayer((Player) event.getVictim());
			String shooterName = playerShooter.getPlayerName();
			String shotName = playerShot.getPlayerName();
			if (playerShooter.getTeam().equalsIgnoreCase(playerShot.getTeam()) || this.respawnInvincibilityCooldown.isOnCooldown(shooterName) || this
					.respawnInvincibilityCooldown.isOnCooldown(shotName) || playerShooter.isAfk() || playerShot.isAfk()) {
				event.setCancelled(true);
				return;
			} else {
				AssistHandler.addData(shotName, shooterName);
			}
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent Event) {
		if (!TDMGame.gameInProgress) {
			Event.setCancelled(true);
		}
	}

	@EventHandler
	public void forceRespawn(final ForceRespawnEvent Event) {
		Player player = Event.getPlayer();
		String playerName = player.getName();
		fPlayer fPlayer = FakeboardHandler.getPlayer(playerName);
		if (TDMGame.gameInProgress) {
			Event.setForcedRespawn(true);
			if (fPlayer.getTeam().equalsIgnoreCase("T")) {
				player.teleport(new SpawnpointConfig(player.getWorld().getName(), TDMGame.TeamType.Terrorist).getSpawn());
			} else {
				Event.getPlayer().teleport(new SpawnpointConfig(player.getWorld().getName(), TDMGame.TeamType.CounterTerrorist).getSpawn());
			}
			TDMGame.runnableManager.runTaskLater(new RestoreInventory(playerName), 20);
		}
		this.respawnInvincibilityCooldown.setOnCooldown(Event.getPlayer().getName());

		if (fPlayer.isAfk()) {
			fPlayer.setAfk(false);
		}
	}

	@EventHandler
	public void RespawnEvent(PlayerRespawnEvent Event) {
		if (TDMGame.gameInProgress) {
			if (FakeboardHandler.getPlayer(Event.getPlayer()).getTeam().equalsIgnoreCase("T")) {
				Event.setRespawnLocation(new SpawnpointConfig(Event.getPlayer().getWorld().getName(), TDMGame.TeamType.Terrorist).getSpawn());
				Event.getPlayer().getInventory().setArmorContents(new ItemStack[]{ItemHandler.makeLeatherItemStack(Material.LEATHER_HELMET, Color.BLUE),
						ItemHandler.makeLeatherItemStack(Material.LEATHER_CHESTPLATE, Color.BLUE), ItemHandler.makeLeatherItemStack(Material.LEATHER_LEGGINGS,
						Color.BLUE), ItemHandler.makeLeatherItemStack(Material.LEATHER_BOOTS, Color.BLUE)});
			} else {
				Event.setRespawnLocation(new SpawnpointConfig(Event.getPlayer().getWorld().getName(), TDMGame.TeamType.CounterTerrorist).getSpawn());
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
								player.teleport(new SpawnpointConfig(player.getWorld().getName(), TDMGame.TeamType.Terrorist).getSpawn());
							} else {
								player.teleport(new SpawnpointConfig(player.getWorld().getName(), TDMGame.TeamType.CounterTerrorist).getSpawn());
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
		FakeboardHandler.removePlayerr(player);
	}
}
