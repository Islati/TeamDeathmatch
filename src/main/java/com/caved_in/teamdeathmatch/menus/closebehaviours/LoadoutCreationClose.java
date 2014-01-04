package com.caved_in.teamdeathmatch.menus.closebehaviours;

import me.xhawk87.PopupMenuAPI.MenuCloseBehaviour;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class LoadoutCreationClose implements MenuCloseBehaviour {
	@Override
	public void onClose(Player player) {
		player.sendMessage(ChatColor.GOLD + "Select your newly created loadout by using /kit");
	}
}
