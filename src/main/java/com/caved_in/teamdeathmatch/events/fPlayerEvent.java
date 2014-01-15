package com.caved_in.teamdeathmatch.events;

import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import com.caved_in.teamdeathmatch.fakeboard.fPlayer;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 15/01/14
 * Time: 9:16 AM
 */
public class fPlayerEvent extends PlayerEvent {
	private fPlayer fPlayer;

	public fPlayerEvent(Player who) {
		super(who);
		//Get the fPlayer involved
		this.fPlayer = FakeboardHandler.getPlayer(who);
	}

	@Override
	public HandlerList getHandlers() {
		return null;
	}

	public fPlayer getfPlayer() {
		return fPlayer;
	}
}
