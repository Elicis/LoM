package de.elicis.lom.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import de.elicis.lom.data.Arena;

public class GameStartEvent extends Event{
	private static final HandlerList handlers = new HandlerList();
	Arena arena;
	boolean cancelled = false;
	
	public GameStartEvent(Arena arena){
		this.arena = arena;
	}
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	public Arena getArena() {
		return arena;
	}
	public boolean isCancelled() {
		return cancelled;
	}
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

}
