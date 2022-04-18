package helpmethods;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import objects.PathPoint;

public class LoadSave {
	public static BufferedImage getSpriteAtlas() {
		BufferedImage img = null;
		InputStream inp = LoadSave.class.getClassLoader().getResourceAsStream("Sprites/sprites.png");
		
		try {
			img = ImageIO.read(inp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return img;
	}
	
	public static void playMusic(String path) {
		try {
			AudioInputStream inp = AudioSystem.getAudioInputStream(new File(path));
			Clip clip = AudioSystem.getClip();
			clip.open(inp);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-25.0f);
			clip.loop(5);
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static BufferedImage getMenuBG() {
		BufferedImage menuBg = null;
		InputStream inp = LoadSave.class.getClassLoader().getResourceAsStream("Misc/menuBackground.png");
		try {
			menuBg = ImageIO.read(inp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return menuBg;
	}
	
	public static BufferedImage getSettingsBG() {
		BufferedImage settingsBg = null;
		InputStream inp = LoadSave.class.getClassLoader().getResourceAsStream("Misc/settingsBackground.png");
		try {
			settingsBg = ImageIO.read(inp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return settingsBg;
	}
	
	public static BufferedImage getGameOverScreen() {
		BufferedImage gameOverScreen = null;
		InputStream inp = LoadSave.class.getClassLoader().getResourceAsStream("Misc/gameOverScreen.png");
		try {
			gameOverScreen = ImageIO.read(inp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return gameOverScreen;
	}
	
	public static void createTxtFile() {
		File txtFile = new File("Resources/SaveFiles/testTxt.txt");
		try {
			txtFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void createLvl(String name, int[] idArr) {
		File newLvl = new File("Resources/SaveFiles/" + name + ".txt");
		if (newLvl.exists()) {
			System.out.println("File: " + name + " already exists!");
			return;
		} else {
			try {
				newLvl.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			writeToFile(newLvl, idArr, new PathPoint(0,0), new PathPoint(0,0));
		}
	}
	
	private static void writeToFile(File f, int[] idArr, PathPoint start, PathPoint end) {
		try (PrintWriter pw = new PrintWriter(f)){
			for (Integer i : idArr) {
				pw.println(i);
			}
			pw.println(start.getxCord());
			pw.println(start.getyCord());
			pw.println(end.getxCord());
			pw.println(end.getyCord());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveLvl(String name, int [][] idArr, PathPoint start, PathPoint end) {
		File lvlFile = new File("Resources/SaveFiles/" + name + ".txt");
		if (lvlFile.exists()) {
			writeToFile(lvlFile, Utilities.twoDArrListToOneDArrList(idArr), start, end);
		} else {
			System.out.println("File: " + name + " already exists!");
			return;
		}
	}
	
	private static ArrayList<Integer> readFile(File file) {
		ArrayList<Integer> list = new ArrayList<>();
		try (Scanner in = new Scanner(file)){
			while(in.hasNextLine()) {
				list.add(Integer.parseInt(in.nextLine()));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static ArrayList<PathPoint> getLvlStartEnd(String name) {
		File lvlFile = new File("Resources/SaveFiles/" + name + ".txt");
		if (lvlFile.exists()) {
			ArrayList<Integer> list = readFile(lvlFile);
			ArrayList<PathPoint> startEndPoints = new ArrayList<>();
			startEndPoints.add(new PathPoint(list.get(400), list.get(401)));
			startEndPoints.add(new PathPoint(list.get(402), list.get(403)));
			return startEndPoints;
		} else {
			System.out.println("File: " + name + " does not exist!");
			return null;
		}
		
	}
	
	public static int[][] getLvlData(String name) {
		File lvlFile = new File("Resources/SaveFiles/" + name + ".txt");
		if (lvlFile.exists()) {
			ArrayList<Integer> list = readFile(lvlFile);
			return Utilities.arrTo2Dint(list, 20, 20);
		} else {
			System.out.println("File: " + name + " does not exist!");
			return null;
		}
	}

}
