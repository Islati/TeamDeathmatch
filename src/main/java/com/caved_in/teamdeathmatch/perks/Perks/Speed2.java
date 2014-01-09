package com.caved_in.teamdeathmatch.perks.Perks;

import com.caved_in.commons.time.TimeHandler;
import com.caved_in.teamdeathmatch.perks.Perk;
import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

public class Speed2 extends Perk {

	public Speed2() {
		super("Enhanced Nimbleness", 5000, new String[]{ChatColor.YELLOW + "Sure, you were fast before", ChatColor.YELLOW + "But you're even faster with " +
				"this!", "", ChatColor.GREEN + "Increases your run & walk speed when active."}, true, "Nimbleness");
	}

	@Override
	public List<PotionEffect> getEffects() {
		return Arrays.asList(new PotionEffect(PotionEffectType.SPEED, (int) TimeHandler.getTimeInTicks(25, TimeHandler.TimeType.Minutes), 2));
	}

}
