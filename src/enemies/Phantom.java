package enemies;

import static helpmethods.Constants.EnemyType.PHANTOM;
import gamemanagers.EnemyManager;

public class Phantom extends Enemy {

	public Phantom(float x, float y, int ID, EnemyManager enemyManager) {
		super(x, y, ID, PHANTOM, enemyManager);
	}

}
