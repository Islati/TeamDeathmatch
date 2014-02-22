package com.caved_in.teamdeathmatch.guns;

import com.caved_in.teamdeathmatch.TDMGame;

import java.util.*;

public class GunHandler {
	private List<GunWrapper> gunPistols = new ArrayList<>();

	private List<GunWrapper> gunAssaultRifles = new ArrayList<>();

	private List<GunWrapper> gunSniperRifles = new ArrayList<>();

	private List<GunWrapper> gunSpecial = new ArrayList<>();

	private List<GunWrapper> gunShotgun = new ArrayList<>();

	private Map<String, GunWrapper> gunDefault = new HashMap<>();

	public GunHandler() {
		initData();
	}

	public void initData() {
		List<GunWrapper> gunData = TDMGame.configuration.getGunShopConfiguration().getGunData();
		for (GunWrapper gunWrapper : gunData) {
			//Check if it's a default gun, and if so add it to the list of defaults
			if (gunWrapper.isDefaultGun()) {
				gunDefault.put(gunWrapper.getGunName(), gunWrapper);
			}

			//Sort all the gun data
			switch (gunWrapper.getGunType()) {
				case PISTOL:
					gunPistols.add(gunWrapper);
					break;
				case ASSAULT:
					gunAssaultRifles.add(gunWrapper);
					break;
				case SPECIAL:
					gunSpecial.add(gunWrapper);
					break;
				case SHOTGUN:
					gunShotgun.add(gunWrapper);
					break;
				case SNIPER:
					gunSniperRifles.add(gunWrapper);
					break;
				default:
					break;
			}
		}
	}

	public Map<String,GunWrapper> getDefaultGunMap() {
		return gunDefault;
	}

	public List<GunWrapper> getGuns(GunType gunType) {
		switch (gunType) {
			case ASSAULT:
				return gunAssaultRifles;
			case PISTOL:
				return gunPistols;
			case SHOTGUN:
				return gunShotgun;
			case SNIPER:
				return gunSniperRifles;
			case SPECIAL:
				return gunSpecial;
			default:
				return null;
		}
	}

}
