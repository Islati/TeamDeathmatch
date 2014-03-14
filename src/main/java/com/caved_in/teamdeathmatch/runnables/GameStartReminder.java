package com.caved_in.teamdeathmatch.runnables;

import com.caved_in.commons.player.Players;
import com.caved_in.teamdeathmatch.Game;
import com.caved_in.teamdeathmatch.gamehandler.GameSetupHandler;

/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */
public class GameStartReminder implements Runnable {
	int messageCooldown = 30;
	int currentTicks = 0;
	int timeRemaining = 30;

	@Override
	public void run() {
		if (currentTicks >= 6) {
			GameSetupHandler.doSetup();
			Game.runnableManager.cancelTask("GameStart");
			Game.runnableManager.registerSynchRepeatTask("GameEndCheck", new GameOverRunnable(), 40L, 40L);
			Game.runnableManager.registerSynchRepeatTask("ValidateMap", new ValidateMap(), 120L, 60L);
		} else {
			Players.sendMessageToAllPlayers("&aThe round will begin in &e" + (messageCooldown - (currentTicks * 5)) + "&a seconds!");
			Game.gameStartTime = (messageCooldown - currentTicks);
			currentTicks += 1;
		}

	}

}
