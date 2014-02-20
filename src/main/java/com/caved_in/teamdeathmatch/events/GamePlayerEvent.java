package com.caved_in.teamdeathmatch.events;

import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.GamePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 15/01/14
 * Time: 9:16 AM
 */
public class GamePlayerEvent extends PlayerEvent {
	private static final HandlerList handlers = new HandlerList();
	private GamePlayer GamePlayer;

	public GamePlayerEvent(Player who) {
		super(who);
		//Get the GamePlayer involved
		this.GamePlayer = FakeboardHandler.getPlayer(who);
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public GamePlayer getGamePlayer() {
		return GamePlayer;
	}
}
