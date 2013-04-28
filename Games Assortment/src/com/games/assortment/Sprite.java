package com.games.assortment;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Sprite {
	
	private ArrayList<Entity> entity      = new ArrayList<>();
	private ArrayList<Integer> frameIndex = new ArrayList<>();
	
	private Timer   animation;
	private int     frameTotal;
	private int     interval;
	private BufferedImage[] images;

	public Sprite(BufferedImage[] images, int interval) {
		
		this.images   = images;
		this.interval = interval;
		
		frameTotal = images.length-1;
		
	}
	
	public void startAnimation() {
		
		if(interval == -1) {return;}
		
		if(animation == null) {
			
			frameIndex.add(0);
			animation  = new Timer(interval, new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					for(int i=0; i<entity.size(); i++) {
						if(frameIndex.get(i) < frameTotal) {frameIndex.set(i, frameIndex.get(i)+1);}
						else                               {frameIndex.set(i, 0);}
						sendImage(i);
					}
				}
				
			});
			animation.setRepeats(true);
			animation.start();
			return;
			
		}
		
		if(animation.isRunning()) {return;}
		else                      {animation.start();}
		
		
	}
	public void stopAnimation() {
		
		if(interval < 0)          {return;}
		if(animation == null)     {return;}
		if(animation.isRunning()) {animation.stop();}
		
	}
	
	public void setScale(int width, int height) {
		
		for(int i=0; i<frameTotal+1; i++) {
			images[i] = (BufferedImage) images[i].getScaledInstance(width, height, Image.SCALE_SMOOTH);
		}
		
	}
	
	public void setInterval(int interval) {
		this.interval = interval;
		animation.setDelay(interval);
	}
	public int  getInterval() {
		return interval;
	}
	
	public void addEntity(Entity entity) {
		if(this.entity.contains(entity)) {return;}
		this.entity.add(entity);
		frameIndex.add(0);
		
		entity.setSprite(this);
		entity.setImage(images[frameIndex.get(this.entity.size()-1)]);
		
		//Start animation if this is the first entity
		if(this.entity.size() == 1) {startAnimation();}
	}
	public void removeEntity(Entity entity) {
		if(!this.entity.contains(entity)) {return;}
		this.entity.remove(entity);
		
		//Stop animation if there are no longer any entities
		if(this.entity.size() == 0) {stopAnimation();}
		
	}
	public void removeEntity(int index) {
		if(entity.size() > index) {entity.remove(index);}
		
		//Stop animation if there are no longer any entities
		if(this.entity.size() == 0) {stopAnimation();}
		
	}
	public Entity getEntity(int index) {
		if(entity.size() == 0) {return null;}
		return entity.get(index);
	}
	public int getEntityCount() {
		return entity.size();
	}
	
	public void sendImage(int index) {
		entity.get(index).setImage(images[frameIndex.get(index)]);
	}
	
	public Dimension getMaxDimension() {
		
		int width  = 0;
		int height = 0;
		
		for(int i=0; i<images.length; i++) {
			ImageIcon tempIcon = new ImageIcon(images[i]);
			if(tempIcon.getIconWidth()  > width)  {width  = tempIcon.getIconWidth();}
			if(tempIcon.getIconHeight() > height) {height = tempIcon.getIconHeight();}
		}
		
		return new Dimension(width, height);
		
	}
	
	/*public BufferedImage getImage() {
				
		return images[frameIndex];
		
	}*/
	
	public void paint(Graphics g) {
		
		for(int i=0; i<entity.size(); i++) {
			entity.get(i).paint(g);
		}
		
	}
}
