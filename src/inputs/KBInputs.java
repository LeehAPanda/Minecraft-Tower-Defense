package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static main.GameStates.*;
import main.Game;
import main.GamePanel;
import main.GameStates;

public class KBInputs implements KeyListener {

	private GamePanel gamePanel;
	private Game game;
	public KBInputs(GamePanel gamePanel, Game game) {
		this.gamePanel = gamePanel;
		this.game = game;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (GameStates.gameState == CREATIVE) {
			game.getCreative().keyPressed(e);
		} else if (GameStates.gameState == PLAYING) {
			game.getPlaying().keyPressed(e);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
