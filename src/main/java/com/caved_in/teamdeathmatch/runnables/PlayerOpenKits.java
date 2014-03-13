package com.caved_in.teamdeathmatch.runnables;

import com.caved_in.commons.Commons;
import com.caved_in.teamdeathmatch.gamehandler.GameSetupHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */
public class PlayerOpenKits implements Runnable {
	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			GameSetupHandler.openLoadoutSelectionMenu(player, true);
		}
	}
}
