package userinterface;

import static main.GameStates.*;
import gamescenes.PlayingScene;
import helpmethods.Constants.TowerType;
import helpmethods.LoadSave;
import objects.Tower;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class PlayerInventory extends Bar {
	
	private PlayingScene playing;
	
	private Button bMenu, bPause;
	private Button[] towerButtons;
	private Button sellTower, upgradeTower;
	
	private Tower selectedTower;
	private Tower towerInfo;
	
	private DecimalFormat format;
	// Money variables
	private int emerald = 15;
	private BufferedImage emeraldSprite;
	private BufferedImage smallerEmerald;
	private boolean showCost;
	private int towerTypeCost;
	
	private int hearts = 20;
	private BufferedImage heartsSprite;
	public PlayerInventory(int x, int y, int width, int height, PlayingScene playing) {
		super(x, y, width, height);
		this.playing = playing;
		format = new DecimalFormat("0.0");
		initButtons();
		importEmerald();
		importHeart();
	}
	
	public void resetEverything() {
		hearts = 20;
		towerTypeCost = 0;
		showCost = false;
		emerald = 15;
		selectedTower = null;
		towerInfo = null;
	}
	
	private void initButtons() {
		bMenu = new Button("Menu", 2, 645, 100, 30);
		bPause = new Button("Pause!", 2, 680, 100, 30);
		towerButtons = new Button[4];
		int w = 50;
		int h = 50;
		int xPos = 110;
		int yPos = 650;
		int xOffset = (int)(w * 1.1f);
		
		for (int i = 0; i < towerButtons.length; i++) {
			towerButtons[i]	= new Button("", xPos + xOffset * i, yPos, w, h, i);
		}
		
		// Sell
		sellTower = new Button("Sell", 420, 702, 80, 25);
		// Upgrade
		upgradeTower = new Button("Upgrade", 545, 702, 80, 25);
	}
	
	public void removeHearts() {
		hearts--;
		if (hearts <= 0)
			SetGameState(GAMEOVER);
	}

	private void importEmerald() {
		emeraldSprite = LoadSave.getSpriteAtlas().getSubimage(10*32, 0, 32, 32);
		smallerEmerald = LoadSave.getSpriteAtlas().getSubimage(12*32, 0, 12, 12);
	}
	
	private void importHeart() {
		heartsSprite = LoadSave.getSpriteAtlas().getSubimage(11*32, 0, 32, 32);
	}
	
	private void drawButtons(Graphics g) {
		bMenu.draw(g);
		bPause.draw(g);
		for (Button b : towerButtons) {
			g.setColor(new Color(198, 198, 198));
			g.fillRect(b.x, b.y, b.width, b.height);
			g.drawImage(playing.getTowerManager().getTowerSprites()[b.getId()], b.x, b.y, b.width, b.height, null);
			buttonFeedback(g, b);
		}
	}

	public void draw(Graphics g) {
		// Background color of bottom bar
		g.setColor(new Color(130, 130, 130));
		g.fillRect(x, y, width, height);
		// Buttons in the bottom bar
		drawButtons(g);
		// Tower info
		drawTowerInfo(g);
		// Wave info
		drawWaveInfo(g);
		// Emeralds info
		drawEmeraldAmount(g);
		// Tower Cost
		if (showCost)
			drawTowerCost(g);
		// Game paused screen
		if (playing.isGamePaused()) {
			g.setColor(Color.black);
			g.setFont(new Font("ComicSans", Font.BOLD, 20));
			g.drawString("Paused!", 15, 740);
		}
		// Lives
		g.setColor(Color.black);
		g.setFont(new Font("ComicSans", Font.BOLD, 20));
		g.drawImage(heartsSprite, 110, 750, null);
		g.drawString("" + hearts, 146, 772);
	}

	private void drawTowerCost(Graphics g) {
		g.setColor(new Color(198, 198, 198));
		g.fillRect(185, 710, 120, 50);
		g.setColor(Color.black);
		g.drawRect(185, 710, 120, 50);
		g.drawString("" + getTowerNameCost(), 190, 730);
		g.drawString("Cost: " + getTowerCost(), 190, 755);
		g.drawImage(emeraldSprite, 268, 728, null);
		
		if (isNotEnoughEmeralds()) {
			g.setColor(Color.RED);
			g.setFont(new Font("ComicSans", Font.BOLD, 20));
			g.drawString("Can't afford!", 185, 785);
		}
	}

	private boolean isNotEnoughEmeralds() {
		return getTowerCost() > emerald;
	}

	private String getTowerNameCost() {
		return helpmethods.Constants.TowerType.GetTowerName(towerTypeCost);
	}
	
	private int getTowerCost() {
		return helpmethods.Constants.TowerType.GetTowerCost(towerTypeCost);
	}

	private void drawEmeraldAmount(Graphics g) {
		g.drawImage(emeraldSprite, 110, 710, null);
		g.drawString("" + emerald, 142, 732);
	}

	private void drawWaveInfo(Graphics g) {
		g.setColor(Color.black);
		g.setFont(new Font("ComicSans", Font.BOLD, 20));
		drawWaveTimer(g);
		drawEnemiesInfo(g);
		drawWavesLeftInfo(g);
	}
	
	private void drawWavesLeftInfo(Graphics g) {
		int current = playing.getWaveManager().getWaveInd();
		int size = playing.getWaveManager().getWaves().size();
		g.drawString("Wave " + (current + 1) + " / " + size, 460, 770);
	}

	private void drawEnemiesInfo(Graphics g) {
		int remaining = playing.getEnemyManager().getAmountOfAliveEnemies();
		g.drawString("Enemies Left: " + remaining, 460, 790);
	}

	private void drawWaveTimer(Graphics g) {
		if (playing.getWaveManager().isWaveTimerStarted()) {
			float timeLeft = playing.getWaveManager().getRemainingTime();
			String formatDecimal = format.format(timeLeft);
			g.drawString("Time left: " + formatDecimal, 460, 750);
		}
	}

	public void displayTowerInfo(Tower t) {
		towerInfo = t;
	}
	
	private void drawTowerInfo(Graphics g) {
		if (towerInfo != null) {
			g.setColor(new Color(198, 198, 198));
			g.fillRect(410, 645, 220, 85);
			g.setColor(Color.black);
			g.drawRect(410, 645, 220, 85);
			g.drawRect(420, 650, 50, 50);
			g.drawImage(playing.getTowerManager().getTowerSprites()[towerInfo.getTowerType()], 420,  650, 50, 50, null);
			g.setFont(new Font("ComicSans", Font.BOLD, 15));
			g.drawString("" + TowerType.GetTowerName(towerInfo.getTowerType()), 480, 665);
			g.drawString("ID: " + towerInfo.getId(), 480, 680);
			g.drawString("Tier: " + towerInfo.getTier(), 560, 670);
			towerInfoBorder(g);
			towerRangeInfo(g);
			
			// Sell
			sellTower.draw(g);
			buttonFeedback(g, sellTower);
			// Upgrade
			if (towerInfo.getTier() < 3 && emerald >= getUpgradeAmount(towerInfo)) {
				upgradeTower.draw(g);
				buttonFeedback(g, upgradeTower);
			}
			
			if (sellTower.isMouseOver()) {
				g.setColor(Color.red);
				g.setFont(new Font("ComicSans", Font.BOLD, 15));
				g.drawString("Sell for: " + getSellAmount(towerInfo), 480, 695);
				g.drawImage(smallerEmerald, 556, 683, null);
			} else if (upgradeTower.isMouseOver() && emerald >= getUpgradeAmount(towerInfo)) {
				g.setColor(Color.blue);
				g.setFont(new Font("ComicSans", Font.BOLD, 15));
				g.drawString("Upgrade for: " + getUpgradeAmount(towerInfo), 480, 695);
				g.drawImage(smallerEmerald, 589, 683, null);
			}
		}
	}
	
	private int getUpgradeAmount(Tower towerInfo) {
		return (int)(helpmethods.Constants.TowerType.GetTowerCost(towerInfo.getTowerType()) * 0.75f);
	}

	private int getSellAmount(Tower towerInfo) {
		int upgradeCost = (towerInfo.getTier() - 1) * getUpgradeAmount(towerInfo);
		upgradeCost *= 0.5f;
		return (int)(helpmethods.Constants.TowerType.GetTowerCost(towerInfo.getTowerType()) * 0.6f + upgradeCost);
	}

	private void towerRangeInfo(Graphics g) {
		g.setColor(Color.ORANGE);
		g.drawOval(towerInfo.getX()+16 - (int)(towerInfo.getRange()*2)/2, towerInfo.getY()+16 - (int)(towerInfo.getRange()*2)/2, (int)towerInfo.getRange()*2, (int)towerInfo.getRange()*2);
	}

	private void towerInfoBorder(Graphics g) {
		g.setColor(Color.CYAN);
		g.drawRect(towerInfo.getX(), towerInfo.getY(), 32, 32);
	}
	
	public void towerPayment(int towerType) {
		this.emerald -= helpmethods.Constants.TowerType.GetTowerCost(towerType);
	}
	
	public void addEmeralds(int getEmeralds) {
		this.emerald += getEmeralds;
	}
	
	private boolean isEnoughEmeralds(int towerType) {
		return emerald >= helpmethods.Constants.TowerType.GetTowerCost(towerType);
	}
	
	private void sellTowerClicked() {
		playing.removeTower(towerInfo);
		emerald += (int)(helpmethods.Constants.TowerType.GetTowerCost(towerInfo.getTowerType()) * 0.6f);
		int upgradeCost = (towerInfo.getTier() - 1) * getUpgradeAmount(towerInfo);
		upgradeCost *= 0.5f;
		emerald += upgradeCost;
		towerInfo = null;
	}
	
	private void upgradeTowerClicked() {
		playing.upgradeTower(towerInfo);
		emerald -= getUpgradeAmount(towerInfo);
	}
	
	private void pauseToggle() {
		playing.setGamePaused(!playing.isGamePaused());
		if (playing.isGamePaused())
			bPause.setText("Unpause!");
		else
			bPause.setText("Pause!");
	}

	public void mouseClicked(int x, int y) {
		if (bMenu.getInBounds().contains(x, y))
			SetGameState(MENU);
		else if (bPause.getInBounds().contains(x, y))
			pauseToggle();
		else {
			if (towerInfo != null) {
				if (sellTower.getInBounds().contains(x, y)) {
					sellTowerClicked();
					return;
				} else if (upgradeTower.getInBounds().contains(x, y) && towerInfo.getTier() < 3 && emerald >= getUpgradeAmount(towerInfo)) {
					upgradeTowerClicked();
					return;
				}
			}
			for (Button b : towerButtons)
				if (b.getInBounds().contains(x, y)) {
					if (!isEnoughEmeralds(b.getId()))
						return;
					selectedTower = new Tower(0, 0, -1, b.getId());
					playing.setSelectedTower(selectedTower);
					return;
				}
		}
	}

	public void mouseMoved(int x, int y) {
		bMenu.setMouseOver(false);
		bPause.setMouseOver(false);
		showCost = false;
		sellTower.setMouseOver(false);
		upgradeTower.setMouseOver(false);
		for (Button b : towerButtons)
			b.setMouseOver(false);
		if (bMenu.getInBounds().contains(x, y))
			bMenu.setMouseOver(true);
		else if (bPause.getInBounds().contains(x, y))
			bPause.setMouseOver(true);
		else {
			if (towerInfo != null) {
				if (sellTower.getInBounds().contains(x, y)) {
					sellTower.setMouseOver(true);
					return;
				} else if (upgradeTower.getInBounds().contains(x, y) && towerInfo.getTier() < 3) {
					upgradeTower.setMouseOver(true);
					return;
				}
			}
			for (Button b : towerButtons) {
				if (b.getInBounds().contains(x, y)) {
					b.setMouseOver(true);
					showCost = true;
					towerTypeCost = b.getId();
					return;
				}
			}
		}
	}

	public void mousePressed(int x, int y) {
		if (bMenu.getInBounds().contains(x, y))
			bMenu.setMousePressed(true);
		else if (bPause.getInBounds().contains(x, y))
			bPause.setMousePressed(true);
		else {
			if (towerInfo != null) {
				if (sellTower.getInBounds().contains(x, y)) {
					sellTower.setMousePressed(true);
					return;
				} else if (upgradeTower.getInBounds().contains(x, y) && towerInfo.getTier() < 3) {
					upgradeTower.setMousePressed(true);
					return;
				}
			}
			for (Button b : towerButtons)
				if (b.getInBounds().contains(x, y)) {
					b.setMousePressed(true);
					towerTypeCost = b.getId();
					return;
				}
		}
	}

	public void mouseReleased(int x, int y) {
		bMenu.resetBools();
		bPause.resetBools();
		for (Button b : towerButtons)
			b.resetBools();
		sellTower.resetBools();
		upgradeTower.resetBools();
	}
	
	public int getHearts() {
		return hearts;
	}
}
