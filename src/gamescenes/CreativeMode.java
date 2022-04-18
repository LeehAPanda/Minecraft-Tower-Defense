package gamescenes;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import helpmethods.LoadSave;
import main.Game;
import objects.PathPoint;
import objects.Tiles;
import userinterface.CreativeInventory;
import static helpmethods.Constants.TileCheck.ROAD_TILE;

public class CreativeMode extends GameScene implements SceneMethods {
	private int[][] lvl;
	private Tiles selectedTile;
	private int mouseX, mouseY, lastTileX, lastTileY, lastTileId;
	private boolean drawSelect;
	private CreativeInventory creativeInv;
	private PathPoint start, end;
	
	public CreativeMode(Game game) {
		super(game);
		loadDefaultLvl();
		creativeInv = new CreativeInventory(0, 640, 640, 160, this);
	}
	
	private void loadDefaultLvl() {
		lvl = LoadSave.getLvlData("new_level");
		ArrayList<PathPoint> startEndPoints = LoadSave.getLvlStartEnd("new_level");
		start = startEndPoints.get(0);
		end = startEndPoints.get(1);
	}
	
	public void update() {
		updateTick();
	}

	@Override
	public void render(Graphics g) {
		drawLvl(g);
		creativeInv.draw(g);
		drawSelectedTile(g);
		drawStartEndPoints(g);
		}
	
	private void drawStartEndPoints(Graphics g) {
		if (start != null) {
			g.drawImage(creativeInv.getStartImg(), start.getxCord()*32, start.getyCord()*32, 32, 32, null);
		}
		if (end != null) {
			g.drawImage(creativeInv.getEndImg(), end.getxCord()*32, end.getyCord()*32, 32, 32, null);
		}
	}

	private void drawLvl(Graphics g) {
		for (int j = 0; j < lvl.length; j++) {
			for (int i = 0; i < lvl[j].length; i++) {
				int id = lvl[j][i];
				if (isAnimed(id)) {
					g.drawImage(getSprite(id, animInd), i*32, j*32, null);
				} else {
					g.drawImage(getSprite(id), i*32, j*32, null);
				}
			}
		}
	}
	
	public void saveLvl() {
		LoadSave.saveLvl("new_level", lvl, start, end);
		game.getPlaying().setLvl(lvl);
	}
	
	private void drawSelectedTile(Graphics g) {
		if (selectedTile != null && drawSelect) {
			g.drawImage(selectedTile.getSprite(), mouseX, mouseY, 32, 32, null);
		}
	}
	
	public void setSelectedTile(Tiles tile) {
		this.selectedTile = tile;
		drawSelect = true;
	}
	
	private void changeTile(int x, int y) {
		if (selectedTile != null) {
			int tileX = x / 32;
			int tileY = y / 32;
			if(selectedTile.getId() >= 0) {
				if (lastTileX == tileX && lastTileY == tileY && lastTileId == selectedTile.getId()) {
					return;
				}
				lastTileX = tileX;
				lastTileY = tileY;
				lastTileId = selectedTile.getId();
				lvl[tileY][tileX] = selectedTile.getId();
			} else {
				int id = lvl[tileY][tileX];
				if (game.getTileEditor().getTile(id).getTileType() == ROAD_TILE) {
					if (selectedTile.getId() == -1) {
						start = new PathPoint(tileX, tileY);
					} else {
						end = new PathPoint(tileX, tileY);
					}
				}
			}
		}
	}

	@Override
	public void mouseClicked(int x, int y) {
		if (y >= 640) {
			creativeInv.mouseClicked(x, y);
		} else {
			changeTile(mouseX, mouseY);
		}
	}

	@Override
	public void mouseMoved(int x, int y) {
		if (y >= 640) {
			creativeInv.mouseMoved(x, y);
			drawSelect = false;
		} else {
			drawSelect = true;
			mouseX = (x / 32) * 32;
			mouseY = (y / 32) * 32;
		}
	}

	@Override
	public void mousePressed(int x, int y) {
		if (y >= 640) {
			creativeInv.mousePressed(x, y);
		} else {
			changeTile(mouseX, mouseY);
		}
	}

	@Override
	public void mouseReleased(int x, int y) {
		creativeInv.mouseReleased(x, y);
	}

	@Override
	public void mouseDragged(int x, int y) {
		if (y >= 640) {
			
		} else {
			changeTile(x, y);
		}
	}
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_R) {
			creativeInv.rotateSprite();
		}
	}
	
}
