package com.caved_in.teamdeathmatch.fakeboard;

import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.commons.potions.PotionHandler;
import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.guns.GunWrap;
import com.caved_in.teamdeathmatch.loadout.Loadout;
import com.caved_in.teamdeathmatch.perks.Perk;
import com.caved_in.teamdeathmatch.perks.Perks.Nothing;
import com.caved_in.teamdeathmatch.scoreboard.PlayerScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class fPlayer {
	private String playerName = "";
	private String teamName = "";

	private int playerScore = 0;
	private int teamKills = 0;
	private int killStreak = 0;
	private int playerDeaths = 0;

	private static final int afkDuration = (20 * 60) * 10;

	private String primaryGunID = "AK-47";
	private String secondaryGunID = "USP45";

	private PlayerScoreboard playerScoreboard = new PlayerScoreboard();

	private com.caved_in.teamdeathmatch.perks.Perk activePerk;

	private boolean isAfk = false;

	private Set<Perk> playerPerks = new HashSet<>();

	private int activeLoadout = 1;

	private HashMap<Integer, Loadout> playerLoadouts = new HashMap<Integer, Loadout>();

	private Set<String> unlockedGuns = new HashSet<>();

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
		if (!TDMGame.loadoutSQL.hasData(playerName)) {
			List<Loadout> loadouts = new ArrayList<>();
			for (int i = 0; i < getLoadoutLimit(); i++) {
				loadouts.add(new Loadout(playerName, i + 1, primaryGunID, secondaryGunID, activePerk));
			}
			TDMGame.loadoutSQL.insertLoadouts(loadouts);
		}

		if (!TDMGame.perksSQL.hasData(playerName)) {
			TDMGame.perksSQL.insertPerk(new Nothing(), playerName);
		}


		List<String> guns = new ArrayList<String>();
		for (GunWrap Gun : TDMGame.gunHandler.getDefaultGuns()) {
			guns.add(Gun.getGunName());
		}
		TDMGame.gunsSQL.insertGuns(playerName, guns);


		//TODO else if they have data, load that shit
	}

	public void setupPlayerData() {
		for (Loadout Loadout : TDMGame.loadoutSQL.getLoadouts(playerName)) {
			playerLoadouts.put(Loadout.getNumber(), Loadout);
		}

		for (Perk Perk : TDMGame.perksSQL.getPerks(playerName)) {
			playerPerks.add(Perk);
		}

		for (String Gun : TDMGame.gunsSQL.getGuns(playerName)) {
			unlockedGuns.add(Gun);
		}

		if (PlayerHandler.isOnline(playerName)) {
			PlayerHandler.getPlayer(playerName).setScoreboard(playerScoreboard.getScoreboard());
		}
	}


	public PlayerScoreboard getPlayerScoreboard() {
		return playerScoreboard;
	}

	public void updateScoreboard() {
		playerScoreboard.updateScoreboardData(this);
	}

	public void clearScoreboard() {
		if (playerScoreboard != null) {
			playerScoreboard.clearScoreboard();
		}
	}

	public Set<String> getUnlockedGuns() {
		return unlockedGuns;
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
			TDMGame.gunsSQL.insertGun(playerName, ID);
		}
	}


	public int getLoadoutLimit() {
		return (getPlayer().isWhitelisted() ? 9 : 3);
	}

	public Set<Perk> getPlayerPerks() {
		return this.playerPerks;
	}

	public boolean hasPerk(Perk Perk) {
		return this.playerPerks.contains(Perk);
	}

	public void addPerk(Perk Perk) {
		playerPerks.add(Perk);
		TDMGame.perksSQL.insertPerk(Perk, playerName);
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
	 * @param playerName
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
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
		Loadout selectedLoad =
				getLoadout(loadoutNumber);
		if (selectedLoad != null) {
			activeLoadout = loadoutNumber;
			primaryGunID = selectedLoad.getPrimary();
			secondaryGunID = selectedLoad.getSecondary();
			activePerk = selectedLoad.getPerk();
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
		this.isAfk = isAfk;
		Player player = getPlayer();
		if (isAfk) {
			PlayerHandler.addPotionEffect(player, PotionHandler.getPotionEffect(PotionEffectType.INVISIBILITY, 1, afkDuration));
		} else {
			player.removePotionEffect(PotionEffectType.INVISIBILITY);
		}
		PlayerHandler.sendMessage(player, "&7You are " + (isAfk ? "now" : "no longer") + " afk");
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
