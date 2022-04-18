package gamemanagers;

import java.util.ArrayList;
import java.util.Arrays;
import events.Wave;
import gamescenes.PlayingScene;

public class WaveManager {
	private PlayingScene playing;
	private ArrayList<Wave> waves = new ArrayList<>();
	private int enemySpawnTimerLimit = 60 * 1;
	private int enemySpawnTimer = enemySpawnTimerLimit;
	private int enemyInd, waveInd;
	private int waveTimerLimit = 60 * 5;
	private int waveTimer = 0;
	private boolean waveStartTimer, waveTickTimerOver;
	public WaveManager(PlayingScene playing) {
		this.playing = playing;
		createWaves();
	}
	
	public void update() {
		if (enemySpawnTimer < enemySpawnTimerLimit)
			enemySpawnTimer++;
		if (waveStartTimer) {
			waveTimer++;
			if (waveTimer >= waveTimerLimit) {
				waveTickTimerOver = true;
			}
		}
	}
	
	public void increaseWaveInd() {
		waveInd++;
		waveTimer = 0;
		waveTickTimerOver = false;
		waveStartTimer = false;
	}
	
	public boolean isWaveTimerOver() {
		return waveTickTimerOver;
	}
	
	public void startWaveTimer() {
		waveStartTimer = true;
	}
	
	public int getNextEnemy() {
		enemySpawnTimer = 0;
		return waves.get(waveInd).getEnemyList().get(enemyInd++);
	}
	
	private void createWaves() {
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 1))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 1, 1, 1))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(3, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 3, 1))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(3, 3, 3, 3, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(3, 3, 3, 3, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(3, 3, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 4))));
	}

	public ArrayList<Wave> getWaves() {
		return waves;
	}

	public boolean newEnemyTimer() {
		return enemySpawnTimer >= enemySpawnTimerLimit;
	}
	
	public boolean isThereMoreEnemiesInWave() {
		return enemyInd < waves.get(waveInd).getEnemyList().size();
	}

	public boolean isThereMoreWaves() {
		return (waveInd + 1) < waves.size();
	}

	public void resetEnemyInd() {
		enemyInd = 0;
	}
	
	public int getWaveInd() {
		return waveInd;
	}
	
	public float getRemainingTime() {
		float secondsLeft = waveTimerLimit - waveTimer;
		return secondsLeft / 60.0f;
	}
	
	public boolean isWaveTimerStarted() {
		return waveStartTimer;
	}
	
	public void reset() {
		waves.clear();
		createWaves();
		enemyInd = 0;
		enemySpawnTimer = enemySpawnTimerLimit;
		waveInd = 0;
		waveTimer = 0;
		waveStartTimer = false;
		waveTickTimerOver = false;
	}
}
