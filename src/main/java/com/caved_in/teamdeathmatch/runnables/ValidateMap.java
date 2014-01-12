package com.caved_in.teamdeathmatch.runnables;

import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.worldmanager.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class ValidateMap implements Runnable {

	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!player.getWorld().getName().equalsIgnoreCase(Bukkit.getWorld(TDMGame.gameMap).getName())) {
				if (player.getGameMode() != GameMode.CREATIVE) {
					player.teleport(WorldManager.getWorldSpawn(TDMGame.gameMap));
				}
			}
		}
	}

}
