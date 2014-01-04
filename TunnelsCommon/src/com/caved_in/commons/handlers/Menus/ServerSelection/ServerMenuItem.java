package com.caved_in.commons.handlers.Menus.ServerSelection;

import java.util.ArrayList;
import java.util.List;

import me.xhawk87.PopupMenuAPI.MenuItem;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public class ServerMenuItem extends MenuItem
{

	private List<String> itemLore;
	private String commandToExecute = "/hub";
	
	/**
	 * 
	 * @param text
	 * @param icon
	 * @param itemLore
	 * @param commandToExecute
	 */
	public ServerMenuItem(String text, MaterialData icon, List<String> itemLore, String commandToExecute)
	{
		super(text, icon);
		List<String> loreSet = new ArrayList<String>();
		for(String lore : itemLore)
		{
			loreSet.add(ChatColor.translateAlternateColorCodes('&', lore));
		}
		this.itemLore = loreSet;
		this.setDescriptions(itemLore);
		this.commandToExecute = commandToExecute;
	}
	
	public String getCommandToExecute()
	{
		return this.commandToExecute;
	}
	
	public List<String> getItemLore()
	{
		return itemLore;
	}
	
	@Override
	public void onClick(Player player)
	{
		player.chat(this.commandToExecute);
	}

}
