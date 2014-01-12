package com.caved_in.teamdeathmatch.runnables;

import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.TeamType;
import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.gamehandler.GameSetupHandler;

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
		int terroristScore = FakeboardHandler.getTeam(TeamType.TERRORIST.toString()).getTeamScore();
		int counterTerroristScore = FakeboardHandler.getTeam(TeamType.COUNTER_TERRORIST.toString()).getTeamScore();

		//Check if the game has been running for less than 10 minutes
		if (gameCurrentTicks <= gameStopTicks) {
			if (terroristScore >= 50 || counterTerroristScore >= 50) {
				GameSetupHandler.setGameInProgress(false);
				gameCurrentTicks = 0;
				PlayerHandler.sendMessageToAllPlayers(String.format("&6%s WIN!", terroristScore >= 50 ? "TERRORISTS" : "COUNTER TERRORISTS"));
				GameSetupHandler.awardEndgamePoints(terroristScore >= 50 ? TeamType.TERRORIST.toString() : TeamType.COUNTER_TERRORIST.toString(),75,50);
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
			PlayerHandler.sendMessageToAllPlayers(String.format("&6TIME'S UP; %s WIN!", terroristScore >= 50 ? "TERRORISTS" : "COUNTER TERRORISTS"));
			GameSetupHandler.awardEndgamePoints(terroristScore >= 50 ? TeamType.TERRORIST.toString() : TeamType.COUNTER_TERRORIST.toString(),75,50);
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
