package com.caved_in.teamdeathmatch.menus.loadoutselector.weaponselection.primary;

import com.caved_in.teamdeathmatch.guns.GunType;
import me.xhawk87.PopupMenuAPI.MenuItem;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */
public class PrimaryWeaponTypeMenuItem extends MenuItem {

	private GunType gunType;
	private int loadoutNumber = 0;

	public PrimaryWeaponTypeMenuItem(String text, MaterialData icon, GunType gunType, int loadoutNumber) {
		super(text, icon);
		this.gunType = gunType;
		this.loadoutNumber = loadoutNumber;
	}

	@Override
	public void onClick(Player player) {
		this.getMenu().switchMenu(player, PrimarySelectionMenu.getMenu(PrimaryWeaponRender.getPrimaryWeapons(this.gunType, this.loadoutNumber, player)));
		player.sendMessage(ChatColor.GREEN + "Select your primary weapon");
	}

}
