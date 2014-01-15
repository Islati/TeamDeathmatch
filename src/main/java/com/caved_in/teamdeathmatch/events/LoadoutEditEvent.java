package com.caved_in.teamdeathmatch.events;

import com.caved_in.teamdeathmatch.loadout.Loadout;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 15/01/14
 * Time: 9:57 AM
 */
public class LoadoutEditEvent extends fPlayerEvent implements Cancellable {
	private Loadout loadout;
	private boolean cancelled = false;

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
}
