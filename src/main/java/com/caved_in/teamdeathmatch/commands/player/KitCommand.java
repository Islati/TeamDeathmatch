package com.caved_in.teamdeathmatch.commands.player;

import com.caved_in.commons.commands.CommandController;
import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.teamdeathmatch.gamehandler.GameSetupHandler;
import com.caved_in.teamdeathmatch.menus.loadoutselector.LoadoutSelectionMenu;
import org.bukkit.entity.Player;

import static com.caved_in.teamdeathmatch.GameMessages.GAME_MUST_BEGIN_LOADOUT_SELECTION;
import static com.caved_in.teamdeathmatch.GameMessages.LOADOUT_EDIT_INSTRUCTION;
import static com.caved_in.teamdeathmatch.GameMessages.PLAYER_DATA_LOAD_ERROR;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 09/03/14
 * Time: 12:39 PM
 */
public class KitCommand {

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
			PlayerHandler.sendMessage(player, GAME_MUST_BEGIN_LOADOUT_SELECTION);
		}
	}
}
