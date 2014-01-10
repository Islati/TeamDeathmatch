package com.caved_in.teamdeathmatch.menus.loadoutselector.weaponselection.primary;

import com.caved_in.commons.items.ItemHandler;
import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.commons.player.PlayerWrapper;
import com.caved_in.commons.utilities.StringUtil;
import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.fPlayer;
import com.caved_in.teamdeathmatch.guns.GunWrap;
import com.caved_in.teamdeathmatch.menus.loadoutselector.LoadoutCreationMenu;
import me.xhawk87.PopupMenuAPI.MenuItem;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.List;

public class PrimarySelectionItem extends MenuItem {
	private String gunID = "";
	private int loadoutNumber = 1;
	private GunWrap gunData;
	private boolean hasAlreadyClicked = false;

	public PrimarySelectionItem(GunWrap gunWrapper, ItemStack gunItemStack, int loadoutNumber, boolean purchased) {
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
		fPlayer fPlayer = FakeboardHandler.getPlayer(player);
		if (this.gunData.isDefaultGun() || fPlayer.hasGun(this.gunID) || this.gunData.getGunPrice() == 0) {
			fPlayer.getLoadout(this.loadoutNumber).setPrimary(this.gunID);
			//TDMGame.Console("[Loadout Creation] Primary for " + fPlayer.getPlayerName() + " on loadout " + this.loadoutNumber + " set to " + this.gunID);
			player.sendMessage(StringUtil.formatColorCodes("&aThe '" + this.getText() + "'&a is now your primary for loadout #" + this.loadoutNumber));
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
					PlayerHandler.updateData(playerWrapper); //TODO test if this call is needed
					fPlayer.unlockGun(this.gunID);
					player.sendMessage(StringUtil.formatColorCodes("&bYou've unlocked the " + this.getText() + "&b! You have &a" + ((int) playerWrapper
							.getCurrency()) + "&b XP remaining!"));

					fPlayer.getLoadout(this.loadoutNumber).setPrimary(this.gunID);
					player.sendMessage(StringUtil.formatColorCodes("&aThe '" + this.getText() + "'&a is now your primary for loadout #" + this.loadoutNumber));
					this.getMenu().switchMenu(player, new LoadoutCreationMenu().getMenu(player));
				} else {
					player.sendMessage(StringUtil.formatColorCodes("&cYou don't have enough XP to unlock this."));
					this.hasAlreadyClicked = false;
				}
			}
		}
	}

}
