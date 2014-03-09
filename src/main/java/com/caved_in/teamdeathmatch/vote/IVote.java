package com.caved_in.teamdeathmatch.vote;

public interface IVote {
	public boolean hasVoted(String playerName);

	public void addYes();

	public void addNo();

	public void addVote(ChatCommand chatCommand);

	public void setVoted(String playerName);

	public void execute();

	public void announce();

	public String getCaster();

	public boolean canExecute();

	public int getYesVotes();

	public int getNoVotes();
}
