package com.caved_in.teamdeathmatch.menus.loadoutselector;

import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.teamdeathmatch.menus.loadoutselector.weaponselection.selectiontypemenu.SelectionMenu;
import me.xhawk87.PopupMenuAPI.MenuItem;
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
public class LoadoutCreationItem extends MenuItem {
	private int loadoutNumber = 0;

	public LoadoutCreationItem(String text, MaterialData icon, int loadoutNumber) {
		super(text, icon);
		this.loadoutNumber = loadoutNumber;
	}

	@Override
	public void onClick(Player player) {
		this.getMenu().switchMenu(player, new SelectionMenu(this.loadoutNumber).getMenu());
		PlayerHandler.sendMessage(player, "&aSelect which item you're editing; Primary or Secondary?");
	}

}
