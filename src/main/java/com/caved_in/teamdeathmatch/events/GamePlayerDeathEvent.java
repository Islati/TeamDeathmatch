package com.caved_in.teamdeathmatch.events;

import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.GamePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 15/01/14
 * Time: 9:08 AM
 */
public class GamePlayerDeathEvent extends PlayerDeathEvent {
	private static final HandlerList handlers = new HandlerList();
	private GamePlayer gamePlayer;
	private boolean keepInventory = true;

	public GamePlayerDeathEvent(PlayerDeathEvent event) {
		super(event.getEntity(), event.getDrops(), event.getDroppedExp(), event.getDeathMessage());
		this.gamePlayer = FakeboardHandler.getPlayer(event.getEntity());
	}

	public GamePlayerDeathEvent(Player player, List<ItemStack> drops, int droppedExp, String deathMessage) {
		super(player, drops, droppedExp, deathMessage);
		this.gamePlayer = FakeboardHandler.getPlayer(player);
	}

	public GamePlayerDeathEvent(Player player, List<ItemStack> drops, int droppedExp, int newExp, String deathMessage) {
		super(player, drops, droppedExp, newExp, deathMessage);
		this.gamePlayer = FakeboardHandler.getPlayer(player);
	}

	public GamePlayerDeathEvent(Player player, List<ItemStack> drops, int droppedExp, int newExp, int newTotalExp, int newLevel, String deathMessage) {
		super(player, drops, droppedExp, newExp, newTotalExp, newLevel, deathMessage);
		this.gamePlayer = FakeboardHandler.getPlayer(player);
	}

	public GamePlayer getGamePlayer() {
		return gamePlayer;
	}


	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public boolean isInventoryKept() {
		return keepInventory;
	}

	public void setKeepInventory(boolean keepInventory) {
		this.keepInventory = keepInventory;
	}
}
