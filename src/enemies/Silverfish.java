package enemies;

import static helpmethods.Constants.EnemyType.SILVERFISH;
import gamemanagers.EnemyManager;

public class Silverfish extends Enemy {

	public Silverfish(float x, float y, int ID, EnemyManager enemyManager) {
		super(x, y, ID, SILVERFISH, enemyManager);
	}

}
