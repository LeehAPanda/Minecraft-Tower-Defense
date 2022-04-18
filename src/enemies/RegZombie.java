package enemies;

import static helpmethods.Constants.EnemyType.ZOMBIE;
import gamemanagers.EnemyManager;

public class RegZombie extends Enemy {

	public RegZombie(float x, float y, int ID, EnemyManager enemyManager) {
		super(x, y, ID, ZOMBIE, enemyManager);
	}

}
