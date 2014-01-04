package com.caved_in.teamdeathmatch.loadout;

import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.perks.Perk;
import com.caved_in.teamdeathmatch.perks.Perks.Nothing;

public class Loadout {
	private String primary = "AK-47";
	private String secondary = "USP45";

	private int loadoutNumber = 0;

	private Perk perk = new Nothing();
	private String playerName = "";

	public Loadout(String playerName, int loadoutNumber, String primaryWeapon, String secondaryWeapon, Perk loadoutPerk) {
		this.playerName = playerName;
		this.loadoutNumber = loadoutNumber;
		this.primary = primaryWeapon;
		this.secondary = secondaryWeapon;
		this.perk = loadoutPerk;
	}

	public String getPlayerName() {
		return this.playerName;
	}

	public int getNumber() {
		return this.loadoutNumber;
	}

	public String getPrimary() {
		return this.primary;
	}

	public String getSecondary() {
		return this.secondary;
	}

	public Perk getPerk() {
		return this.perk;
	}

	public void setPrimary(String ID) {
		this.primary = ID;
		updateSql();
	}

	public void setSecondary(String ID) {
		this.secondary = ID;
		updateSql();
	}

	public void setPerk(Perk Perk) {
		this.perk = Perk;
		updateSql();
	}

	public void updateSql() {
		TDMGame.runnableManager.runTaskAsynch(new Runnable() {
			@Override
			public void run() {
				TDMGame.loadoutSQL.updateLoadout(playerName, loadoutNumber, primary, secondary, perk.getPerkName());
			}
		});
	}
}
