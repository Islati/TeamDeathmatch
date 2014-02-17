package com.caved_in.teamdeathmatch.config;

import com.caved_in.commons.sql.SQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GunsSQL extends SQL {
	private static String gunsTable = "Guns_Weapons";
	private static String playerColumn = "Player";
	private static String gunColumn = "GunID";
	private static String getDataStatement = "SELECT * FROM " + gunsTable + " WHERE " + playerColumn + "=?";
	private static String insertDataStatement = "INSERT INTO " + gunsTable + " (" + playerColumn + ", " + gunColumn + ") VALUES (?,?)";
	private static String createTableStatement = "CREATE TABLE IF NOT EXISTS `[DB]`.`Guns_Weapons` (`ID` int(10) unsigned NOT NULL AUTO_INCREMENT, `Player` text NOT NULL, `GunID` text NOT NULL, PRIMARY KEY (`ID`) ) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=22 ;";
	public GunsSQL(SqlConfiguration sqlConfig) {
		super(
				sqlConfig.getHost(),
				sqlConfig.getPort(),
				sqlConfig.getDatabase(),
				sqlConfig.getUsername(),
				sqlConfig.getPassword()
		);
		createTableStatement = createTableStatement.replace("[DB]",sqlConfig.getDatabase());
		execute(createTableStatement);
	}

	public boolean hasData(String playerName) {
		PreparedStatement preparedStatement = prepareStatement(getDataStatement);
		boolean hasData = false;
		try {
			preparedStatement.setString(1, playerName);
			hasData = preparedStatement.executeQuery().next();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement);
		}
		return hasData;
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

	public void insertGuns(String playerName, List<String> gunIds) {
		PreparedStatement preparedStatement = prepareStatement(insertDataStatement);
		try {
			for(String gun : gunIds) {
				if (!hasGun(playerName,gun)) {
					preparedStatement.setString(1, playerName);
					preparedStatement.setString(2, gun);
					preparedStatement.addBatch();
				}
			}
			preparedStatement.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement);
		}
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
