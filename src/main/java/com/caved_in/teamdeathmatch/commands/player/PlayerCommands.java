package com.caved_in.teamdeathmatch.commands.player;

import com.caved_in.commons.handlers.Items.ItemHandler;
import com.caved_in.commons.handlers.Player.PlayerHandler;
import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.commands.CommandController.CommandHandler;
import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.Team;
import com.caved_in.teamdeathmatch.menus.loadoutselector.LoadoutCreationMenu;
import com.caved_in.teamdeathmatch.menus.loadoutselector.LoadoutSelectionMenu;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerCommands {
	@CommandHandler(name = "afk", description = "Set yourself as 'afk' which stops all damage, and turns you invisible", usage = "/afk")
	public void AFKCommand(Player Sender, String[] Args) {
		if (TDMGame.gameInProgress) {
			Player Player = (Player) Sender;
			if (!TDMGame.afkCooldown.isOnCooldown(Player.getName())) {
				if (FakeboardHandler.getPlayer(Player) != null) {
					FakeboardHandler.getPlayer(Player).setAfk(!FakeboardHandler.getPlayer(Player).isAfk());
					TDMGame.afkCooldown.setOnCooldown(Player.getName());
				}
			} else {
				Player.sendMessage(ChatColor.GRAY + "You've used this command in the past 10 seconds, please don't abuse it Q_Q Wait and try again..");
			}
		}
	}

	@CommandHandler(name = "kit", description = "Select & Set your active loadout", usage = "/kit")
	public void KITCommand(Player Sender, String[] Args) {
		if (TDMGame.gameInProgress) {
			try {
				Player Player = (Player) Sender;
				Team pTeam = FakeboardHandler.getTeamByPlayer(Player);
				PlayerHandler.clearInventory(Player);
				if (pTeam.getName().equalsIgnoreCase("CT")) {
					Player.getInventory().setArmorContents(new ItemStack[]{ItemHandler.makeLeatherItemStack(Material.LEATHER_HELMET, Color.RED),
							ItemHandler.makeLeatherItemStack(Material.LEATHER_CHESTPLATE, Color.RED), ItemHandler.makeLeatherItemStack(Material
							.LEATHER_LEGGINGS, Color.RED), ItemHandler.makeLeatherItemStack(Material.LEATHER_BOOTS, Color.RED)});
				} else {
					Player.getInventory().setArmorContents(new ItemStack[]{ItemHandler.makeLeatherItemStack(Material.LEATHER_HELMET, Color.BLUE),
							ItemHandler.makeLeatherItemStack(Material.LEATHER_CHESTPLATE, Color.BLUE), ItemHandler.makeLeatherItemStack(Material
							.LEATHER_LEGGINGS, Color.BLUE), ItemHandler.makeLeatherItemStack(Material.LEATHER_BOOTS, Color.BLUE)});
				}
				Player.sendMessage(ChatColor.GREEN + "To edit your loadouts, use /loadout");
				new LoadoutSelectionMenu(Player.getName());
			} catch (Exception Ex) {
				Ex.printStackTrace();
				Sender.kickPlayer(ChatColor.YELLOW + "Please Re-Log; There was an error loading your data.");
			}
		} else {
			Sender.sendMessage(ChatColor.GREEN + "[Tunnels] " + ChatColor.YELLOW + "The round must begin before you can select a class; You can edit your " +
					"classes however, by using " + ChatColor.GRAY + "/loadout");
		}
	}

	@CommandHandler(name = "xp", description = "View your current amount of XP", usage = "/xp")
	public void XPCommand(Player Sender, String[] Args) {
		Sender.chat("/money");
	}

	@CommandHandler(name = "loadout", description = "Create & Modify your loadouts", usage = "/loadout")
	public void LoadoutCommand(Player Sender, String[] Args) {
		if (TDMGame.gameInProgress) {
			new LoadoutCreationMenu(Sender);
			Sender.chat("/afk");
		} else {
			new LoadoutCreationMenu(Sender);
		}
	}

	@CommandHandler(name = "vote", description = "Get info on how to vote, and the rewards", usage = "/vote")
	public void VoteCommand(CommandSender Sender, String[] Args) {
		Sender.sendMessage(ChatColor.GREEN + "To vote, visit http://caved.in/vote.php");
		Sender.sendMessage(ChatColor.GREEN + "On the website there's links which correspond to sites you can vote on");
		Sender.sendMessage(ChatColor.GREEN + "You can vote every 24 hours; And each vote gets you 250 XP; Thanks for voting!");
	}
}
