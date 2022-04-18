package main;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import inputs.KBInputs;
import inputs.MouseInputs;

public class GamePanel extends JPanel {
	private MouseInputs mouseInputs;
	private Game game;
	private Dimension windSize;
	
	public GamePanel(Game game) {
		this.game = game;
		setWindSize();
		mouseInputs = new MouseInputs(this, game);
		addKeyListener(new KBInputs(this, game));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}
	
	private void setWindSize() {
		windSize = new Dimension(640, 800);
		setPreferredSize(windSize);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.getRender().renderGame(g);
	}
	
}
