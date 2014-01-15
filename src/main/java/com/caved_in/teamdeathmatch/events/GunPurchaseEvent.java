package com.caved_in.teamdeathmatch.events;

import com.caved_in.teamdeathmatch.guns.GunWrap;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 15/01/14
 * Time: 9:57 AM
 */
public class GunPurchaseEvent extends fPlayerEvent implements Cancellable {
	private boolean cancelled = false;

	private GunWrap gun;

	public GunPurchaseEvent(Player who, GunWrap gun) {
		super(who);
	}

	public GunWrap getGun() {
		return gun;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
