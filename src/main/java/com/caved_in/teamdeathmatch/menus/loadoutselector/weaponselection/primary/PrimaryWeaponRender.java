package com.caved_in.teamdeathmatch.menus.loadoutselector.weaponselection.primary;

import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.fPlayer;
import com.caved_in.teamdeathmatch.guns.GunType;
import com.caved_in.teamdeathmatch.guns.GunWrap;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PrimaryWeaponRender {

	public static List<PrimarySelectionItem> getPrimaryWeapons(GunType gunType, int loadoutNumber, Player player) {
		List<PrimarySelectionItem> primaryWeapons = new ArrayList<PrimarySelectionItem>();
		fPlayer fPlayer = FakeboardHandler.getPlayer(player);
		for (GunWrap gunWrapper : TDMGame.gunHandler.getGuns(gunType)) {
			primaryWeapons.add(new PrimarySelectionItem(gunWrapper, gunWrapper.getItemStack(), loadoutNumber, fPlayer.hasGun(gunWrapper)));
		}
		return primaryWeapons;
	}

}
