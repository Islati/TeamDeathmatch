package com.caved_in.teamdeathmatch.runnables;

import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 17/02/14
 * Time: 7:43 PM
 */
public class InitPlayerTask implements Runnable {

	private String playerName;

	public InitPlayerTask(String playerName) {
		this.playerName = playerName;
	}

	@Override
	public void run() {
		FakeboardHandler.loadPlayer(playerName);
	}
}
