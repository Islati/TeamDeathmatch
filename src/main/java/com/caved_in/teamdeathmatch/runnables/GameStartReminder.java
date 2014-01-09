package com.caved_in.teamdeathmatch.runnables;

import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.gamehandler.GameSetupHandler;
import org.bukkit.Bukkit;

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
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "say The round will begin in " + (messageCooldown - (currentTicks * 5)) + " seconds!");
			TDMGame.gameStartTime = (messageCooldown - currentTicks);
			this.currentTicks += 1;
		}

	}

}
