package main;

import javax.swing.JFrame;
import gamemanagers.TileEditor;
import gamescenes.CreativeMode;
import gamescenes.GameOverScene;
import gamescenes.MenuScene;
import gamescenes.PlayingScene;
import gamescenes.SettingsScene;
import helpmethods.LoadSave;

public class Game extends JFrame implements Runnable {
	private Thread gameThread;
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private final double LOCKED_FPS = 120.0;
	private final double TICKRATE = 60.0;
	
	// Classes for Game Renders
	private Render render;
	private MenuScene menu;
	private PlayingScene playing;
	private SettingsScene settings;
	private CreativeMode creative;
	private GameOverScene gameOver;
	
	private TileEditor tileEditor;
	
	public Game() {
		initClasses();
		createDefLvl();
		startGame();
	}
	
	private void initClasses() {
		tileEditor = new TileEditor();
		render = new Render(this);
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus();
		menu = new MenuScene(this);
		playing = new PlayingScene(this);
		settings = new SettingsScene(this);
		creative = new CreativeMode(this);
		gameOver = new GameOverScene(this);
	}
	
	private void createDefLvl() {
		int[] arr = new int[400];
		for (int i = 0; i < arr.length; i++)
			arr[i] = 0;
		LoadSave.createLvl("new_level", arr);
	}

	protected void startGame() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	private void updateGame() {
		switch(GameStates.gameState) {
		case CREATIVE:
			creative.update();
			break;
		case MENU:
			break;
		case PLAYING:
			playing.update();
			break;
		case SETTINGS:
			break;
		default:
			break;
		
		}
	}
	
	@Override
	public void run() {
		double frameTimer;
		long currentTime;
		long lastCheck = System.nanoTime();
		long lastTick = System.nanoTime();
		long lastTime = System.currentTimeMillis();
		double tickUpdate = System.currentTimeMillis();
		tickUpdate = 1000000000.0 / TICKRATE;
		frameTimer = 1000000000.0 / LOCKED_FPS;
		int frames = 0;
		int ticks = 0;
		
		while (true) {
			currentTime = System.nanoTime();
			if (currentTime - lastCheck >= frameTimer) {
				gamePanel.repaint();
				lastCheck = currentTime;
				frames++;
			}
			
			if (currentTime - lastTick >= tickUpdate) {
				updateGame();
				lastTick = currentTime;
				ticks++;
			}
			
			if (System.currentTimeMillis() - lastTime >= 1000) {
				System.out.println("FPS: " + frames + " | TICK: " + ticks);
				frames = 0;
				ticks = 0;
				lastTime = System.currentTimeMillis();
			}
		}
	}
	
	// Getters
	public TileEditor getTileEditor() {
		return tileEditor;
	}
	
	public Render getRender() {
		return render;
	}

	public MenuScene getMenu() {
		return menu;
	}
	
	public PlayingScene getPlaying() {
		return playing;
	}

	public SettingsScene getSettings() {
		return settings;
	}
	
	public CreativeMode getCreative() {
		return creative;
	}
	
	public GameOverScene getGameOver() {
		return gameOver;
	}
	
}
