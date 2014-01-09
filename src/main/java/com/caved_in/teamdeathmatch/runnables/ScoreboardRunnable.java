package com.caved_in.teamdeathmatch.runnables;

import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.fPlayer;
import com.caved_in.teamdeathmatch.gamehandler.GameSetupHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ScoreboardRunnable implements Runnable {
	@Override
	public void run() {
		if (GameSetupHandler.isGameInProgress()) {
			for (Player Player : Bukkit.getOnlinePlayers()) {
				fPlayer fPlayer = FakeboardHandler.getPlayer(Player);
				if (fPlayer != null) {
					fPlayer.updateScoreboard();
					Player.setScoreboard(fPlayer.getPlayerScoreboard().getScoreboard());
				}
			}
		}
	}
}
