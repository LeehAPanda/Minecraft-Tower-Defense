package gamemanagers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import enemies.*;
import gamescenes.PlayingScene;
import helpmethods.LoadSave;
import helpmethods.Utilities;
import objects.PathPoint;
import static helpmethods.Constants.Direction.*;
import static helpmethods.Constants.TileCheck.*;
import static helpmethods.Constants.EnemyType.*;

public class EnemyManager {
	private PlayingScene playing;
	private BufferedImage[] enemySprites;
	private ArrayList<Enemy> enemies = new ArrayList<>();
	private PathPoint start, end;
	private int HPBarWidth = 20;
	private BufferedImage slow;
	private BufferedImage stun;
	public EnemyManager(PlayingScene playing, PathPoint start, PathPoint end) {
		this.playing = playing;
		this.start = start;
		this.end = end;
		enemySprites = new BufferedImage[5];
		loadEnemySprites();
		loadEffectImg();
	}

	private void loadEffectImg() {
		slow = LoadSave.getSpriteAtlas().getSubimage(32*9, 32*2, 32, 32);
		stun = LoadSave.getSpriteAtlas().getSubimage(32*10, 32*2, 32, 32);
	}
	
	private void loadEnemySprites() {
		BufferedImage atlas = LoadSave.getSpriteAtlas();
		for (int i = 0; i < 5; i++)
			enemySprites[i] = atlas.getSubimage(i * 32, 32, 32, 32);
	}

	public void update() {
		for (Enemy e : enemies)
			if (e.isAlive())
				enemyMove(e);
	}

	public void enemyMove(Enemy e) {
		if (e.getLastDir() == -1)
			setNewDir(e);
		int xNew = (int)(e.getX() + getSpeedWidth(e.getLastDir(), e.getEnemyType()));
		int yNew = (int)(e.getY() + getSpeedHeight(e.getLastDir(), e.getEnemyType()));
		
		if (getTileType(xNew, yNew) == ROAD_TILE)
			e.move(getEnemySpeed(e.getEnemyType()), e.getLastDir());
		else if (atEnd(e)) {
			e.kill();
			playing.removeHearts();
		} else
			setNewDir(e);
	}

	private void setNewDir(Enemy e) {
		int dir = e.getLastDir();
		int xCord = (int)(e.getX() / 32);
		int yCord = (int)(e.getY() / 32);
		fixEnemyOffset(e, dir, xCord, yCord);
		
		if (atEnd(e))
			return;
		
		if (dir == LEFT || dir == RIGHT) {
			int yNew = (int)(e.getY() + getSpeedHeight(UP, e.getEnemyType()));
			if (getTileType((int)e.getX(), yNew) == ROAD_TILE)
				e.move(getEnemySpeed(e.getEnemyType()), UP);
			else
				e.move(getEnemySpeed(e.getEnemyType()), DOWN);
		} else {
			int xNew = (int)(e.getX() + getSpeedWidth(RIGHT, e.getEnemyType()));
			if (getTileType(xNew, (int)e.getY()) == ROAD_TILE)
				e.move(getEnemySpeed(e.getEnemyType()), RIGHT);
			else
				e.move(getEnemySpeed(e.getEnemyType()), LEFT);
		}
	}

	private void fixEnemyOffset(Enemy e, int dir, int xCord, int yCord) {
		switch(dir) {
		case RIGHT:
			if (xCord < 19)
				xCord++;
			break;
		case DOWN:
			if (yCord < 19)
				yCord++;
			break;
		}
		e.setPos(xCord * 32, yCord * 32);
	}

	private boolean atEnd(Enemy e) {
		if (e.getX() == end.getxCord()*32)
			if (e.getY() == end.getyCord()*32)
				return true;
		return false;
	}

	private int getTileType(int x, int y) {
		return playing.getTileType(x, y);
	}

	private float getSpeedHeight(int dir, int enemyType) {
		if (dir == UP)
			return -getEnemySpeed(enemyType);
		else if (dir == DOWN)
			return getEnemySpeed(enemyType) + 32;
		return 0;
	}

	private float getSpeedWidth(int dir, int enemyType) {
		if (dir == LEFT)
			return -getEnemySpeed(enemyType);
		else if (dir == RIGHT)
			return getEnemySpeed(enemyType) + 32;
		return 0;
	}
	
	public void spawnEnemy(int nextEnemy) {
		addEnemy(nextEnemy);
	}

	public void addEnemy(int enemyType) {
		int x = start.getxCord()*32;
		int y = start.getyCord()*32;
		switch(enemyType) {
		case ZOMBIE:
			enemies.add(new RegZombie(x, y, 0, this));
			break;
		case PHANTOM:
			enemies.add(new Phantom(x, y, 0, this));
			break;
		case DIAMOND_ZOMBIE:
			enemies.add(new DiamondZombie(x, y, 0, this));
			break;
		case SILVERFISH:
			enemies.add(new Silverfish(x, y, 0, this));
			break;
		case GHAST:
			enemies.add(new Ghast(x, y, 0, this));
		}
	}
	
	public void draw(Graphics g) {
		for (Enemy e : enemies)
			if (e.isAlive() ) {
				drawEnemy(e, g);
				drawHealth(e, g);
				drawEffects(e, g);
			}
	}

	private void drawEffects(Enemy e, Graphics g) {
		if (e.isSlowed())
			g.drawImage(slow, (int)e.getX(), (int)e.getY(), null);
		if (e.isStunned())
			g.drawImage(stun, (int)e.getX(), (int)e.getY(), null);
	}

	private void drawEnemy(Enemy e, Graphics g) {
		g.drawImage(enemySprites[e.getEnemyType()], (int)e.getX(), (int)e.getY(), null);
	}
	
	private void drawHealth(Enemy e, Graphics g) {
		g.setColor(Color.RED);
		g.fillRect((int)e.getX() + 16 - (newHPBarWidth(e) / 2), (int)e.getY() - 5, newHPBarWidth(e), 3);
	}
	
	private int newHPBarWidth(Enemy e) {
		return (int)(HPBarWidth * e.getHealthFloat());
	}
	
	public int getAmountOfAliveEnemies() {
		int size = 0;
		for (Enemy e : enemies)
			if (e.isAlive())
				size++;
		return size;
	}

	public void givePlayerEmeralds(int enemyType) {
		playing.givePlayerEmeralds(enemyType);
	}
	
	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}
	
	public void reset() {
		enemies.clear();
	}
}
