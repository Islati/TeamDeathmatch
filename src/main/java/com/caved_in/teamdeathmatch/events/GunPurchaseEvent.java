package com.caved_in.teamdeathmatch.events;

import com.caved_in.teamdeathmatch.guns.GunWrap;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 15/01/14
 * Time: 9:57 AM
 */
public class GunPurchaseEvent extends GamePlayerEvent implements Cancellable {

	public static final HandlerList handlers = new HandlerList();

	private boolean cancelled = false;

	private GunWrap gun;

	private int loadoutNumber;

	public GunPurchaseEvent(Player who, GunWrap gun, int loadoutNumber) {
		super(who);
		this.gun = gun;
		this.loadoutNumber = loadoutNumber;
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

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public int getLoadoutNumber() {
		return loadoutNumber;
	}

	public void setLoadoutNumber(int loadoutNumber) {
		this.loadoutNumber = loadoutNumber;
	}

	public void setGun(GunWrap gun) {
		this.gun = gun;
	}
}
