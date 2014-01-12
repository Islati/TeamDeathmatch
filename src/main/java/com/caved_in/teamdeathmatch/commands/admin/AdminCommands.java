package com.caved_in.teamdeathmatch.commands.admin;

import com.caved_in.commons.Messages;
import com.caved_in.commons.commands.CommandController;
import com.caved_in.commons.menu.HelpScreen;
import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.TeamType;
import com.caved_in.teamdeathmatch.config.spawns.TeamSpawnLocation;
import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.menus.help.HelpMenus;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class AdminCommands {
	@CommandController.CommandHandler(name = "forcemap",
			description = "Force change a map",
			permission = "gungame.admin",
			usage = "/forcemap <Map>"
	)
	public void onForceMapCommand(CommandSender sender, String[] args) {
		//Check if we've got any arguments included
		if (args.length > 0) {
			//Get the name of the map they included as an argument
			String mapSelection = args[0];
			//Check if the argument isn't a map name, and rather list
			if (mapSelection.equalsIgnoreCase("list")) {
				//Get the help menu which lists all available maps
				HelpScreen forcemapHelp = HelpMenus.getAdminCommandsHelp();
				//Check if they included a page number aswell!
				if (args.length == 1) {
					//They didn't include a page number, so send them the first page
					forcemapHelp.sendTo(sender, 1, 7);
				} else {
					//They included a second argument, so get it
					String pageGet = args[1];
					//Check if the second argument is a number
					if (StringUtils.isNumeric(pageGet)) {
						//Its a number! Send them to the page they want
						forcemapHelp.sendTo(sender, Integer.parseInt(pageGet), 7);
					}
				}
				//They didn't want a list of the maps
			} else {
				//Now we get the list of all available maps
				List<String> worldList = TDMGame.worldList.getContentsAsList();
				//Check if the world list contains the map they requested
				if (worldList.contains(mapSelection)) {
					//Send the player a message saying we forced a map change
					PlayerHandler.sendMessage(sender, String.format("&aMap forced to &7%s", mapSelection));
					//Actually change the map
					TDMGame.gameMap = mapSelection;
				} else {
					//The map they requested doesn't exist; Send them the command to see all the maps
					PlayerHandler.sendMessage(sender, "&eDo &a/forcemap list&e to see a list of available maps");
				}
			}
		}
	}

	@CommandController.CommandHandler(name = "spawns", permission = "gungame.spawn")
	public void onSpawnCommand(Player player, String[] args) {
		if (args.length > 0) {
			PlayerHandler.sendMessage(player, String.format("&e%s&a has &e%s&a spawns in world &7%s", args[0].equalsIgnoreCase(TeamType.TERRORIST.toString()) ? "terrorist" : "counter terrorist", TDMGame.configuration.getSpawnConfiguration().getWorldSpawns(player.getWorld().getName()).getSpawnLocations(TeamType.getTeamByInitials(args[0])).size(), player.getWorld().getName()));
		} else {
			PlayerHandler.sendMessage(player, Messages.INVALID_COMMAND_USAGE("Team"));
		}
	}

	@CommandController.CommandHandler(name = "forcewin",
			description = "Forcewin the game to rotate players",
			permission = "gungame.admin",
			usage = "/forcewin [Team]"
	)
	public void onForceWinCommand(CommandSender sender, String[] args) {
		//Check for command arguments
		if (args.length > 0) {
			//Get the argument passed; Our team argument
			String winningTeam = args[0];
			//Switch on the argument passed
			switch (winningTeam.toLowerCase()) {
				case "ct":
				case "t":
					//The winning team is either terrorist or counterterrorist; Add 50 to their score
					FakeboardHandler.getTeam(winningTeam).addTeamScore(50);
					break;
				default:
					//They didn't enter a valid team name, so send a list of available ones
					PlayerHandler.sendMessage(sender, "&cThe available teams are &eT&c and &eCT");
			}
		} else {
			//Send them the invalid command message
			PlayerHandler.sendMessage(sender, Messages.INVALID_COMMAND_USAGE("team (&7CT&8/&7T&e)"));
		}
	}

	@CommandController.CommandHandler(name = "setteamspawn", description = "Used to add spawn locations for teams", permission = "gungame.admin",
			usage = "/setteamspawn [team]")
	public void SetTeamSpawn(Player sender, String[] args) {
		if (args.length > 0) {
			String teamName = args[0];
			//Get the world spawns for the player issueing the command
			String playerWorldName = sender.getWorld().getName();
			switch (teamName.toLowerCase()) {
				case "t":
				case "ct":
					TDMGame.configuration.getSpawnConfiguration().addSpawn(new TeamSpawnLocation(sender.getLocation(), TeamType.getTeamByInitials(teamName)));
					PlayerHandler.sendMessage(sender, "&aSpawn point for &e" + teamName + "&a has been added for the world &e" + playerWorldName);
					break;
				default:
					PlayerHandler.sendMessage(sender, "&cThe available teams are &eT&c and &eCT");
					break;
			}
		} else {
			PlayerHandler.sendMessage(sender, Messages.INVALID_COMMAND_USAGE("team"));
		}
	}

	@CommandController.CommandHandler(name = "gungame", description = "The parent command of (almost) all admin-related commands",
			permission = "gungame.admin", usage = "/gungame")
	public void TDMAdmin(CommandSender Sender, String[] Args) {
		Sender.sendMessage(ChatColor.YELLOW + "For help with the /gungame command do '/gungame help'");
	}

	@CommandController.SubCommandHandler(parent = "gungame", name = "help", permission = "gungame.admin")
	public void TotalWarHelpCommand(CommandSender sender, String[] args) {
		HelpScreen HelpScreen = new HelpScreen("GunGame Admin Menu");
		HelpScreen.setHeader(ChatColor.BLUE + "<name> Page <page> of <maxpage>");
		HelpScreen.setFormat("<name> --> <desc>");
		HelpScreen.setFlipColor(ChatColor.GREEN, ChatColor.DARK_GREEN);

		HelpScreen.setEntry("/gungame help", "Shows this menu of commands");
		HelpScreen.setEntry("/gungame reload", "Reloads all the config related to gungame (Shop-data, guns, and messages)");
		HelpScreen.setEntry("/setteamspean [T/CT]", "Adds a spawnpoint for the specified team");
		HelpScreen.setEntry("/forcewin [T/CT]", "Force a team to win so the round will re-start");
		HelpScreen.setEntry("/forcemap <Map>", "Forces a map change to the given map");
		HelpScreen.setEntry("/forcemap list", "List all available maps");
		int Page = 1;
		if (args.length > 1 && StringUtils.isNumeric(args[1])) {
			Page = Integer.parseInt(args[1]);
		}
		HelpScreen.sendTo(sender, Page, 6);
	}

	@CommandController.SubCommandHandler(parent = "gungame", name = "reload", permission = "gungame.admin")
	public void TDMReload(CommandSender sender, String[] args) {
		TDMGame.gunHandler.initData();
		TDMGame.reloadMessages();
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "shot config reload");
		sender.sendMessage(ChatColor.GREEN + "[Tunnels] GunData and ShopData reloaded");
	}
}
