package de.elicis.lom.sign;

public enum LoM_SignType{
	
	
	
	ARENA("arena"), CHAMPION("champion"), NEXUS("nexus");

	String name;

	LoM_SignType(String name2) {
		name = name2;
	}

	public String getType() {
		return name;
	}
}
