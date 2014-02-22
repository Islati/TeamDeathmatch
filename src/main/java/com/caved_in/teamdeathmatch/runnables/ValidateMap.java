package com.caved_in.teamdeathmatch.runnables;

import com.caved_in.commons.Commons;
import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.worldmanager.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class ValidateMap implements Runnable {

	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			String gameMap = TDMGame.gameMap;
			//If there's a null map, or the world isn't loaded/exists, then try to get another
			if (gameMap == null || Bukkit.getWorld(gameMap) == null) {
				TDMGame.gameMap = TDMGame.getGameWorld();
			}
			try {
				//If the player's not in the world for the current game, then teleport them there
				if (!player.getWorld().getName().equalsIgnoreCase(Bukkit.getWorld(TDMGame.gameMap).getName())) {
					if (player.getGameMode() != GameMode.CREATIVE) {
						player.teleport(WorldManager.getWorldSpawn(TDMGame.gameMap));
					}
				}
			} catch (Exception ex) {
				Commons.messageConsole(String.format("Error in ValidateMap.java; Unable to load/get world '%s'",TDMGame.gameMap));
			}
		}
	}

}
