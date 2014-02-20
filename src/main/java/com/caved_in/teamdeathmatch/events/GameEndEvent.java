package com.caved_in.teamdeathmatch.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 15/01/14
 * Time: 9:57 AM
 */
public class GameEndEvent extends Event {

	public static final HandlerList handlers = new HandlerList();

	public GameEndEvent() {
		//Not an async event
		super(false);
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
