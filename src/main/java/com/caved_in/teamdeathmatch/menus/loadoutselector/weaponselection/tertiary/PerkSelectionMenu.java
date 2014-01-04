package com.caved_in.teamdeathmatch.menus.loadoutselector.weaponselection.tertiary;

import com.caved_in.commons.handlers.Menus.MenuHandler;
import me.xhawk87.PopupMenuAPI.PopupMenu;
import me.xhawk87.PopupMenuAPI.PopupMenuAPI;
import org.bukkit.entity.Player;

import java.util.List;

public class PerkSelectionMenu {
	private PopupMenu PerkMenu;

	public PerkSelectionMenu(int loadoutSlot, Player player) {
		List<PerkMenuItem> perkMenuItems = PerkRender.renderPerks(loadoutSlot, player);
		this.PerkMenu = PopupMenuAPI.createMenu("Select a perk!", MenuHandler.getRows(perkMenuItems.size()));
		for (int I = 0; I < perkMenuItems.size(); I++) {
			this.PerkMenu.addMenuItem(perkMenuItems.get(I), I);
		}
		this.PerkMenu.setExitOnClickOutside(false);
	}

	public PopupMenu getMenu() {
		return this.PerkMenu;
	}
}
