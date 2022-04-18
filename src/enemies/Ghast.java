package enemies;

import static helpmethods.Constants.EnemyType.GHAST;
import gamemanagers.EnemyManager;

public class Ghast extends Enemy {
	public Ghast(float x, float y, int ID, EnemyManager enemyManager) {
		super(x, y, ID, GHAST, enemyManager);
	}
}
