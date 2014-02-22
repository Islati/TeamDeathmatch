package com.caved_in.teamdeathmatch.config.shop;

import com.caved_in.teamdeathmatch.guns.GunWrapper;
import org.simpleframework.xml.ElementList;

import java.util.ArrayList;
import java.util.List;

public class GunShopConfiguration {
	@ElementList(name = "guns_data", type = XMLGun.class)
	private List<XMLGun> gunData = new ArrayList<>();

	private List<GunWrapper> gunWrappers = new ArrayList<>();

	public GunShopConfiguration(@ElementList(name = "guns_data", type = XMLGun.class)List<XMLGun> gunData) {
		this.gunData = gunData;
		initData();
	}

	public GunShopConfiguration() {
		gunData.add(new XMLGun());
		initData();
	}

	private void initData() {
		for(XMLGun gun : gunData) {
			gunWrappers.add(gun.getGunWrapper());
		}
	}

	public List<GunWrapper> getGunData() {
		return gunWrappers;
	}
}
