package com.caved_in.teamdeathmatch.commands;

import com.caved_in.commons.commands.CommandController;
import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.commands.admin.AdminCommands;
import com.caved_in.teamdeathmatch.commands.player.PlayerCommands;

public class CommandRegister {
	public CommandRegister(TDMGame Plugin) {
		CommandController.registerCommands(Plugin, new PlayerCommands());
		CommandController.registerCommands(Plugin, new AdminCommands());
//		CommandController.registerCommands(Plugin, new Utility());
	}
}
