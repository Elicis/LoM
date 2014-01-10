package de.elicis.lom.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import de.elicis.lom.data.Arena;
import de.elicis.lom.data.Nexus;

public class NexusDamageEvent extends Event{
	private static final HandlerList handlers = new HandlerList();
	boolean cancelled = false;
	Arena a;
	Nexus nexus;
	int amount;
	LivingEntity entity;
	
	public NexusDamageEvent(Arena a, int amount, LivingEntity entity, Nexus nexus) {
		super();
		this.a = a;
		this.amount = amount;
		this.entity = entity;
		this.nexus = nexus;
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
	public Arena getArena() {
		return a;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public LivingEntity getEntity() {
		return entity;
	}
	public Nexus getNexus(){
		return nexus;
	}

}
