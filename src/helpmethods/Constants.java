package helpmethods;

public class Constants {
	public static class Direction {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}
	
	public static class TileCheck {
		public static final int WATER_TILE = 0;
		public static final int GRASS_TILE = 1;
		public static final int ROAD_TILE = 2;
	}
	
	public class TowerType {
		public static final int CANNON = 0;
		public static final int STEVE = 1;
		public static final int ALEX = 2;
		public static final int PIRATE = 3;
		
		public static String GetTowerName(int towerType) {
			switch(towerType) {
			case CANNON:
				return "Cannon";
			case STEVE:
				return "Steve";
			case ALEX:
				return "Alex";
			case PIRATE:
				return "Pirate";
			}
			return "";
		}
		
		public static int GetTowerCost(int towerType) {
			switch(towerType) {
			case CANNON:
				return 15;
			case STEVE:
				return 7;
			case ALEX:
				return 8;
			case PIRATE:
				return 12;
			}
			return 0;
		}
		
		public static int GetDefDmg(int towerType) {
			switch(towerType) {
			case CANNON:
				return 15;
			case STEVE:
				return 6;
			case ALEX:
				return 2;
			case PIRATE:
				return 12;
			}
			return 0;
		}
		
		public static float GetDefRange(int towerType) {
			switch(towerType) {
			case CANNON:
				return 50;
			case STEVE:
				return 100;
			case ALEX:
				return 85;
			case PIRATE:
				return 65;
			}
			return 0;
		}
		
		public static float GetDefAttackSpeed(int towerType) {
			switch(towerType) {
			case CANNON:
				return 120;
			case STEVE:
				return 25;
			case ALEX:
				return 50;
			case PIRATE:
				return 90;
			}
			return 0;
		}
		
	}
	
	public static class TowerProjectiles {
		public static final int ARROW = 0;
		public static final int TNT = 1;
		public static final int LIGHTNING = 2;
		public static final int TRIDENT = 3;
		
		public static float GetSpeed(int type) {
			switch(type) {
			case ARROW:
				return 9f;
			case TNT:
				return 4f;
			case LIGHTNING:
				return 6f;
			case TRIDENT:
				return 5f;
			}
			return 0f;
		}
	}
	
	public static class EnemyType {
		public static final int ZOMBIE = 0;
		public static final int PHANTOM = 1;
		public static final int DIAMOND_ZOMBIE = 2;
		public static final int SILVERFISH = 3;
		public static final int GHAST = 4;
		
		public static int getEmeralds(int enemyType) {
			switch(enemyType) {
			case ZOMBIE:
				return 1;
			case PHANTOM:
				return 2;
			case DIAMOND_ZOMBIE:
				return 5;
			case SILVERFISH:
				return 3;
			case GHAST:
				return 25;
			}
			return 0;
		}
		
		public static final float getEnemySpeed(int enemyType) {
			switch(enemyType) {
			case ZOMBIE:
				return 0.5f;
			case PHANTOM:
				return 0.85f;
			case DIAMOND_ZOMBIE:
				return 0.4f;
			case SILVERFISH:
				return 0.95f;
			case GHAST:
				return 0.35f;
			}
			return 0;
		}

		public static int getMaxHealth(int enemyType) {
			switch(enemyType) {
			case ZOMBIE:
				return 100;
			case PHANTOM:
				return 75;
			case DIAMOND_ZOMBIE:
				return 225;
			case SILVERFISH:
				return 50;
			case GHAST:
				return 450;
			}
			return 0;
		}
	}
	
}
