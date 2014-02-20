package com.caved_in.teamdeathmatch.menus.loadoutselector.weaponselection.tertiary;

import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.GamePlayer;
import com.caved_in.teamdeathmatch.perks.Perk;
import com.caved_in.teamdeathmatch.perks.PerkHandler;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PerkRender {
	public static List<PerkMenuItem> renderPerks(int selectedLoadout, Player player) {
		List<PerkMenuItem> perkMenuItems = new ArrayList<PerkMenuItem>();
		GamePlayer GamePlayer = FakeboardHandler.getPlayer(player);
		for (Perk perk : PerkHandler.getPerks()) {
			if (!perk.getPerkName().equalsIgnoreCase("Nothing")) {
				perkMenuItems.add(new PerkMenuItem(perk, selectedLoadout, GamePlayer.hasPerk(perk)));
			}
		}
		return perkMenuItems;
	}
}
