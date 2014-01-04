package com.caved_in.teamdeathmatch.chatvote;

import java.util.ArrayList;

public class VoteKick {
	private String voteCaster = "";
	private String playerToKick = "";
	private String kickReason = "";

	private int yesVotes = 0;
	private int noVotes = 0;

	private ArrayList<String> alreadyVoted = new ArrayList<String>();

	public VoteKick(String playerCastingVote, String playerToKick, String kickReason) {
		this.voteCaster = playerCastingVote;
		this.playerToKick = playerToKick;
		this.kickReason = kickReason;
		this.yesVotes = 1;
	}

	public int getYesVotes() {
		return this.yesVotes;
	}

	public int getNoVotes() {
		return this.noVotes;
	}

	public boolean doAction() {
		if (this.yesVotes > this.noVotes) {
			return true;
		}
		return false;
	}

	public String getKickReason() {
		return this.kickReason;
	}

	public String getPlayerToKick() {
		return this.playerToKick;
	}

	public String getVoteCaster() {
		return this.voteCaster;
	}

	public void addYes() {
		this.yesVotes += 1;
	}

	public void addNo() {
		this.noVotes += 1;
	}

	public boolean hasVoted(String playerName) {
		return this.alreadyVoted.contains(playerName);
	}

	public void setVoted(String playerName) {
		this.alreadyVoted.add(playerName);
	}
}
