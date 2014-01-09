package com.caved_in.teamdeathmatch.runnables;

import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.fPlayer;
import com.caved_in.teamdeathmatch.gamehandler.GameSetupHandler;
import com.caved_in.teamdeathmatch.perks.Perk;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class RestoreInventory implements Runnable {
	private ItemStack[] inventory = null;
	private String playerName = "";

	public RestoreInventory(String Player) {
		this.playerName = Player;
		this.inventory = FakeboardHandler.getPlayer(Player).getDeathInventory();
	}

	@Override
	public void run() {
		Player rPlayer = Bukkit.getPlayer(this.playerName);
		fPlayer fPlayer = FakeboardHandler.getPlayer(rPlayer);
		if (rPlayer != null) {

			if (fPlayer.getTeam().equalsIgnoreCase("T")) {
				rPlayer.getInventory().setArmorContents(GameSetupHandler.getBlueTeamArmor());
			} else {
				rPlayer.getInventory().setArmorContents(GameSetupHandler.getRedTeamArmor());
			}

			Perk playerPerk = fPlayer.getActivePerk();
			if (playerPerk != null) {
				if (!playerPerk.getPerkName().equalsIgnoreCase("Nothing")) {
					for (PotionEffect Effect : playerPerk.getEffects()) {
						rPlayer.addPotionEffect(Effect);
					}
				}
			}
			rPlayer.getInventory().setContents(this.inventory);
			rPlayer.updateInventory();
		}

	}

}
