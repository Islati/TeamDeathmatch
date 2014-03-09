package com.caved_in.teamdeathmatch.runnables;

import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.teamdeathmatch.GameMessages;
import com.caved_in.teamdeathmatch.vote.Vote;
import com.caved_in.teamdeathmatch.vote.VoteFactory;

public class ExecuteVoteRunnable implements Runnable{
	@Override
	public void run() {
		Vote activeVote = VoteFactory.getActiveVote();
		if (activeVote.canExecute()) {
			activeVote.execute();
		} else {
			PlayerHandler.sendMessageToAllPlayers(GameMessages.VOTE_FAILED(activeVote));
		}
	}
}
