package com.caved_in.teamdeathmatch.events;

import com.caved_in.teamdeathmatch.loadout.Loadout;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 15/01/14
 * Time: 9:57 AM
 */
public class LoadoutEditEvent extends GamePlayerEvent implements Cancellable {
	private Loadout loadout;
	private boolean cancelled = false;

	public static final HandlerList handlers = new HandlerList();

	public LoadoutEditEvent(Player who, Loadout loadout) {
		super(who);
		this.loadout = loadout;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public Loadout getLoadout() {
		return loadout;
	}

	public void setLoadout(Loadout loadout) {
		this.loadout = loadout;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
