package com.caved_in.teamdeathmatch.menus.loadoutselector;


import me.xhawk87.PopupMenuAPI.MenuItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 10/01/14
 * Time: 11:37 PM
 */
public class LoadoutItem extends MenuItem {

	private LoadoutAction loadoutAction;
	public LoadoutItem(LoadoutAction loadoutAction) {
		super(loadoutAction == LoadoutAction.SELECT ? "Select a loadout" : "Edit a loadout",new MaterialData(loadoutAction == LoadoutAction.SELECT ? Material.CHEST : Material.EMERALD));
		this.loadoutAction = loadoutAction;
	}

	@Override
	public void onClick(Player player) {
		switch (loadoutAction) {
			case SELECT:
				getMenu().switchMenu(player, new LoadoutSelectionMenu(player.getName()).getMenu());
				break;
			case EDIT:
				getMenu().switchMenu(player, new LoadoutCreationMenu().getMenu(player));
				break;
			default:
				break;
		}
	}

	enum LoadoutAction {
		SELECT,
		EDIT
	}
}
