package com.caved_in.teamdeathmatch.events;

import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.fPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Set;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 15/01/14
 * Time: 9:22 AM
 */
public class ChatCommandEvent extends AsyncPlayerChatEvent{
	private fPlayer fPlayer;
	public ChatCommandEvent(boolean async, Player who, String message, Set<Player> players) {
		super(async, who, message, players);
		this.fPlayer = FakeboardHandler.getPlayer(who);
	}

	public fPlayer getfPlayer() {
		return fPlayer;
	}


}
