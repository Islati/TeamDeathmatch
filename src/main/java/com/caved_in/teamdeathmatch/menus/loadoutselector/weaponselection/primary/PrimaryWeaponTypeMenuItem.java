package com.caved_in.teamdeathmatch.menus.loadoutselector.weaponselection.primary;

import com.caved_in.teamdeathmatch.guns.GunType;
import me.xhawk87.PopupMenuAPI.MenuItem;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

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
