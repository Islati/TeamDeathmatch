package com.caved_in.teamdeathmatch.guns;

public enum GunType {
	SHOTGUN("shotgun"),
	SNIPER("sniper"),
	ASSAULT("assault"),
	PISTOL("pistol"),
	SPECIAL("special");

	private String category;
	GunType(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return this.category;
	}
}
