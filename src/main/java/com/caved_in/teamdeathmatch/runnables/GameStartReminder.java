package com.caved_in.teamdeathmatch.runnables;

import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.gamehandler.GameSetupHandler;

public class GameStartReminder implements Runnable {
	int messageCooldown = 30;
	int currentTicks = 0;
	int timeRemaining = 30;

	@Override
	public void run() {
		if (currentTicks >= 6) {
			GameSetupHandler.doSetup();
			TDMGame.runnableManager.cancelTask("GameStart");
			TDMGame.runnableManager.registerSynchRepeatTask("GameEndCheck", new GameOverRunnable(), 40L, 40L);
			TDMGame.runnableManager.registerSynchRepeatTask("ValidateMap", new ValidateMap(), 120L, 60L);
		} else {
			PlayerHandler.sendMessageToAllPlayers("&aThe round will begin in &e" + (messageCooldown - (currentTicks * 5)) + "&a seconds!");
			TDMGame.gameStartTime = (messageCooldown - currentTicks);
			currentTicks += 1;
		}

	}

}
