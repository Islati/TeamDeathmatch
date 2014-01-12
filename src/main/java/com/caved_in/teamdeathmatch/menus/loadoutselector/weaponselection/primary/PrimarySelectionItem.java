package com.caved_in.teamdeathmatch.menus.loadoutselector.weaponselection.primary;

import com.caved_in.commons.items.ItemHandler;
import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.commons.player.PlayerWrapper;
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
		//Check if its a default gun or they already purchased it
		if (gunData.isDefaultGun() || fPlayer.hasGun(gunID) || gunData.getGunPrice() == 0) {
			fPlayer.getLoadout(loadoutNumber).setPrimary(gunID);
			PlayerHandler.sendMessage(player, String.format("&aThe &e%s&a is now your primary weapon for loadout #&e%s", getText(), loadoutNumber));
			getMenu().switchMenu(player, new LoadoutCreationMenu().getMenu(player));
		} else {
			//Check if they've already clicked this item
			if (!hasAlreadyClicked) {
				hasAlreadyClicked = true;
				PlayerHandler.sendMessage(player, "&eClick again to purchase the " + getText());
			} else {
				//Second click? Begin the purchase!
				PlayerWrapper playerWrapper = PlayerHandler.getData(player.getName());
				double playerBalance = playerWrapper.getCurrency();
				if (playerBalance >= gunData.getGunPrice()) {
					hasAlreadyClicked = false;
					playerWrapper.removeCurrency(gunData.getGunPrice());
					PlayerHandler.updateData(playerWrapper);
					fPlayer.unlockGun(gunID);
					PlayerHandler.sendMessage(player, String.format("&bYou've unlocked the &e%s&b! You have &a%s&b Tunnels XP Remaining",getText(), (int)playerWrapper.getCurrency()));
					fPlayer.getLoadout(loadoutNumber).setPrimary(gunID);
					PlayerHandler.sendMessage(player, String.format("&aThe &e%s&a is now your primary weapon for loadout #&e%s", getText(), loadoutNumber));
					getMenu().switchMenu(player, new LoadoutCreationMenu().getMenu(player));
				} else {
					PlayerHandler.sendMessage(player, "&cYou don't have enough XP to unlock this.");
					hasAlreadyClicked = false;
				}
			}
		}
	}

}
