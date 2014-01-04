package com.caved_in.commons.commands.Admin;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.TunnelsPermissions;
import com.caved_in.commons.handlers.Player.PlayerHandler;
import com.caved_in.commons.commands.CommandController.CommandHandler;
import com.caved_in.commons.handlers.Player.PlayerWrapper;
import com.caved_in.commons.handlers.Utilities.StringUtil;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommands
{
	@CommandHandler
	(
			name = "buyinfo",
			usage = "/buyinfo <player>",
			permission = "tunnels.common.buyinfo"
	)
	public void playerBuyInfoCommand(Player Player, String[] Args)
	{
		if (Args.length > 0)
		{
			if (Args[0] != null)
			{
				String PlayerName = Args[0];
				Player.chat("/buycraft payments " + PlayerName);
			}
		}
	}

	@CommandHandler(name = "buypremium", description = "Used by the console to give users premium for a set amount of time",permission = "tunnels.common.buypremium")
	public void buyPlayerPremium(CommandSender sender, String[] args)
	{
		if (args.length > 0)
		{
			String playerName = args[0];
			if (!Commons.playerDatabase.updatePlayerPremium(playerName,true))
			{
				sender.sendMessage(StringUtil.formatColorCodes("&eThe user &c" + playerName + " &edoesn't exist in the player database, please assure you've spelt the name properly."));
			}
			else
			{
				sender.sendMessage(StringUtil.formatColorCodes("&aSuccessfully promoted &e" + playerName + " &ato premium status!"));
			}
		}
		else
		{
			sender.sendMessage(StringUtil.formatColorCodes("&cThe proper usage is &e/buypremium <Name>"));
		}
	}

	@CommandHandler(name = "removepremium", description = "Used by the console to remove premium from players",permission = "tunnels.common.removepremium")
	public void removePlayerPremium(CommandSender sender,String[] args)
	{
		if (args.length > 0)
		{
			String playerName = args[0];
			if (!Commons.playerDatabase.updatePlayerPremium(playerName,false))
			{
				sender.sendMessage(StringUtil.formatColorCodes("&eThe user &c" + playerName + " &edoesn't exist in the player database, please assure you've spelt the name properly."));
			}
			else
			{
				sender.sendMessage(StringUtil.formatColorCodes("&aSuccessfully removed premium from &e" + playerName));
			}
		}
		else
		{
			sender.sendMessage(StringUtil.formatColorCodes("&cThe proper usage is &e/removepremium <Name>"));
		}
	}

	@CommandHandler(name = "silence", permission = "tunnels.common.silence")
	public void silenceLobbyCommand(CommandSender sender, String[] commandArgs)
	{
		Commons.getConfiguration().getWorldConfig().setChatSilenced(true);
		sender.sendMessage(ChatColor.YELLOW + "The chat has been silenced!");
		PlayerHandler.sendMessageToAllPlayers(ChatColor.RED + "Chat has been silenced, you may only speak if you are the required permissions");
	}

	@CommandHandler(name = "unsilence", permission = "tunnels.common.silence")
	public void unsilenceLobbyCommand(CommandSender sender, String[] commandArgs)
	{
		Commons.getConfiguration().getWorldConfig().setChatSilenced(false);
		sender.sendMessage(ChatColor.YELLOW + "The chat has been unsilenced.");
		PlayerHandler.sendMessageToAllPlayers(ChatColor.RED + "Chat has been unsilenced, you may now speak");
	}

	@CommandHandler(name = "maintenance", usage = "maintainance [on/off/toggle]", permission = "tunnels.common.maintenance")
	public void maintainanceToggleCommand(CommandSender commandSender, String[] commandArgs)
	{
		if (commandArgs.length > 0)
		{
			if (commandArgs[0] != null && !commandArgs[0].isEmpty())
			{
				String maintainanceHandle = commandArgs[0];
				switch (maintainanceHandle.toLowerCase())
				{
				case "on":
					Commons.getConfiguration().getMaintenanceConfig().setMaintenanceMode(true);
					PlayerHandler.kickAllPlayersWithoutPermission(Commons.getConfiguration().getMaintenanceConfig().getKickMessage(), TunnelsPermissions.MAINTAINANCE_WHITELIST);
					commandSender.sendMessage(StringUtil.formatColorCodes("&aMaintenance mode is now &eenabled&a, to disable it do &e/maintenance off&a or &e/Maintenance toggle"));
					break;
				case "off":
					Commons.getConfiguration().getMaintenanceConfig().setMaintenanceMode(false);
					commandSender.sendMessage(StringUtil.formatColorCodes("&aMaintenancemode is now &edisabled&a, to enable it do &e/maintenance on&a or &e/Maintenance toggle"));
					break;
				case "toggle":
					Commons.getConfiguration().getMaintenanceConfig().toggleMaintenance();
					if (Commons.getConfiguration().getMaintenanceConfig().isMaintenanceMode())
					{
						PlayerHandler.kickAllPlayersWithoutPermission(Commons.getConfiguration().getMaintenanceConfig().getKickMessage(), TunnelsPermissions.MAINTAINANCE_WHITELIST);
						commandSender.sendMessage(StringUtil.formatColorCodes("&aMaintenance mode is now &eenabled&a, to disable it do &e/Maintenance off&a or &e/Maintenance toggle"));
					}
					else
					{
						commandSender.sendMessage(StringUtil.formatColorCodes("&aMaintenance mode is now &edisabled&a, to enable it do &e/Maintenance on&a or &e/Maintenance toggle"));
					}
					break;
				default:
					commandSender.sendMessage(StringUtil.formatColorCodes("&eProper Usage: &a/Maintenance [on/off/toggle]"));
					break;
				}
			}
		}
		else
		{
			commandSender.sendMessage(StringUtil.formatColorCodes("&aMaintenance mode is currently " + (Commons.getConfiguration().getMaintenanceConfig().isMaintenanceMode() ? "&eEnabled " : "&eDisabled ") + " to change this, do &e/Maintenance toggle"));
		}
	}

	/*
	@CommandHandler(name = "sc", permission = "tunnels.common.staffchat")
	public void staffChatMessage(Player Sender, String[] Args)
	{
		String senderName = Sender.getName();
		if (Args.length > 1)
		{
			String staffMessage = "";
			for (String argument : Args)
			{
				staffMessage += (staffMessage.isEmpty() ? argument : " " + argument);
			}
			//PluginMessage.sendStaffChatMessage(Sender, staffMessage);
		}
		else
		{
			PlayerWrapper playerData = PlayerHandler.getData(senderName);
			playerData.setInStaffChat(!playerData.isInStaffChat());
			Sender.sendMessage(ChatColor.YELLOW + "You are " + (playerData.isInStaffChat() ? "now in Staff Chat; only you and other staff members will be able to see these messages." : "no longer in Staff Chat and have re-joined Public Chat."));
		}
	}
	
	@CommandHandler(name = "setplayerrank", permission = "tunnels.common.playerrank")
	public void promotePlayer(CommandSender sender, String[] Args)
	{
		if (Args.length >= 2)
		{
			if (Args[0] != null && Args[1] != null)
			{
				String playerName = Args[0];
				if (Commons.playerDatabase.hasData(playerName))
				{
					String rankName = Args[1];
					Rank playerRank = Rank.getRankFromName(rankName);
					if (playerRank != null)
					{
						Rank previousRank = Commons.playerDatabase.getRank(playerName);
						Commons.playerDatabase.setRank(playerName, playerRank);
						if (PlayerHandler.isOnline(playerName))
						{
							PlayerHandler.getPlayer(playerName).sendMessage(ChatColor.GREEN + "Your rank has been changed from " + previousRank.getColoured() + previousRank.getName() + ChatColor.RESET + " to " + playerRank.getColoured() + playerRank.getName() + ChatColor.GREEN  + "; please relog for it to take effect.");
							PlayerHandler.getData(playerName).setRank(playerRank);
						}
						sender.sendMessage(ChatColor.GREEN + playerName + "'s rank has been changed from " + previousRank.getColoured() + previousRank.getName() + ChatColor.GREEN + " to " + playerRank.getColoured() + playerRank.getName());
					}
					else
					{
						sender.sendMessage(StringUtil.formatColorCodes("&c" + rankName + "&eisn't a valid rank"));
						String validRanks = "";
						for(Rank rank : Rank.values())
						{
							validRanks += rank.getColoured() + rank.getName() + ChatColor.RESET + " ";
						}
						sender.sendMessage(ChatColor.YELLOW + "The valid ranks are " + validRanks);
					}
				}
				else
				{
					sender.sendMessage(StringUtil.formatColorCodes("&e" + playerName + "&c has never played on this server before; Is the name correct?"));
				}
			}
		}
	}
	*/
}
