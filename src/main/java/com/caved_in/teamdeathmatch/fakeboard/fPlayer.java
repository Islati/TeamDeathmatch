package com.caved_in.teamdeathmatch.fakeboard;

import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.commons.potions.PotionHandler;
import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.guns.GunWrap;
import com.caved_in.teamdeathmatch.loadout.Loadout;
import com.caved_in.teamdeathmatch.perks.Perk;
import com.caved_in.teamdeathmatch.perks.Perks.Nothing;
import com.caved_in.teamdeathmatch.scoreboard.PlayerScoreboard;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class fPlayer {
	private static final int PREMIUM_LOADOUT_LIMIT = 9;
	private static final int NON_PREMIUM_LOADOUT_LIMIT = 4;

	private int loadoutSlots = NON_PREMIUM_LOADOUT_LIMIT;

	private String name = "";
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

	private boolean afk = false;

	private Set<Perk> playerPerks = new HashSet<>();

	private int activeLoadout = 1;

	private HashMap<Integer, Loadout> playerLoadouts = new HashMap<Integer, Loadout>();

	private Set<String> unlockedGuns = new HashSet<>();

	private List<ItemStack> deathRestoreInventory = new ArrayList<ItemStack>();
	private ItemStack[] deathInventory = new ItemStack[]{};

	/**
	 * Initiates a new fPlayer Instance
	 *
	 * @param playerName Players Name
	 */
	public fPlayer(String playerName) {
		this.name = playerName;
		this.activePerk = new Nothing();
		defaultPlayerData();
		setupPlayerData();
		//Instance the players loadout slots
		loadoutSlots = getPlayer().isWhitelisted() ? PREMIUM_LOADOUT_LIMIT : NON_PREMIUM_LOADOUT_LIMIT;
	}

	/**
	 * Initiates a new fPlayer Instance
	 *
	 * @param player Player to make new fPlayer of
	 */
	public fPlayer(Player player) {
		this(player.getName());
	}

	public void defaultPlayerData() {
		if (!TDMGame.loadoutSQL.hasData(name)) {
			List<Loadout> loadouts = new ArrayList<>();
			for (int i = 0; i < getLoadoutLimit(); i++) {
				loadouts.add(new Loadout(name, i + 1, primaryGunID, secondaryGunID, activePerk));
			}
			TDMGame.loadoutSQL.insertLoadouts(loadouts);
		}

		if (!TDMGame.perksSQL.hasData(name)) {
			TDMGame.perksSQL.insertPerk(new Nothing(), name);
		}


		List<String> gunNames = new ArrayList<String>();
		for (GunWrap gunWrapper : TDMGame.gunHandler.getDefaultGuns()) {
			gunNames.add(gunWrapper.getGunName());
		}
		TDMGame.gunsSQL.insertGuns(name, gunNames);


		//TODO else if they have data, load that shit
	}

	public void setupPlayerData() {
		for (Loadout loadout : TDMGame.loadoutSQL.getLoadouts(name)) {
			playerLoadouts.put(loadout.getNumber(), loadout);
		}

		for (Perk perk : TDMGame.perksSQL.getPerks(name)) {
			playerPerks.add(perk);
		}

		for (String gun : TDMGame.gunsSQL.getGuns(name)) {
			unlockedGuns.add(gun);
		}

		if (PlayerHandler.isOnline(name)) {
			PlayerHandler.getPlayer(name).setScoreboard(playerScoreboard.getScoreboard());
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

	public boolean hasGun(String gunName) {
		return unlockedGuns.contains(gunName);
	}

	public boolean hasGun(GunWrap gunWrapper) {
		return hasGun(gunWrapper.getGunName());
	}

	public void unlockGun(String gunId) {
		if (!unlockedGuns.contains(gunId)) {
			unlockedGuns.add(gunId);
			TDMGame.gunsSQL.insertGun(name, gunId);
		}
	}


	public int getLoadoutLimit() {
		return (getPlayer().isWhitelisted() ? 9 : 5);
	}

	public Set<Perk> getPlayerPerks() {
		return playerPerks;
	}

	public boolean hasPerk(Perk Perk) {
		return playerPerks.contains(Perk);
	}

	public void addPerk(Perk Perk) {
		playerPerks.add(Perk);
		TDMGame.perksSQL.insertPerk(Perk, name);
	}

	/**
	 * Gets the fPlayers Name
	 *
	 * @return
	 */
	public String getPlayerName() {
		return this.name;
	}

	/**
	 * Set the name of the fPlayer
	 *
	 * @param playerName
	 */
	public void setPlayerName(String playerName) {
		this.name = playerName;
	}

	/**
	 * Gets the Player for the fPlayer in this instance; Uses Bukkit.getPlayer
	 *
	 * @return
	 */
	public Player getPlayer() {
		return PlayerHandler.getPlayer(name);
	}

	/**
	 * Gets this fPlayers Score
	 *
	 * @return
	 */
	public int getPlayerScore() {
		return playerScore;
	}

	/**
	 * Sets the score for this fPlayer
	 *
	 * @param score
	 */
	public void setPlayerScore(int score) {
		playerScore = score;
	}

	/**
	 * Add more to the fPlayers Score
	 *
	 * @param amount
	 */
	public void addScore(int amount) {
		playerScore += amount;
	}

	/**
	 * Set the team this fPlayer is on
	 *
	 * @param teamName
	 */
	public void setTeam(String teamName) {
		this.teamName = teamName;
	}

	/**
	 * Gets the name of the team this fPlayer is on
	 *
	 * @return
	 */
	public String getTeam() {
		return teamName;
	}

	/**
	 * Gets how many Team-Kills this fPlayer has
	 *
	 * @return
	 */
	public int getTeamKills() {
		return teamKills;
	}

	/**
	 * Adds to the Team-Kills this player has
	 *
	 * @param amount
	 */
	public void addTeamKills(int amount) {
		this.teamKills += amount;
	}

	/**
	 * Gets how many kills this fPlayer has on their current Killstreak
	 *
	 * @return
	 */
	public int getKillStreak() {
		return killStreak;
	}

	/**
	 * Sets the players Killstreak to 0
	 */
	public void resetKillstreak() {
		killStreak = 0;
	}

	/**
	 * Adds the Amount to this players Killstreak
	 *
	 * @param amount
	 */
	public void addKillstreak(int amount) {
		killStreak += amount;
	}

	/**
	 * Gets this players Chosen gun
	 *
	 * @return
	 */
	public String getPrimaryGunID() {
		return primaryGunID;
	}

	/**
	 * Returns the secondary gun in the players selected loadout
	 *
	 * @return
	 */
	public String getSecondaryGunID() {
		return secondaryGunID;
	}

	public String getPrimaryGunID(int loadoutNumber) {
		return getLoadout(loadoutNumber).getPrimary();
	}

	public String getSecondaryGunID(int loadoutNumber) {
		return getLoadout(loadoutNumber).getSecondary();
	}

	public Perk getPerk(int loadoutNumber) {
		return getLoadout(loadoutNumber).getPerk();
	}

	public void setActiveLoadout(int loadoutNumber) {
		Loadout selectedLoad = getLoadout(loadoutNumber);
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
		return playerLoadouts.get(loadoutNumber);
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
		this.afk = isAfk;
		Player player = getPlayer();
		if (isAfk) {
			PlayerHandler.addPotionEffect(player, PotionHandler.getPotionEffect(PotionEffectType.INVISIBILITY, 1, afkDuration));
		} else {
			player.removePotionEffect(PotionEffectType.INVISIBILITY);
		}
		PlayerHandler.sendMessage(player, "&7You are " + (isAfk ? "now" : "no longer") + " afk");
	}

	public void toggleAfk() {
		setAfk(!afk);
	}

	public boolean isAfk() {
		return this.afk;
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
