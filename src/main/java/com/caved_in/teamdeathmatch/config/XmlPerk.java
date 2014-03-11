package com.caved_in.teamdeathmatch.config;

import com.caved_in.commons.potions.PotionHandler;
import com.caved_in.commons.potions.PotionType;
import com.caved_in.teamdeathmatch.perks.Perk;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.Root;

/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */
@Root
public class XmlPerk {
	@Attribute(name = "perk_name")
	private String perkName = "Nothing";

	@Element(name = "perk_cost")
	private int perkCost = 0;

	@ElementArray(name = "perk_description", entry = "line")
	private String[] perkDescription;

	@Attribute(name = "is_tiered")
	private boolean tiered = false;

	@Attribute(name = "required_perk")
	private String requiredPerk = "";

	@ElementArray(name = "potion_effects")
	private XmlPotionEffect[] xmlPotionEffects;

	private PotionEffect[] potionEffects;

	private Perk perk;

	public XmlPerk(@Attribute(name = "perk_name") String perkName, @Element(name = "perk_cost") int perkCost,
				   @ElementArray(name = "perk_description", entry = "line") String[] perkDescription,
				   @Attribute(name = "is_tiered") boolean tiered,
				   @Attribute(name = "required_perk") String requiredPerk,
				   @ElementArray(name = "potion_effects") XmlPotionEffect[] xmlPotionEffects) {
		this.perkName = perkName;
		this.perkCost = perkCost;
		this.perkDescription = perkDescription;
		this.tiered = tiered;
		this.requiredPerk = requiredPerk;
		this.xmlPotionEffects = xmlPotionEffects;
		init();
	}

	public XmlPerk() {
		perkDescription = new String[]{"&eIt's actually nothing.", "&lHonestly... Nothing", "&oI promise!"};
		xmlPotionEffects = new XmlPotionEffect[]{new XmlPotionEffect("speed", 1)};
		init();
	}

	private void init() {
		//Parse all the potion effects and instance an array
		potionEffects = new PotionEffect[xmlPotionEffects.length];
		for (int i = 0; i < xmlPotionEffects.length; i++) {
			potionEffects[i] = xmlPotionEffects[i].getPotionEffect();
		}
		//Generate the perk
		if (!isTiered()) {
			this.perk = new Perk(perkName, perkCost, perkDescription, potionEffects);
		} else {
			this.perk = new Perk(perkName, perkCost, perkDescription, potionEffects, tiered, requiredPerk);
		}
	}

	public String getRequiredPerk() {
		return requiredPerk;
	}

	public boolean isTiered() {
		return tiered;
	}

	public String[] getPerkDescription() {
		return perkDescription;
	}

	public int getPerkCost() {
		return perkCost;
	}

	public String getPerkName() {
		return perkName;
	}

	public PotionEffect[] getPotionEffects() {
		return potionEffects;
	}

	public Perk getPerk() {
		return perk;
	}

	private class XmlPotionEffect {
		@Element(name = "potion_type")
		private String alias = "";

		@Attribute(name = "level")
		private int level = 1;

		private PotionEffect potionEffect;
		private boolean valid = false;

		public XmlPotionEffect(@Element(name = "potion_type") String alias, @Attribute(name = "level") int level) {
			this.alias = alias;
			this.level = level;
			if (PotionType.isPotionType(alias)) {
				valid = true;
				this.potionEffect = PotionHandler.getPotionEffect(PotionType.getPotionType(alias).getPotionEffectType(), level, Integer.MAX_VALUE);
			} else {
				this.potionEffect = new PotionEffect(PotionEffectType.CONFUSION, 1, 1);
			}
		}

		public boolean isValid() {
			return valid;
		}

		public PotionEffect getPotionEffect() {
			return potionEffect;
		}
	}
}
