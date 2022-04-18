package enemies;

import java.awt.Rectangle;

import gamemanagers.EnemyManager;

import static helpmethods.Constants.Direction.*;

public abstract class Enemy {
	protected EnemyManager enemyManager;
	protected float x, y;
	protected Rectangle bounds;
	protected int health;
	protected int maxHealth;
	protected int ID;
	protected int enemyType;
	protected int lastDir;
	protected int slowTickLimit = 120;
	protected int slowTick = slowTickLimit;
	protected int stunTickLimit = 30;
	protected int stunTick = stunTickLimit;
	protected boolean alive = true;
	public Enemy(float x, float y, int ID, int enemyType, EnemyManager enemyManager) {
		this.x = x;
		this.y = y;
		this.ID = ID;
		this.enemyType = enemyType;
		this.enemyManager = enemyManager;
		bounds = new Rectangle((int)x, (int)y, 32, 32);
		lastDir = -1;
		setMaxHealth();
	}
	
	private void setMaxHealth() {
		health = helpmethods.Constants.EnemyType.getMaxHealth(enemyType);
		maxHealth = health;
	}
	
	public void damaged(int dmg) {
		this.health -= dmg;
		if (health <= 0) {
			alive = false;
			enemyManager.givePlayerEmeralds(enemyType);
		}
	}
	
	public void kill() {
		// Despawns enemy when it reaches the end
		alive = false;
		health = 0;
	}
	
	public void slow() {
		slowTick = 0;
	}
	
	public void stun() {
		stunTick = 0;
	}
	
	public void move(float enemySpeed, int dir) {
		lastDir = dir;
		if (slowTick < slowTickLimit) {
			slowTick++;
			enemySpeed *= 0.5f;
		}
		if (stunTick < stunTickLimit) {
			stunTick++;
			enemySpeed = 0;
		}
		switch(dir) {
		case LEFT:
			this.x -= enemySpeed;
			break;
		case UP:
			this.y -= enemySpeed;
			break;
		case RIGHT:
			this.x += enemySpeed;
			break;
		case DOWN:
			this.y += enemySpeed;
			break;
		}
		updateHitbox();
	}
	
	private void updateHitbox() {
		bounds.x = (int)x;
		bounds.y = (int)y;
	}

	public void setPos(int x, int y) { // used for fixing enemy position, not for speed
		this.x = x;
		this.y = y;
	}
	
	public float getHealthFloat() {
		return health / (float) maxHealth;
	}
	
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public Rectangle getBounds() {
		return bounds;
	}
	public int getHealth() {
		return health;
	}
	public int getID() {
		return ID;
	}
	public int getEnemyType() {
		return enemyType;
	}
	
	public int getLastDir() {
		return lastDir;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public boolean isSlowed() {
		return slowTick < slowTickLimit;
	}
	
	public boolean isStunned() {
		return stunTick < stunTickLimit;
	}
}
