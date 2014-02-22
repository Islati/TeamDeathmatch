package com.caved_in.teamdeathmatch.config.spawns;

import com.caved_in.teamdeathmatch.TeamType;
import org.bukkit.Location;

public class TeamSpawnLocation {
	private Location location;
	private TeamType teamType;

	public TeamSpawnLocation(Location location, TeamType teamType) {
		this.location = location;
		this.teamType = teamType;
	}

	public TeamSpawnLocation(XMLSpawnPoint xmlSpawnPoint) {
		this.location = xmlSpawnPoint.getLocation();
		this.teamType = xmlSpawnPoint.getTeamType();
	}

	public TeamType getTeamType() {
		return teamType;
	}

	public Location getLocation() {
		return location;
	}

}
