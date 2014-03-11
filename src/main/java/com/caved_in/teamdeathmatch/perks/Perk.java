package com.caved_in.teamdeathmatch.perks;

import com.google.common.collect.Sets;
import org.bukkit.potion.PotionEffect;

import java.util.Set;

/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */
public class Perk implements IPerk {
	private String perkName = "";
	private int perkCost = 0;
	private String[] perkDescription;
	private boolean requiresPerk = false;
	private String perkRequired = "";
	private Set<PotionEffect> potionEffects;

	public Perk(String perkName, int perkCost, String[] perkDescription, PotionEffect[] potionEffects) {
		this.perkName = perkName;
		this.perkCost = perkCost;
		this.perkDescription = perkDescription;
		this.potionEffects = Sets.newHashSet(potionEffects);
	}

	public Perk(String perkName, int perkCost, String[] perkDescription, PotionEffect[] potionEffects, boolean requiresAnotherPerk, String perkRequiredName) {
		this(perkName,perkCost,perkDescription,potionEffects);
		this.perkRequired = perkRequiredName;
		this.requiresPerk = requiresAnotherPerk;
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
	public Set<PotionEffect> getEffects() {
		return potionEffects;
	}

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
