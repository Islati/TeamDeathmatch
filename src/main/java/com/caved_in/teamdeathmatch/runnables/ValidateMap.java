package com.caved_in.teamdeathmatch.runnables;

import com.caved_in.teamdeathmatch.TDMGame;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class ValidateMap implements Runnable {

	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.getWorld() != Bukkit.getWorld(TDMGame.gameMap)) {
				if (player.getGameMode() != GameMode.CREATIVE) {
					player.teleport(WorldHandler.WorldHandler.getWorldSpawn(TDMGame.gameMap));
				}
			}
		}
	}

}
