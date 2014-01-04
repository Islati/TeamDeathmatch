package com.caved_in.teamdeathmatch.config;

import com.caved_in.commons.Commons;
import com.caved_in.commons.handlers.SQL.SQL;
import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.perks.Perk;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PerksSQL {
	private SQL SQL;

	private String Table = "Bans";
	private String IDTag = "ID";
	private String TypeTag = "Type";
	private String NameTag = "Name";
	private String ReasonTag = "Reason";
	private String IssuedByTag = "IssuedBy";
	private String IssuedTag = "Issued";
	private String ExpiresTag = "Expires";
	private String ActiveTag = "Active";

	public PerksSQL() {
		this.SQL = new SQL("localhost", "3306", "DATABASE", "username", "PASS");
	}

	/**
	 * Gets the data for a player via ResultSet
	 *
	 * @param PlayerName Name to get data of
	 * @return ResultSet of Data
	 */
	public ResultSet getPlayerData(String PlayerName) {
		return this.SQL.executeQueryOpen("SELECT * FROM Guns_Perks WHERE Player= '" + PlayerName + "';");
	}

	public List<Perk> getPerks(String PlayerName) {
		List<Perk> Perks = new ArrayList<Perk>();
		ResultSet PlayerData = this.getPlayerData(PlayerName);
		try {
			while (PlayerData.next()) {
				Perks.add(TDMGame.perkHandler.getPerk(PlayerData.getString("Perk")));
			}
			PlayerData.close();
		} catch (SQLException Ex) {
			Ex.printStackTrace();
		}
		return Perks;
	}

	public void insertPerk(Perk Perk, String PlayerName) {
		this.SQL.executeUpdate("INSERT INTO Guns_Perks (Player, Perk) VALUES ('" + PlayerName + "','" + Perk.getPerkName() + "');");
		Commons.messageConsole("Added perk " + Perk.getPerkName() + " to player " + PlayerName);
	}
}
