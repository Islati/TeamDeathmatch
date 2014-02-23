package com.caved_in.teamdeathmatch.events;

import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.GamePlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 15/01/14
 * Time: 9:20 AM
 */
public class GamePlayerRespawnEvent extends PlayerRespawnEvent {

	public static final HandlerList handlers = new HandlerList();

	private GamePlayer gamePlayer;

	public GamePlayerRespawnEvent(Player respawnPlayer, Location respawnLocation, boolean isBedSpawn) {
		super(respawnPlayer, respawnLocation, isBedSpawn);
		this.gamePlayer = FakeboardHandler.getPlayer(player);
	}

	public GamePlayer getGamePlayer() {
		return gamePlayer;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
