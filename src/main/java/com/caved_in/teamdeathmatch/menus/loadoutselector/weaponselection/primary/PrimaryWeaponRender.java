package com.caved_in.teamdeathmatch.menus.loadoutselector.weaponselection.primary;

import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.GamePlayer;
import com.caved_in.teamdeathmatch.guns.GunType;
import com.caved_in.teamdeathmatch.guns.GunWrapper;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PrimaryWeaponRender {

	public static List<PrimarySelectionItem> getPrimaryWeapons(GunType gunType, int loadoutNumber, Player player) {
		List<PrimarySelectionItem> primaryWeapons = new ArrayList<PrimarySelectionItem>();
		GamePlayer GamePlayer = FakeboardHandler.getPlayer(player);
		for (GunWrapper gunWrapper : TDMGame.gunHandler.getGuns(gunType)) {
			primaryWeapons.add(new PrimarySelectionItem(gunWrapper, gunWrapper.getItemStack(), loadoutNumber, GamePlayer.hasGun(gunWrapper)));
		}
		return primaryWeapons;
	}

}
