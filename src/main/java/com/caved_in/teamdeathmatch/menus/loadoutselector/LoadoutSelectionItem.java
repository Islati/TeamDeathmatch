package com.caved_in.teamdeathmatch.menus.loadoutselector;

import com.caved_in.teamdeathmatch.events.CustomEventHandler;
import com.caved_in.teamdeathmatch.events.LoadoutSelectEvent;
import me.xhawk87.PopupMenuAPI.MenuItem;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public class LoadoutSelectionItem extends MenuItem {
	private int selectedLoadout = 0;

	public LoadoutSelectionItem(String text, MaterialData icon, int loadoutNumber) {
		super(text, icon);
		this.selectedLoadout = loadoutNumber;
	}

	@Override
	public void onClick(Player player) {
		//Create a loadout selection event and call it, then close this menu
		LoadoutSelectEvent loadoutSelectEvent = new LoadoutSelectEvent(player, selectedLoadout);
		CustomEventHandler.handleLoadoutSelectEvent(loadoutSelectEvent);
		getMenu().closeMenu(player);
	}

}
