package enemies;

import static helpmethods.Constants.EnemyType.DIAMOND_ZOMBIE;
import gamemanagers.EnemyManager;

public class DiamondZombie extends Enemy {

	public DiamondZombie(float x, float y, int ID, EnemyManager enemyManager) {
		super(x, y, ID, DIAMOND_ZOMBIE, enemyManager);
	}

}
