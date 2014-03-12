package com.caved_in.teamdeathmatch.menus.loadoutselector.weaponselection.secondary;

import com.caved_in.teamdeathmatch.Game;
import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.GamePlayer;
import com.caved_in.teamdeathmatch.guns.GunType;
import com.caved_in.teamdeathmatch.guns.GunWrapper;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */
public class SecondaryWeaponRender {

	public static List<SecondarySelectionItem> getSecondaryWeapons(GunType gunType, int loadout, Player player) {
		List<SecondarySelectionItem> secondarySelectionItems = new ArrayList<>();
		GamePlayer GamePlayer = FakeboardHandler.getPlayer(player);
		for (GunWrapper gunWrapper : Game.gunHandler.getGuns(gunType)) {
			secondarySelectionItems.add(new SecondarySelectionItem(gunWrapper, gunWrapper.getItemStack(), loadout, GamePlayer.hasGun(gunWrapper)));
		}
		return secondarySelectionItems;
	}

}
