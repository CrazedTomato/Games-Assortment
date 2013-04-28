package com.games.assortment;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;


public class Entity {
	
	public static final int PLAYER             = 0;
	public static final int INVADER1           = 1;
	public static final int INVADER2           = 2;
	public static final int INVADER3           = 3;
	public static final int INVADER4           = 4;
	public static final int BARRICADE          = 5;
	public static final int PLAYER_PROJECTILE  = 6;
	public static final int INVADER_PROJECTILE = 7;
	
	private BufferedImage image;
	private Sprite        sprite;
	private Dimension     maxDim;
	private HitBox        hitBox;
	private HitBoxManager hitBoxManager;
	
	private float x    = 0;
	private float y    = 0;
	private float oldx = -1;
	private float oldy = -1;
	private float vx   = 0;
	private float vy   = 0;
	private long  timePassed = 0;
	private long  lastUpdate = System.currentTimeMillis();
	
	private int type;
	private int health;
	private int damage;
	private int points;
	private double hitLevel;

	public Entity(int type, Dimension maxDim) {
		setProporties(type);
		this.maxDim = maxDim;
		
		hitBox = new HitBox();
		hitBox.setBounds((int) x, (int) y, this.maxDim.width, this.maxDim.height);
		
	}
	
	public int[] update() {
		
		int[] pos = new int[6];
		
		if(oldx != -1) {pos[0] = Math.round(oldx); oldx = -1;}
		else           {pos[0] = Math.round(x);}
		
		if(oldy != -1) {pos[1] = Math.round(oldy); oldy = -1;}
		else           {pos[1] = Math.round(y);}
		
		timePassed = System.currentTimeMillis() - lastUpdate;
		lastUpdate = System.currentTimeMillis();
		
		x += vx*timePassed;
		y += vy*timePassed;
		hitBox.setLocation((int) x, (int) y);
		
		pos[2] = Math.round(x);
		pos[3] = Math.round(y);
		pos[4] = maxDim.width;
		pos[5] = maxDim.height;
		
		return pos;
		
	}
	
	public void setProporties(int type) {
		this.type = type;
		switch(type) {
		
		case PLAYER:
			health = 100;
			damage = -1;
			points = -1;
			hitLevel = 2;
			break;
			
		case INVADER1:
			health = 100;
			damage = -1;
			points = 10;
			hitLevel = 1;
			break;
			
		case INVADER2:
			health = 100;
			damage = -1;
			points = 20;
			hitLevel = 1;
			break;
			
		case INVADER3:
			health = 100;
			damage = -1;
			points = 30;
			hitLevel = 1;
			break;
			
		case INVADER4:
			health = 100;
			damage = -1;
			points = 999;
			hitLevel = 1;
			break;
		
		case BARRICADE: break;
		
		case PLAYER_PROJECTILE:
			health = -1;
			damage = 100;
			points = -1;
			hitLevel = 3.1;
			break;
			
		case INVADER_PROJECTILE:
			health = -1;
			damage = 100;
			points = -1;
			hitLevel = 3.2;
			break;
		}
	}
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public void setX(float x) {
		oldx   = this.y;
		this.x = x;
	}
	public void setY(float y) {
		oldy   = this.y;
		this.y = y;
	}
	public void setVelocity(float vx, float vy) {
		this.vx = vx;
		this.vy = vy;
	}
	public void setHorVelocity(float vx) {
		this.vx = vx;
	}
	public void setVerVelocity(float vy) {
		this.vy = vy;
	}
	
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public float getHorVelocity() {
		return vx;
	}
	public float getVerVelocity() {
		return vy;
	}
	
	public void setHitBoxManager(HitBoxManager hitBoxManager) {
		update();
		
		if(this.hitBoxManager != null) {this.hitBoxManager.removeHitBox(this);}
		this.hitBoxManager = hitBoxManager;
		this.hitBoxManager.addHitBox(hitLevel, hitBox, this);
	}
	public void removeHitBoxManager() {
		if(this.hitBoxManager != null) {this.hitBoxManager.removeHitBox(this);}
	}
	public void collideWith(Entity entity) {
		System.out.println("Collide");
		entity.kill();
		this.kill();
	}
	
	public void kill() {
		
		sprite.removeEntity(this);
		removeHitBoxManager();
		
	}
	
	public void paint(Graphics g) {
		g.drawImage(image, Math.round(x), Math.round(y), null);
	}
	
}
