package com.caved_in.teamdeathmatch.commands.player;

import com.caved_in.commons.Messages;
import com.caved_in.commons.commands.CommandController;
import com.caved_in.commons.commands.HelpMenus;
import com.caved_in.commons.menu.HelpScreen;
import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.teamdeathmatch.Game;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MapsCommand {
	private static HelpScreen mapsMenu = null;

	private static HelpScreen getMapsMenu() {
		if (mapsMenu == null) {
			HelpScreen helpScreen = HelpMenus.generateHelpScreen("Maps List", HelpMenus.PageDisplay.DEFAULT, HelpMenus.ItemFormat.NO_DESCRIPTION, ChatColor.GREEN, ChatColor.DARK_GREEN);
			for(String worldName : Game.worldList.getContentsAsList()) {
				helpScreen.setEntry(worldName,"");
			}
			mapsMenu = helpScreen;
		}
		return mapsMenu;
	}

	@CommandController.CommandHandler(name = "maps", description = "View a list of all the available maps", usage = "/maps")
	public void onMapsCommand(Player player, String[] args) {
		int page = 1;
		if (args.length > 0) {
			String pageArg = args[0];
			if (StringUtils.isNumeric(pageArg)) {
				page = Integer.parseInt(pageArg);
			} else {
				PlayerHandler.sendMessage(player, Messages.INVALID_COMMAND_USAGE("page number"));
			}
		}
		getMapsMenu().sendTo(player,page,6);
	}
}
