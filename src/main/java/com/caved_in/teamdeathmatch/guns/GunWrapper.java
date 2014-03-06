package com.caved_in.teamdeathmatch.guns;

import com.caved_in.teamdeathmatch.Game;
import org.bukkit.inventory.ItemStack;

public class GunWrapper {
	private String gunName = "";
	private boolean isDefaultGun = false;
	private int gunPrice = 0;
	private GunType gunType;

	public GunWrapper(String gunName, int gunPrice, boolean isDefault, GunType gunType) {
		this.gunPrice = gunPrice;
		this.gunName = gunName;
		this.isDefaultGun = isDefault;
		this.gunType = gunType;
	}

	public boolean isDefaultGun() {
		return this.isDefaultGun;
	}

	public String getGunName() {
		return this.gunName;
	}

	public int getGunPrice() {
		return this.gunPrice;
	}

	public GunType getGunType() {
		return this.gunType;
	}

	public ItemStack getItemStack() {
		return Game.crackShotAPI.generateWeapon(gunName);
	}

}
