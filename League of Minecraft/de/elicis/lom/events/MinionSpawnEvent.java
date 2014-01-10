package de.elicis.lom.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MinionSpawnEvent extends Event{
	private static final HandlerList handlers = new HandlerList();
	LivingEntity entity;
	boolean cancelled = false;
	
	public MinionSpawnEvent(LivingEntity entity) {
		super();
		this.entity = entity;
	}
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	public LivingEntity getLivingEntity() {
		return entity;
	}
	public boolean isCancelled() {
		return cancelled;
	}
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

}
