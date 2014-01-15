package com.caved_in.teamdeathmatch.chatcommands;

import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.commons.time.Cooldown;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatManager {
	private static VoteKick activeVoteKick = null;
	private static Cooldown voteCastCooldown = new Cooldown(300);

	public enum VoteCastType {
		MapChange,
		KickPlayer
	}

	public enum VoteType {
		YES, NO
	}

	public static boolean isActiveVoteKick() {
		return (activeVoteKick != null);
	}

	public static VoteKick getActiveVoteKick() {
		if (isActiveVoteKick()) {
			return activeVoteKick;
		}
		return null;
	}

	public static void addVote(Player votingPlayer, VoteType voteType) {
		if (isActiveVoteKick()) {
			if (!activeVoteKick.hasVoted(votingPlayer.getName())) {
				switch (voteType) {
					case YES:
						activeVoteKick.addYes();
						break;
					case NO:
						activeVoteKick.addNo();
						break;
					default:
						break;
				}
			}
		}
	}

	public static VoteType getVoteType(String text) {
		if (startsWith(text, "!yes")) {
			return VoteType.YES;
		} else if (startsWith(text, "!no")) {
			return VoteType.NO;
		}
		return null;
	}

	public static boolean startsWith(String Input, String Check) {
		return (Input.toLowerCase().startsWith(Check.toLowerCase()));
	}

	public static void handleActiveVoteKick() {
		if (isActiveVoteKick()) {
			String kickPlayerName = activeVoteKick.getPlayerToKick();
			if (activeVoteKick.doAction()) {
				Player playerToKick = Bukkit.getPlayer(kickPlayerName);
				PlayerHandler.sendMessageToAllPlayers("&bThe vote to kick &e" + playerToKick.getName() + " &bhas &apassed.");
				if (playerToKick != null) {
					playerToKick.kickPlayer(StringUtil.formatColorCodes("You were kicked via Player Vote for: '&e" + activeVoteKick.getKickReason() + "&r' " +
							"--" +
							" " +
							"If you feel that the vote to kick was abused, please report it on our forums @ www.caved.in"));
				}
				activeVoteKick = null;
			} else {
				PlayerHandler.sendMessageToAllPlayers("&bThe vote to kick &e" + kickPlayerName + " &bhas &cfailed.");
			}
		}
	}

	public static boolean canCastVote(String playerName) {
		return (!voteCastCooldown.isOnCooldown(playerName));
	}

	public static boolean hasVoted(String playerName) {
		if (isActiveVoteKick()) {
			return activeVoteKick.hasVoted(playerName);
		}
		return true;
	}

	public static void newVoteKick(Player playerKicking, Player playerToKick, String kickReason) {
		activeVoteKick = new VoteKick(playerKicking.getName(), playerToKick.getName(), kickReason);
	}

}
