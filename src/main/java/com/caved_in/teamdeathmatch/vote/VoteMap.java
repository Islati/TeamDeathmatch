package com.caved_in.teamdeathmatch.vote;

import com.caved_in.commons.Commons;
import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.teamdeathmatch.Game;
import com.caved_in.teamdeathmatch.GameMessages;

public class VoteMap extends Vote {
	private String map = "";

	public VoteMap(String voteCaster, String[] args) {
		super(voteCaster,args);
		this.map = args[0];
	}

	@Override
	public void execute() {
		Commons.threadManager.runTaskOneTickLater(new Runnable() {

			@Override
			public void run() {
				Game.gameMap = map;
				PlayerHandler.sendMessageToAllPlayers(GameMessages.MAP_CHANGED(map));
			}
		});
	}

	@Override
	public void announce() {
		String mapChangeAnnoucnement = GameMessages.ANNOUNCE_VOTE_MAP_CHANGE(getCaster(), map);
		PlayerHandler.sendMessageToAllPlayers(mapChangeAnnoucnement);
	}
}
