package gamemanagers;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import enemies.Enemy;
import gamescenes.PlayingScene;
import helpmethods.LoadSave;
import objects.Tower;
import objects.TowerProjectiles;
import static helpmethods.Constants.TowerType.*;
import static helpmethods.Constants.TowerProjectiles.*;

public class ProjectileManager {
	private PlayingScene playing;
	private ArrayList<TowerProjectiles> projectiles = new ArrayList<>();
	private ArrayList<Explosion> explosion = new ArrayList<>();
	private BufferedImage[] projSprites, explodeAnim;
	private int proj_id = 0;
	public ProjectileManager(PlayingScene playing) {
		this.playing = playing;
		importSprites();
	}
	
	private void importSprites() {
		BufferedImage atlas = LoadSave.getSpriteAtlas();
		projSprites = new BufferedImage[4];
		for (int i = 0; i < 4; i++)
			projSprites[i] = atlas.getSubimage((9+i)*32, 32, 32, 32);
		importExplosion(atlas);
	}
	
	private void importExplosion(BufferedImage atlas) {
		explodeAnim = new BufferedImage[7];
		for (int i = 0; i < 7; i++)
			explodeAnim[i] = atlas.getSubimage(0+i*32, 32*2, 32, 32);
	}

	public void newProj(Tower t, Enemy e) {
		int type = getProjType(t);
		// math of calculating projectile speed.
		int xDist = (int)(t.getX() - e.getX());
		int yDist = (int)(t.getY() - e.getY());
		int totDist = Math.abs(xDist) + Math.abs(yDist);
		float xPer = (float) Math.abs(xDist) / totDist;
		
		float xSpeed = xPer * helpmethods.Constants.TowerProjectiles.GetSpeed(type);
		float ySpeed = helpmethods.Constants.TowerProjectiles.GetSpeed(type) - xSpeed;
		
		if (t.getX() > e.getX())
			xSpeed *= -1;
		if (t.getY() > e.getY())
			ySpeed *= -1;
		
		float rotate = 0;
		if (type == ARROW || type == TRIDENT) {
			float arcVal = (float) Math.atan(yDist / (float)xDist);
			rotate = (float) Math.toDegrees(arcVal);
			if (xDist < 0)
				rotate += 180;
		}
		for (TowerProjectiles p : projectiles)
			if (!p.isActive())
				if (p.getProjectileType() == type) {
					p.reuse(t.getX()+16, t.getY()+16, xSpeed, ySpeed, t.getDmg(), rotate);
					return;
				}
		projectiles.add(new TowerProjectiles(t.getX()+16, t.getY()+16, xSpeed, ySpeed, t.getDmg(), rotate, proj_id++, type)); 
	}

	public void update() {
		for (TowerProjectiles p : projectiles)
			if (p.isActive()) {
				p.move();
				if (projHitsEnemy(p)) {
					p.setActive(false);
					if (p.getProjectileType() == TNT) {
						explosion.add(new Explosion(p.getPos()));
						explodeOnEnemies(p);
					}
				} else if (isProjOutBounds(p)) {
					p.setActive(false);
				}
			}
		for (Explosion e : explosion)
			if (e.getInd() < 7)
				e.update();
	}

	private void explodeOnEnemies(TowerProjectiles p) {
		for (Enemy e : playing.getEnemyManager().getEnemies()) {
			if (e.isAlive()) {
				float radius = 40.0f;
				float xDist = Math.abs(p.getPos().x - e.getX());
				float yDist = Math.abs(p.getPos().y - e.getY());
				float hypotenuse = (float) Math.hypot(xDist, yDist);
				if (hypotenuse < radius)
					e.damaged(p.getDmg());
			}
		}
	}

	private boolean projHitsEnemy(TowerProjectiles p) {
		for (Enemy e : playing.getEnemyManager().getEnemies()) {
			if (e.isAlive())
				if (e.getBounds().contains(p.getPos())) {
					e.damaged(p.getDmg());
					if (p.getProjectileType() == LIGHTNING)
						e.slow();
					if (p.getProjectileType() == TRIDENT)
						e.stun();
					return true;
				}
		}
		return false;
	}
	
	private boolean isProjOutBounds(TowerProjectiles p) {
		if (p.getPos().x >= 0)
			if (p.getPos().x <= 640)
				if (p.getPos().y >= 0)
					if (p.getPos().y <= 800)
						return false;
		return true;
	}

	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		for (TowerProjectiles p : projectiles)
			if(p.isActive()) {
				if (p.getProjectileType() == ARROW || p.getProjectileType() == TRIDENT) {
					g2.translate(p.getPos().x, p.getPos().y);
					g2.rotate(Math.toRadians(p.getRotation()));
					g2.drawImage(projSprites[p.getProjectileType()], -16, -16, null);
					g2.rotate(-Math.toRadians(p.getRotation()));
					g2.translate(-p.getPos().x, -p.getPos().y);
				} else {
					g2.drawImage(projSprites[p.getProjectileType()], (int)p.getPos().x-16, (int)p.getPos().y-16, null);
				}
			}
		drawExplode(g2);
	}
	
	private void drawExplode(Graphics2D g2) {
		for (Explosion e : explosion)
			if (e.getInd() < 7)
				g2.drawImage(explodeAnim[e.getInd()], (int)e.getPos().x-16, (int)e.getPos().y-16, null);
	}

	private int getProjType(Tower t) {
		switch(t.getTowerType()) {
		case STEVE:
			return ARROW;
		case CANNON:
			return TNT;
		case ALEX:
			return LIGHTNING;
		case PIRATE:
			return TRIDENT;
		}
		return 0;
	}
	
	public class Explosion {
		private Point2D.Float pos;
		private int explodeTick = 0, explodeInd = 0;
		public Explosion(Point2D.Float pos) {
			this.pos = pos;
		}
		
		public void update() {
			explodeTick++;
			if (explodeTick >= 12) {
				explodeTick = 0;
				explodeInd++;
			}
		}
		
		public int getInd() {
			return explodeInd;
		}
		
		public Point2D.Float getPos() {
			return pos;
		}
	}
	
	public void reset() {
		projectiles.clear();
		explosion.clear();
		proj_id = 0;
	}
}
