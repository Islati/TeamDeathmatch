package com.caved_in.teamdeathmatch.runnables;

import com.caved_in.teamdeathmatch.TDMGame;

public class StartCheckRunnable implements Runnable {

	@Override
	public void run() {
		if (TDMGame.setupHandler.canSetup()) {
			TDMGame.runnableManager.registerSynchRepeatTask("GameStart", new GameStartReminder(), 20L, 100L);
			TDMGame.runnableManager.cancelTask("SetupCheck");
		}
	}

}
