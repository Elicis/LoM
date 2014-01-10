package de.elicis.lom.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import de.elicis.lom.data.Arena;
import de.elicis.lom.tower.Tower;

public class TowerDestroyEvent extends Event{
	private static final HandlerList handlers = new HandlerList();
	boolean cancelled = false;
	Tower tower;
	Arena arena;
	LivingEntity entity;
	
	public TowerDestroyEvent(Tower tower, Arena arena,
			LivingEntity entity) {
		super();
		this.tower = tower;
		this.arena = arena;
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
	public LivingEntity getLivingEntity() {
		return entity;
	}


}
