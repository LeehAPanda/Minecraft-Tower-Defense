package userinterface;

import static main.GameStates.MENU;
import static main.GameStates.SetGameState;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import gamescenes.CreativeMode;
import helpmethods.LoadSave;
import objects.Tiles;

public class CreativeInventory extends Bar {
	private Button bMenu, bSave;
	private Button bStart, bEnd;
	private BufferedImage start, end;
	private CreativeMode creative;
	// private ArrayList<Button> tileButtons = new ArrayList<>();
	private Map<Button, ArrayList<Tiles>> map = new HashMap<Button, ArrayList<Tiles>>();
	private Button bGrass, bWater, bStraightRoads, bCornerRoads, bWaterCorners, bBeaches, bIslands, currButton;
	private int currInd = 0;
	private Tiles selectedTile;
	public CreativeInventory(int x, int y, int width, int height, CreativeMode creative) {
		super(x, y, width, height);
		this.creative = creative;
		initButtons();
		initStartEnd();
	}

	private void initButtons() {
		bMenu = new Button("Menu", 2, 642, 100, 30);
		bSave = new Button("Save", 2, 676, 100, 30);
		
		int w = 50;
		int h = 50;
		int xPos = 110;
		int yPos = 650;
		int xOffset = (int)(w * 1.1f);
		int i = 0;
		
		bGrass = new Button("Grass", xPos, yPos, w, h, i++);
		bWater = new Button("Water", xPos + xOffset, yPos, w, h, i++);
		initMap(bStraightRoads, creative.getGame().getTileEditor().getStraightRoads(), xPos, yPos, xOffset, w, h, i++);
		initMap(bCornerRoads, creative.getGame().getTileEditor().getCornerRoads(), xPos, yPos, xOffset, w, h, i++);
		initMap(bWaterCorners, creative.getGame().getTileEditor().getWaterCorners(), xPos, yPos, xOffset, w, h, i++);
		initMap(bBeaches, creative.getGame().getTileEditor().getBeaches(), xPos, yPos, xOffset, w, h, i++);
		initMap(bIslands, creative.getGame().getTileEditor().getIslands(), xPos, yPos, xOffset, w, h, i++);
		bStart = new Button("Start", xPos, yPos + xOffset, w, h, i++);
		bEnd = new Button("End", xPos + xOffset, yPos + xOffset, w, h, i++);
	}
	
	private void initStartEnd() {
		start = LoadSave.getSpriteAtlas().getSubimage(7*32, 2*32, 32, 32);
		end = LoadSave.getSpriteAtlas().getSubimage(8*32, 2*32, 32, 32);
	}
	
	private void initMap(Button b, ArrayList<Tiles> list, int x, int y, int xOffset, int w, int h, int id) {
		b = new Button("", x + xOffset * id, y, w, h, id);
		map.put(b, list);
		map.get(b);
	}
	
	private void saveLvl() {
		creative.saveLvl();
	}
	
	private void drawButtons(Graphics g) {
		bMenu.draw(g);
		bSave.draw(g);
		drawStartEndButtons(g, bStart, start);
		drawStartEndButtons(g, bEnd, end);
		// bStart.draw(g);
		// bEnd.draw(g);
		// drawTileButtons(g);
		drawRegButtons(g, bGrass);
		drawRegButtons(g, bWater);
		drawMapButtons(g);
		drawSelectedTile(g);
	}
	
	private void drawStartEndButtons(Graphics g, Button b, BufferedImage img) {
		g.drawImage(img, b.x, b.y, b.width, b.height, null);
		buttonFeedback(g, b);
	}

	private void drawRegButtons(Graphics g, Button b) {
		g.drawImage(getButtonImg(b.getId()), b.x, b.y, b.width, b.height, null);
		buttonFeedback(g, b);
	}

	private void drawMapButtons(Graphics g) {
		for (Map.Entry<Button, ArrayList<Tiles>> entry : map.entrySet()) {
			Button b = entry.getKey();
			BufferedImage img = entry.getValue().get(0).getSprite();
			g.drawImage(img, b.x, b.y, b.width, b.height, null);
			buttonFeedback(g, b);
			}
		}
	
	public void rotateSprite() {
		currInd++;
		if (currInd >= map.get(currButton).size()) {
			currInd = 0;
		}
		selectedTile = map.get(currButton).get(currInd);
		creative.setSelectedTile(selectedTile);
	}

