package com.caved_in.teamdeathmatch.perks.Perks;

import com.caved_in.teamdeathmatch.handlers.misc.TimeUtils.TimeType;
import com.caved_in.teamdeathmatch.perks.Perk;
import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

public class ThunderThighs_One extends Perk {

	public ThunderThighs_One() {
		super("Thunder Thighs", 10000, new String[]{ChatColor.YELLOW + "Run faster, and jump higher", ChatColor.YELLOW + "making you a difficult target"});
	}

	@Override
	public List<PotionEffect> getEffects() {
		return Arrays.asList(new PotionEffect[]{new PotionEffect(PotionEffectType.SPEED, TimeUtils.getTimeInTicks(25, TimeType.Minute), 2),
				new PotionEffect(PotionEffectType.JUMP, TimeUtils.getTimeInTicks(25, TimeType.Minute), 1)});
	}

}
