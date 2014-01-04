package com.caved_in.teamdeathmatch.runnables;

import com.caved_in.commons.handlers.Items.ItemHandler;
import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.fPlayer;
import com.caved_in.teamdeathmatch.perks.Perk;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class RestoreInventory implements Runnable {
	private ItemStack[] Inventory = null;
	private String Player = "";

	public RestoreInventory(String Player) {
		this.Player = Player;
		this.Inventory = FakeboardHandler.getPlayer(Player).getDeathInventory();
	}

	@Override
	public void run() {
		Player rPlayer = Bukkit.getPlayer(this.Player);
		fPlayer fPlayer = FakeboardHandler.getPlayer(rPlayer);
		if (rPlayer != null) {
			if (fPlayer.getTeam().equalsIgnoreCase("T")) {
				rPlayer.getInventory().setArmorContents(new ItemStack[]{ItemHandler.makeLeatherItemStack(Material.LEATHER_HELMET, Color.BLUE),
						ItemHandler.makeLeatherItemStack(Material.LEATHER_CHESTPLATE, Color.BLUE), ItemHandler.makeLeatherItemStack(Material.LEATHER_LEGGINGS,
						Color.BLUE), ItemHandler.makeLeatherItemStack(Material.LEATHER_BOOTS, Color.BLUE)});
			} else {
				rPlayer.getInventory().setArmorContents(new ItemStack[]{ItemHandler.makeLeatherItemStack(Material.LEATHER_HELMET, Color.RED),
						ItemHandler.makeLeatherItemStack(Material.LEATHER_CHESTPLATE, Color.RED), ItemHandler.makeLeatherItemStack(Material.LEATHER_LEGGINGS,
						Color.RED), ItemHandler.makeLeatherItemStack(Material.LEATHER_BOOTS, Color.RED)});
			}
			Perk playerPerk = fPlayer.getActivePerk();
			if (playerPerk != null) {
				if (!playerPerk.getPerkName().equalsIgnoreCase("Nothing")) {
					for (PotionEffect Effect : playerPerk.getEffects()) {
						rPlayer.addPotionEffect(Effect);
					}
				}
			}
			rPlayer.getInventory().setContents(this.Inventory);
			rPlayer.updateInventory();
		}

	}

}
