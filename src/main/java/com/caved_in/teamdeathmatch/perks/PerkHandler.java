package com.caved_in.teamdeathmatch.perks;

import com.caved_in.teamdeathmatch.perks.Perks.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PerkHandler {
	private static HashMap<String, Perk> perks = new HashMap<String, Perk>();

	static {
		initializePerk(new Jump1());
		initializePerk(new Speed1());
		initializePerk(new Speed2());
		initializePerk(new Speed3());
		initializePerk(new Nothing());
		initializePerk(new ThunderThighs_One());
		initializePerk(new Regen1());
	}

	public static void initializePerk(Perk perk) {
		perks.put(perk.getPerkName(), perk);
	}

	public static List<Perk> getPerks() {
		return new ArrayList<Perk>(perks.values());
	}

	public static Perk getPerk(String perkName) {
		return perks.get(perkName);
	}

}
