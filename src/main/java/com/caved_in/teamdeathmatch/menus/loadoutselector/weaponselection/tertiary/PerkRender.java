package com.caved_in.teamdeathmatch.menus.loadoutselector.weaponselection.tertiary;

import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.fPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PerkRender {
	public static List<PerkMenuItem> renderPerks(int selectedLoadout, Player player) {
		List<PerkMenuItem> perkMenuItems = new ArrayList<PerkMenuItem>();
		fPlayer fPlayer = FakeboardHandler.getPlayer(player);
		for (com.caved_in.teamdeathmatch.perks.Perk perk : TDMGame.perkHandler.getPerks()) {
			if (!perk.getPerkName().equalsIgnoreCase("Nothing")) {
				perkMenuItems.add(new PerkMenuItem(perk, selectedLoadout, fPlayer.hasPerk(perk)));
			}
		}
		return perkMenuItems;
	}
}
