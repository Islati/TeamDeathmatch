package com.caved_in.teamdeathmatch.guns;

import com.caved_in.teamdeathmatch.TDMGame;

import java.util.ArrayList;
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
		this.LoadGunData();
	}

	public void LoadGunData() {
		List<GunWrap> gunData = TDMGame.gunShopConfig.loadGuns();
		this.gunPistols = this.filterGuns(GunType.Pistol, gunData);
		this.gunAssaultRifles = this.filterGuns(GunType.Assault, gunData);
		this.gunSniperRifles = this.filterGuns(GunType.Sniper, gunData);
		this.gunSpecial = this.filterGuns(GunType.Special, gunData);
		this.gunShotgun = this.filterGuns(GunType.Shotgun, gunData);
		this.gunDefault = new ArrayList<GunWrap>();

		for (GunWrap gunWrapper : gunData) {
			if (gunWrapper.isDefaultGun()) {
				gunDefault.add(gunWrapper);
			}
		}
	}

	public List<GunWrap> getDefaultGuns() {
		return gunDefault;
	}

	public List<GunWrap> getGuns(GunType gunType) {
		switch (gunType) {
			case Assault:
				return this.gunAssaultRifles;
			case Pistol:
				return this.gunPistols;
			case Shotgun:
				return this.gunShotgun;
			case Sniper:
				return this.gunSniperRifles;
			case Special:
				return this.gunSpecial;
			default:
				break;
		}
		return null;
	}

	/**
	 * public List<GunWrap> getGuns(GunType gunType, Player player)
	 * {
	 * fPlayer fPlayer = FakeboardHandler.getPlayer(player);
	 * List<GunWrap> playerGuns = new ArrayList<GunWrap>();
	 * for(GunWrap gunWrapper : getGuns(gunType))
	 * {
	 * if (fPlayer.hasGun(gunWrapper))
	 * {
	 * playerGuns.add(gunWrapper);
	 * }
	 * }
	 * return playerGuns;
	 * }
	 */
	private List<GunWrap> filterGuns(GunType gunType, List<GunWrap> gunList) {
		List<GunWrap> filteredGuns = new ArrayList<GunWrap>();
		for (GunWrap gunWrapper : gunList) {
			if (gunWrapper.getGunType() == gunType) {
				filteredGuns.add(gunWrapper);
			}
		}
		return filteredGuns;
	}

	private GunWrap getRandomFromList(List<GunWrap> input) {
		return input.get(new Random().nextInt(input.size()));
	}
}
