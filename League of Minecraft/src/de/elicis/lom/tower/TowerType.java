package de.elicis.lom.tower;

public enum TowerType {
	NEXUSTOP("nexus_top"), NEXUSBOT("nexus_bot"), INHIBTOP("inhib_top"), INHIBMID("inhib_mid"), INHIBBOT("inhib_bot"),
	INNERTOP("inner_top"), INNERMID("inner_mid"), INNERBOT("inner_bot"), OUTERTOP("outer_top"), OUTERMID("outer_mid"), OUTERBOT("outer_bot");
	String type;
	
	TowerType(String type){
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
