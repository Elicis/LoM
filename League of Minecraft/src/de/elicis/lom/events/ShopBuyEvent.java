package de.elicis.lom.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import de.elicis.lom.shop.ShopItem;

public class ShopBuyEvent extends Event{
	private static final HandlerList handlers = new HandlerList();
	Player player;
	ShopItem item;
	int price;
	boolean cancelled = false;
	
	public ShopBuyEvent(Player player, ShopItem item, int price) {
		super();
		this.player = player;
		this.item = item;
		this.price = price;
	}

	
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public Player getPlayer() {
		return player;
	}

	public ShopItem getItem() {
		return item;
	}

	public int getPrice() {
		return price;
	}
	
	public boolean isCancelled() {
        return cancelled;
    }
 
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

}
