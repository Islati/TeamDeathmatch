package com.caved_in.teamdeathmatch.menus.loadoutselector.weaponselection.tertiary;

import com.caved_in.commons.menus.MenuHandler;
import com.caved_in.teamdeathmatch.menus.closebehaviours.LoadoutMenuCloseBehaviour;
import me.xhawk87.PopupMenuAPI.PopupMenu;
import me.xhawk87.PopupMenuAPI.PopupMenuAPI;
import org.bukkit.entity.Player;

import java.util.List;

public class PerkSelectionMenu {
	private PopupMenu perkMenu;

	public PerkSelectionMenu(int loadoutSlot, Player player) {
		List<PerkMenuItem> perkMenuItems = PerkRender.renderPerks(loadoutSlot, player);
		perkMenu.setMenuCloseBehaviour(LoadoutMenuCloseBehaviour.getInstance());
		this.perkMenu = PopupMenuAPI.createMenu("Select a perk!", MenuHandler.getRows(perkMenuItems.size()));
		for (int I = 0; I < perkMenuItems.size(); I++) {
			this.perkMenu.addMenuItem(perkMenuItems.get(I), I);
		}
		this.perkMenu.setExitOnClickOutside(false);
	}

	public PopupMenu getMenu() {
		return this.perkMenu;
	}
}
