package com.caved_in.teamdeathmatch.gamehandler;

import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.fakeboard.GamePlayer;
import com.caved_in.teamdeathmatch.soundhandler.SoundHandler;
import com.caved_in.teamdeathmatch.soundhandler.SoundHandler.SoundEffect;
import org.bukkit.entity.Player;

public class KillstreakHandler {
	public static void HandleKillStreak(GamePlayer GamePlayer) {
		Player player = GamePlayer.getPlayer();
		int playerKills = GamePlayer.getKillStreak();
		if (playerKills > 0 && playerKills <= 9) {
			switch (playerKills) {
				case 2:
					SoundHandler.playSoundForPLayer(player, SoundEffect.DoubleKill);
					break;
				case 3:
					SoundHandler.playSoundForPLayer(player, SoundEffect.Dominating);
					TDMGame.crackShotAPI.giveWeapon(player, "Grenade", 1);
					break;
				case 4:
					SoundHandler.playSoundForPLayer(player, SoundEffect.UltraKill);
					break;
				case 5:
					SoundHandler.playSoundForPLayer(player, SoundEffect.MegaKill);
					TDMGame.crackShotAPI.giveWeapon(player, "CocoPops", 1);
					break;
				case 6:
					SoundHandler.playSoundForPLayer(player, SoundEffect.Rampage);
					break;
				case 7:
					SoundHandler.playSoundForPLayer(player, SoundEffect.Unstoppable);
					TDMGame.crackShotAPI.giveWeapon(player, "Airstrike", 1);
					break;
				case 8:
					SoundHandler.playSoundForPLayer(player, SoundEffect.Godlike);
					break;
				default:
					break;
			}
		} else if (playerKills > 9) {
			SoundHandler.playSoundForPLayer(player, SoundEffect.HOLYSHIT);
		}
		GamePlayer.getPlayer().updateInventory();
	}
}
