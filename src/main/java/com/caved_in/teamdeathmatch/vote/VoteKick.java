package com.caved_in.teamdeathmatch.vote;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.teamdeathmatch.GameMessages;

public class VoteKick extends Vote {

	private String playerToKick;
	private String kickReason;

	public VoteKick(String voteCaster, String... args) {
		super(voteCaster, args);
		addYes();
		playerToKick = args[0];
		kickReason = args[1];
	}

	public String getKickReason() {
		return this.kickReason;
	}

	public String getPlayerToKick() {
		return this.playerToKick;
	}

	@Override
	public void execute() {
		Commons.threadManager.runTaskOneTickLater(new Runnable() {
			@Override
			public void run() {
				PlayerHandler.kickPlayer(PlayerHandler.getPlayer(playerToKick),kickReason);
				PlayerHandler.sendMessageToAllPlayers(Messages.PLAYER_KICKED(playerToKick,kickReason));
			}
		});
	}

	@Override
	public void announce() {
		String voteKick = GameMessages.ANNOUNCE_VOTE_PLAYER_KICK(getCaster(),playerToKick,kickReason);
		PlayerHandler.sendMessageToAllPlayers(voteKick);
	}
}
