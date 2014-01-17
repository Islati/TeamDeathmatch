package com.caved_in.teamdeathmatch.menus.loadoutselector;

import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.fPlayer;
import com.caved_in.teamdeathmatch.gamehandler.GameSetupHandler;
import me.xhawk87.PopupMenuAPI.MenuItem;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;

public class LoadoutSelectionItem extends MenuItem {
	private int selectedLoadout = 0;

	public LoadoutSelectionItem(String text, MaterialData icon, int loadoutNumber) {
		super(text, icon);
		this.selectedLoadout = loadoutNumber;
	}

	@Override
	public void onClick(Player player) {
		PlayerHandler.clearInventory(player,false);
		fPlayer fPlayer = FakeboardHandler.getPlayer(player);
		fPlayer.setActiveLoadout(selectedLoadout);
		TDMGame.crackShotAPI.giveWeapon(player, fPlayer.getPrimaryGunID(), 1);
		TDMGame.crackShotAPI.giveWeapon(player, fPlayer.getSecondaryGunID(), 1);
		if (fPlayer.getActivePerk() != null && !fPlayer.getActivePerk().getPerkName().equalsIgnoreCase("Nothing")) {
			for (PotionEffect Effect : fPlayer.getActivePerk().getEffects()) {
				player.addPotionEffect(Effect);
			}
		}
		GameSetupHandler.givePlayerLoadoutGem(player);
		getMenu().closeMenu(player);
	}

}
