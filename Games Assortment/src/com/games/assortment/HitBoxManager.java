package com.games.assortment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class HitBoxManager {
	
	ArrayList<Object[]> hitBox = new ArrayList<>();
	Timer collisionTimer;
	
	/* Hit Levels
	 -1 -> Disabled      (eg. temporarily disable collisions for an entity)
	  0 -> Passive       (eg. walls, floors, ceilings)
	  1 -> Low checking  (eg. enemies)
	  2 -> Med checking  (eg. player)
	  3 -> High checking (eg. projectiles)
	 */

	public HitBoxManager(int interval) {
		collisionTimer = new Timer(interval, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				checkCollisions();
			}
			
		});
		collisionTimer.setRepeats(true);
		collisionTimer.start();
	}
	
	public void checkCollisions() {
		
		for(int i=0; i<hitBox.size(); i++) {
			String hitLevels = hitBox.get(i)[0].toString();
			ArrayList<Integer> colliders = new ArrayList<>();
			if(Integer.parseInt(hitLevels.substring(0, 1)) > 0) {colliders.add(0);}
			
			if(hitLevels.length() > 2) {
				for(int n=2; n<hitLevels.length(); n++) {
					colliders.add(Integer.parseInt(hitLevels.substring(n, n+1)));
				}
			}
			
			for(int n=0; n<colliders.size(); n++) {
				for(int t=0; t<hitBox.size(); t++) {
					if(i >= hitBox.size() || t >= hitBox.size()) {return;}
					
					if(colliders.get(n) == Integer.parseInt(hitBox.get(t)[0].toString().substring(0, 1))
					&& ((HitBox) hitBox.get(i)[1]).intersects((HitBox) hitBox.get(t)[1])) {
						((Entity) hitBox.get(i)[2]).collideWith((Entity) hitBox.get(t)[2]);
					}
				}
			}
		}
	}
	
	public void addHitBox(double hitLevel, HitBox hitBox, Entity entity) {
		this.hitBox.add(new Object[]{hitLevel, hitBox, entity});
	}
	public void removeHitBox(Entity entity) {
		
		for(int i=0; i<this.hitBox.size(); i++) {
			if(this.hitBox.get(i)[2] == entity) {
				this.hitBox.remove(i);
			}
		}
		
	}

	public void disableCollisions() {
		if(!collisionTimer.isRunning()) {return;}
		collisionTimer.stop();
	}
	public void enableCollisions() {
		if(collisionTimer.isRunning()) {return;}
		collisionTimer.start();
	}
	
}
