package gamescenes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import helpmethods.LoadSave;
import main.Game;
import userinterface.Button;
import static main.GameStates.*;

public class MenuScene extends GameScene implements SceneMethods {
	private Button bPlay, bCreative, bSettings, bQuit;
	private BufferedImage menuBg;
	public MenuScene(Game game) {
		super(game);
		importMenuBG();
		initButtons();
		LoadSave.playMusic("Resources/Songs/playingMusic.wav");
	}

	private void importMenuBG() {
		menuBg = LoadSave.getMenuBG();
	}

	private void initButtons() {
		int w = 150;
		int h = w / 3;
		int x = 640 / 2 - w / 2;
		int y = 275;
		int yOffset = 100;
		bPlay = new Button("Play", x, y, w, h);
		bCreative = bSettings = new Button("Creative", x, y + yOffset, w, h);
		bSettings = new Button("Settings", x, y + yOffset * 2, w, h);
		bQuit = new Button("Quit", x, y + yOffset * 3, w, h);
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(menuBg, 0, 0, 640, 800, null);
		drawButtons(g);
	}
	
	private void drawButtons(Graphics g) {
		bPlay.draw(g);
		bCreative.draw(g);
		bSettings.draw(g);
		bQuit.draw(g);
	}

	@Override
	public void mouseClicked(int x, int y) {
		if (bPlay.getInBounds().contains(x, y)) {
			SetGameState(PLAYING);
		} else if (bCreative.getInBounds().contains(x, y)) {
			SetGameState(CREATIVE);
		} else if (bSettings.getInBounds().contains(x, y)) {
			SetGameState(SETTINGS);
		} else if (bQuit.getInBounds().contains(x, y)) {
			System.exit(0);
		}
	}

	@Override
	public void mouseMoved(int x, int y) {
		bPlay.setMouseOver(false);
		bCreative.setMouseOver(false);
		bSettings.setMouseOver(false);
		bQuit.setMouseOver(false);
		if (bPlay.getInBounds().contains(x, y)) {
			bPlay.setMouseOver(true);
		} else if (bCreative.getInBounds().contains(x, y)) {
			bCreative.setMouseOver(true);
		} else if (bSettings.getInBounds().contains(x, y)) {
			bSettings.setMouseOver(true);
		} else if (bQuit.getInBounds().contains(x, y)) {
			bQuit.setMouseOver(true);
		}
	}

	@Override
	public void mousePressed(int x, int y) {
		if (bPlay.getInBounds().contains(x, y)) {
			bPlay.setMousePressed(true);
		} else if (bCreative.getInBounds().contains(x, y)) {
			bCreative.setMousePressed(true);
		} else if (bSettings.getInBounds().contains(x, y)) {
			bSettings.setMousePressed(true);
		} else if (bQuit.getInBounds().contains(x, y)) {
			bQuit.setMousePressed(true);
		}
	}

	@Override
	public void mouseReleased(int x, int y) {
		resetButtons();
	}
	
	@Override
	public void mouseDragged(int x, int y) {
		
	}

	private void resetButtons() {
		bPlay.resetBools();
		bCreative.resetBools();
		bSettings.resetBools();
		bQuit.resetBools();
	}
	
}
