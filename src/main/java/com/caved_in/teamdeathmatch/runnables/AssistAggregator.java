package com.caved_in.teamdeathmatch.runnables;

import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.assists.AssistManager;
import org.bukkit.Bukkit;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 09/01/14
 * Time: 12:39 PM
 */
public class AssistAggregator implements Runnable {
	private String killedName = "";
	private String killerName = "";

	public AssistAggregator(String killedName, String killerName) {
		this.killedName = killedName;
		this.killerName = killerName;
	}


	@Override
	//TODO Crete a better implementation to aggregate this data
	public void run() {
		if (AssistManager.hasData(killedName)) {
			for (String playersWhoAssisted : AssistManager.getData(killedName).getAttackers()) {
				if (!playersWhoAssisted.equalsIgnoreCase(killerName)) {
					if (PlayerHandler.isOnline(playersWhoAssisted)) {
						TDMGame.givePlayerTunnelsXP(playersWhoAssisted, 1, true);
					}
				}
			}
			AssistManager.removeData(killedName);
			TDMGame.givePlayerTunnelsXP(Bukkit.getPlayer(killerName), 4);
		}
	}
}
