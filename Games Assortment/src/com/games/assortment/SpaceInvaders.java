package com.games.assortment;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

public class SpaceInvaders extends JPanel {
	
	private boolean fullscreen   = false;
	private boolean rightPressed = false;
	private boolean leftPressed  = false;
	private boolean spacePressed = false;
	
	private int     drop       = 0;
	private int     direction   = 1;
	
	private Timer updateEntities;
	private Timer createMissile;
	
	private HitBoxManager hitBoxManager;
	
	private Sprite invader1;
	private Sprite invader2;
	private Sprite invader3;
	private Sprite invader4;
	private Sprite player;
	private Sprite sprBarricade;
	private Sprite missile1;
	private Sprite sprProjectile2;
	private Sprite sprProjectile3;
	private Sprite sprExplsoion;
	
	public SpaceInvaders() {
		
		//Key bindings
		createKeybindings();
		
		//Graphics
		createSprites();
		
		//Create HitBoxManager for managing collisions
		hitBoxManager = new HitBoxManager(20);
		
		//Entities
		createEntities();
		updateEntities = new Timer(20, new ActionListener() {

			int  framerate  = 0;
			long timePassed = 0;
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				long curTime = System.currentTimeMillis();
				if(timePassed < 1000) {
					framerate++;
					timePassed += 20+System.currentTimeMillis()-curTime;
				} else {timePassed = 0; framerate = 0;}
				updateInvaders();
				updatePlayer();
				updateMissiles();
			}
			
		});
		updateEntities.setRepeats(true);
		updateEntities.start();
		
