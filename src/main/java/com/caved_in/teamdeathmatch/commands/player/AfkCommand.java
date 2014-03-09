package com.caved_in.teamdeathmatch.commands.player;

import com.caved_in.commons.commands.CommandController;
import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.teamdeathmatch.Game;
import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.GamePlayer;
import com.caved_in.teamdeathmatch.gamehandler.GameSetupHandler;
import org.bukkit.entity.Player;

import static com.caved_in.teamdeathmatch.GameMessages.AFK_COMMAND_ON_COOLDOWN;

public class AfkCommand {
	@CommandController.CommandHandler(name = "afk", description = "Set yourself as 'afk' which stops all damage, and turns you invisible", usage = "/afk")
	public void onAfkCommand(Player player, String[] args) {
		if (GameSetupHandler.isGameInProgress()) {
			String playerName = player.getName();
			if (!Game.afkCooldown.isOnCooldown(playerName)) {
				GamePlayer GamePlayer = FakeboardHandler.getPlayer(playerName);
				if (GamePlayer != null) {
					GamePlayer.setAfk(!GamePlayer.isAfk());
					Game.afkCooldown.setOnCooldown(playerName);
				}
			} else {
				PlayerHandler.sendMessage(player, AFK_COMMAND_ON_COOLDOWN);
			}
		}
	}
}
