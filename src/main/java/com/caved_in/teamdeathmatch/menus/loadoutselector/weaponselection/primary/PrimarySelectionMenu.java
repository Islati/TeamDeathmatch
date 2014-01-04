package com.caved_in.teamdeathmatch.menus.loadoutselector.weaponselection.primary;

import com.caved_in.commons.handlers.Menus.MenuHandler;
import me.xhawk87.PopupMenuAPI.PopupMenu;
import me.xhawk87.PopupMenuAPI.PopupMenuAPI;

import java.util.List;

public class PrimarySelectionMenu {

	public static PopupMenu getMenu(List<PrimarySelectionItem> primarySelectionItems) {
		PopupMenu psMenu = PopupMenuAPI.createMenu("Select a Weapon", MenuHandler.getRows(primarySelectionItems.size()));
		for (int I = 0; I < primarySelectionItems.size(); I++) {
			psMenu.addMenuItem(primarySelectionItems.get(I), I);
		}
		psMenu.setExitOnClickOutside(false);
		return psMenu;
	}
}
