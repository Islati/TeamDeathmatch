package com.caved_in.teamdeathmatch;

import com.caved_in.teamdeathmatch.vote.Vote;

public class GameMessages {
	private static final String PREFIX = "[Tunnels] ";
	public static final String LOADOUT_EDIT_INSTRUCTION = "&aTo edit your loadouts, use &e/loadout";
	public static final String PLAYER_DATA_LOAD_ERROR = "&ePlease Relog; There was an error loading your data &r:&b'&r(";
	public static final String GAME_MUST_BEGIN_LOADOUT_SELECTION = "&eThe round must begin before you can select a class; You can edit your classes however, by using &a/loadout";
	public static final String AFK_COMMAND_ON_COOLDOWN = "&7You can only use this command once every &o10&r&7 seconds; please wait and try again soon.";
	public static final String VOTE_ALREADY_CASTED = "&cYou've already casted your vote";

	public static String MAP_CHANGED(String to) {
		return String.format("&7The map has been changed to &l%s", to);
	}

	public static String ANNOUNCE_VOTE_MAP_CHANGE(String voteCaster, String mapName) {
		return String.format("&e%s&a has voted that the map be switched to &e%s&a. If you want to switch the map to &e%s&a, type &e!yes&a in chat, otherwise type &e!no", voteCaster, mapName, mapName);
	}

	public static String ANNOUNCE_VOTE_PLAYER_KICK(String voteCaster, String playerName, String reason) {
		return String.format("&e%s&a wants to kick &e%s&a for '&e%s&a'; To kick &e%s&a type &6!yes&a in chat, otherwise type &6!no", voteCaster, playerName, reason, playerName);
	}

	public static String VOTE_FAILED(int yesVoteCount, int noVoteCount) {
		return String.format("&eThe vote has &cfailed&e with &a%s&e yes' and &c%s&e no's", yesVoteCount, noVoteCount);
	}

	public static String VOTE_FAILED(Vote vote) {
		return VOTE_FAILED(vote.getYesVotes(), vote.getNoVotes());
	}

	public static String INSUFFICIENT_CHAT_COMMAND_ARGUMENTS(String command, int argsRequired) {
		return String.format("&eTo use &a!%s&e you need &a%s&e arguments", command, argsRequired);
	}

	public static String INVALID_CHAT_COMMAND(String str) {
		return String.format("&c%s&e is not a valid chat command, please try again.",str);
	}
}
