package com.caved_in.teamdeathmatch.config;

import com.caved_in.commons.sql.SQL;
import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.loadout.Loadout;
import com.caved_in.teamdeathmatch.perks.PerkHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoadoutSQL extends SQL{
	private static String loadoutTable = "Guns_Loadouts";
	private static String playerColumn = "Player";
	private static String loadoutNumberColumn = "Loadout";
	private static String primaryWeaponColumn = "PrimaryG";
	private static String secondaryWeaponColumn = "Secondary";
	private static String perkColumn = "Perk";

	private static String getDataStatement = "SELECT * FROM " + loadoutTable + " WHERE " + playerColumn + "=?";
	private static String insertLoadoutStatement = "INSERT INTO " + loadoutTable + " (" + playerColumn + ", " + loadoutNumberColumn + ", " + primaryWeaponColumn + ", " + secondaryWeaponColumn + ", " + perkColumn + ") VALUES (?,?,?,?,?)";
	private static String updateLoadoutStatement = "UPDATE " + loadoutTable + " SET " + primaryWeaponColumn + "=?, " + secondaryWeaponColumn + "=?, " + perkColumn + "=? WHERE " + playerColumn + "=? AND " + loadoutNumberColumn + "=?";

	public LoadoutSQL() {
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

	public List<Loadout> getLoadouts(String playerName) {
		List<Loadout> playerLoadouts = new ArrayList<Loadout>();
		PreparedStatement preparedStatement = prepareStatement(getDataStatement);
		try {
			preparedStatement.setString(1, playerName);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				playerLoadouts.add(new Loadout(playerName, resultSet.getInt(loadoutNumberColumn), resultSet.getString(primaryWeaponColumn), resultSet.getString(secondaryWeaponColumn), PerkHandler.getPerk(resultSet.getString(perkColumn))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement);
		}
		return playerLoadouts;
	}

	public void insertLoadouts(List<Loadout> loadouts) {
		PreparedStatement preparedStatement = prepareStatement(insertLoadoutStatement);
		try {
			for (Loadout loadout : loadouts) {
				preparedStatement.setString(1, loadout.getPlayerName());
				preparedStatement.setInt(2,loadout.getNumber());
				preparedStatement.setString(3, loadout.getPrimary());
				preparedStatement.setString(4, loadout.getSecondary());
				preparedStatement.setString(5, loadout.getPerk().getPerkName());
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement);
		}
	}

	public void insertLoadout(String playerName, int loadout, String primary, String secondary, String perk) {
		PreparedStatement preparedStatement = prepareStatement(insertLoadoutStatement);
		try {
			//Set all the variables required to insert a perk
			preparedStatement.setString(1, playerName);
			preparedStatement.setInt(2,loadout);
			preparedStatement.setString(3, primary);
			preparedStatement.setString(4, secondary);
			preparedStatement.setString(5, perk);
			//execute the insert statement
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement);
		}
	}

	public void updateLoadout(String name, int loadout, String primary, String secondary, String perk) {
		PreparedStatement preparedStatement = prepareStatement(updateLoadoutStatement);
		try {
			//Set the variables required to update a players loadout
			preparedStatement.setString(1, primary);
			preparedStatement.setString(2, secondary);
			preparedStatement.setString(3, perk);
			preparedStatement.setString(4, name);
			preparedStatement.setInt(5, loadout);
			//Execute the update
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement);
		}
	}
}
