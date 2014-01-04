package com.caved_in.teamdeathmatch.config;

import com.caved_in.commons.Commons;
import com.caved_in.commons.handlers.SQL.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GunsSQL {
	private SQL SQL;

	public GunsSQL() {
		this.SQL = new SQL("localhost", "3306", "DATABASE", "username", "PASS");
	}

	/**
	 * Gets the data for a player via ResultSet
	 *
	 * @param PlayerName Name to get data of
	 * @return ResultSet of Data
	 */
	public ResultSet getPlayerData(String PlayerName) {
		return this.SQL.executeQueryOpen("SELECT * FROM Guns_Weapons WHERE Player= '" + PlayerName + "';");
	}

	public List<String> getGuns(String PlayerName) {
		List<String> Guns = new ArrayList<String>();
		ResultSet PlayerData = this.getPlayerData(PlayerName);
		try {
			while (PlayerData.next()) {
				Guns.add(PlayerData.getString("GunID"));
			}
			PlayerData.close();
		} catch (SQLException Ex) {
			Ex.printStackTrace();
		}
		return Guns;
	}

	public boolean hasGun(String Player, String Gun) {
		boolean Ret = false;
		List<String> Guns = new ArrayList<String>();
		ResultSet PlayerData = this.SQL.executeQueryOpen("SELECT * FROM Guns_Weapons WHERE Player= '" + Player + "' AND GunID= '" + Gun + "';");
		try {
			Ret = PlayerData.next();
			PlayerData.close();
		} catch (SQLException Ex) {
			Ex.printStackTrace();
		}
		return Ret;
	}

	public void insertGun(String PlayerName, String GunID) {
		if (!this.hasGun(PlayerName, GunID)) {
			this.SQL.executeUpdate("INSERT INTO Guns_Weapons (Player, GunID) VALUES ('" + PlayerName + "','" + GunID + "');");
			Commons.messageConsole("Gave Player " + PlayerName + " gun[ID] = " + GunID);
		}
	}
}
