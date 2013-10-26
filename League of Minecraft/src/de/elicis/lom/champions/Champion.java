package de.elicis.lom.champions;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.elicis.lom.champions.skills.Skill;

public abstract class Champion {
	String name;
	String player;
	boolean isReady;
	int money;
	int level;
	Double health;
	int manamax;
	int mana;
	int itemmana;
	Double manaregen;
	Double itemmanaregen;
	Double goldregen;
	Double itemhealth;
	int damage;
	int itemdamage;
	int abilityPower;
	int itemabilityPower;
	int armor;
	int itemarmor;
	int magicResist;
	int itemmagicResist;
	int speed;
	int itemspeed;
	ArrayList<Skill> skills;

	ItemStack weapon;

	public Champion() {
		money = 400;
		itemhealth = (double) 0;
		itemmana = 0;
		itemmanaregen = (double) 0;
		itemdamage = 0;
		itemabilityPower = 0;
		itemarmor = 0;
		itemmagicResist = 0;
		itemspeed = 0;
		isReady = false;
	}

	public String getName() {
		return name;
	}

	public Player getPlayer() {
		Player player2 = Bukkit.getPlayer(player);
		return player2;
	}
	public boolean isReady(){
		return isReady;
	}

	public String getPlayerName() {
		return player;
	}

	public int getMoney() {
		return money;
	}

	public Double getHealth() {
		return health;
	}

	public int getDamage() {
		return damage;
	}

	public int getMaxMana() {
		return manamax;
	}

	public int getMana() {
		return mana;
	}

	public int getItemMana() {
		return itemmana;
	}

	public Double getItemManaRegen() {
		return itemmanaregen;
	}

	public Double getManaRegen() {
		return manaregen;
	}

	public Double getGoldRegen() {
		return goldregen;
	}

	public int getItemDamage() {
		return itemdamage;
	}

	public int getabilityPower() {
		return abilityPower;
	}

	public int getitemabilityPower() {
		return itemabilityPower;
	}

	public int getArmor() {
		return armor;
	}

	public int getItemArmor() {
		return itemarmor;
	}

	public int getMagicResist() {
		return magicResist;
	}

	public int getitemMagicResist() {
		return itemmagicResist;
	}

	public int getSpeed() {
		return speed;
	}

	public int getitemSpeed() {
		return speed;
	}
	
	public ArrayList<Skill> getSkills(){
		return skills;
	}

	public void setMoney(int moneynew) {
		money = moneynew;
	}
	public void setReady(boolean newready){
		isReady = newready;
	}

	public void setDamage(int newint) {
		damage = newint;
	}

	public void setitemDamage(int newint) {
		itemdamage = newint;
	}

	public void setHealth(Double newint) {
		health = newint;
	}

	public void setitemHealth(Double newint) {
		itemhealth = newint;
	}

	public void setitemMana(int newint) {
		itemmana = newint;
	}

	public void setMaxMana(int newint) {
		manamax = newint;
	}

	public void setMana(int newint) {
		mana = newint;
	}

	public void setManaRegen(Double newint) {
		manaregen = newint;
	}

	public void setItemManaRegen(Double newint) {
		itemmanaregen = newint;
	}

	public void setGoldRegen(Double newint) {
		goldregen = newint;
	}

	public void setAbilityPower(int newint) {
		abilityPower = newint;
	}

	public void setitemAbilityPower(int newint) {
		itemabilityPower = newint;
	}

	public void setDefense(int newint) {
		armor = newint;
	}

	public void setitemDefense(int newint) {
		itemarmor = newint;
	}

	public void setMagicResist(int newint) {
		magicResist = newint;
	}

	public void setitemMagicResist(int newint) {
		itemmagicResist = newint;
	}

	public void setSpeed(int newint) {
		speed = newint;
	}

	public void setitemSpeed(int newint) {
		itemspeed = newint;
	}

	public void updateChamp() {
	}

}
