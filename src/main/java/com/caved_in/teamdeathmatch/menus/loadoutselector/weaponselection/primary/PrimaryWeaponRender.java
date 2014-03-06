package com.caved_in.teamdeathmatch.menus.loadoutselector.weaponselection.primary;

import com.caved_in.teamdeathmatch.Game;
import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.GamePlayer;
import com.caved_in.teamdeathmatch.guns.GunType;
import com.caved_in.teamdeathmatch.guns.GunWrapper;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PrimaryWeaponRender {

	public static List<PrimarySelectionItem> getPrimaryWeapons(GunType gunType, int loadoutNumber, Player player) {
		List<PrimarySelectionItem> primaryWeapons = new ArrayList<>();
		GamePlayer GamePlayer = FakeboardHandler.getPlayer(player);
		for (GunWrapper gunWrapper : Game.gunHandler.getGuns(gunType)) {
			primaryWeapons.add(new PrimarySelectionItem(gunWrapper, gunWrapper.getItemStack(), loadoutNumber, GamePlayer.hasGun(gunWrapper)));
		}
		return primaryWeapons;
	}

}
