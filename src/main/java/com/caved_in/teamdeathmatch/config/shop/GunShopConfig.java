package com.caved_in.teamdeathmatch.config.shop;

import com.caved_in.commons.handlers.File.DataHandler;
import com.caved_in.commons.handlers.File.Tag;
import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.handlers.filehandlers.FolderHandler;
import com.caved_in.teamdeathmatch.guns.GunType;
import com.caved_in.teamdeathmatch.guns.GunWrap;

import java.util.ArrayList;
import java.util.List;

public class GunShopConfig {
	private DataHandler dataHandler;
	private com.caved_in.teamdeathmatch.handlers.filehandlers.FolderHandler folderHandler;
	private Tag gunIdTag = new Tag("GunID");
	private Tag gunCostTag = new Tag("Cost");
	private Tag defaultGunTag = new Tag("Default");
	private Tag gunTypeTag = new Tag("Type");

	public GunShopConfig(String location) {
		this.folderHandler = new FolderHandler(location);
	}

	public List<GunWrap> loadGuns(GunType gunType) {
		List<GunWrap> Guns = new ArrayList<GunWrap>();
		for (GunWrap Gun : this.loadGuns()) {
			if (Gun.getGunType() == gunType) {
				Guns.add(Gun);
			}
		}
		return Guns;
	}

	public List<GunWrap> loadGuns() {
		List<GunWrap> Guns = new ArrayList<GunWrap>();
		for (String Data : this.getGunfolderData()) {
			Guns.add(new GunWrap(getGunID(Data), getCost(Data), getDefault(Data), getType(Data)));
		}
		return Guns;
	}

	public String getGunID(String Text) {
		return DataHandler.getStringBetween(Text, this.gunIdTag.getOpen(), this.gunIdTag.getClose());
	}

	public int getCost(String Text) {
		try {
			return Integer.parseInt(DataHandler.getStringBetween(Text, this.gunCostTag.getOpen(), this.gunCostTag.getClose()));
		} catch (Exception Ex) {
			Ex.printStackTrace();
			TDMGame.Console(Text);
			return 0;
		}
	}

	public boolean getDefault(String Text) {
		if (Text.contains(this.defaultGunTag.getOpen()) && Text.contains(this.defaultGunTag.getClose())) {
			return (DataHandler.getStringBetween(Text, this.defaultGunTag.getOpen(), this.defaultGunTag.getClose()).equalsIgnoreCase("true"));
		}
		return false;
	}

	public GunType getType(String Text) {
		GunType gunType = GunType.valueOf(DataHandler.getStringBetween(Text, this.gunTypeTag.getOpen(), this.gunTypeTag.getClose()));
		return gunType;
	}

	public List<String> getGunfolderData() {
		List<String> fileData = new ArrayList<String>();
		for (String gunFile : this.folderHandler.getFiles()) {
			String gunData = new DataHandler(gunFile).getText();
			if (gunData.contains(this.gunIdTag.getOpen()) && gunData.contains(this.gunIdTag.getClose())) {
				fileData.add(gunData);
			}
		}
		return fileData;
	}
}
