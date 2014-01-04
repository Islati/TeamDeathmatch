package com.caved_in.teamdeathmatch.config;

import com.caved_in.commons.Commons;
import com.caved_in.commons.handlers.SQL.SQL;
import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.loadout.Loadout;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoadoutSQL {
	private SQL SQL;

	public LoadoutSQL() {
		this.SQL = new SQL("localhost", "3306", "DATABASE", "username", "PASS");
	}

	/**
	 * Gets the data for a player via ResultSet
	 *
	 * @param PlayerName Name to get data of
	 * @return ResultSet of Data
	 */
	public ResultSet getPlayerData(String PlayerName) {
		return this.SQL.executeQueryOpen("SELECT * FROM Guns_Loadouts WHERE Player= '" + PlayerName + "';");
	}

	public List<Loadout> getLoadouts(String Name) {
		List<Loadout> Loadouts = new ArrayList<Loadout>();
		ResultSet PlayerData = this.getPlayerData(Name);
		try {
			while (PlayerData.next()) {
				Loadouts.add(new Loadout(PlayerData.getString("Player"), PlayerData.getInt("Loadout"), PlayerData.getString("PrimaryG"),
						PlayerData.getString("Secondary"), TDMGame.perkHandler.getPerk(PlayerData.getString("Perk"))));
			}
			PlayerData.close();
		} catch (SQLException Ex) {
			Ex.printStackTrace();
		}
		return Loadouts;
	}

	public void insertLoadout(String playerName, int loadout, String primary, String secondary, String perk) {
		this.SQL.executeUpdate("INSERT INTO Guns_Loadouts (Player, Loadout, PrimaryG, Secondary, Perk) VALUES ('" + playerName + "','" + loadout + "'," +
				"'" + primary + "','" + secondary + "','" + perk + "');");
		Commons.messageConsole("Inserted Loadout for " + playerName + " with " + primary + "," + secondary + ", and Perk [" + perk + "]");
	}

	public void updateLoadout(String name, int loadout, String primary, String secondary, String perk) {
		this.SQL.executeUpdate("UPDATE Guns_Loadouts SET PrimaryG='" + primary + "', Secondary='" + secondary + "', " +
				"Perk='" + perk + "' WHERE Player='" + name + "' AND Loadout='" + loadout + "';");
		Commons.messageConsole("Updated Loadout " + loadout + " for player " + name + " with [" + primary + "," + secondary + "," +
				"" + " and Perk [" + perk + "]");
	}
}
