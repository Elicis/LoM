package de.elicis.lom.shop;

import java.util.ArrayList;
import java.util.HashMap;

public class ShopProxy {
	HashMap<Integer, ArrayList<ShopItem>> store = new HashMap<Integer,ArrayList<ShopItem>>();
	
	public ShopProxy(){
		ArrayList<ShopItem> emptypage = new ArrayList<ShopItem>();
		store.put(0, emptypage);
		store.put(1, emptypage);
		store.put(2, emptypage);
		store.put(3, emptypage);
		store.put(4, emptypage);
		store.put(5, emptypage);
		store.put(6, emptypage);
		store.put(7, emptypage);
	}
	public void addItem(int i, ShopItem item){
		store.get(i).add(item);
	}
	public ShopItem getItem(int i, int n){
		if(store.get(i) != null){
			return store.get(i).get(n);
		}else{
			return null;
		}
	}
}
