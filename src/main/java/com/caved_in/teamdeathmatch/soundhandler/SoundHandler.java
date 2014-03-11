package com.caved_in.teamdeathmatch.soundhandler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */
public class SoundHandler {
	private static String TerroristsWinSound = "mob.horse.breathe2";
	private static String CounterTerroristsWinSound = "mob.horse.breathe1";
	private static String LockNLoad = "mob.horse.breathe3";
	private static String Headshot = "mob.horse.hit";
	private static String Dominating = "mob.horse.so";
	private static String Ultrakill = "mob.horse.soft";
	private static String Doublekill = "mob.horse.leather";
	private static String Megakill = "mob.horse.soft";
	private static String Rampage = "mob.horse.soft";
	private static String Unstoppable = "mob.horse.soft";
	private static String Godlike = "mob.horse.jump";
	private static String HolyShit = "mob.horse.soft";

	public static void playSoundForPLayer(Player Player, SoundEffect Sound) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "playsound " + getSound(Sound) + " " + Player.getName());
	}

	public enum SoundEffect {
		TerroristsWin,
		CounterTerroristsWin,
		LockAndLoad,
		HeadShots,
		DoubleKill,
		Dominating,
		UltraKill,
		MegaKill,
		Rampage,
		Unstoppable,
		Godlike,
		HOLYSHIT

	}

	public static String getSound(SoundEffect Sound) {
		switch (Sound) {
			case CounterTerroristsWin:
				return CounterTerroristsWinSound;
			case HeadShots:
				return Headshot;
			case LockAndLoad:
				return LockNLoad;
			case TerroristsWin:
				return TerroristsWinSound;
			case DoubleKill:
				return Doublekill;
			case Dominating:
				return Dominating;
			case UltraKill:
				return Ultrakill;
			case MegaKill:
				return Megakill;
			case Rampage:
				return Rampage;
			case Unstoppable:
				return Unstoppable;
			case Godlike:
				return Godlike;
			case HOLYSHIT:
				return HolyShit;
			default:
				break;
		}
		return "";
	}
}
