package com.caved_in.teamdeathmatch.runnables;

import com.caved_in.teamdeathmatch.gamehandler.GameSetupHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerOpenKits implements Runnable {
	@Override
	public void run() {
		for(Player player: Bukkit.getOnlinePlayers()) {
			GameSetupHandler.openLoadoutSelectionMenu(player, true);
		}
	}
}
