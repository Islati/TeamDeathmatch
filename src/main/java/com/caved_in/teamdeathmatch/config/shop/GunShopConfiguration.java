package com.caved_in.teamdeathmatch.config.shop;

import com.caved_in.teamdeathmatch.guns.GunWrap;
import org.simpleframework.xml.ElementList;

import java.util.ArrayList;
import java.util.List;

public class GunShopConfiguration {
	@ElementList(name = "guns_data", type = XMLGun.class)
	private List<XMLGun> gunData = new ArrayList<XMLGun>();

	private List<GunWrap> gunWraps = new ArrayList<>();

	public GunShopConfiguration(@ElementList(name = "guns_data", type = XMLGun.class)List<XMLGun> gunData) {
		this.gunData = gunData;
	}

	public GunShopConfiguration() {
		gunData.add(new XMLGun());
	}

	private void initData() {
		for(XMLGun gun : gunData) {
			gunWraps.add(gun.getGunWrapper());
		}
	}

	public List<GunWrap> getGunData() {
		return gunWraps;
	}
}
