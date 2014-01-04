package com.caved_in.teamdeathmatch.menus.loadoutselector;

import com.caved_in.commons.handlers.Items.ItemHandler;
import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.fPlayer;
import me.xhawk87.PopupMenuAPI.PopupMenu;
import me.xhawk87.PopupMenuAPI.PopupMenuAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import java.util.Arrays;

public class LoadoutCreationMenu {

	public LoadoutCreationMenu(Player player) {
		getMenu(player).openMenu(player);
	}

	public LoadoutCreationMenu() {
	}

	public PopupMenu getMenu(Player player) {
		PopupMenu loadoutCreationMenu = PopupMenuAPI.createMenu("Create a Loadout", 1);
		fPlayer fPlayer = FakeboardHandler.getPlayer(player);
		int loadoutLimit = fPlayer.getLoadoutLimit();
		for (int I = 0; I < loadoutLimit; I++) {
			int loadoutNumber = I + 1;
			LoadoutCreationItem loadoutCreationItem = new LoadoutCreationItem("Loadout #" + loadoutNumber, new MaterialData(Material.CHEST), loadoutNumber);
			loadoutCreationItem.setDescriptions(Arrays.asList(ItemHandler.getItemName(TDMGame.crackShotAPI.generateWeapon(fPlayer.getPrimaryGunID
					(loadoutNumber))), ItemHandler.getItemName(TDMGame.crackShotAPI.generateWeapon(fPlayer.getSecondaryGunID(loadoutNumber)))));
			loadoutCreationMenu.addMenuItem(loadoutCreationItem, I);
		}

		loadoutCreationMenu.setExitOnClickOutside(false);
		return loadoutCreationMenu;
	}
}
