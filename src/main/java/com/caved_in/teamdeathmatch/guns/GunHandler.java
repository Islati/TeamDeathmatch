package com.caved_in.teamdeathmatch.guns;

import com.caved_in.teamdeathmatch.TDMGame;

import java.util.List;
import java.util.Random;

public class GunHandler {
	private List<GunWrap> gunPistols;

	private List<GunWrap> gunAssaultRifles;

	private List<GunWrap> gunSniperRifles;

	private List<GunWrap> gunSpecial;

	private List<GunWrap> gunShotgun;

	private List<GunWrap> gunDefault;

	public GunHandler() {
		initData();
	}

	public void initData() {
		List<GunWrap> gunData = TDMGame.configuration.getGunShopConfiguration().getGunData();
		for (GunWrap gunWrapper : gunData) {
			//Check if it's a default gun, and if so add it to the list of defaults
			if (gunWrapper.isDefaultGun()) {
				gunDefault.add(gunWrapper);
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

	public List<GunWrap> getDefaultGuns() {
		return gunDefault;
	}

	public List<GunWrap> getGuns(GunType gunType) {
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
				break;
		}
		return null;
	}

	private GunWrap getRandomFromList(List<GunWrap> input) {
		return input.get(new Random().nextInt(input.size()));
	}
}
