package com.caved_in.teamdeathmatch.perks;

import com.caved_in.teamdeathmatch.perks.Perks.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PerkHandler {
	private HashMap<String, Perk> perks = new HashMap<String, Perk>();

	public PerkHandler() {
		initializePerk(new Jump1());
		initializePerk(new Speed1());
		initializePerk(new Speed2());
		initializePerk(new Speed3());
		initializePerk(new Nothing());
		initializePerk(new ThunderThighs_One());
		initializePerk(new Regen1());
	}

	public void initializePerk(Perk perk) {
		perks.put(perk.getPerkName(), perk);
	}

	public List<Perk> getPerks() {
		return new ArrayList<Perk>(perks.values());
	}

	public Perk getPerk(String perkName) {
		return (perks.containsKey(perkName) ? perks.get(perkName) : null);
	}

}
