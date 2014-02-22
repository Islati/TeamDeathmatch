package com.caved_in.teamdeathmatch.menus.closebehaviours;

import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import me.xhawk87.PopupMenuAPI.MenuCloseBehaviour;
import org.bukkit.entity.Player;

public class LoadoutMenuCloseBehaviour implements MenuCloseBehaviour {

	private static LoadoutMenuCloseBehaviour instance = null;

	protected LoadoutMenuCloseBehaviour() {

	}

	public static LoadoutMenuCloseBehaviour getInstance() {
		//If there's no instance of this behaviour, create one
		if (instance == null) {
			//Synchronize it to make it thread safe. Syncing inside the
			//Method as opposed to the entire method itself for performance increases
			//And lazy instancing.
			synchronized (LoadoutMenuCloseBehaviour.class) {
				//Double check the instance is null for sync assurance
				if (instance == null) {
					instance = new LoadoutMenuCloseBehaviour();
				}
			}
		}
		return instance;

	}

	@Override
	public void onClose(Player player) {
		//When the player closes the menu, remove their 'AFK' status
		FakeboardHandler.getPlayer(player).setAfk(false, false);
	}
}
