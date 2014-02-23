package com.caved_in.teamdeathmatch.runnables;

import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.GamePlayer;
import com.caved_in.teamdeathmatch.gamehandler.GameSetupHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ScoreboardRunnable implements Runnable {
	@Override
	public void run() {
		if (GameSetupHandler.isGameInProgress()) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				GamePlayer GamePlayer = FakeboardHandler.getPlayer(player);
				if (GamePlayer != null) {
					try {
						GamePlayer.updateScoreboard();
						player.setScoreboard(GamePlayer.getPlayerScoreboard().getScoreboard());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}
}
