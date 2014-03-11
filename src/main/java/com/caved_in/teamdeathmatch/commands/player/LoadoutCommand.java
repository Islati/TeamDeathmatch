package com.caved_in.teamdeathmatch.commands.player;

import com.caved_in.commons.commands.CommandController;
import com.caved_in.teamdeathmatch.gamehandler.GameSetupHandler;
import com.caved_in.teamdeathmatch.menus.loadoutselector.LoadoutCreationMenu;
import org.bukkit.entity.Player;

/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */
public class LoadoutCommand {
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
