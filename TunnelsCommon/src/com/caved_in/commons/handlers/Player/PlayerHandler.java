package com.caved_in.commons.handlers.Player;

import java.util.HashMap;
import java.util.Map;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.Formatting.ColorCode;
import com.caved_in.commons.config.TunnelsPermissions;
import com.caved_in.commons.handlers.Utilities.StringUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class PlayerHandler
{
	private static Map<String, PlayerWrapper> playerData = new HashMap<String, PlayerWrapper>();

	public PlayerHandler()
	{
	}

	public static boolean hasData(String Name)
	{
		return playerData.containsKey(Name);
	}

	private static boolean hasData(Player player)
	{
		return playerData.containsKey(player.getName());
	}

	public static PlayerWrapper getData(String Name)
	{
		return playerData.get(Name);
	}

	private static PlayerWrapper getData(Player player)
	{
		return playerData.get(player.getName());
	}

	public static void addData(Player player)
	{
		String playerName = player.getName();

		PlayerWrapper PlayerWrapper = null;

		if (Commons.playerDatabase.hasData(playerName))
		{
			Commons.messageConsole("&a" + playerName + " has data, attempting to load it.");
			if (hasData(playerName))
			{
				playerData.remove(playerName);
				Commons.messageConsole("&aRemoved pre-cached data for " + playerName);
			}
		}
		else
		{
			Commons.messageConsole("&e" + playerName + " has no data, inserting defaults.");
			Commons.playerDatabase.insertDefaults(playerName);
			Commons.messageConsole("&aInserted defaults for " + playerName + ", and loaded the default values");
		}
		PlayerWrapper = Commons.playerDatabase.getPlayerWrapper(playerName);
		PlayerWrapper.setTagColor(getNameTagColor(player));
		Commons.messageConsole("&aLoaded data for " + playerName);
		playerData.put(playerName, PlayerWrapper);
	}

	public static void updateData(PlayerWrapper playerWrapper)
	{
		playerData.put(playerWrapper.getName(),playerWrapper);
		Commons.playerDatabase.updatePlayerPremium(playerWrapper);
	}

	public static void removeData(String playerName)
	{
		if (hasData(playerName))
		{
			Commons.messageConsole("&aPreparing to sync " + playerName + "'s data to database");
			Commons.playerDatabase.syncPlayerWrapperData(playerData.get(playerName));
			playerData.remove(playerName);
			Commons.messageConsole("&a" + playerName + "'s data has been synchronized");
		}
	}

	public static boolean isOnline(String playerName)
	{
		return Bukkit.getPlayerExact(playerName) != null;
	}

	public static boolean isOnlineFuzzy(String playerName)
	{
		return Bukkit.getPlayer(playerName) != null;
	}

	public static Player getPlayer(String playerName)
	{
		return Bukkit.getPlayer(playerName);
	}

	public static void kickAllPlayers(String kickReason)
	{
		for (Player Player : Bukkit.getOnlinePlayers())
		{
			Player.kickPlayer(StringUtil.formatColorCodes(kickReason));
		}
	}

	public static void kickAllPlayersWithoutPermission(String kickReason, String permission)
	{
		if (permission != null && !permission.isEmpty())
		{
			for (Player Player : Bukkit.getOnlinePlayers())
			{
				if (!Player.hasPermission(permission))
				{
					Player.kickPlayer(StringUtil.formatColorCodes(kickReason));
				}
			}
		}
		else
		{
			kickAllPlayers(kickReason);
		}
	}

	public static void sendMessageToAllPlayers(String message)
	{
		for (Player Player : Bukkit.getOnlinePlayers())
		{
			Player.sendMessage(StringUtil.formatColorCodes(message));
		}
	}

	public static void sendMessageToAllPlayersWithPermission(String message, String permission)
	{
		for (Player Player : Bukkit.getOnlinePlayers())
		{
			if (Player.hasPermission(permission))
			{
				Player.sendMessage(StringUtil.formatColorCodes(message));
			}
		}
	}

	public static void sendMessageToAllPlayersWithoutPermission(String message, String permission)
	{
		for (Player Player : Bukkit.getOnlinePlayers())
		{
			if (!Player.hasPermission(permission))
			{
				Player.sendMessage(StringUtil.formatColorCodes(message));
			}
		}
	}

	public static ChatColor getNameTagColor(Player Player)
	{
		if (!Player.isOp())
		{
			for (ColorCode colorCode : ColorCode.values())
			{
				if (Player.hasPermission(colorCode.getPermission()))
				{
					return colorCode.getColor();
				}
			}
		}
		return ChatColor.AQUA;
	}

	//Disable disguises-- They aren't needed

	/*
	public static void setDisguise(Player playerToDisguise, String toDisguiseAs)
	{
		if (Commons.getConfiguration().getWorldConfig().isNickNamesEnabled())
		{
			if (hasData(playerToDisguise))
			{
				if (!isOnline(toDisguiseAs))
				{
					PlayerWrapper playerWrapperDisguise = getData(playerToDisguise);
					playerToDisguise.setPlayerListName(toDisguiseAs);
					playerToDisguise.setDisplayName(toDisguiseAs + ChatColor.RESET);
					TagAPI.refreshPlayer(playerToDisguise);
					playerToDisguise.sendMessage(StringUtil.formatColorCodes("&aYou're now disguised as &e" + toDisguiseAs));
				}
				else
				{
					playerToDisguise.sendMessage(StringUtil.formatColorCodes("&cYou're trying to disguise yourself as &e" + toDisguiseAs + "&c but they're already online..."));
				}
			}
		}
		else
		{
			playerToDisguise.sendMessage(StringUtil.formatColorCodes("&eSorry but disguises are disabled on this server..."));
		}
	}

	public static void clearDisguise(Player playerToUnhide)
	{
		if (Commons.getConfiguration().getWorldConfig().isNickNamesEnabled())
		{
			if (hasData(playerToUnhide))
			{
				PlayerWrapper PlayerWrapper = getData(playerToUnhide);
				playerToUnhide.setPlayerListName(playerToUnhide.getName());
				playerToUnhide.setDisplayName(playerToUnhide.getName() + ChatColor.RESET);
				TagAPI.refreshPlayer(playerToUnhide);
				playerToUnhide.sendMessage(StringUtil.formatColorCodes("&aYou're no longer disguised!"));
			}
		}
	}
	*/

	public static boolean canChatWhileSilenced(Player player)
	{
		return (isPremium(player.getName()));
	}

	public static boolean isInStaffChat(String Player)
	{
		return (playerData.get(Player).isInStaffChat());
	}

	public static void setPlayerInStaffChat(String Player, boolean InChat)
	{
		playerData.get(Player).setInStaffChat(InChat);
	}

	public static void sendToAllStaff(String Player, String Message)
	{
		sendMessageToAllPlayersWithPermission(TunnelsPermissions.STAFF_PERMISSION, ChatColor.RED + "[Staff Chat] " + Player + ChatColor.RESET + ": " + Message);
	}

	public static boolean isPremium(String playerName)
	{
		return playerData.get(playerName).isPremium();
	}

	public static boolean isPremium(Player player)
	{
		return isPremium(player.getName());
	}

	public static void clearInventory(Player player)
	{
		player.getInventory().clear();
		player.getInventory().setArmorContents(new ItemStack[]{null, null, null, null});
	}

	public static void removePotionEffects(Player player)
	{
		for(PotionEffect effect : player.getActivePotionEffects())
		{
			player.removePotionEffect(effect.getType());
		}
	}
}
