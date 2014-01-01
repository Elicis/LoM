package de.elicis.lom.shop;

import java.util.ArrayList;
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
	ItemStack back;
	ShopProxy proxy;
	public Shop() {
		back = new ItemStack(Material.SNOW_BALL);
		ItemMeta meta = back.getItemMeta();
		meta.setDisplayName("Back");
		back.setItemMeta(meta);
		proxy = new ShopProxy();
		createShop();
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
		Player player = (Player) event.getWhoClicked();
		if(LoM_API.isInArena(player)){
			event.setCancelled(true);
		}
		if(isShopInventory(event.getInventory())){
			event.setCancelled(true);
			if(!event.getInventory().getName().equalsIgnoreCase(sites.get(0).getName()) ){
				if(event.getCurrentItem() != null){
					if(event.getCurrentItem().getType() != Material.AIR){
						if(!(event.getSlot() >= 53)){
							ShopItem item = proxy.getItem(this.getInventoryId(event.getInventory()), event.getSlot());
							ShopBuyEvent event1 = new ShopBuyEvent(player, item, item.getPrice(player));
							if(!event1.isCancelled()){
								if(LoM_API.isInArena(player)){
									Arena a = LoM_API.getArenaP(player);
									if(a.isActive()){
										Champion champ = a.getChamps().get(player.getName());
										if((champ.getMoney() - event1.getPrice()) >= 0){
											champ.setMoney(champ.getMoney() - event1.getPrice());
											player.getInventory().addItem(item.getItemStack());
											player.updateInventory();
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
							if(event.getSlot() == 53){
								player.openInventory(sites.get(0));
							}
						}
					}
				}
			}else{
				ItemStack item = event.getCurrentItem();
				switch(item.getType()){
				case REDSTONE_BLOCK:
					player.openInventory(sites.get(1));
					break;
				case IRON_CHESTPLATE:
					player.openInventory(sites.get(2));
					break;
				case LEATHER_CHESTPLATE:
					player.openInventory(sites.get(3));
					break;
				case IRON_SWORD:
					player.openInventory(sites.get(4));
					break;
				case DIAMOND_HOE:
					player.openInventory(sites.get(5));
					break;
				case LAPIS_BLOCK:
					player.openInventory(sites.get(7));
					break;
				case POTION:
					player.openInventory(sites.get(6));
					break;
				default:
					player.openInventory(sites.get(0));
					break;
				}
			}
		}
		
	} 
	
	public void createShop() {
		createMainPage();
		createHealthPage();
		createArmorPage();
		createMagicResistPage();
		createDamagePage();
		createAbilityPPage();
		createConsumePage();
		createManaPage();
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
		Inventory inv = Bukkit.createInventory(null, 54, "Health");
		//Ruby Crystal
		ShopItem ruby = new ShopItem("Ruby Crystal",Material.REDSTONE_BLOCK,ShopItemType.HEALTH, 180, 475);
		ruby.getItemMeta().setDisplayName("Ruby Crystal");
		inv.addItem(ruby);
		proxy.addItem(1, ruby);
		//Giants Belt
		ShopItem giant = new ShopItem("Giants Belt",Material.LEASH, ShopItemType.HEALTH, 360, 1000);
		giant.getItemMeta().setDisplayName("Giant's Belt");
		inv.addItem(giant);
		proxy.addItem(1, giant);
		//TODO: Add more Items
		inv.setItem(53, back);
		sites.add(inv);
	}
	public void createArmorPage(){
		Inventory inv = Bukkit.createInventory(null, 54, "Armor");
		//Cloth Armor
		ShopItem clArmor = new ShopItem("Cloth Armor", Material.LEATHER_CHESTPLATE, ShopItemType.ARMOR, 15, 300);
		inv.addItem(clArmor);
		proxy.addItem(2, clArmor);
		//Chain Vest
		ShopItem chArmor = new ShopItem("Chain Vest",Material.IRON_CHESTPLATE, ShopItemType.ARMOR, 40, 720);
		inv.addItem(chArmor);
		proxy.addItem(2, chArmor);
		//TODO: Add more Items
		
		inv.setItem(53, back);
		sites.add(inv);
	}
	public void createMagicResistPage(){
		Inventory inv = Bukkit.createInventory(null, 54, "Magicresistance");
		//Null Magic Mantle
		ShopItem nullmagicmantle = new ShopItem("Null Magic Mantle", Material.LEATHER_CHESTPLATE, ShopItemType.MAGICRESISTANCE, 20, 400);
		LeatherArmorMeta meta2 = (LeatherArmorMeta) nullmagicmantle.getItemMeta();
		meta2.setColor(Color.ORANGE);
		nullmagicmantle.setItemMeta(meta2);
		inv.addItem(nullmagicmantle);
		proxy.addItem(3, nullmagicmantle);
		// Negatron Cloak
		ShopItem negatroncloak = new ShopItem("Negatron Cloak", Material.LEATHER_CHESTPLATE, ShopItemType.MAGICRESISTANCE, 40, 720);
		LeatherArmorMeta meta = (LeatherArmorMeta) negatroncloak.getItemMeta();
		meta.setColor(Color.WHITE);
		negatroncloak.setItemMeta(meta);
		inv.addItem(negatroncloak);
		proxy.addItem(3, negatroncloak);
		//TODO: Add more Items
		
		inv.setItem(53, back);
		sites.add(inv);
	}
	public void createDamagePage(){
		Inventory inv = Bukkit.createInventory(null, 54, "Damage");
		//Long Sword
		ShopItem longs = new ShopItem("Long Sword", Material.IRON_SWORD, ShopItemType.DAMAGE, 10, 360);
		inv.addItem(longs);
		proxy.addItem(4, longs);
		//B.F.Sword
		ShopItem bfsword = new ShopItem("B.F.Sword", Material.IRON_SWORD, ShopItemType.DAMAGE, 45, 1550);
		inv.addItem(bfsword);
		proxy.addItem(4, bfsword);
		//TODO: Add more Items
		
		inv.setItem(53, back);
		sites.add(inv);
	}
	public void createAbilityPPage(){
		Inventory inv = Bukkit.createInventory(null, 54, "Abilitypower");
		//Amplifying Tome
		ShopItem ampltome = new ShopItem("Amplifying Tome", Material.ENCHANTED_BOOK, ShopItemType.ABILITYPOWER, 20, 435);
		inv.addItem(ampltome);
		proxy.addItem(5, ampltome);
		//Blasting Wand
		ShopItem blastwand = new ShopItem("Blasting Wand", Material.STICK, ShopItemType.ABILITYPOWER, 40, 860);
		inv.addItem(blastwand);
		proxy.addItem(5, blastwand);
		//Needlessly Large Rod
		ShopItem nedrod = new ShopItem("Needlessly Large Rod", Material.STICK, ShopItemType.ABILITYPOWER, 80, 1600);
		inv.addItem(nedrod);
		proxy.addItem(5, nedrod);
		//TODO: Add more Items
		
		inv.setItem(53, back);
		sites.add(inv);
	}
	public void createManaPage(){
		Inventory inv = Bukkit.createInventory(null, 54, "Mana");
		//Sapphire Crystal
		ShopItem manacrystal = new ShopItem("Sapphire Crystal", Material.BED, ShopItemType.MANA, 200, 400); // TODO: Change to lapis, just did this to test
		inv.addItem(manacrystal);
		proxy.addItem(6, manacrystal);
		//TODO: Add more Items
		
		inv.setItem(53, back);
		sites.add(inv);
	}
	public void createConsumePage(){
		Inventory inv = Bukkit.createInventory(null, 54, "Consume");
		//TODO: Add more Items
		
		inv.setItem(53, back);
		sites.add(inv);
	}
	public int getInventoryId(Inventory i){
		for(int n = 0; n < sites.size(); n++){
			if(sites.get(n).getName().equalsIgnoreCase(i.getName())){
				return n;
			}
		}
		return 0;
	}
}
