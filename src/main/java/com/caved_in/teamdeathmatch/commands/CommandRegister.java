package com.caved_in.teamdeathmatch.commands;

import com.caved_in.commons.commands.CommandController;
import com.caved_in.teamdeathmatch.Game;
import com.caved_in.teamdeathmatch.commands.admin.AdminCommands;
import com.caved_in.teamdeathmatch.commands.player.PlayerCommands;

public class CommandRegister {
	public CommandRegister(Game Plugin) {
		CommandController.registerCommands(Plugin, new PlayerCommands());
		CommandController.registerCommands(Plugin, new AdminCommands());
	}
}
