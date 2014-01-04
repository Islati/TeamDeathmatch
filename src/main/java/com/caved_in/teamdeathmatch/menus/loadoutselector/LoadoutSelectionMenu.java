package com.caved_in.teamdeathmatch.menus.loadoutselector;

import com.caved_in.commons.handlers.Items.ItemHandler;
import com.caved_in.commons.handlers.Menus.MenuHandler;
import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.fPlayer;
import me.xhawk87.PopupMenuAPI.PopupMenu;
import me.xhawk87.PopupMenuAPI.PopupMenuAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

import java.util.Arrays;

public class LoadoutSelectionMenu {
	private PopupMenu sLoadout;

	public LoadoutSelectionMenu(String playerName) {
		fPlayer fPlayer = FakeboardHandler.getPlayer(playerName);
		int loadoutLimit = fPlayer.getLoadoutLimit();
		this.sLoadout = PopupMenuAPI.createMenu("Select a Loadout", MenuHandler.getRows(loadoutLimit));
		for (int I = 0; I < loadoutLimit; I++) {
			int loadoutNumber = I + 1;
			LoadoutSelectionItem loadoutItem = new LoadoutSelectionItem("Loadout #" + loadoutNumber, new MaterialData(Material.CHEST), loadoutNumber);
			loadoutItem.setDescriptions(Arrays.asList(ItemHandler.getItemName(TDMGame.crackShotAPI.generateWeapon(fPlayer.getPrimaryGunID(loadoutNumber))),
					ItemHandler.getItemName(TDMGame.crackShotAPI.generateWeapon(fPlayer.getSecondaryGunID(loadoutNumber)))));
			this.sLoadout.addMenuItem(loadoutItem, I);
		}
		this.sLoadout.setExitOnClickOutside(false);
		this.sLoadout.openMenu(Bukkit.getPlayer(playerName));
	}

}
