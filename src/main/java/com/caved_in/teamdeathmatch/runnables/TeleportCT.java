package com.caved_in.teamdeathmatch.runnables;

import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.TeamType;
import com.caved_in.teamdeathmatch.config.spawns.WorldSpawns;
import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import org.bukkit.entity.Player;

public class TeleportCT implements Runnable {
	@Override
	public void run() {
		WorldSpawns worldSpawns = TDMGame.configuration.getSpawnConfiguration().getWorldSpawns(TDMGame.gameMap);
		for (Player player : FakeboardHandler.getPlayers(TeamType.COUNTER_TERRORIST.toString())) {
			player.teleport(worldSpawns.getRandomSpawn(TeamType.COUNTER_TERRORIST).getLocation());
		}
	}
}
