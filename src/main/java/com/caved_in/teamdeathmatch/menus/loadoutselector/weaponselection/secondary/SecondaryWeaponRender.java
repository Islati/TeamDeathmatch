package com.caved_in.teamdeathmatch.menus.loadoutselector.weaponselection.secondary;

import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.GamePlayer;
import com.caved_in.teamdeathmatch.guns.GunType;
import com.caved_in.teamdeathmatch.guns.GunWrapper;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SecondaryWeaponRender {

	public static List<SecondarySelectionItem> getSecondaryWeapons(GunType gunType, int loadout, Player player) {
		List<SecondarySelectionItem> secondarySelectionItems = new ArrayList<>();
		GamePlayer GamePlayer = FakeboardHandler.getPlayer(player);
		for (GunWrapper gunWrapper : TDMGame.gunHandler.getGuns(gunType)) {
			secondarySelectionItems.add(new SecondarySelectionItem(gunWrapper, gunWrapper.getItemStack(), loadout, GamePlayer.hasGun(gunWrapper)));
		}
		return secondarySelectionItems;
	}

}
