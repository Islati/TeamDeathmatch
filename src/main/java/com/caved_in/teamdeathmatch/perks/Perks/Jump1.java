package com.caved_in.teamdeathmatch.perks.Perks;

import com.caved_in.commons.time.TimeHandler;
import com.caved_in.teamdeathmatch.perks.Perk;
import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

public class Jump1 extends Perk {

	public Jump1() {
		super("By Leaps and Bounds", 4500, new String[]{ChatColor.YELLOW + "Hurl yourself towards enemies and clear",
				ChatColor.YELLOW + "even the largest of gaps", "", ChatColor.GREEN + "Increases your Jump height when active."});
	}

	@Override
	public List<PotionEffect> getEffects() {
		return Arrays.asList(new PotionEffect(PotionEffectType.JUMP, (int) TimeHandler.getTimeInTicks(25, TimeHandler.TimeType.Minutes), 1));
	}

}
