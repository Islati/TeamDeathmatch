package com.caved_in.teamdeathmatch.config;

import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.config.shop.GunShopConfiguration;
import com.caved_in.teamdeathmatch.config.spawns.SpawnConfiguration;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;

public class Configuration {

	private SpawnConfiguration spawnConfiguration;

	private GunShopConfiguration gunShopConfiguration;

	private static Serializer serializer = new Persister();

	public Configuration() {
		loadConfig();
	}

	public SpawnConfiguration getSpawnConfiguration() {
		return spawnConfiguration;
	}

	public GunShopConfiguration getGunShopConfiguration() {
		return gunShopConfiguration;
	}

	public void saveConfig() {
		try {
			serializer.write(spawnConfiguration, new File(TDMGame.SPAWN_CONFIG_FILE));
			serializer.write(gunShopConfiguration, new File(TDMGame.GUN_CONFIG_FILE));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadConfig() {
		try {
			spawnConfiguration = serializer.read(SpawnConfiguration.class, new File(TDMGame.SPAWN_CONFIG_FILE));
			gunShopConfiguration = serializer.read(GunShopConfiguration.class, new File(TDMGame.GUN_CONFIG_FILE));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
