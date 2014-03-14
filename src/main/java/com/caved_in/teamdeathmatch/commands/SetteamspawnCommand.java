package com.caved_in.teamdeathmatch.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.commands.CommandController;
import com.caved_in.commons.player.Players;
import com.caved_in.teamdeathmatch.Game;
import com.caved_in.teamdeathmatch.TeamType;
import com.caved_in.teamdeathmatch.config.TeamSpawnLocation;
import org.bukkit.entity.Player;

/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */
public class SetteamspawnCommand {

	@CommandController.CommandHandler(name = "setteamspawn", description = "Used to add spawn locations for teams", permission = "gungame.admin",
			usage = "/setteamspawn [team]")
	public void onSetTeamSpawnCommand(Player sender, String[] args) {
		if (args.length > 0) {
			String teamName = args[0];
			//Get the world spawns for the player issueing the command
			String playerWorldName = Players.getWorldName(sender);
			switch (teamName.toLowerCase()) {
				case "t":
				case "ct":
					Game.configuration.getSpawnConfiguration().addSpawn(new TeamSpawnLocation(sender.getLocation(), TeamType.getTeamByInitials(teamName)));
					Players.sendMessage(sender, "&aSpawn point for &e" + teamName + "&a has been added for the world &e" + playerWorldName);
					break;
				default:
					Players.sendMessage(sender, "&cThe available teams are &eT&c and &eCT");
					break;
			}
		} else {
			Players.sendMessage(sender, Messages.INVALID_COMMAND_USAGE("team"));
		}
	}

}
