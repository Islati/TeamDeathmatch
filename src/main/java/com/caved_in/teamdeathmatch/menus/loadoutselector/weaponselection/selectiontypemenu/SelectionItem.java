package com.caved_in.teamdeathmatch.menus.loadoutselector.weaponselection.selectiontypemenu;

import com.caved_in.teamdeathmatch.Game.LoadoutSlot;
import com.caved_in.teamdeathmatch.menus.loadoutselector.weaponselection.primary.PrimaryWeaponTypeMenu;
import com.caved_in.teamdeathmatch.menus.loadoutselector.weaponselection.secondary.SecondaryWeaponTypeMenu;
import com.caved_in.teamdeathmatch.menus.loadoutselector.weaponselection.tertiary.PerkSelectionMenu;
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
public class SelectionItem extends MenuItem {

	private LoadoutSlot loadoutSlot;
	private int loadoutNumber = 0;

	public SelectionItem(String text, MaterialData icon, LoadoutSlot loadoutSlot, int loadoutNumber) {
		super(text, icon);
		this.loadoutSlot = loadoutSlot;
		this.loadoutNumber = loadoutNumber;
	}

	@Override
	public void onClick(Player player) {
		switch (this.loadoutSlot) {
			case Primary:
				getMenu().switchMenu(player, new PrimaryWeaponTypeMenu(loadoutNumber).getMenu());
				player.sendMessage(ChatColor.GREEN + "Select your primary weapon type");
				break;
			case Secondary:
				getMenu().switchMenu(player, new SecondaryWeaponTypeMenu(loadoutNumber).getMenu());
				player.sendMessage(ChatColor.GREEN + "Select your secondary weapon type");
				break;
			case Tertiary:
				getMenu().switchMenu(player, new PerkSelectionMenu(loadoutNumber, player).getMenu());
				player.sendMessage(ChatColor.GREEN + "Select your active perk");
				break;
			default:
				break;
		}
	}

}
