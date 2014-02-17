package com.caved_in.teamdeathmatch.commands.player;

import com.caved_in.commons.commands.CommandController;
import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.fPlayer;
import com.caved_in.teamdeathmatch.gamehandler.GameSetupHandler;
import com.caved_in.teamdeathmatch.menus.loadoutselector.LoadoutCreationMenu;
import com.caved_in.teamdeathmatch.menus.loadoutselector.LoadoutSelectionMenu;
import org.bukkit.entity.Player;

import static com.caved_in.teamdeathmatch.TdmMessages.*;


public class PlayerCommands {
	@CommandController.CommandHandler(name = "afk", description = "Set yourself as 'afk' which stops all damage, and turns you invisible", usage = "/afk")
	public void onAfkCommand(Player player, String[] args) {
		if (GameSetupHandler.isGameInProgress()) {
			String playerName = player.getName();
			if (!TDMGame.afkCooldown.isOnCooldown(playerName)) {
				fPlayer fPlayer = FakeboardHandler.getPlayer(playerName);
				if (fPlayer != null) {
					fPlayer.setAfk(!fPlayer.isAfk());
					TDMGame.afkCooldown.setOnCooldown(playerName);
				}
			} else {
				PlayerHandler.sendMessage(player,AFK_COMMAND_ON_COOLDOWN);
			}
		}
	}

	@CommandController.CommandHandler(name = "kit", description = "Select & Set your active loadout", usage = "/kit")
	public void onKitCommand(Player player, String[] args) {
		if (GameSetupHandler.isGameInProgress()) {
			try {
				PlayerHandler.sendMessage(player, LOADOUT_EDIT_INSTRUCTION);
				new LoadoutSelectionMenu(player);
			} catch (Exception ex) {
				ex.printStackTrace();
				PlayerHandler.kickPlayer(player, PLAYER_DATA_LOAD_ERROR);
			}
		} else {
			PlayerHandler.sendMessage(player,GAME_MUST_BEGIN_LOADOUT_SELECTION);
		}
	}


	@CommandController.CommandHandler(name = "loadout", description = "Create & Modify your loadouts", usage = "/loadout")
	public void onLoadoutCommand(Player player, String[] args) {
		if (GameSetupHandler.isGameInProgress()) {
			new LoadoutCreationMenu(player);
			player.chat("/afk");
		} else {
			new LoadoutCreationMenu(player);
		}
	}
}
