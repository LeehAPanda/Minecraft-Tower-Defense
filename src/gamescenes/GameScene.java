package gamescenes;

import java.awt.image.BufferedImage;

import main.Game;

public class GameScene {
	protected int tick, animInd;
	protected Game game;
	public GameScene(Game game) {
		this.game = game;
	}
	
	public Game getGame() {
		return game;
	}
	
	protected boolean isAnimed(int spriteID) {
		return game.getTileEditor().isSpriteAnimed(spriteID);
	}
	
	protected BufferedImage getSprite(int spriteID) {
		return game.getTileEditor().getSprite(spriteID);
	}
	
	protected BufferedImage getSprite(int spriteID, int animInd) {
		return game.getTileEditor().getAnimSprite(spriteID, animInd);
	}
	
	protected void updateTick() {
		tick++;
		if (tick >= 15) {
			tick = 0;
			animInd++;
			if (animInd >= 4)
				animInd = 0;
		}
	}
	
}
