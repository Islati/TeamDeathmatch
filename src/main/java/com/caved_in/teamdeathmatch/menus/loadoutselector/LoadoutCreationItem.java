package com.caved_in.teamdeathmatch.menus.loadoutselector;

import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.teamdeathmatch.menus.loadoutselector.weaponselection.selectiontypemenu.SelectionMenu;
import me.xhawk87.PopupMenuAPI.MenuItem;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

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
