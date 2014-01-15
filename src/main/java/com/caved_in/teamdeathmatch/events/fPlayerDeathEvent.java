package com.caved_in.teamdeathmatch.events;

import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.fPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 15/01/14
 * Time: 9:08 AM
 */
public class fPlayerDeathEvent extends PlayerDeathEvent {
	private fPlayer fPlayer;

	public fPlayerDeathEvent(Player player, List<ItemStack> drops, int droppedExp, String deathMessage) {
		super(player, drops, droppedExp, deathMessage);
		this.fPlayer = FakeboardHandler.getPlayer(player);
	}

	public fPlayerDeathEvent(Player player, List<ItemStack> drops, int droppedExp, int newExp, String deathMessage) {
		super(player, drops, droppedExp, newExp, deathMessage);
		this.fPlayer = FakeboardHandler.getPlayer(player);
	}

	public fPlayerDeathEvent(Player player, List<ItemStack> drops, int droppedExp, int newExp, int newTotalExp, int newLevel, String deathMessage) {
		super(player, drops, droppedExp, newExp, newTotalExp, newLevel, deathMessage);
		this.fPlayer = FakeboardHandler.getPlayer(player);
	}

	public fPlayer getfPlayer() {
		return fPlayer;
	}
}
