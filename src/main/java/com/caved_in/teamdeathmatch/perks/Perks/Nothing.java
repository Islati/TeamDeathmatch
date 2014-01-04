package com.caved_in.teamdeathmatch.perks.Perks;

import com.caved_in.teamdeathmatch.perks.Perk;
import org.bukkit.potion.PotionEffect;

import java.util.Arrays;
import java.util.List;

public class Nothing extends Perk {

	public Nothing() {
		super("Nothing", 0, new String[]{""});
	}

	@Override
	public List<PotionEffect> getEffects() {
		return Arrays.asList(new PotionEffect[]{});
	}

}
