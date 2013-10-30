package de.elicis.lom.sign;

import org.bukkit.Location;
import org.bukkit.block.Sign;

import de.elicis.lom.data.LoMLocation;
import de.elicis.lom.tower.Tower;

public class LoM_TowerSign {
	String name;
	Tower t;
	String line1;
	String line2;
	String line3;
	LoMLocation loc;
	public LoM_TowerSign(String name, Tower t) {
		super();
		this.name = name;
		this.t = t;
		loc = t.getLocation();
		line1 = "[Tower]";
		line2 = t.getTeam();
		line3 = "" + t.getHealth();
	}
	public void updateSign(){
		Sign sign = getSign();
		line1 = "[Tower]";
		line2 = t.getTeam();
		line3 = "HP: " + t.getHealth();
		sign.setLine(0, line1);
		sign.setLine(1, line2);
		sign.setLine(2, line3);
		sign.update(true);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Tower getTower() {
		return t;
	}
	public void setTower(Tower t) {
		this.t = t;
	}
	public String getLine1() {
		return line1;
	}
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	public String getLine2() {
		return line2;
	}
	public void setLine2(String line2) {
		this.line2 = line2;
	}
	public String getLine3() {
		return line3;
	}
	public void setLine3(String line3) {
		this.line3 = line3;
	}
	public Location getLocation(){
		return loc.getLocation();
	}
	public Sign getSign(){
			return (Sign) (getLocation().getBlock().getState());
	}
}
