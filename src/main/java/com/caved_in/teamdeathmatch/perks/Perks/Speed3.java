package com.caved_in.teamdeathmatch.perks.Perks;

import com.caved_in.teamdeathmatch.perks.Perk;
import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

public class Speed3 extends Perk {

	public Speed3() {
		super("Extreme Nimbleness", 10000, new String[]{ChatColor.YELLOW + "Hurl yourself towards enemies and clear",
				ChatColor.YELLOW + "even the largest of gaps", "", ChatColor.GREEN + "Increases your Jump height when active."}, true, "Enhanced Nimbleness");
	}

	@Override
	public List<PotionEffect> getEffects() {
		return Arrays.asList(new PotionEffect[]{new PotionEffect(PotionEffectType.SPEED, TimeUtils.getTimeInTicks(25, TimeUtils.TimeType.Minute), 3)});
	}

}
