package de.elicis.lom.shop;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import de.elicis.lom.api.LoM_API;
import de.elicis.lom.champions.Champion;
import de.elicis.lom.data.Arena;
import de.elicis.lom.events.ShopBuyEvent;

public class Shop implements Listener{
	int pages;
	ArrayList<Inventory> sites = new ArrayList<Inventory>();

	public Shop() {
		createShop();
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
		Player player = (Player) event.getWhoClicked();
		if(isShopInventory(event.getInventory())){
			if(!event.isCancelled()){
				event.setCancelled(true);
			}
			if(!event.getInventory().getName().equalsIgnoreCase("LoM Shop")){
				ShopItem item = (ShopItem) event.getCurrentItem();
				ShopBuyEvent event1 = new ShopBuyEvent(player, item, item.getPrice());
				if(!event1.isCancelled()){
					if(LoM_API.isInArena(player)){
						Arena a = LoM_API.getArenaP(player);
						if(a.isActive()){
							Champion champ = a.getChamps().get(player.getName());
							if((champ.getMoney() - event1.getPrice()) > 0){
								champ.setMoney(champ.getMoney() - event1.getPrice());
								player.getInventory().addItem(item.getItemStack());
								for(ShopItemType type : item.getItemType()){
									int eff = item.getEffects().get(item.getItemType().indexOf(type));
									switch(type){
									case ARMOR:
										champ.setitemDefense(champ.getItemArmor() + eff);
										break;
									case DAMAGE:
										champ.setitemDamage(champ.getItemDamage() + eff);
										break;
									case HEALTH:
										champ.setitemHealth(champ.getItemHealth() + eff);
										break;
									case MAGICRESISTANCE:
										champ.setitemMagicResist(champ.getitemMagicResist() + eff);
										break;
									case MANA:
										champ.setitemMana(champ.getItemMana() + eff);
										break;
									default:
										break;
									}
								}
							}
						}
					}
				}
			}else{
				//TODO:Call the different InventoryViews
			}
		}
	} 
	
	public void createShop() {
		createMainPage();
	}

	public int getNumPages() {
		pages = sites.size();
		return pages;
	}
	public Inventory getPage(int i){
		Inventory inv = sites.get(i);
		return inv;
	}
	
	public boolean isShopInventory(Inventory inv){
		boolean isShop = false;
		for(Inventory inv2: sites){
			if(inv2.getTitle().equalsIgnoreCase(inv.getTitle())){
				isShop = true;
			}
		}
		return isShop;
	}
	public void createMainPage() {
		Inventory inv1 = Bukkit.createInventory(null, 54, "LoM Shop");
		ItemMeta meta;
		// Redstoneblock
		ItemStack health = new ItemStack(Material.REDSTONE_BLOCK);
		meta = health.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + "Health");
		health.setItemMeta(meta);
		inv1.setItem(10, health);
		// Ironarmor
		ItemStack armor = new ItemStack(Material.IRON_CHESTPLATE);
		meta = armor.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + "Armor");
		armor.setItemMeta(meta);
		inv1.setItem(12, armor);
		// Leather tunic (blue)
		ItemStack magicresist = new ItemStack(Material.LEATHER_CHESTPLATE);
		LeatherArmorMeta meta2 = (LeatherArmorMeta) magicresist.getItemMeta();
		meta2.setDisplayName(ChatColor.GOLD + "Magigresistance");
		meta2.setColor(Color.BLUE);
		magicresist.setItemMeta(meta2);
		inv1.setItem(14, magicresist);
		// Iron Sword
		ItemStack damage = new ItemStack(Material.IRON_SWORD);
		meta = damage.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + "Damage");
		damage.setItemMeta(meta);
		inv1.setItem(29, damage);
		// Diamond hoe
		ItemStack ability = new ItemStack(Material.DIAMOND_HOE);
		meta = ability.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + "Abilitypower");
		ability.setItemMeta(meta);
		inv1.setItem(33, ability);
		// Lapisblock
		ItemStack mana = new ItemStack(Material.LAPIS_BLOCK);
		meta = mana.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + "Mana");
		mana.setItemMeta(meta);
		inv1.setItem(16, mana);
		// Health potion
		ItemStack consume = new ItemStack(Material.POTION, 1, (short) 8261);
		meta = consume.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + "Consume");
		consume.setItemMeta(meta);
		inv1.setItem(31, consume);
		sites.add(inv1);
	}
	public void createHealthPage(){
		List<String> list = new ArrayList<String>();
		Inventory inv = Bukkit.createInventory(null, 54, "Health");
		//Ruby Crystal
		ShopItem ruby = new ShopItem(Material.REDSTONE_BLOCK,ShopItemType.HEALTH, 180, 475);
		ruby.getItemMeta().setDisplayName("Ruby Crystal");
		list.add("Health +180");
		ruby.getItemMeta().setLore(list);
		inv.addItem(ruby);
		//Giants Belt
		ShopItem giant = new ShopItem(Material.LEASH, ShopItemType.HEALTH, 360, 1000);
		giant.getItemMeta().setDisplayName("Giant's Belt");
		list.clear();
		list.add("Health +380");
		giant.getItemMeta().setLore(list);
		inv.addItem(giant);
		//
		sites.add(inv);
	}
}
