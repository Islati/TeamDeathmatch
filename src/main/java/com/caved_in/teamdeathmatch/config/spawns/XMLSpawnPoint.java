package com.caved_in.teamdeathmatch.config.spawns;

import com.caved_in.commons.location.LocationHandler;
import com.caved_in.teamdeathmatch.TeamType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

public class XMLSpawnPoint {
	@Attribute(name="team_name")
	private String teamName;

	@Element(name = "worldName")
	private String worldName;

	@Element(name = "locX")
	private double locX;

	@Element(name="locY")
	private double locY;

	@Element(name="locZ")
	private double locZ;

	private TeamType teamType;

	private Location spawnLocation;

	public XMLSpawnPoint(@Attribute(name="team_name")String teamName,
						 @Element(name = "worlName")String worldName,
						 @Element(name="locX")double locX,
						 @Element(name="locY")double locY,
						 @Element(name="locZ")double locZ
	) {
		this.teamName = teamName;
		this.teamType = TeamType.getTeamByInitials(teamName);
		this.worldName = worldName;
		this.locX = locX;
		this.locY = locY;
		this.locZ = locZ;
		this.spawnLocation = new Location(Bukkit.getWorld(worldName),locX,locY,locZ);
	}

	public XMLSpawnPoint(TeamType teamType, Location spawnLocation) {
		this.spawnLocation = spawnLocation;
		int[] xyz = LocationHandler.getXYZ(spawnLocation);
		this.locX = xyz[0];
		this.locY = xyz[1];
		this.locZ = xyz[2];
		this.worldName = spawnLocation.getWorld().getName();
		this.teamType = teamType;
		this.teamName = teamType.toString();
	}

	public Location getLocation() {
		return spawnLocation;
	}

	public TeamType getTeamType() {
		return teamType;
	}

	public String getWorldName() {
		return worldName;
	}
}
