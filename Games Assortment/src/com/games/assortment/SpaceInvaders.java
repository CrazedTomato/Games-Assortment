package com.games.assortment;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

public class SpaceInvaders extends JPanel {
	
	private boolean fullscreen = false;
	
	public SpaceInvaders() {
		
		//Cardlayout manager
		
		//Key bindings
		this.getInputMap().put(KeyStroke.getKeyStroke("F11"), "goFullscreen");
		this.getActionMap().put("goFullscreen", new GoFullscreen(this));
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		setBackground(Color.BLACK);
		
		
		
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
	
}
