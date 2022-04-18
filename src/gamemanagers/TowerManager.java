package gamemanagers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import enemies.Enemy;
import gamescenes.PlayingScene;
import helpmethods.LoadSave;
import objects.Tower;
import static helpmethods.Constants.TowerType.*;

public class TowerManager {
	private PlayingScene playing;
	private BufferedImage[] towerSprites;
	private ArrayList<Tower> towers = new ArrayList<>();
	private int towerAmount = 0;
	public TowerManager(PlayingScene playing) {
		this.playing = playing;
		loadTowerSprites();
	}

	private void loadTowerSprites() {
		BufferedImage atlas = LoadSave.getSpriteAtlas();
		towerSprites = new BufferedImage[4];
		for (int i = 0; i < 4; i++) {
			towerSprites[i] = atlas.getSubimage((5+i)*32, 32, 32, 32);
		}
	}
	
	public void addTower(Tower selectedTower, int xPos, int yPos) {
		towers.add(new Tower(xPos, yPos, towerAmount++, selectedTower.getTowerType()));
	}
	
	public void upgradeTower(Tower towerInfo) {
		for (Tower t : towers)
			if (t.getId() == towerInfo.getId())
				t.upgradeTier();
	}
	
	public void removeTower(Tower towerInfo) {
		for (int i = 0; i < towers.size(); i++)
			if (towers.get(i).getId() == towerInfo.getId())
				towers.remove(i);
	}
	
	public void update() {
		for (Tower t : towers) {
			t.update();
			attackCloseEnemy(t);
		}
	}
	
	private void attackCloseEnemy(Tower t) {
		for (Enemy e : playing.getEnemyManager().getEnemies()) {
			if (e.isAlive()) {
				if (enemyInRange(t, e)) {
					if(t.towerAttackSpeed()) {
						playing.shootEnemy(t, e);
						t.resetAttackSpeed();
					}
				} else {
						
				}
			}
		}
	}

	private boolean enemyInRange(Tower t, Enemy e) {
		int range = helpmethods.Utilities.GetHypoDistance(t.getX(), t.getY(), e.getX(), e.getY());
		return range < t.getRange();
	}

	public void draw(Graphics g) {
		for (Tower t : towers) {
			g.drawImage(towerSprites[t.getTowerType()], t.getX(), t.getY(), null);
		}
	}
	
	public Tower getTowerAt(int x, int y) {
		for (Tower t : towers) {
			if (t.getX() == x) {
				if (t.getY() == y) {
					return t;
				}
			}
		}
		return null;
	 }
	
	public BufferedImage[] getTowerSprites() {
		return towerSprites;
	}
	
	public void reset() {
		towers.clear();
		towerAmount = 0;
	}
}
