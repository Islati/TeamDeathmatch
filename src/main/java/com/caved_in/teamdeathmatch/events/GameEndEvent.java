package com.caved_in.teamdeathmatch.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 15/01/14
 * Time: 9:57 AM
 */
public class GameEndEvent extends Event {

	public GameEndEvent() {
		//Not an async event
		super(false);
	}

	@Override
	public HandlerList getHandlers() {
		return null;
	}
}
