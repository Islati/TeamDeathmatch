package com.caved_in.teamdeathmatch;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum TeamType {
	TERRORIST("t", "terrorist"),
	COUNTER_TERRORIST("ct", "counterterrorist", "counter_terrorist", "c_t");

	private String[] teamInitials;
	private static Map<String, TeamType> teamMap = new HashMap<String, TeamType>();

	static {
		for (TeamType teamType : EnumSet.allOf(TeamType.class)) {
			for (String initial : teamType.teamInitials) {
				teamMap.put(initial, teamType);
			}
		}
	}

	TeamType(String... teamInitials) {
		this.teamInitials = teamInitials;
	}

	@Override
	public String toString() {
		return this.teamInitials[0];
	}

	public static TeamType getTeamByInitials(String teamInitials) {
		return teamMap.get(teamInitials.toLowerCase());
	}
}
