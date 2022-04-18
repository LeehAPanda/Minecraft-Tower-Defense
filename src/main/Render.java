package main;

import java.awt.Graphics;

public class Render {
	
	private Game game;
	
	public Render(Game game) {
		this.game = game;
	}
	
	public void renderGame(Graphics g) {
		switch(GameStates.gameState) {
		case MENU:
			game.getMenu().render(g);
			break;
		case PLAYING:
			game.getPlaying().render(g);
			break;
		case SETTINGS:
			game.getSettings().render(g);
			break;
		case CREATIVE:
			game.getCreative().render(g);
			break;
		case GAMEOVER:
			game.getGameOver().render(g);
			break;
		default:
			break;
		}
	}
	
}
