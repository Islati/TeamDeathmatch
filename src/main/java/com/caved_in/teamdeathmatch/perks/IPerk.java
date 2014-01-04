package com.caved_in.teamdeathmatch.perks;

import org.bukkit.potion.PotionEffect;

import java.util.List;

public interface IPerk {
	public String getPerkName();

	public String[] getPerkDescription();

	public List<PotionEffect> getEffects();

	public int getPurchaseCost();

	public boolean isTieredPerk();

	public String getPerkRequired();
}
