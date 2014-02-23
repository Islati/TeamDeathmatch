package com.caved_in.teamdeathmatch.menus.loadoutselector;

import com.caved_in.commons.items.ItemHandler;
import com.caved_in.commons.menus.MenuHandler;
import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.GamePlayer;
import com.caved_in.teamdeathmatch.gamehandler.GameSetupHandler;
import me.xhawk87.PopupMenuAPI.PopupMenu;
import me.xhawk87.PopupMenuAPI.PopupMenuAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import java.util.Arrays;

public class LoadoutSelectionMenu {
	private PopupMenu sLoadout;

	private LoadoutSelectionMenu(String playerName) {
		GamePlayer gamePlayer = FakeboardHandler.getPlayer(playerName);
		gamePlayer.setAfk(true, false);
		int loadoutLimit = gamePlayer.getLoadoutLimit();
		sLoadout = PopupMenuAPI.createMenu("Select a Loadout", MenuHandler.getRows(loadoutLimit));
		for (int I = 0; I < loadoutLimit; I++) {
			int loadoutNumber = I + 1;
			LoadoutSelectionItem loadoutItem = new LoadoutSelectionItem("Loadout #" + loadoutNumber, new MaterialData(Material.CHEST), loadoutNumber);
			loadoutItem.setDescriptions(Arrays.asList(ItemHandler.getItemName(TDMGame.crackShotAPI.generateWeapon(gamePlayer.getPrimaryGunID(loadoutNumber))),
					ItemHandler.getItemName(TDMGame.crackShotAPI.generateWeapon(gamePlayer.getSecondaryGunID(loadoutNumber)))));
			sLoadout.addMenuItem(loadoutItem, I);
		}
		sLoadout.setExitOnClickOutside(false);
	}

	public LoadoutSelectionMenu(Player player) {
		this(player.getName());
		GameSetupHandler.givePlayerLoadoutGem(player);
	}

	public PopupMenu getMenu() {
		return sLoadout;
	}
}
