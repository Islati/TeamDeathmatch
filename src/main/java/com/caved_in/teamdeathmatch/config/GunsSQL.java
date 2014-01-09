package com.caved_in.teamdeathmatch.config;

import com.caved_in.commons.sql.SQL;
import com.caved_in.teamdeathmatch.TDMGame;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class GunsSQL extends SQL {
	private static String gunsTable = "Guns_Weapons";
	private static String playerColumn = "Player";
	private static String gunColumn = "GunID";
	private static String getDataStatement = "SELECT * FROM " + gunsTable + " WHERE " + playerColumn + "=?";
	private static String insertDataStatement = "INSERT INTO " + gunsTable + " (" + playerColumn + ", " + gunColumn + ") VALUES (?,?)";

	public GunsSQL() {
		super(
				TDMGame.configuration.getSqlConfiguration().getHost(),
				TDMGame.configuration.getSqlConfiguration().getPort(),
				TDMGame.configuration.getSqlConfiguration().getDatabase(),
				TDMGame.configuration.getSqlConfiguration().getUsername(),
				TDMGame.configuration.getSqlConfiguration().getPassword()
		);
	}

	public Set<String> getGuns(String playerName) {
		Set<String> guns = new HashSet<String>();
		PreparedStatement preparedStatement = prepareStatement(getDataStatement);
		try {
			preparedStatement.setString(1, playerName);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				guns.add(resultSet.getString(gunColumn));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement);
		}
		return guns;
	}

	public boolean hasGun(String playerName, String gunId) {
		return getGuns(playerName).contains(gunId);
	}

	public void insertGun(String playerName, String gunId) {
		if (!hasGun(playerName, gunId)) {
			PreparedStatement preparedStatement = prepareStatement(insertDataStatement);
			try {
				preparedStatement.setString(1, playerName);
				preparedStatement.setString(2, gunId);
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(preparedStatement);
			}
		}
	}
}
