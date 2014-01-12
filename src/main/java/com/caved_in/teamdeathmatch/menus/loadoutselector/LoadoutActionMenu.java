package com.caved_in.teamdeathmatch.menus.loadoutselector;

import me.xhawk87.PopupMenuAPI.PopupMenu;
import org.bukkit.entity.Player;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 12/01/14
 * Time: 12:24 AM
 */
public class LoadoutActionMenu extends PopupMenu {

	public LoadoutActionMenu(Player player) {
		super("Select & Edit Loadouts",1);
		addMenuItem(new LoadoutItem(LoadoutItem.LoadoutAction.SELECT),0);
		addMenuItem(new LoadoutItem(LoadoutItem.LoadoutAction.EDIT),1);
		openMenu(player);
	}
}
