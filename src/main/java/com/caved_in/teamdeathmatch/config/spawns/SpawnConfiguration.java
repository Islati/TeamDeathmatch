package com.caved_in.teamdeathmatch.config.spawns;

import org.simpleframework.xml.ElementList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpawnConfiguration {

	@ElementList(name = "spawnpoints", type = XMLSpawnPoint.class)
	private List<XMLSpawnPoint> spawnPoints = new ArrayList<XMLSpawnPoint>();

	private Map<String, WorldSpawns> worldSpawnLocations = new HashMap<>();

	public SpawnConfiguration(@ElementList(name = "spawnpoints", type = XMLSpawnPoint.class)
							  List<XMLSpawnPoint> spawnPoints) {
		this.spawnPoints = spawnPoints;
		initializeTeamSpawns();
	}

	public SpawnConfiguration() {
		initializeTeamSpawns();
		spawnPoints.add(new XMLSpawnPoint());
	}

	private void initializeTeamSpawns() {
		//Loop through our loaded XMLSpawnPoints
		for(XMLSpawnPoint xmlSpawnPoint : spawnPoints) {
			//Get the world name of the selected spawn point
			String worldName = xmlSpawnPoint.getWorldName();
			//Check if our worldSpawns lists has an entry for this world already
			if (worldSpawnLocations.containsKey(worldName)) {
				//Add a teamspawnlocation to the instance for this world
				worldSpawnLocations.get(worldName).add(new TeamSpawnLocation(xmlSpawnPoint));
			} else {
				//Create our worldSpawns object for the given world
				WorldSpawns worldSpawns = new WorldSpawns(worldName);
				//Add a new TeamSpawnObject based on the current xmlspawnpoint
				worldSpawns.add(new TeamSpawnLocation(xmlSpawnPoint));
				//Add our worldSpawns object to the map of elements
				worldSpawnLocations.put(worldName, worldSpawns);
			}
		}
	}

	public WorldSpawns getWorldSpawns(String worldName) {
		if (!worldSpawnLocations.containsKey(worldName)) {
			worldSpawnLocations.put(worldName, new WorldSpawns(worldName));
		}
		return worldSpawnLocations.get(worldName);
	}


	public void addSpawn(TeamSpawnLocation teamSpawnLocation) {
		String worldName = teamSpawnLocation.getLocation().getWorld().getName();
		getWorldSpawns(worldName).add(teamSpawnLocation);
		spawnPoints.add(new XMLSpawnPoint(teamSpawnLocation));
	}

}
