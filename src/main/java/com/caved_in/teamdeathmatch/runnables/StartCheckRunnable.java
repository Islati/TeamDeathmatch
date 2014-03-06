package com.caved_in.teamdeathmatch.runnables;

import com.caved_in.teamdeathmatch.Game;
import com.caved_in.teamdeathmatch.gamehandler.GameSetupHandler;

public class StartCheckRunnable implements Runnable {

	@Override
	public void run() {
		if (GameSetupHandler.canSetup()) {
			Game.runnableManager.registerSynchRepeatTask("GameStart", new GameStartReminder(), 20L, 100L);
			Game.runnableManager.cancelTask("SetupCheck");
		}
	}

}
