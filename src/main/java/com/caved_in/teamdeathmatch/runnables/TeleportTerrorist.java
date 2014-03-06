package com.caved_in.teamdeathmatch.runnables;

import com.caved_in.teamdeathmatch.Game;
import com.caved_in.teamdeathmatch.TeamType;
import com.caved_in.teamdeathmatch.config.spawns.WorldSpawns;
import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import org.bukkit.entity.Player;

public class TeleportTerrorist implements Runnable {
	@Override
	public void run() {
		WorldSpawns worldSpawns = Game.configuration.getSpawnConfiguration().getWorldSpawns(Game.gameMap);
		for (Player player : FakeboardHandler.getPlayers(TeamType.TERRORIST.toString())) {
			player.teleport(worldSpawns.getRandomSpawn(TeamType.TERRORIST).getLocation());
		}
	}
}
