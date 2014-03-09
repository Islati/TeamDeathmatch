package com.caved_in.teamdeathmatch.vote;

import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.teamdeathmatch.GameMessages;
import org.bukkit.entity.Player;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ChatCommand {
	VOTE_KICK_PLAYER("kick", 2) {
		@Override
		public void doCommand(Player player, String... args) {
			VoteFactory.setActiveVote(VoteFactory.createVote(VOTE_KICK_PLAYER, player.getName(), args));
		}
	},
	VOTE_MAP_CHANGE("map", 1) {
		@Override
		public void doCommand(Player player, String... args) {
			VoteFactory.setActiveVote(VoteFactory.createVote(VOTE_MAP_CHANGE, player.getName(),args));
		}
	},
	YES("yes", 0) {
		@Override
		public void doCommand(Player player, String... args) {
			Vote vote = VoteFactory.getActiveVote();
			String playerName = player.getName();
			if (!vote.hasVoted(playerName)) {
				vote.addYes();
			} else {
				PlayerHandler.sendMessage(player, GameMessages.VOTE_ALREADY_CASTED);
			}
		}
	},
	NO("no", 0) {
		@Override
		public void doCommand(Player player, String... args) {
			Vote vote = VoteFactory.getActiveVote();
			String playerName = player.getName();
			if (!vote.hasVoted(playerName)) {
				vote.addNo();
			} else {
				PlayerHandler.sendMessage(player, GameMessages.VOTE_ALREADY_CASTED);
			}
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
