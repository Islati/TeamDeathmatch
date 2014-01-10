package com.caved_in.teamdeathmatch.menus.loadoutselector.weaponselection.secondary;

import com.caved_in.teamdeathmatch.guns.GunType;
import me.xhawk87.PopupMenuAPI.PopupMenu;
import me.xhawk87.PopupMenuAPI.PopupMenuAPI;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

public class SecondaryWeaponTypeMenu {
	private PopupMenu wTypeMenu;

	public SecondaryWeaponTypeMenu(int Loadout) {
		this.wTypeMenu = PopupMenuAPI.createMenu("Select a Weapon Type", 1);
		this.wTypeMenu.addMenuItem(new SecondaryWeaponTypeMenuItem("Off-Hand", new MaterialData(Material.GOLDEN_CARROT), GunType.PISTOL, Loadout), 0);
		this.wTypeMenu.setExitOnClickOutside(false);
	}

	public PopupMenu getMenu() {
		return this.wTypeMenu;
	}

}
