package com.caved_in.teamdeathmatch.perks.Perks;

import com.caved_in.commons.time.TimeHandler;
import com.caved_in.teamdeathmatch.perks.Perk;
import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

public class Speed1 extends Perk {

	public Speed1() {
		super("Nimbleness", 1500, new String[]{ChatColor.YELLOW + "Run faster and further making", ChatColor.YELLOW + "you harder to hit, and kill!", "",
				ChatColor.GREEN + "Increases your run & walk speed when active."});
	}

	@Override
	public List<PotionEffect> getEffects() {
		return Arrays.asList(new PotionEffect(PotionEffectType.SPEED, (int) TimeHandler.getTimeInTicks(25, TimeHandler.TimeType.Minutes), 1));
	}

}
