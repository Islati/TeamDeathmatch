package com.caved_in.teamdeathmatch.events;

import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.fPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 15/01/14
 * Time: 9:20 AM
 */
public class fPlayerRespawnEvent extends PlayerRespawnEvent {
	private fPlayer fPlayer;

	public fPlayerRespawnEvent(Player respawnPlayer, Location respawnLocation, boolean isBedSpawn) {
		super(respawnPlayer, respawnLocation, isBedSpawn);
		this.fPlayer = FakeboardHandler.getPlayer(player);
	}

	public fPlayer getfPlayer() {
		return fPlayer;
	}
}
