package com.caved_in.teamdeathmatch.perks;

import org.bukkit.potion.PotionEffect;

import java.util.List;

public abstract class Perk implements IPerk {
	private String perkName = "";
	private int perkCost = 0;
	private String[] perkDescription;
	private boolean requiresPerk = false;
	private String perkRequired = "";

	public Perk(String perkName, int perkCost, String[] perkDescription) {
		this.perkName = perkName;
		this.perkCost = perkCost;
		this.perkDescription = perkDescription;
		//this.Effect = new PotionEffect(Type, TimeUtils.getTimeInTicks(15, TimeType.Minute),Level);
	}

	public Perk(String perkName, int perkCost, String[] perkDescription, boolean RequiresAnotherPerk, String PerkRequiredName) {
		this.perkName = perkName;
		this.perkCost = perkCost;
		this.perkDescription = perkDescription;
		this.perkRequired = PerkRequiredName;
		this.requiresPerk = RequiresAnotherPerk;
	}

	@Override
	public String getPerkName() {
		return this.perkName;
	}

	@Override
	public String[] getPerkDescription() {
		return this.perkDescription;
	}

	@Override
	public abstract List<PotionEffect> getEffects();

	@Override
	public int getPurchaseCost() {
		return this.perkCost;
	}

	@Override
	public boolean isTieredPerk() {
		return this.requiresPerk;
	}

	@Override
	public String getPerkRequired() {
		return this.perkRequired;
	}
}
