package com.caved_in.teamdeathmatch.chatcommands;

import java.util.ArrayList;
import java.util.List;

public class VoteMap {
	private String voteCaster = "";
	private String votingMap = "";

	private int yesVotes = 0;
	private int noVotes = 0;

	private List<String> alreadyVoted = new ArrayList<String>();

	public VoteMap(String voteCaster, String votingMap) {
		this.voteCaster = voteCaster;
		this.votingMap = votingMap;
	}

	public boolean hasVoted(String playerName) {
		return this.alreadyVoted.contains(playerName);
	}

	public void addYes() {
		this.yesVotes += 1;
	}

	public void addNo() {
		this.noVotes += 1;
	}

	public void setVoted(String playerName) {
		this.alreadyVoted.add(playerName);
	}

	public String getVotingMap() {
		return this.votingMap;
	}

	public String getVoteCaster() {
		return this.voteCaster;
	}

	public boolean doAction() {
		if (yesVotes == noVotes) {
			return false;
		} else {
			return (yesVotes > noVotes);
		}
	}
}
