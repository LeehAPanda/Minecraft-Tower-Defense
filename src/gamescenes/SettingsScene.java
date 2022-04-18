package gamescenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import helpmethods.LoadSave;
import main.Game;
import userinterface.Button;
import static main.GameStates.*;

public class SettingsScene extends GameScene implements SceneMethods {
	
	private Button bMenu;
	private BufferedImage settingsBg;
	public SettingsScene(Game game) {
		super(game);
		importSettingsBG();
		initButtons();
	}

	private void importSettingsBG() {
		settingsBg = LoadSave.getSettingsBG();
	}

	private void initButtons() {
		bMenu = new Button("Menu", 2, 2, 100, 30);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(settingsBg, 0, 0, 640, 800, null);
		drawButtons(g);
		g.setColor(Color.WHITE);
		g.setFont(new Font("ComicSans", Font.BOLD, 30));
		g.drawString("Coming Soon!", 200, 350);
	}
	
	private void drawButtons(Graphics g) {
		bMenu.draw(g);
	}

	@Override
	public void mouseClicked(int x, int y) {
		if (bMenu.getInBounds().contains(x, y))
			SetGameState(MENU);
	}

	@Override
	public void mouseMoved(int x, int y) {
		bMenu.setMouseOver(false);
		if (bMenu.getInBounds().contains(x, y))
			bMenu.setMouseOver(true);
	}

	@Override
	public void mousePressed(int x, int y) {
		if (bMenu.getInBounds().contains(x, y))
			bMenu.setMousePressed(true);
	}

	@Override
	public void mouseReleased(int x, int y) {
		resetButtons();
	}
	
	@Override
	public void mouseDragged(int x, int y) {
		
	}

	private void resetButtons() {
		bMenu.resetBools();
	}

}
