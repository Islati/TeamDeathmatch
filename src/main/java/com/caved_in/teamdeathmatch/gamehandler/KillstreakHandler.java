package com.caved_in.teamdeathmatch.gamehandler;

import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.fakeboard.fPlayer;
import com.caved_in.teamdeathmatch.soundhandler.SoundHandler;
import com.caved_in.teamdeathmatch.soundhandler.SoundHandler.SoundEffect;

public class KillstreakHandler {
	public static void HandleKillStreak(fPlayer player) {
		if (player.getKillStreak() > 0) {
			if (player.getKillStreak() <= 9) {
				switch (player.getKillStreak()) {
					case 2:
						SoundHandler.playSoundForPLayer(player.getPlayer(), SoundEffect.DoubleKill);
						break;
					case 3:
						SoundHandler.playSoundForPLayer(player.getPlayer(), SoundEffect.Dominating);
						TDMGame.crackShotAPI.giveWeapon(player.getPlayer(), "Grenade", 1);
						break;
					case 4:
						SoundHandler.playSoundForPLayer(player.getPlayer(), SoundEffect.UltraKill);
						break;
					case 5:
						SoundHandler.playSoundForPLayer(player.getPlayer(), SoundEffect.MegaKill);
						TDMGame.crackShotAPI.giveWeapon(player.getPlayer(), "CocoPops", 1);
						break;
					case 6:
						SoundHandler.playSoundForPLayer(player.getPlayer(), SoundEffect.Rampage);
						break;
					case 7:
						SoundHandler.playSoundForPLayer(player.getPlayer(), SoundEffect.Unstoppable);
						TDMGame.crackShotAPI.giveWeapon(player.getPlayer(), "Airstrike", 1);
						break;
					case 8:
						SoundHandler.playSoundForPLayer(player.getPlayer(), SoundEffect.Godlike);
						break;
					default:
						break;
				}
			} else if (player.getKillStreak() > 9) {
				SoundHandler.playSoundForPLayer(player.getPlayer(), SoundEffect.HOLYSHIT);
				return;
			}
		}
		player.getPlayer().updateInventory();
	}
}
