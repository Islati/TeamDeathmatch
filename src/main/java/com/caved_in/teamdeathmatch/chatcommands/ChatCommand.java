package com.caved_in.teamdeathmatch.chatcommands;

import org.bukkit.entity.Player;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


/**
 * Created By: TheGamersCave (Brandon)
 * Date: 15/01/14
 * Time: 9:32 AM
 */
public enum ChatCommand {
	VOTE_KICK_PLAYER("kick", 2) {
		@Override
		public void doCommand(Player player, String... args) {

		}
	},
	VOTE_MAP_CHANGE("map", 1) {
		@Override
		public void doCommand(Player player, String... args) {

		}
	};

	/* This is the method that each command will call */
	public abstract void doCommand(Player player, String... args);

	private static Map<String, ChatCommand> chatCommands = new HashMap<>();

	static {
		//Instance all the valid commands
		for (ChatCommand chatCommand : EnumSet.allOf(ChatCommand.class)) {
			chatCommands.put(chatCommand.command, chatCommand);
		}
	}

	private String command;
	private int minArgs;

	ChatCommand(String command, int minArgs) {
		this.command = command;
		this.minArgs = minArgs;
	}

	public int getMinArgs() {
		return minArgs;
	}

	public static boolean isValidCommand(String command) {
		return chatCommands.containsKey(command.toLowerCase());
	}

	public static ChatCommand getCommand(String command) {
		return chatCommands.get(command.toLowerCase());
	}
}
