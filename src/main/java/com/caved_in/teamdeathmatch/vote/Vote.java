package com.caved_in.teamdeathmatch.vote;

import java.util.HashSet;
import java.util.Set;

public abstract class Vote implements IVote {

	private String voteCaster;

	private int yesVotes = 0;
	private int noVotes = 0;

	private Set<String> alreadyVoted = new HashSet<>();

	public Vote(String voteCaster,String[] args) {
		this.voteCaster = voteCaster;
	}

	@Override
	public boolean hasVoted(String playerName) {
		return alreadyVoted.contains(playerName);
	}

	@Override
	public void addYes() {
		yesVotes += 1;
	}

	@Override
	public void addNo() {
		noVotes += 1;
	}

	@Override
	public void addVote(ChatCommand chatCommand) {
		switch (chatCommand) {
			case YES:
				addYes();
				break;
			case NO:
				addNo();
				break;
			default:
				break;
		}
	}

	@Override
	public int getYesVotes() {
		return yesVotes;
	}

	@Override
	public int getNoVotes() {
		return noVotes;
	}

	@Override
	public void setVoted(String playerName) {
		alreadyVoted.add(playerName);
//		VoteFactory.setVoted(playerName); TODO remove the vote cooldown
	}

	@Override
	public String getCaster() {
		return voteCaster;
	}

	@Override
	public boolean canExecute() {
		return yesVotes != noVotes && (yesVotes > noVotes);
	}

	public abstract void execute();

	public abstract void announce();
}
