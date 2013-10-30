package de.elicis.lom.sign;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;

import de.elicis.lom.tower.Tower;

public class LoM_TowerSign {
	String name;
	Tower t;
	String line1;
	String line2;
	String line3;
	public LoM_TowerSign(String name, Tower t) {
		super();
		this.name = name;
		this.t = t;
		line1 = "[Tower]";
		line2 = t.getTeam();
		line3 = "" + t.getHealth();
	}
	public void updateSign(){
		line1 = "[Tower]";
		line2 = t.getTeam();
		line3 = "" + t.getHealth();
		getSign().setLine(0, line1);
		getSign().setLine(1, line2);
		getSign().setLine(2, line3);
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
		return t.getLocation().getLocation();
	}
	public Sign getSign(){
		if(getLocation().getBlock().getType() == Material.SIGN || 
				getLocation().getBlock().getType() == Material.SIGN_POST){
			return (Sign) (getLocation().getBlock().getState());
		}else{
			return null;
		}
	}
}
