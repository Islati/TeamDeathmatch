package com.caved_in.teamdeathmatch.runnables;

import com.caved_in.commons.handlers.Player.PlayerHandler;
import com.caved_in.commons.handlers.Utilities.StringUtil;
import com.caved_in.teamdeathmatch.TDMGame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class MessageRunnable implements Runnable {
	int lastMessageIndex = 0;
	int currentMessageIndex = 0;

	public MessageRunnable() {

	}

	@Override
	public void run() {
		if (currentMessageIndex >= TDMGame.messages.size()) {
			currentMessageIndex = 0;
		}

		String sendingMessage = StringUtil.formatColorCodes(TDMGame.messages.get(currentMessageIndex));
		boolean isPremiumMessage = false;
		if (sendingMessage.toLowerCase().contains("premium")) {
			isPremiumMessage = true;
		}

		for (Player player : Bukkit.getOnlinePlayers()) {
			if (isPremiumMessage == true) {
				if (!PlayerHandler.isPremium(player)) {
					player.sendMessage(sendingMessage);
				}
			} else {
				player.sendMessage(sendingMessage);
			}
		}
		currentMessageIndex += 1;
	}
}