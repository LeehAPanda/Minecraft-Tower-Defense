package gamemanagers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import helpmethods.LoadSave;
import helpmethods.SpriteEditor;
import objects.Tiles;
import static helpmethods.Constants.TileCheck.*;

public class TileEditor {
	
	public Tiles GRASS, WATER, ROAD_LR, ROAD_TB, ROAD_B_TO_R, ROAD_L_TO_B, ROAD_L_TO_T, ROAD_T_TO_R, BL_WATER_CORNER,
	TL_WATER_CORNER, TR_WATER_CORNER, BR_WATER_CORNER, T_WATER, R_WATER, B_WATER, L_WATER, TL_ISLE, TR_ISLE,
	BR_ISLE, BL_ISLE;
	public BufferedImage atlas;
	public ArrayList<Tiles> tiles = new ArrayList<>();
	public ArrayList<Tiles> straightRoads = new ArrayList<>();
	public ArrayList<Tiles> cornerRoads = new ArrayList<>();
	public ArrayList<Tiles> waterCorners = new ArrayList<>();
	public ArrayList<Tiles> beaches = new ArrayList<>();
	public ArrayList<Tiles> islands = new ArrayList<>();

	public TileEditor() {
		loadAtlas();
		createTiles();
	}

	private void createTiles() {
		int id = 0;
		tiles.add(GRASS = new Tiles(getSprite(9, 0), id++, GRASS_TILE));
		tiles.add(WATER = new Tiles(getAnimSprites(0, 0), id++, WATER_TILE));
		
		straightRoads.add(ROAD_LR = new Tiles(getSprite(8, 0), id++, ROAD_TILE));
		straightRoads.add(ROAD_TB = new Tiles(SpriteEditor.getRotatedImg(getSprite(8, 0), 90), id++, ROAD_TILE));
		
		cornerRoads.add(ROAD_B_TO_R = new Tiles(getSprite(7, 0), id++, ROAD_TILE));
		cornerRoads.add(ROAD_L_TO_B = new Tiles(SpriteEditor.getRotatedImg(getSprite(7, 0), 90), id++, ROAD_TILE));
		cornerRoads.add(ROAD_L_TO_T = new Tiles(SpriteEditor.getRotatedImg(getSprite(7, 0), 180), id++, ROAD_TILE));
		cornerRoads.add(ROAD_T_TO_R = new Tiles(SpriteEditor.getRotatedImg(getSprite(7, 0), 270), id++, ROAD_TILE));
		
		waterCorners.add(BL_WATER_CORNER = new Tiles(SpriteEditor.getRotatedLayeredImg(getAnimSprites(0, 0), getSprite(5, 0), 0), id++, WATER_TILE));
		waterCorners.add(TL_WATER_CORNER = new Tiles(SpriteEditor.getRotatedLayeredImg(getAnimSprites(0, 0), getSprite(5, 0), 90), id++, WATER_TILE));
		waterCorners.add(TR_WATER_CORNER = new Tiles(SpriteEditor.getRotatedLayeredImg(getAnimSprites(0, 0), getSprite(5, 0), 180), id++, WATER_TILE));
		waterCorners.add(BR_WATER_CORNER = new Tiles(SpriteEditor.getRotatedLayeredImg(getAnimSprites(0, 0), getSprite(5, 0), 270), id++, WATER_TILE));
		
		beaches.add(T_WATER = new Tiles(SpriteEditor.getRotatedLayeredImg(getAnimSprites(0, 0), getSprite(6, 0), 0), id++, WATER_TILE));
		beaches.add(R_WATER = new Tiles(SpriteEditor.getRotatedLayeredImg(getAnimSprites(0, 0), getSprite(6, 0), 90), id++, WATER_TILE));
		beaches.add(B_WATER = new Tiles(SpriteEditor.getRotatedLayeredImg(getAnimSprites(0, 0), getSprite(6, 0), 180), id++, WATER_TILE));
		beaches.add(L_WATER = new Tiles(SpriteEditor.getRotatedLayeredImg(getAnimSprites(0, 0), getSprite(6, 0), 270), id++, WATER_TILE));
		
		islands.add(TL_ISLE = new Tiles(SpriteEditor.getRotatedLayeredImg(getAnimSprites(0, 0), getSprite(4, 0), 0), id++, WATER_TILE));
		islands.add(TR_ISLE = new Tiles(SpriteEditor.getRotatedLayeredImg(getAnimSprites(0, 0), getSprite(4, 0), 90), id++, WATER_TILE));
		islands.add(BR_ISLE = new Tiles(SpriteEditor.getRotatedLayeredImg(getAnimSprites(0, 0), getSprite(4, 0), 180), id++, WATER_TILE));
		islands.add(BL_ISLE = new Tiles(SpriteEditor.getRotatedLayeredImg(getAnimSprites(0, 0), getSprite(4, 0), 270), id++, WATER_TILE));
		
		tiles.addAll(straightRoads);
		tiles.addAll(cornerRoads);
		tiles.addAll(waterCorners);
		tiles.addAll(beaches);
		tiles.addAll(islands);
	}

	private void loadAtlas() {
		atlas = LoadSave.getSpriteAtlas();
	}
	
	public Tiles getTile(int id) {
		return tiles.get(id);
	}
	
	public BufferedImage getSprite(int id) {
		return tiles.get(id).getSprite();
	}
	
	public BufferedImage getAnimSprite(int id, int animInd) {
		return tiles.get(id).getSprite(animInd);
	}
	
	private BufferedImage[] getAnimSprites(int xCord, int yCord) {
		BufferedImage[] arr = new BufferedImage[4];
		for (int i = 0; i < 4; i++) {
			arr[i] = getSprite(xCord + i, yCord);
		}
		return arr;
	}
	
	private BufferedImage getSprite(int xCord, int yCord) {
		return atlas.getSubimage(xCord*32, yCord*32, 32, 32);
	}
	
	public boolean isSpriteAnimed(int spriteID) {
		return tiles.get(spriteID).isAnimed();
	}
	
	public int[][] getTypeArr() {
		int[][] idArr = LoadSave.getLvlData("new_level");
		int[][] typeArr = new int[idArr.length][idArr[0].length];
		for (int j = 0; j < idArr.length; j++)
			for (int i = 0; i < idArr[j].length; i++) {
				int id = idArr[j][i];
				typeArr[j][i] = tiles.get(id).getTileType();
			}
		return typeArr;
	}
	
	public ArrayList<Tiles> getStraightRoads() {
		return straightRoads;
	}

	public ArrayList<Tiles> getCornerRoads() {
		return cornerRoads;
	}

	public ArrayList<Tiles> getWaterCorners() {
		return waterCorners;
	}

	public ArrayList<Tiles> getBeaches() {
		return beaches;
	}

	public ArrayList<Tiles> getIslands() {
		return islands;
	}
	
}
