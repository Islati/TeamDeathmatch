package com.caved_in.teamdeathmatch.menus.loadoutselector.weaponselection.secondary;

import com.caved_in.commons.menu.MenuHandler;
import me.xhawk87.PopupMenuAPI.PopupMenu;
import me.xhawk87.PopupMenuAPI.PopupMenuAPI;

import java.util.List;

/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */
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
