package com.caved_in.teamdeathmatch.menus.loadoutselector.weaponselection.secondary;

import com.caved_in.commons.handlers.Menus.MenuHandler;
import me.xhawk87.PopupMenuAPI.PopupMenu;
import me.xhawk87.PopupMenuAPI.PopupMenuAPI;

import java.util.List;

public class SecondarySelectionMenu {
	public static PopupMenu getMenu(List<SecondarySelectionItem> secondarySelectionItems) {
		PopupMenu psMenu = PopupMenuAPI.createMenu("Select a Secondary", MenuHandler.getRows(secondarySelectionItems.size()));
		for (int I = 0; I < secondarySelectionItems.size(); I++) {
			psMenu.addMenuItem(secondarySelectionItems.get(I), I);
		}
		psMenu.setExitOnClickOutside(false);
		return psMenu;
	}
}
