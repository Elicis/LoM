package de.elicis.lom.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import de.elicis.lom.data.Arena;
import de.elicis.lom.tower.Tower;

public class TowerDamageEvent extends Event{
	private static final HandlerList handlers = new HandlerList();
	boolean cancelled = false;
	Tower tower;
	Arena arena;
	int amount;
	LivingEntity entity;
	
	public TowerDamageEvent(Tower tower, Arena arena, int amount,
			LivingEntity entity) {
		super();
		this.tower = tower;
		this.arena = arena;
		this.amount = amount;
		this.entity = entity;
	}
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	public boolean isCancelled() {
		return cancelled;
	}
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	public Tower getTower() {
		return tower;
	}
	public Arena getArena() {
		return arena;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public LivingEntity getLivingEntity() {
		return entity;
	}

}
