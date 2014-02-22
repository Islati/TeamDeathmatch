package com.caved_in.teamdeathmatch.runnables;

import com.caved_in.commons.Commons;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerOpenKits implements Runnable {
	@Override
	public void run() {
		Commons.messageConsole("&eForcing players to open their loadout selection menus");
		for(Player player: Bukkit.getOnlinePlayers()) {
			player.chat("/kit");
		}
	}
}
