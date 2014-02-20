package com.caved_in.teamdeathmatch.guns;

import com.caved_in.teamdeathmatch.TDMGame;

import java.util.*;

public class GunHandler {
	private List<GunWrap> gunPistols = new ArrayList<>();

	private List<GunWrap> gunAssaultRifles = new ArrayList<>();

	private List<GunWrap> gunSniperRifles = new ArrayList<>();

	private List<GunWrap> gunSpecial = new ArrayList<>();

	private List<GunWrap> gunShotgun = new ArrayList<>();

	private Map<String, GunWrap> gunDefault = new HashMap<>();

	public GunHandler() {
		initData();
	}

	public void initData() {
		List<GunWrap> gunData = TDMGame.configuration.getGunShopConfiguration().getGunData();
		for (GunWrap gunWrapper : gunData) {
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

	public Set<GunWrap> getDefaultGuns() {
		return new HashSet<>(gunDefault.values());
	}	
	public Map<String,GunWrap> getDefaultGunMap() {
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
				return null;
		}
	}

	private GunWrap getRandomFromList(List<GunWrap> input) {
		return input.get(new Random().nextInt(input.size()));
	}
}
