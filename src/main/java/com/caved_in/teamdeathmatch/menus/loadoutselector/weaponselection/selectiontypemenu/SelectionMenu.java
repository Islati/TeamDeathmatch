package com.caved_in.teamdeathmatch.menus.loadoutselector.weaponselection.selectiontypemenu;

import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.menus.closebehaviours.LoadoutMenuCloseBehaviour;
import me.xhawk87.PopupMenuAPI.PopupMenu;
import me.xhawk87.PopupMenuAPI.PopupMenuAPI;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

public class SelectionMenu {
	private PopupMenu slotSelectionMenu;

	public SelectionMenu(int loadoutNumber) {
		this.slotSelectionMenu = PopupMenuAPI.createMenu("Which Item?", 1);
		slotSelectionMenu.setMenuCloseBehaviour(LoadoutMenuCloseBehaviour.getInstance());
		this.slotSelectionMenu.addMenuItem(new SelectionItem("Primary Weapon", new MaterialData(Material.DIAMOND_SWORD), TDMGame.LoadoutSlot.Primary, loadoutNumber),
				0);
		this.slotSelectionMenu.addMenuItem(new SelectionItem("Secondary Weapon", new MaterialData(Material.ARROW), TDMGame.LoadoutSlot.Secondary, loadoutNumber), 1);
		this.slotSelectionMenu.addMenuItem(new SelectionItem("Active Perk", new MaterialData(Material.EXP_BOTTLE), TDMGame.LoadoutSlot.Tertiary, loadoutNumber), 2);
		this.slotSelectionMenu.setExitOnClickOutside(false);
	}

	public PopupMenu getMenu() {
		return this.slotSelectionMenu;
	}

}
