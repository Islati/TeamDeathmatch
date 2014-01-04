package com.caved_in.teamdeathmatch.runnables;

import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import org.bukkit.entity.Player;

public class TeleportCT implements Runnable {
	@Override
	public void run() {
		for (Player player : FakeboardHandler.getPlayers("CT")) {
			player.teleport(new SpawnpointConfig(player.getWorld().getName(), TDMGame.TeamType.CounterTerrorist).getSpawn());
		}
	}
}
