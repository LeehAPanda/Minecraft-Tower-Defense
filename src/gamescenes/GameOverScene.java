package gamescenes;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import helpmethods.LoadSave;
import main.Game;
import userinterface.Button;
import static main.GameStates.*;

public class GameOverScene extends GameScene implements SceneMethods {
	private Button bReplay, bMenu;
	private BufferedImage gameOverScreen;
	public GameOverScene(Game game) {
		super(game);
		initButtons();
		importGameOverScreen();
	}

	private void importGameOverScreen() {
		gameOverScreen = LoadSave.getGameOverScreen();
	}

	private void initButtons() {
		int w = 150;
		int h = w / 3;
		int x = 640 / 2 - w / 2;
		int y = 300;
		int yOffset = 100;
		
		bReplay = new Button("Replay", x, y, w, h);
		bMenu = new Button("Menu", x, y + yOffset, w, h);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(gameOverScreen, 0, 0, 640, 800, null);
		g.setFont(new Font("ComicSans", Font.BOLD, 20));
		bReplay.draw(g);
		bMenu.draw(g);
	}
	
	private void replayGame() {
		resetAll();
		SetGameState(PLAYING);
	}

	private void resetAll() {
		game.getPlaying().resetEverything();
	}

	@Override
	public void mouseClicked(int x, int y) {
		if (bReplay.getInBounds().contains(x, y))
			replayGame();
		else if (bMenu.getInBounds().contains(x, y)) {
			SetGameState(MENU);
			resetAll();
		}
	}

	@Override
	public void mouseMoved(int x, int y) {
		bReplay.setMouseOver(false);
		bMenu.setMouseOver(false);
		if (bReplay.getInBounds().contains(x, y))
			bReplay.setMouseOver(true);
		else if (bMenu.getInBounds().contains(x, y))
			bMenu.setMouseOver(true);
	}

	@Override
	public void mousePressed(int x, int y) {
		if (bReplay.getInBounds().contains(x, y))
			bReplay.setMousePressed(true);
		else if (bMenu.getInBounds().contains(x, y))
			bMenu.setMousePressed(true);
	}

	@Override
	public void mouseReleased(int x, int y) {
		bReplay.resetBools();
		bMenu.resetBools();
	}

	@Override
	public void mouseDragged(int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
