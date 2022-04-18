package main;

import javax.swing.JFrame;

public class GameWindow extends JFrame {
	private JFrame jframe;
	public GameWindow(GamePanel gamePanel) {
		jframe = new JFrame();
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setResizable(false);
		jframe.setTitle("Minecraft Tower Defense!");
		jframe.add(gamePanel);
		jframe.pack();
		jframe.setVisible(true);
	}
}
