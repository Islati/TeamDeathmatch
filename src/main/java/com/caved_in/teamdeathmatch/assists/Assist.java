package com.caved_in.teamdeathmatch.assists;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Assist {
	private String attacked = "";
	private Set<String> attackers = new HashSet<>();

	public Assist(String Attacked) {
		this.attacked = Attacked;
	}

	public String getAttacked() {
		return this.attacked;
	}

	public Set<String> getAttackers() {
		return this.attackers;
	}

	public void addAttacker(String playerName) {
		this.attackers.add(playerName);
	}
}
