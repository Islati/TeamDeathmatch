package com.caved_in.teamdeathmatch.commands.player;

import com.caved_in.commons.commands.CommandController;
import com.caved_in.teamdeathmatch.gamehandler.GameSetupHandler;
import com.caved_in.teamdeathmatch.menus.loadoutselector.LoadoutCreationMenu;
import org.bukkit.entity.Player;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 09/03/14
 * Time: 12:40 PM
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
