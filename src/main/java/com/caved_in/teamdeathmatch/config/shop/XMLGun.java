package com.caved_in.teamdeathmatch.config.shop;

import com.caved_in.teamdeathmatch.guns.GunType;
import com.caved_in.teamdeathmatch.guns.GunWrap;
import org.simpleframework.xml.Element;

public class XMLGun {

	@Element(name = "gun_name")
	private String gunName = "AK-47";

	@Element(name = "gun_type")
	private String gunCategory = "Assault";

	@Element(name = "gun_price")
	private int gunPrice = 0;

	@Element(name = "default_gun")
	private boolean defaultGun = true;

	private GunType gunType = GunType.ASSAULT;

	private GunWrap gunWrapper;

	public XMLGun(@Element(name="gun_name")String gunName,
				  @Element(name="gun_type")String gunCategory,
				  @Element(name="gun_price")int gunPrice,
				  @Element(name="default_gun")boolean defaultGun) {
		this.gunName = gunName;
		this.gunCategory = gunCategory;
		this.gunPrice = gunPrice;
		this.defaultGun = defaultGun;
		this.gunType = GunType.valueOf(gunCategory.toUpperCase());
		initGunWrapper();
	}

	public XMLGun() {
		initGunWrapper();
	}

	private void initGunWrapper() {
		this.gunWrapper = new GunWrap(gunName,gunPrice,defaultGun,gunType);
	}

	public boolean isDefaultGun() {
		return defaultGun;
	}

	public int getGunPrice() {
		return gunPrice;
	}

	public String getGunCategory() {
		return gunCategory;
	}

	public String getGunName() {
		return gunName;
	}

	public GunType getGunType() {
		return gunType;
	}

	public GunWrap getGunWrapper() {
		return gunWrapper;
	}
}
