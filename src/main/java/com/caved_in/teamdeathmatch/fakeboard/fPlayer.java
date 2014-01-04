package com.caved_in.teamdeathmatch.fakeboard;

import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.guns.GunWrap;
import com.caved_in.teamdeathmatch.loadout.Loadout;
import com.caved_in.teamdeathmatch.perks.Perk;
import com.caved_in.teamdeathmatch.perks.Perks.Nothing;
import com.caved_in.teamdeathmatch.scoreboard.PlayerScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class fPlayer {
	private String playerName = "";
	private String teamName = "";

	private int playerScore = 0;
	private int teamKills = 0;
	private int killStreak = 0;
	private int playerDeaths = 0;

	private String primaryGunID = "AK-47";
	private String secondaryGunID = "USP45";

	private PlayerScoreboard playerScoreboard;

	private com.caved_in.teamdeathmatch.perks.Perk activePerk;

	private boolean isAfk = false;

	private List<Perk> playerPerks = new ArrayList<Perk>();

	private int activeLoadout = 1;

	private HashMap<Integer, Loadout> playerLoadouts = new HashMap<Integer, Loadout>();

	private List<String> unlockedGuns = new ArrayList<String>();

	private List<ItemStack> deathRestoreInventory = new ArrayList<ItemStack>();
	private ItemStack[] deathInventory = new ItemStack[]{};

	/**
	 * Initiates a new fPlayer Instance
	 *
	 * @param Player Players Name
	 */
	public fPlayer(String Player) {
		this.playerName = Player;
		this.activePerk = new Nothing();
		defaultPlayerData();
		setupPlayerData();
	}

	/**
	 * Initiates a new fPlayer Instance
	 *
	 * @param Player Player to make new fPlayer of
	 */
	public fPlayer(Player Player) {
		this.playerName = Player.getName();
		this.activePerk = new Nothing();
		defaultPlayerData();
		setupPlayerData();
	}

	public void defaultPlayerData() {
		if (TDMGame.loadoutSQL.getLoadouts(this.playerName).size() <= 0 || TDMGame.loadoutSQL.getLoadouts(this.playerName).size() < this.getLoadoutLimit()) {
			for (int I = 0; I < this.getLoadoutLimit(); I++) {
				TDMGame.loadoutSQL.insertLoadout(this.playerName, (I + 1), this.primaryGunID, this.secondaryGunID, this.activePerk.getPerkName());
			}
		}

		if (TDMGame.gunsSQL.getGuns(this.playerName).size() < TDMGame.gunHandler.getDefaultGuns().size()) {
			for (GunWrap Gun : TDMGame.gunHandler.getDefaultGuns()) {
				TDMGame.gunsSQL.insertGun(this.playerName, Gun.getGunName());
			}
		}

		if (TDMGame.perksSQL.getPerks(this.playerName).size() <= 0) {
			TDMGame.perksSQL.insertPerk(new Nothing(), this.playerName);
		}
	}

	public void setupPlayerData() {
		for (Loadout Loadout : TDMGame.loadoutSQL.getLoadouts(this.playerName)) {
			this.playerLoadouts.put(Loadout.getNumber(), Loadout);
		}

		for (Perk Perk : TDMGame.perksSQL.getPerks(this.playerName)) {
			this.playerPerks.add(Perk);
		}

		for (String Gun : TDMGame.gunsSQL.getGuns(this.playerName)) {
			this.unlockedGuns.add(Gun);
		}
		this.playerScoreboard = new PlayerScoreboard();
		if (Bukkit.getPlayer(this.playerName) != null) {
			Bukkit.getPlayer(this.playerName).setScoreboard(this.getPlayerScoreboard().getScoreboard());
		}
	}


	public PlayerScoreboard getPlayerScoreboard() {
		return this.playerScoreboard;
	}

	public void updateScoreboard() {
		this.playerScoreboard.updateScoreboardData(this);
	}

	public void clearScoreboard() {
		this.playerScoreboard.clearScoreboard();
	}

	public List<String> getUnlockedGuns() {
		return this.unlockedGuns;
	}

	public boolean hasGun(String ID) {
		return this.unlockedGuns.contains(ID);
	}

	public boolean hasGun(GunWrap Gun) {
		return this.unlockedGuns.contains(Gun.getGunName());
	}

	public void unlockGun(String ID) {
		if (!this.unlockedGuns.contains(ID)) {
			this.unlockedGuns.add(ID);
			TDMGame.gunsSQL.insertGun(this.playerName, ID);
		}
	}


	public int getLoadoutLimit() {
		return (getPlayer().isWhitelisted() ? 9 : 3);
	}

	public List<Perk> getPlayerPerks() {
		return this.playerPerks;
	}

	public boolean hasPerk(Perk Perk) {
		return this.playerPerks.contains(Perk);
	}

	public void addPerk(Perk Perk) {
		if (!hasPerk(Perk)) {
			this.playerPerks.add(Perk);
			TDMGame.perksSQL.insertPerk(Perk, this.playerName);
		}
	}

	/**
	 * Gets the fPlayers Name
	 *
	 * @return
	 */
	public String getPlayerName() {
		return this.playerName;
	}

	/**
	 * Set the name of the fPlayer
	 *
	 * @param Name
	 */
	public void setPlayerName(String Name) {
		this.playerName = Name;
	}

	/**
	 * Gets the Player for the fPlayer in this instance; Uses Bukkit.getPlayer
	 *
	 * @return
	 */
	public Player getPlayer() {
		return Bukkit.getPlayer(this.playerName);
	}

	/**
	 * Gets this fPlayers Score
	 *
	 * @return
	 */
	public int getPlayerScore() {
		return this.playerScore;
	}

	/**
	 * Sets the score for this fPlayer
	 *
	 * @param Score
	 */
	public void setPlayerScore(int Score) {
		this.playerScore = Score;
	}

	/**
	 * Add more to the fPlayers Score
	 *
	 * @param Amount
	 */
	public void addScore(int Amount) {
		this.playerScore += Amount;
	}

	/**
	 * Set the team this fPlayer is on
	 *
	 * @param Name
	 */
	public void setTeam(String Name) {
		this.teamName = Name;
	}

	/**
	 * Gets the name of the team this fPlayer is on
	 *
	 * @return
	 */
	public String getTeam() {
		return this.teamName;
	}

	/**
	 * Gets how many Team-Kills this fPlayer has
	 *
	 * @return
	 */
	public int getTeamKills() {
		return this.teamKills;
	}

	/**
	 * Adds to the Team-Kills this player has
	 *
	 * @param Amount
	 */
	public void addTeamKills(int Amount) {
		this.teamKills += Amount;
	}

	/**
	 * Gets how many kills this fPlayer has on their current Killstreak
	 *
	 * @return
	 */
	public int getKillStreak() {
		return this.killStreak;
	}

	/**
	 * Sets the players Killstreak to 0
	 */
	public void resetKillstreak() {
		this.killStreak = 0;
	}

	/**
	 * Adds the Amount to this players Killstreak
	 *
	 * @param amount
	 */
	public void addKillstreak(int amount) {
		this.killStreak += amount;
	}

	/**
	 * Gets this players Chosen gun
	 *
	 * @return
	 */
	public String getPrimaryGunID() {
		return this.primaryGunID;
	}

	/**
	 * Returns the secondary gun in the players selected loadout
	 *
	 * @return
	 */
	public String getSecondaryGunID() {
		return this.secondaryGunID;
	}

	public String getPrimaryGunID(int loadoutNumber) {
		return this.getLoadout(loadoutNumber).getPrimary();
	}

	public String getSecondaryGunID(int loadoutNumber) {
		return this.getLoadout(loadoutNumber).getSecondary();
	}

	public Perk getPerk(int loadoutNumber) {
		return this.getLoadout(loadoutNumber).getPerk();
	}

	public void setActiveLoadout(int loadoutNumber) {
		Loadout selectedLoad = this.getLoadout(loadoutNumber);
		if (selectedLoad != null) {
			this.activeLoadout = loadoutNumber;
			this.primaryGunID = selectedLoad.getPrimary();
			this.secondaryGunID = selectedLoad.getSecondary();
			this.activePerk = selectedLoad.getPerk();
		}
	}

	/**
	 * Gets this players inventory to restore after death
	 *
	 * @return
	 */
	public List<ItemStack> getDeathInventoryAsList() {
		return this.deathRestoreInventory;
	}

	/**
	 * Gets this players inventory to restore after death in an ItemStack[]
	 *
	 * @return
	 */
	public ItemStack[] getDeathInventory() {
		return this.deathInventory;
	}

	/**
	 * Sets the items that this player will get after death
	 *
	 * @param items
	 */
	public void setDeathInventory(List<ItemStack> items) {
		this.deathRestoreInventory = items;
	}

	/**
	 * Sets the items that this player will get after death
	 *
	 * @param deathInventory
	 */
	public void setDeathInventory(ItemStack[] deathInventory) {
		this.deathRestoreInventory = Arrays.asList(deathInventory);
		this.deathInventory = deathInventory;
	}

	/**
	 * Clears the inventory data for this player
	 */
	public void clearDeathInventory() {
		this.deathRestoreInventory.clear();
		this.deathInventory = new ItemStack[]{};
	}

	public Loadout getLoadout(int loadoutNumber) {
		return (playerLoadouts.containsKey(loadoutNumber) ? playerLoadouts.get(loadoutNumber) : null);
	}

	public void giveActiveLoadout() {
		Player player = this.getPlayer();
		TDMGame.crackShotAPI.giveWeapon(player, getPrimaryGunID(), 1);
		TDMGame.crackShotAPI.giveWeapon(player, getSecondaryGunID(), 1);

		for (PotionEffect potionEffect : getActivePerk().getEffects()) {
			player.addPotionEffect(potionEffect);
		}
	}

	public void setAfk(boolean isAfk) {
		Player player = getPlayer();
		if (isAfk == true) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, (20 * 60) * 10, 1));
		} else {
			player.removePotionEffect(PotionEffectType.INVISIBILITY);
		}
		this.isAfk = isAfk;
		player.sendMessage(ChatColor.GRAY + "You are" + (this.isAfk() ? " now afk" : " no longer afk"));
	}

	public boolean isAfk() {
		return this.isAfk;
	}

	public Perk getActivePerk() {
		return this.activePerk;
	}

	public int getPlayerDeaths() {
		return this.playerDeaths;
	}

	public void resetDeaths() {
		this.playerDeaths = 0;
	}

	public void addDeath() {
		this.playerDeaths += 1;
	}
}
