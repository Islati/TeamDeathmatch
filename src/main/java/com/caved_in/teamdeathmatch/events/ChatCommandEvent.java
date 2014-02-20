package com.caved_in.teamdeathmatch.events;

import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.GamePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Set;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 15/01/14
 * Time: 9:22 AM
 */
public class ChatCommandEvent extends AsyncPlayerChatEvent {
	private static final HandlerList handlers = new HandlerList();
	private GamePlayer GamePlayer;

	public ChatCommandEvent(boolean async, Player who, String message, Set<Player> players) {
		super(async, who, message, players);
		this.GamePlayer = FakeboardHandler.getPlayer(who);
	}

	public GamePlayer getGamePlayer() {
		return GamePlayer;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
