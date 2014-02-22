package com.caved_in.teamdeathmatch.chatcommands;

import java.util.HashSet;
import java.util.Set;

public class VoteMap {
	private String voteCaster = "";
	private String votingMap = "";

	private int yesVotes = 0;
	private int noVotes = 0;

	private Set<String> alreadyVoted = new HashSet<>();

	public VoteMap(String voteCaster, String votingMap) {
		this.voteCaster = voteCaster;
		this.votingMap = votingMap;
	}

	public boolean hasVoted(String playerName) {
		return alreadyVoted.contains(playerName);
	}

	public void addYes() {
		yesVotes += 1;
	}

	public void addNo() {
		noVotes += 1;
	}

	public void setVoted(String playerName) {
		alreadyVoted.add(playerName);
	}

	public String getVotingMap() {
		return this.votingMap;
	}

	public String getVoteCaster() {
		return this.voteCaster;
	}

	public boolean doAction() {
		return yesVotes != noVotes && (yesVotes > noVotes);
	}
}
