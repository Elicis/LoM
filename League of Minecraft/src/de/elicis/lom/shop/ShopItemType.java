package de.elicis.lom.shop;

public enum ShopItemType {
	HEALTH(), DAMAGE(), MAGICRESISTANCE(),ARMOR(),MANA();
	
	ShopItemType(){
	}
	public String getEffect(){
		return this.name();
	}
	public String getName(){
		return this.name();
	}
}
