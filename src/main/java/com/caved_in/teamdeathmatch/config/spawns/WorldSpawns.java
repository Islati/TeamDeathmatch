package com.caved_in.teamdeathmatch.config.spawns;

import com.caved_in.teamdeathmatch.TeamType;

import java.util.*;

public class WorldSpawns implements Iterable<TeamSpawnLocation> {
	private String worldName;
	private Set<TeamSpawnLocation> spawnLocations = new HashSet<>();

	public WorldSpawns(String worldName) {
		this.worldName = worldName;
	}

	public WorldSpawns(String worldName, Set<TeamSpawnLocation> spawnLocations) {
		this.worldName = worldName;
		this.spawnLocations = spawnLocations;
	}

	public void add(TeamSpawnLocation spawnLocation) {
		spawnLocations.add(spawnLocation);
	}

	public void remove(TeamSpawnLocation spawnLocation) {
		spawnLocations.remove(spawnLocation);
	}

	/**
	 * Filter a list of spawn locations based on the team given
	 * @param teamType Team we wish to get the spawn locations of
	 * @return Set of the spawnLocations for the given team
	 */
	public Set<TeamSpawnLocation> getSpawnLocations(TeamType teamType) {
		Set<TeamSpawnLocation> returnLocations = new HashSet<>();
		for (TeamSpawnLocation spawnLocation : this.spawnLocations) {
			if (spawnLocation.getTeamType() == teamType) {
				returnLocations.add(spawnLocation);
			}
		}
		return returnLocations;
	}

	public TeamSpawnLocation getRandomSpawn(TeamType teamType) {
		ArrayList<TeamSpawnLocation> spawns = new ArrayList<>(getSpawnLocations(teamType));
		return spawns.get(new Random(System.nanoTime()).nextInt(spawns.size()));
	}

	@Override
	public Iterator<TeamSpawnLocation> iterator() {
		return spawnLocations.iterator();
	}

	public String getWorldName() {
		return worldName;
	}
}
