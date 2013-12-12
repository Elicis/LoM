package de.elicis.lom.shop;

public enum ShopItemType {
	HEALTH(), DAMAGE(), MAGICRESISTANCE(),ARMOR(),MANA(), ABILITYPOWER();
	
	ShopItemType(){
	}
	public String getEffect(){
		return this.name();
	}
	public String getName(){
		return this.name().toString();
	}
}
