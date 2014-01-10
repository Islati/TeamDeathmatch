package com.caved_in.teamdeathmatch.menus.loadoutselector.weaponselection.primary;

import com.caved_in.teamdeathmatch.guns.GunType;
import me.xhawk87.PopupMenuAPI.PopupMenu;
import me.xhawk87.PopupMenuAPI.PopupMenuAPI;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

public class PrimaryWeaponTypeMenu {
	private PopupMenu wTypeMenu;

	public PrimaryWeaponTypeMenu(int Loadout) {
		this.wTypeMenu = PopupMenuAPI.createMenu("Select a Weapon Type", 1);
		this.wTypeMenu.addMenuItem(new PrimaryWeaponTypeMenuItem("Assault Rifles", new MaterialData(Material.GOLDEN_CARROT), GunType.ASSAULT, Loadout), 0);
		this.wTypeMenu.addMenuItem(new PrimaryWeaponTypeMenuItem("Shotguns", new MaterialData(Material.STONE_HOE), GunType.SHOTGUN, Loadout), 1);
		this.wTypeMenu.addMenuItem(new PrimaryWeaponTypeMenuItem("Sniper Rifles", new MaterialData(Material.GOLD_PICKAXE), GunType.SNIPER, Loadout), 2);
		this.wTypeMenu.addMenuItem(new PrimaryWeaponTypeMenuItem("Special Weapons", new MaterialData(Material.DIAMOND_PICKAXE), GunType.SPECIAL, Loadout), 3);
		this.wTypeMenu.setExitOnClickOutside(false);
	}

	public PopupMenu getMenu() {
		return this.wTypeMenu;
	}

}
