package com.caved_in.teamdeathmatch.runnables;

import com.caved_in.commons.player.PlayerHandler;

public class PlayerOpenKits implements Runnable {
	@Override
	public void run() {
		PlayerHandler.allPlayersChat("/kit");
	}
}
