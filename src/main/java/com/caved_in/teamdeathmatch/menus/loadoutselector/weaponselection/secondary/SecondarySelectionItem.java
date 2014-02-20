package com.caved_in.teamdeathmatch.menus.loadoutselector.weaponselection.secondary;

import com.caved_in.commons.items.ItemHandler;
import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.commons.player.PlayerWrapper;
import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.GamePlayer;
import com.caved_in.teamdeathmatch.guns.GunWrap;
import com.caved_in.teamdeathmatch.menus.loadoutselector.LoadoutCreationMenu;
import me.xhawk87.PopupMenuAPI.MenuItem;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.List;

public class SecondarySelectionItem extends MenuItem {
	private String gunID = "";
	private int loadoutNumber = 1;
	private GunWrap gunData;
	private boolean hasAlreadyClicked = false;


	public SecondarySelectionItem(GunWrap gunWrapper, ItemStack gunItemStack, int loadoutNumber, boolean purchased) {
		super(ItemHandler.getItemName(gunItemStack), new MaterialData(gunItemStack.getType()));
		this.gunData = gunWrapper;
		this.gunID = gunWrapper.getGunName();
		List<String> itemDescription = ItemHandler.getItemLore(gunItemStack);
		itemDescription.add("");
		itemDescription.add(purchased ? ChatColor.GREEN + "You've unlocked this Item!" : ChatColor.AQUA + "Costs " + gunData.getGunPrice() + " XP to unlock");
		this.setDescriptions(itemDescription);
		this.loadoutNumber = loadoutNumber;
	}

	@Override
	public void onClick(Player player) {
		GamePlayer GamePlayer = FakeboardHandler.getPlayer(player);
		if (this.gunData.isDefaultGun() || GamePlayer.hasGun(this.gunID) || this.gunData.getGunPrice() == 0) {
			GamePlayer.getLoadout(this.loadoutNumber).setSecondary(this.gunID);
			player.sendMessage(ChatColor.GREEN + "The '" + this.getText() + "' is now your secondary for loadout #" + this.loadoutNumber);
			this.getMenu().switchMenu(player, new LoadoutCreationMenu().getMenu(player));
		} else {
			if (!this.hasAlreadyClicked) {
				this.hasAlreadyClicked = true;
				player.sendMessage(ChatColor.YELLOW + "Click again to purchase the " + this.getText());
			} else {
				PlayerWrapper playerWrapper = PlayerHandler.getData(player.getName());
				double playerBalance = playerWrapper.getCurrency();
				if (playerBalance >= this.gunData.getGunPrice()) {
					this.hasAlreadyClicked = false;
					playerWrapper.removeCurrency(this.gunData.getGunPrice());
					PlayerHandler.updateData(playerWrapper);
					GamePlayer.unlockGun(this.gunID);
					player.sendMessage(ChatColor.AQUA + "You've unlocked the " + this.getText() + ChatColor.AQUA + "! You have " + ChatColor.GREEN + ((int)
							playerWrapper.getCurrency()) + " XP " + ChatColor.AQUA + "remaining!");
					//Update the players gun
					GamePlayer.getLoadout(this.loadoutNumber).setSecondary(this.gunID);
					player.sendMessage(ChatColor.GREEN + "The '" + this.getText() + "' is now your secondary for loadout #" + this.loadoutNumber);
					this.getMenu().switchMenu(player, new LoadoutCreationMenu().getMenu(player));
				} else {
					player.sendMessage(ChatColor.RED + "You don't have enough XP to unlock this.");
					this.hasAlreadyClicked = false;
				}
			}
		}
	}

}
