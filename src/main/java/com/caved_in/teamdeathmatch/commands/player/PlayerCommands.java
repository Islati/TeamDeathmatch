package com.caved_in.teamdeathmatch.commands.player;


import com.caved_in.commons.commands.CommandController;
import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.Team;
import com.caved_in.teamdeathmatch.gamehandler.GameSetupHandler;
import com.caved_in.teamdeathmatch.menus.loadoutselector.LoadoutCreationMenu;
import com.caved_in.teamdeathmatch.menus.loadoutselector.LoadoutSelectionMenu;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerCommands {
	@CommandController.CommandHandler(name = "afk", description = "Set yourself as 'afk' which stops all damage, and turns you invisible", usage = "/afk")
	public void AFKCommand(Player Sender, String[] Args) {
		if (GameSetupHandler.isGameInProgress()) {
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

	@CommandController.CommandHandler(name = "kit", description = "Select & Set your active loadout", usage = "/kit")
	public void KITCommand(Player player, String[] Args) {
		if (GameSetupHandler.isGameInProgress()) {
			try {
				Team pTeam = FakeboardHandler.getTeamByPlayer(player);
				PlayerHandler.clearInventory(player);
				if (pTeam.getName().equalsIgnoreCase("CT")) {
					player.getInventory().setArmorContents(GameSetupHandler.getRedTeamArmor());
				} else {
					player.getInventory().setArmorContents(GameSetupHandler.getBlueTeamArmor());
				}
				player.sendMessage(ChatColor.GREEN + "To edit your loadouts, use /loadout");
				new LoadoutSelectionMenu(player.getName());
			} catch (Exception Ex) {
				Ex.printStackTrace();
				player.kickPlayer(ChatColor.YELLOW + "Please Re-Log; There was an error loading your data.");
			}
		} else {
			player.sendMessage(ChatColor.GREEN + "[Tunnels] " + ChatColor.YELLOW + "The round must begin before you can select a class; You can edit your " +
					"classes however, by using " + ChatColor.GRAY + "/loadout");
		}
	}

	@CommandController.CommandHandler(name = "xp", description = "View your current amount of XP", usage = "/xp")
	public void XPCommand(Player Sender, String[] Args) {
		Sender.chat("/money");
	}

	@CommandController.CommandHandler(name = "loadout", description = "Create & Modify your loadouts", usage = "/loadout")
	public void LoadoutCommand(Player Sender, String[] Args) {
		if (GameSetupHandler.isGameInProgress()) {
			new LoadoutCreationMenu(Sender);
			Sender.chat("/afk");
		} else {
			new LoadoutCreationMenu(Sender);
		}
	}

	@CommandController.CommandHandler(name = "vote", description = "Get info on how to vote, and the rewards", usage = "/vote")
	public void VoteCommand(CommandSender Sender, String[] Args) {
		Sender.sendMessage(ChatColor.GREEN + "To vote, visit http://caved.in/vote.php");
		Sender.sendMessage(ChatColor.GREEN + "On the website there's links which correspond to sites you can vote on");
		Sender.sendMessage(ChatColor.GREEN + "You can vote every 24 hours; And each vote gets you 250 XP; Thanks for voting!");
	}
}
