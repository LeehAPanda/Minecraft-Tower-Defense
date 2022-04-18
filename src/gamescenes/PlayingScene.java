package gamescenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import enemies.Enemy;
import gamemanagers.EnemyManager;
import gamemanagers.ProjectileManager;
import gamemanagers.TowerManager;
import gamemanagers.WaveManager;
import helpmethods.LoadSave;
import main.Game;
import objects.PathPoint;
import objects.Tower;
import userinterface.PlayerInventory;
import static helpmethods.Constants.TileCheck.*;

public class PlayingScene extends GameScene implements SceneMethods {

	private int[][] lvl;
	private int mouseX, mouseY;
	private EnemyManager enemyManager;
	private TowerManager towerManager;
	private ProjectileManager projManager;
	private WaveManager waveManager;
	private PlayerInventory plyrInv;
	private PathPoint start, end;
	private Tower selectedTower;
	private int emeraldTick;
	private boolean gamePaused;
	public PlayingScene(Game game) {
		super(game);
		loadDefaultLvl();
		plyrInv = new PlayerInventory(0, 640, 640, 160, this);
		enemyManager = new EnemyManager(this, start, end);
		towerManager = new TowerManager(this);
		projManager = new ProjectileManager(this);
		waveManager = new WaveManager(this);
	}

	private void loadDefaultLvl() {
		lvl = LoadSave.getLvlData("new_level");
		ArrayList<PathPoint> startEndPoints = LoadSave.getLvlStartEnd("new_level");
		start = startEndPoints.get(0);
		end = startEndPoints.get(1);
	}
	
	public void setLvl(int[][] lvl) {
		this.lvl = lvl;
	}
	
	public void update() {
		if (!gamePaused) {
			updateTick();
			waveManager.update();
			
			emeraldTick++;
			if (emeraldTick % (60*3) == 0)
				plyrInv.addEmeralds(1);
			
			if (isWaveOver())
				if (isGameComplete()) {
					waveManager.startWaveTimer();
					if (isWaveTimerOver()) {
						waveManager.increaseWaveInd();
						enemyManager.getEnemies().clear();
						waveManager.resetEnemyInd();
					}
				}
			
			if (newEnemyTimer())
				spawnEnemy();
			enemyManager.update();
			towerManager.update();
			projManager.update();
		}
	}
	
	private boolean isWaveTimerOver() {
		return waveManager.isWaveTimerOver();
	}

	private boolean isGameComplete() {
		return waveManager.isThereMoreWaves();
	}

	private boolean isWaveOver() {
		if (waveManager.isThereMoreEnemiesInWave())
			return false;
		for (Enemy e : enemyManager.getEnemies())
			if (e.isAlive())
				return false;
		return true;
	}

	private boolean newEnemyTimer() {
		if (waveManager.newEnemyTimer())
			if (waveManager.isThereMoreEnemiesInWave())
				return true;
		return false;
	}
	
	private void spawnEnemy() {
		enemyManager.spawnEnemy(waveManager.getNextEnemy());
	}
	
	public void setSelectedTower(Tower selectedTower) {
		this.selectedTower = selectedTower;
	}

	@Override
	public void render(Graphics g) {
		drawLvl(g);
		plyrInv.draw(g);
		enemyManager.draw(g);
		towerManager.draw(g);
		projManager.draw(g);
		drawSelectedTower(g);
		highlightTile(g);
	}

	private void highlightTile(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawRect(mouseX, mouseY, 32, 32);
	}

	private void drawSelectedTower(Graphics g) {
		if (selectedTower != null)
			g.drawImage(towerManager.getTowerSprites()[selectedTower.getTowerType()], mouseX, mouseY, null);
	}

	private void drawLvl(Graphics g) {
		for (int j = 0; j < lvl.length; j++)
			for (int i = 0; i < lvl[j].length; i++) {
				int id = lvl[j][i];
				if (isAnimed(id))
					g.drawImage(getSprite(id, animInd), i*32, j*32, null);
				else
					g.drawImage(getSprite(id), i*32, j*32, null);
			}
	}
	
	public int getTileType(int x, int y) {
		int xCord = x / 32;
		int yCord = y / 32;
		if (xCord < 0 || xCord > 19)
			return 0;
		if (yCord < 0 || yCord > 19)
			return 0;
		
		int id = lvl[y/32][x/32];
		return game.getTileEditor().getTile(id).getTileType();
	}
	
	private void removeEmeralds(int towerType) {
		plyrInv.towerPayment(towerType);
	}
	
	public void upgradeTower(Tower towerInfo) {
		towerManager.upgradeTower(towerInfo);
	}
	
	public void removeTower(Tower towerInfo) {
		towerManager.removeTower(towerInfo);
	}
	
	private Tower getTowerAt(int x, int y) {
		return towerManager.getTowerAt(x, y);
	}

	private boolean isTileGrass(int x, int y) {
		int id = lvl[y / 32][x / 32];
		int tileType = game.getTileEditor().getTile(id).getTileType();
		return tileType == GRASS_TILE;
	}
	
	public void shootEnemy(Tower t, Enemy e) {
		projManager.newProj(t, e);
	}
	
	public void givePlayerEmeralds(int enemyType) {
		plyrInv.addEmeralds(helpmethods.Constants.EnemyType.getEmeralds(enemyType));
	}
	
	public void setGamePaused(boolean gamePaused) {
		this.gamePaused = gamePaused;
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			selectedTower = null;
	}
	
	@Override
	public void mouseClicked(int x, int y) {
		if (y >= 640)
			plyrInv.mouseClicked(x, y);
		else
			if (selectedTower != null) {
				if (isTileGrass(mouseX, mouseY))
					if (getTowerAt(mouseX, mouseY) == null) {
						towerManager.addTower(selectedTower, mouseX, mouseY);
						removeEmeralds(selectedTower.getTowerType());
						selectedTower = null;
					} 
			} else {
					Tower t = getTowerAt(mouseX, mouseY);
					plyrInv.displayTowerInfo(t);
			}
	}

	@Override
	public void mouseMoved(int x, int y) {
		if (y >= 640)
			plyrInv.mouseMoved(x, y);
		else {
			mouseX = (x / 32) * 32;
			mouseY = (y / 32) * 32;
		}
	}

	@Override
	public void mousePressed(int x, int y) {
		if (y >= 640)
			plyrInv.mousePressed(x, y);
	}

	@Override
	public void mouseReleased(int x, int y) {
			plyrInv.mouseReleased(x, y);
	}

	@Override
	public void mouseDragged(int x, int y) {
		
	}
	
	public TowerManager getTowerManager() {
		return towerManager;
	}
	
	public EnemyManager getEnemyManager() {
		return enemyManager;
	}
	
	public WaveManager getWaveManager() {
		return waveManager;
	}
	
	public boolean isGamePaused() {
		return gamePaused;
	}

	public void removeHearts() {
		plyrInv.removeHearts();
	}
	
	public void resetEverything() {
		plyrInv.resetEverything();
		enemyManager.reset();
		towerManager.reset();
		projManager.reset();
		waveManager.reset();
		mouseX = 0;
		mouseY = 0;
		selectedTower = null;
		emeraldTick = 0;
		gamePaused = false;
	}
}
