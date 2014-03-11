package com.caved_in.teamdeathmatch.config;

import com.caved_in.teamdeathmatch.Game;
import org.simpleframework.xml.Serializer;

import java.io.File;

public class Configuration {

	private SpawnConfiguration spawnConfiguration;

	private GunShopConfiguration gunShopConfiguration;

	private SqlConfiguration sqlConfiguration;

	private static Serializer serializer = Game.getPersister();

	public Configuration() {
		loadConfig();
	}

	public SpawnConfiguration getSpawnConfiguration() {
		return spawnConfiguration;
	}

	public GunShopConfiguration getGunShopConfiguration() {
		return gunShopConfiguration;
	}

	public SqlConfiguration getSqlConfiguration() {
		return sqlConfiguration;
	}

	public void saveConfig() {
		try {
			serializer.write(spawnConfiguration, new File(Game.SPAWN_CONFIG_FILE));
			serializer.write(gunShopConfiguration, new File(Game.GUN_CONFIG_FILE));
			serializer.write(sqlConfiguration, new File(Game.SQL_CONFIG_FILE));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadConfig() {
		try {
			spawnConfiguration = serializer.read(SpawnConfiguration.class, new File(Game.SPAWN_CONFIG_FILE));
			gunShopConfiguration = serializer.read(GunShopConfiguration.class, new File(Game.GUN_CONFIG_FILE));
			sqlConfiguration = serializer.read(SqlConfiguration.class, new File(Game.SQL_CONFIG_FILE));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
