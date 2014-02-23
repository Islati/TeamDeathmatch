package com.caved_in.teamdeathmatch.config.spawns;

import com.caved_in.commons.location.LocationHandler;
import com.caved_in.teamdeathmatch.TeamType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

public class XMLSpawnPoint {
	@Attribute(name = "team_name")
	private String teamName = "t";

	@Element(name = "worldName")
	private String worldName = "world";

	@Element(name = "locX")
	private int locX = 0;

	@Element(name = "locY")
	private int locY = 0;

	@Element(name = "locZ")
	private int locZ = 0;

	private TeamType teamType = TeamType.TERRORIST;

	private Location spawnLocation;

	public XMLSpawnPoint(@Attribute(name = "team_name") String teamName,
						 @Element(name = "worldName") String worldName,
						 @Element(name = "locX") int locX,
						 @Element(name = "locY") int locY,
						 @Element(name = "locZ") int locZ
	) {
		this.teamName = teamName;
		this.teamType = TeamType.getTeamByInitials(teamName);
		this.worldName = worldName;
		this.locX = locX;
		this.locY = locY;
		this.locZ = locZ;
		this.spawnLocation = new Location(Bukkit.getWorld(worldName), locX, locY, locZ);
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

	public XMLSpawnPoint() {

	}

	public XMLSpawnPoint(TeamSpawnLocation teamSpawnLocation) {
		this(teamSpawnLocation.getTeamType(), teamSpawnLocation.getLocation());
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
