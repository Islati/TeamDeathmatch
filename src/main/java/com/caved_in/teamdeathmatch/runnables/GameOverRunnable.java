package com.caved_in.teamdeathmatch.runnables;

import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.gamehandler.GameSetupHandler;
import org.bukkit.Bukkit;

public class GameOverRunnable implements Runnable {
	int gameStopTicks = 12000;
	int gameCurrentTicks = 0;

	@Override
	public void run() {
		//Check if we've got no players online
		if (PlayerHandler.getOnlinePlayersCount() <= 0) {
			//No players online? Stop the game!
			GameSetupHandler.setGameInProgress(false);
			gameCurrentTicks = 0;
			//Schedule a map rotation
			TDMGame.runnableManager.runTaskLater(new Runnable() {
				@Override
				public void run() {
					TDMGame.rotateMap(true);
				}
			}, 100L);
			TDMGame.runnableManager.cancelTask("GameEndCheck");
		}
		//Get the scores for both teams; Terrorist and CounterTerrorist
		int terroristScore = FakeboardHandler.getTeam("T").getTeamScore();
		int counterTerroristScore = FakeboardHandler.getTeam("CT").getTeamScore();

		//Check if the game has been running for less than 10 minutes
		if (gameCurrentTicks <= gameStopTicks) {
			if (terroristScore >= 50 || counterTerroristScore >= 50) {
				GameSetupHandler.setGameInProgress(false);
				gameCurrentTicks = 0;
				if (terroristScore >= 50) {
					PlayerHandler.sendMessageToAllPlayers("&6TERRORISTS WIN!");
					TDMGame.setupHandler.awardEndgamePoints("T", 75, 50);
				} else {
					PlayerHandler.sendMessageToAllPlayers("&6COUNTER TERRORISTS WIN!");
					TDMGame.setupHandler.awardEndgamePoints("CT", 75, 50);
				}
				TDMGame.runnableManager.runTaskLater(new Runnable() {
					@Override
					public void run() {
						TDMGame.rotateMap(true);
					}
				}, 100L);
				TDMGame.runnableManager.cancelTask("GameEndCheck");
			}
		} else if (gameCurrentTicks >= gameStopTicks) {
			GameSetupHandler.setGameInProgress(false);
			gameCurrentTicks = 0;
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "say TIMES UP; " + (terroristScore > counterTerroristScore ? "TERRORISTS WIN" :
					"COUNTER TERRORISTS WIN"));
			if (terroristScore >= 50) {
				PlayerHandler.sendMessageToAllPlayers("&6TERRORISTS WIN!");
				TDMGame.setupHandler.awardEndgamePoints("T", 75, 50);
			} else {
				PlayerHandler.sendMessageToAllPlayers("&6COUNTER TERRORISTS WIN!");
				TDMGame.setupHandler.awardEndgamePoints("CT", 75, 50);
			}
			TDMGame.runnableManager.runTaskLater(new Runnable() {
				@Override
				public void run() {
					TDMGame.rotateMap(true);
				}
			}, 100L);
			TDMGame.runnableManager.cancelTask("GameEndCheck");
		}
		gameCurrentTicks += 40;
	}

}
