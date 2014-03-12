package com.caved_in.teamdeathmatch.runnables;

import com.caved_in.teamdeathmatch.Game;
import com.caved_in.teamdeathmatch.TeamType;
import com.caved_in.teamdeathmatch.config.WorldSpawns;
import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import org.bukkit.entity.Player;

/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */
public class TeleportTerrorist implements Runnable {
	@Override
	public void run() {
		WorldSpawns worldSpawns = Game.configuration.getSpawnConfiguration().getWorldSpawns(Game.gameMap);
		for (Player player : FakeboardHandler.getPlayers(TeamType.TERRORIST)) {
			player.teleport(worldSpawns.getRandomSpawn(TeamType.TERRORIST).getLocation());
		}
	}
}
