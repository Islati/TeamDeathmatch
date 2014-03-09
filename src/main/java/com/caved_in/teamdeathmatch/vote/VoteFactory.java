package com.caved_in.teamdeathmatch.vote;

import com.caved_in.commons.time.Cooldown;
import com.caved_in.teamdeathmatch.Game;
import com.caved_in.teamdeathmatch.runnables.ExecuteVoteRunnable;

public class VoteFactory {
	private static Vote activeVote = null;
	private static Cooldown voteCastCooldown = new Cooldown(300);
	private static final long VOTE_EXECUTE_DELAY = 600;
	public static boolean hasActiveVote() {
		return activeVote != null;
	}

	public static Vote getActiveVote() {
		return activeVote;
	}

	public static void setActiveVote(Vote vote) {
		activeVote = vote;
		activeVote.announce();
		Game.runnableManager.runTaskLater(new ExecuteVoteRunnable(), VOTE_EXECUTE_DELAY);
	}

	public static Vote createVote(ChatCommand chatCommand, String caster, String... args) {
		Vote vote = null;
		switch (chatCommand) {
			case VOTE_KICK_PLAYER:
				vote = new VoteKick(caster,args);
				break;
			case VOTE_MAP_CHANGE:
				vote = new VoteMap(caster, args);
				break;
			default:
				break;
		}
		return vote;
	}
}
