package com.caved_in.teamdeathmatch.config;

import com.caved_in.commons.sql.SQL;
import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.perks.Perk;
import com.caved_in.teamdeathmatch.perks.PerkHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PerksSQL extends SQL {

	private static String perkTable = "Guns_Perks";
	private static String playerColumn = "Player";
	private static String perkColumn = "Perk";

	private static String getDataStatement = "SELECT * FROM " + perkTable + " WHERE " + playerColumn + "=?";
	private static String insertDataStatement = "INSERT INTO " + perkTable + " (" + playerColumn + ", " + perkColumn + ") VALUES (?, ?)";

	public PerksSQL() {
		super(
				TDMGame.configuration.getSqlConfiguration().getHost(),
				TDMGame.configuration.getSqlConfiguration().getPort(),
				TDMGame.configuration.getSqlConfiguration().getDatabase(),
				TDMGame.configuration.getSqlConfiguration().getUsername(),
				TDMGame.configuration.getSqlConfiguration().getPassword()
		);
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


	public Set<Perk> getPerks(String playerName) {
		Set<Perk> playerPerks = new HashSet<Perk>();
		PreparedStatement preparedStatement = prepareStatement(getDataStatement);
		try {
			preparedStatement.setString(1, playerName);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				playerPerks.add(PerkHandler.getPerk(resultSet.getString(perkColumn)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement);
		}
		return playerPerks;
	}

	public void insertPerks(String playerName, List<Perk> perks) {
		PreparedStatement preparedStatement = prepareStatement(insertDataStatement);
		try {
			for (Perk perk : perks) {
				preparedStatement.setString(1, playerName);
				preparedStatement.setString(2, perk.getPerkName());
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement);
		}
	}

	public void insertPerk(Perk perk, String playerName) {
		PreparedStatement preparedStatement = prepareStatement(insertDataStatement);
		try {
			preparedStatement.setString(1, playerName);
			preparedStatement.setString(2, perk.getPerkName());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement);
		}
	}
}