	public void draw(Graphics g) {
		// Background color of bottom bar
		g.setColor(new Color(130, 130, 130));
		g.fillRect(x, y, width, height);
		// Buttons in the bottom bar
		drawButtons(g);
	}
	
	private void drawSelectedTile(Graphics g) {
		if (selectedTile != null) {
			g.drawImage(selectedTile.getSprite(), 550, 650, 50, 50, null);
			g.setColor(Color.BLACK);
			g.drawRect(550, 650, 50, 50);
		}
	}
	
	public BufferedImage getButtonImg (int id) {
		return creative.getGame().getTileEditor().getSprite(id);
	}
	
	public void mouseClicked(int x, int y) {
		if (bMenu.getInBounds().contains(x, y)) {
			SetGameState(MENU);
		} else if (bSave.getInBounds().contains(x, y)) {
			saveLvl();
		} else if (bWater.getInBounds().contains(x, y)) {
			selectedTile = creative.getGame().getTileEditor().getTile(bWater.getId());
			creative.setSelectedTile(selectedTile);
			return;
		} else if (bGrass.getInBounds().contains(x, y)) {
			selectedTile = creative.getGame().getTileEditor().getTile(bGrass.getId());
			creative.setSelectedTile(selectedTile);
			return;
		} else if (bStart.getInBounds().contains(x, y)) {
			selectedTile = new Tiles(start, -1, -1);
			creative.setSelectedTile(selectedTile);
		} else if (bEnd.getInBounds().contains(x, y)) {
			selectedTile = new Tiles(end, -2, -2);
			creative.setSelectedTile(selectedTile);
		} else {
			for(Button b : map.keySet()) {
				if (b.getInBounds().contains(x, y)) {
					selectedTile = map.get(b).get(0);
					creative.setSelectedTile(selectedTile);
					currButton = b;
					currInd = 0;
					return;
				}
			}
		}
	}

	public void mouseMoved(int x, int y) {
		bMenu.setMouseOver(false);
		bSave.setMouseOver(false);
		bWater.setMouseOver(false);
		bGrass.setMouseOver(false);
		bStart.setMouseOver(false);
		bEnd.setMouseOver(false);
		for(Button b : map.keySet()) {
			b.setMouseOver(false);
			}

		if (bMenu.getInBounds().contains(x, y)) {
			bMenu.setMouseOver(true);
		} else if (bSave.getInBounds().contains(x, y)) {
			bSave.setMouseOver(true);
		} else if (bWater.getInBounds().contains(x, y)) {
			bWater.setMouseOver(true);
		} else if (bGrass.getInBounds().contains(x, y)) {
			bGrass.setMouseOver(true);
		} else if (bStart.getInBounds().contains(x, y)) {
			bStart.setMouseOver(true);
		} else if (bEnd.getInBounds().contains(x, y)) {
			bEnd.setMouseOver(true);
		} else {
			for(Button b : map.keySet()) {
				if (b.getInBounds().contains(x, y)) {
					b.setMouseOver(true);
					return;
				}
			}
		}
	}

	public void mousePressed(int x, int y) {
		if (bMenu.getInBounds().contains(x, y)) {
			bMenu.setMousePressed(true);
		} else if (bSave.getInBounds().contains(x, y)) {
			bSave.setMousePressed(true);
		} else if (bWater.getInBounds().contains(x, y)) {
			bWater.setMousePressed(true);
		} else if (bGrass.getInBounds().contains(x, y)) {
			bGrass.setMousePressed(true);
		} else if (bStart.getInBounds().contains(x, y)) {
			bStart.setMousePressed(true);
		} else if (bEnd.getInBounds().contains(x, y)) {
			bEnd.setMousePressed(true);
		} else {
			for(Button b : map.keySet()) {
				if (b.getInBounds().contains(x, y)) {
					b.setMousePressed(true);
					return;
				}
			}
		}
	}

	public void mouseReleased(int x, int y) {
		bMenu.resetBools();
		bSave.resetBools();
		bWater.resetBools();
		bGrass.resetBools();
		bStart.resetBools();
		bEnd.resetBools();
		for(Button b : map.keySet()) {
			b.resetBools();
		}
	}
	
	public BufferedImage getStartImg() {
		return start;
	}
	
	public BufferedImage getEndImg() {
		return end;
	}
	
}