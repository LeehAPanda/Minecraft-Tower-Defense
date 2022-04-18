package objects;

import java.awt.image.BufferedImage;

public class Tiles {
	
	private BufferedImage[] sprite;
	private int id, tileType;
	public Tiles(BufferedImage sprite, int id, int tileType) {
		this.sprite = new BufferedImage[1];
		this.sprite[0] = sprite;
		this.id = id;
		this.tileType = tileType;
	}
	
	public Tiles(BufferedImage[] sprite, int id, int tileType) {
		this.sprite = sprite;
		this.id = id;
		this.tileType = tileType;
	}
	
	public BufferedImage getSprite(int animInd) {
		return sprite[animInd];
	}
	
	public BufferedImage getSprite() {
		return sprite[0];
	}
	
	public boolean isAnimed() {
		return sprite.length > 1;
	}
	
	public int getId() {
		return id;
	}
	
	public int getTileType() {
		return tileType;
	}
	
}