		//Stop threads and timers on close
		this.addAncestorListener(new AncestorListener() {

			@Override
			public void ancestorRemoved(AncestorEvent e) {
				stopThreads();
				System.gc();
			}

			@Override
			public void ancestorAdded(AncestorEvent event) {}

			@Override
			public void ancestorMoved(AncestorEvent event) {}
			
		});
		
	}

	private void createKeybindings() {
		
		//F11 - Fullscreen
		this.getInputMap().put(KeyStroke.getKeyStroke("released F11"), "goFullscreen");
		this.getActionMap().put("goFullscreen", new GoFullscreen(this));
		
		//Right arrow - Move right
		this.getInputMap().put(KeyStroke.getKeyStroke("pressed RIGHT") , "startRight");
		this.getInputMap().put(KeyStroke.getKeyStroke("released RIGHT"), "stopRight" );
		this.getActionMap().put("startRight", new MoveRight(true ));
		this.getActionMap().put("stopRight" , new MoveRight(false));
		
		//Left arrow pressed - Move left
		this.getInputMap().put(KeyStroke.getKeyStroke("pressed LEFT") , "startLeft");
		this.getInputMap().put(KeyStroke.getKeyStroke("released LEFT"), "stopLeft" );
		this.getActionMap().put("startLeft", new MoveLeft(true ));
		this.getActionMap().put("stopLeft" , new MoveLeft(false));
		
		//Space bar pressed - Shoot
		this.getInputMap().put(KeyStroke.getKeyStroke("pressed SPACE") , "startShoot");
		this.getInputMap().put(KeyStroke.getKeyStroke("released SPACE"), "stopShoot" );
		this.getActionMap().put("startShoot", new ShootMissile(true ));
		this.getActionMap().put("stopShoot" , new ShootMissile(false));
		
	}
	private void createSprites() {
		
	try {
			
		BufferedImage bimg_invader11 = ImageIO.read(this.getClass().getResource("/resources/Space Invaders/Invader 1.1.png"));
		BufferedImage bimg_invader12 = ImageIO.read(this.getClass().getResource("/resources/Space Invaders/Invader 1.2.png"));
		BufferedImage bimg_invader21 = ImageIO.read(this.getClass().getResource("/resources/Space Invaders/Invader 2.1.png"));
		BufferedImage bimg_invader22 = ImageIO.read(this.getClass().getResource("/resources/Space Invaders/Invader 2.2.png"));
		BufferedImage bimg_invader31 = ImageIO.read(this.getClass().getResource("/resources/Space Invaders/Invader 3.1.png"));
		BufferedImage bimg_invader32 = ImageIO.read(this.getClass().getResource("/resources/Space Invaders/Invader 3.2.png"));
		BufferedImage bimg_invader4  = ImageIO.read(this.getClass().getResource("/resources/Space Invaders/Invader 4.png"));
		BufferedImage bimg_player    = ImageIO.read(this.getClass().getResource("/resources/Space Invaders/Player.png"));
		BufferedImage bimg_missile1  = ImageIO.read(this.getClass().getResource("/resources/Space Invaders/Missile 1.png"));
		
		invader1 = new Sprite(new BufferedImage[]{bimg_invader11, bimg_invader12}, 1000);
		invader2 = new Sprite(new BufferedImage[]{bimg_invader21, bimg_invader22}, 1000);
		invader3 = new Sprite(new BufferedImage[]{bimg_invader31, bimg_invader32}, 1000);
		invader4 = new Sprite(new BufferedImage[]{bimg_invader4}, -1);
		player   = new Sprite(new BufferedImage[]{bimg_player}  , -1);
		missile1 = new Sprite(new BufferedImage[]{bimg_missile1}, -1);
			
	} catch (IOException e) {e.printStackTrace();}
				
		
	//	sprBarricade   = new Sprite(new Image[]{img_barricade} , -1);
	//	sprProjectile1 = new Sprite(new Image[]{img_projectile1}, -1);
	//	sprProjectile2 = new Sprite(new Image[]{img_projectile2}, -1);
	//	sprProjectile3 = new Sprite(new Image[]{img_projectile3}, -1);
	//	sprExplosion   = new Sprite(new Image[]{img_explosion}, -1);
		
	}
	private void createEntities() {
		
		for(int i=0; i<22; i+=2) {
			invader1.addEntity(new Entity(Entity.INVADER1, invader1.getMaxDimension()));
			invader1.getEntity(i).setX(((int) Math.floor(i/2)+1)*48 + 96);
			invader1.getEntity(i).setY(156);
			invader1.getEntity(i).setHitBoxManager(hitBoxManager);
			
			invader1.addEntity(new Entity(Entity.INVADER1, invader1.getMaxDimension()));
			invader1.getEntity(i+1).setX(((int) Math.floor(i/2)+1)*48 + 96);
			invader1.getEntity(i+1).setY(128);
			invader1.getEntity(i+1).setHitBoxManager(hitBoxManager);
			
			invader2.addEntity(new Entity(Entity.INVADER2, invader2.getMaxDimension()));
			invader2.getEntity(i).setX(((int) Math.floor(i/2)+1)*48 + 96);
			invader2.getEntity(i).setY(96);
			invader2.getEntity(i).setHitBoxManager(hitBoxManager);
			
			invader2.addEntity(new Entity(Entity.INVADER2, invader2.getMaxDimension()));
			invader2.getEntity(i+1).setX(((int) Math.floor(i/2)+1)*48 + 96);
			invader2.getEntity(i+1).setY(64);
			invader2.getEntity(i+1).setHitBoxManager(hitBoxManager);
			
			invader3.addEntity(new Entity(Entity.INVADER3, invader3.getMaxDimension()));
			invader3.getEntity((int) Math.floor(i/2)).setX(((int) Math.floor(i/2)+1)*48 + 96);
			invader3.getEntity((int) Math.floor(i/2)).setY(32);
			invader3.getEntity(i/2).setHitBoxManager(hitBoxManager);
		}
		
		player.addEntity(new Entity(Entity.PLAYER, player.getMaxDimension()));
		player.getEntity(0).setX(376);
		player.getEntity(0).setY(500);
		player.getEntity(0).setHitBoxManager(hitBoxManager);
		
	}
	
	private void stopThreads() {
		
		updateEntities.stop();
		hitBoxManager.disableCollisions();
		
		for(int i=0; i<invader1.getEntityCount(); i++) {invader1.stopAnimation();}
		for(int i=0; i<invader2.getEntityCount(); i++) {invader2.stopAnimation();}
		for(int i=0; i<invader3.getEntityCount(); i++) {invader3.stopAnimation();}
		//entInvader4   .stopAnimation();
		//entPlayer     .stopAnimation();
		//entBarricade  .stopAnimation();
		//entProjectile1.stopAnimation();
		//entProjectile2.stopAnimation();
		//entProjectile3.stopAnimation();
		//entExplsoion  .stopAnimation();
		
	}
	
	private synchronized void updateInvaders() {
		
		float left1  = -1;
		float left2  = -1;
		float left3  = -1;
		float right1 = -1;
		float right2 = -1;
		float right3 = -1;
		
		if(invader1.getEntity(0) != null) {left1 = invader1.getEntity(0).getX();}
		if(invader2.getEntity(0) != null) {left2 = invader2.getEntity(0).getX();}
		if(invader3.getEntity(0) != null) {left3 = invader3.getEntity(0).getX();}
		
		if(invader1.getEntity(invader1.getEntityCount()-1) != null) {right1 = invader1.getEntity(invader1.getEntityCount()-1).getX();}
		if(invader2.getEntity(invader2.getEntityCount()-1) != null) {right2 = invader2.getEntity(invader2.getEntityCount()-1).getX();}
		if(invader3.getEntity(invader3.getEntityCount()-1) != null) {right3 = invader3.getEntity(invader3.getEntityCount()-1).getX();}
		
		float[] leftSide  = {left1 , left2 , left3 };
		float[] rightSide = {right1, right2, right3};
		
		int prevDrop = drop;
		for(int i=0; i<3; i++) {
			
			if(leftSide[i] != -1 && leftSide[i] < 32) {
				direction = 1;
				if(drop == 0) {drop = 1;}
				else          {drop = 2;}
				break;
			}
			
			if(rightSide[i] != -1 && rightSide[i] > 704) {
				direction = -1;
				if(drop == 0) {drop = 1;}
				else          {drop = 2;}
				break;
			}
			
		}
		if(drop == prevDrop) {drop = 0;}
		
		//Check which row the bottom invaders are on
		int level = getLevel();
		if(level == 12) {endGame(1); return;}
		
		Sprite[] invader = {invader1, invader2, invader3};
		for(int i=0; i<invader.length; i++) {
			for(int n=0; n<invader[i].getEntityCount(); n++) {
				
				if(drop == 1) {
					invader[i].getEntity(n).setY(invader[i].getEntity(n).getY()+32);
					invader[i].setInterval(1000-(level*50));
				} else {invader[i].getEntity(n).setHorVelocity(0.005f*level*direction);}
				
			    drawInvader(invader[i].getEntity(n));
			}
		}
		
	}
	private synchronized void updatePlayer() {
		
		if(player.getEntity(0).getX() < 32)  {player.getEntity(0).setX(32);}
		if(player.getEntity(0).getX() > 720) {player.getEntity(0).setX(720);}
		drawPlayer();
		
	}
	private synchronized void updateMissiles() {
		
		//If any player missiles exist then check if it is outside the screen
		if(missile1.getEntityCount() > 0 && missile1.getEntity(0).getY() < -12) {missile1.removeEntity(0);}
		drawMissiles();
		
	}
	
	private int getLevel() {
		
		int   level = 0;
		float y     = 0;
		
		if(invader1.getEntityCount() > 0)      {y = getLowestInvader(invader1);}
		else if(invader2.getEntityCount() > 0) {y = getLowestInvader(invader2);}
		else if(invader3.getEntityCount() > 0) {y = getLowestInvader(invader3);}
		else                                   {endGame(0);}
		
		level = (int) y/32;
		return level;
		
	}
	private float getLowestInvader(Sprite sprite) {
		
		float y = 0;		
		for(int i=0; i<sprite.getEntityCount(); i++) {
			if(sprite.getEntity(i).getY() > y) {y = sprite.getEntity(i).getY();}
		} return y;
		
	}
	public void endGame(int ending) {
		System.out.println("GAME OVER"+ending);
		stopThreads();
		//for(int i=0; i<invader1.getEntityCount(); i++) {invader1.stopAnimation();}
		//for(int i=0; i<invader2.getEntityCount(); i++) {invader2.stopAnimation();}
		//for(int i=0; i<invader3.getEntityCount(); i++) {invader3.stopAnimation();}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		setBackground(new Color(25,25,25));
		
		invader1.paint(g);
		invader2.paint(g);
		invader3.paint(g);
		  player.paint(g);
		missile1.paint(g);
		
	}
	
	public void drawInvader(Entity entity) {
		
		int[] pos = entity.update();
		repaint(pos[2], pos[3], pos[4], pos[5]);
		repaint(pos[0], pos[1], pos[4], pos[5]);
		
	}
	public void drawPlayer() {
		
		int[] pos = player.getEntity(0).update();
		repaint(pos[2], pos[3], pos[4], pos[5]);
		repaint(pos[0], pos[1], pos[4], pos[5]);
		
	}
	public void drawMissiles() {
		
		for(int i=0; i<missile1.getEntityCount(); i++) {
			int[] pos = missile1.getEntity(i).update();
			repaint(pos[2], pos[3], pos[4], pos[5]);
			repaint(pos[0], pos[1], pos[4], pos[5]);
		}
		
	}
	
	
	
	
	
	private class GoFullscreen extends AbstractAction {
		
		JPanel panel;
		
		public GoFullscreen(JPanel panel) {
			this.panel = panel;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(fullscreen) {fullscreen = false;}
			else           {fullscreen = true;}
			((CFrame) SwingUtilities.getWindowAncestor(panel)).setFullscreen(fullscreen);
		}
		
	}
	private class MoveRight extends AbstractAction {
		
		boolean pressed = false;		
		
		public MoveRight(boolean pressed) {
			this.pressed = pressed;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(pressed && !rightPressed) {
				rightPressed = true;
				player.getEntity(0).setHorVelocity(player.getEntity(0).getHorVelocity()+0.2f);
			}
			
			else if(!pressed) {
				rightPressed = false;
				player.getEntity(0).setHorVelocity(player.getEntity(0).getHorVelocity()-0.2f);
			}
			
		}
		
	}
	private class MoveLeft extends AbstractAction {
		
		boolean pressed = false;		
		
		public MoveLeft(boolean pressed) {
			this.pressed = pressed;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(pressed && !leftPressed) {
				leftPressed = true;
				player.getEntity(0).setHorVelocity(player.getEntity(0).getHorVelocity()-0.2f);
			}
			
			else if(!pressed) {
				leftPressed = false;
				player.getEntity(0).setHorVelocity(player.getEntity(0).getHorVelocity()+0.2f);
			}
			
		}
		
	}
	private class ShootMissile extends AbstractAction {
		
		boolean pressed;
		
		public ShootMissile(boolean pressed) {
			this.pressed = pressed;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(pressed && !spacePressed) {
				
				if(createMissile == null) {
					createMissile = new Timer(20, new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							if(missile1.getEntityCount() == 0) {
								missile1.addEntity(new Entity(Entity.PLAYER_PROJECTILE, missile1.getMaxDimension()));
								missile1.getEntity(0).setX(player.getEntity(0).getX()+24);
								missile1.getEntity(0).setY(player.getEntity(0).getY()+8);
								missile1.getEntity(0).setVerVelocity(-0.5f);
								missile1.getEntity(0).setHitBoxManager(hitBoxManager);
							}
						}
						
					});
					createMissile.setRepeats(true);
				}
				
				spacePressed = true;
				createMissile.start();
			}
			
			else if(!pressed) {
				spacePressed = false;
				createMissile.stop();
			}
			
			
			
		}
		
	}

}
