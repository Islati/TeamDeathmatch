package com.caved_in.teamdeathmatch.runnables;

import com.caved_in.teamdeathmatch.TDMGame.TeamType;
import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import org.bukkit.entity.Player;

public class TeleportTerrorist implements Runnable {
	@Override
	public void run() {
		for (Player player : FakeboardHandler.getPlayers("T")) {
			player.teleport(new SpawnpointConfig(player.getWorld().getName(), TeamType.Terrorist).getSpawn());
		}
	}
}
