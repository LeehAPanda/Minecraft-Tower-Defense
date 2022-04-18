package objects;

import static helpmethods.Constants.TowerType.*;

public class Tower {
	private int x, y, id, towerType, asTick, dmg;
	private float range, attSpeed;
	private int tier;
	public Tower(int x, int y, int id, int towerType) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.towerType = towerType;
		tier = 1;
		setDefDmg();
		setDefRange();
		setDefAttackSpeed();
	}
	
	public void update() {
		asTick++;
	}
	
	public void upgradeTier() {
		this.tier++;
		switch(towerType) {
		case STEVE:
			dmg += 2;
			range += 25;
			attSpeed -= 5;
			break;
		case CANNON:
			dmg += 5;
			range += 5;
			attSpeed -= 10;
			break;
		case ALEX:
			dmg += 1;
			range += 10;
			attSpeed -= 10;
			break;
		case PIRATE:
			dmg += 4;
			range += 5;
			attSpeed -= 25;
			break;
		}
	}
	
	public boolean towerAttackSpeed() {
		return asTick >= attSpeed;
	}

	public void resetAttackSpeed() {
		asTick = 0;
	}
	
	private void setDefDmg() {
		dmg = helpmethods.Constants.TowerType.GetDefDmg(towerType);
	}
	
	private void setDefRange() {
		range = helpmethods.Constants.TowerType.GetDefRange(towerType);
	}
	
	private void setDefAttackSpeed() {
		attSpeed = helpmethods.Constants.TowerType.GetDefAttackSpeed(towerType);
	}

	public int getDmg() {
		return dmg;
	}

	public float getRange() {
		return range;
	}

	public float getAttSpeed() {
		return attSpeed;
	}

	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getTowerType() {
		return towerType;
	}
	
	public void setTowerType(int towerType) {
		this.towerType = towerType;
	}
	
	public int getTier() {
		return tier;
	}
}
