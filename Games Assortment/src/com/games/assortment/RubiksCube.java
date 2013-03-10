package com.games.assortment;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class RubiksCube extends JPanel {

	public void RubiksCube() {
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//g.setColor(Color.BLACK);
		g.fillRect(0, 0, 633, 506);
		
		g.setColor(Color.WHITE);
		g.fillRect(25, 202, 102, 102);
		g.fillRect(152, 152, 202, 202);
		g.fillRect(202, 25, 102, 102);
		g.fillRect(202, 379, 102, 102);
		g.fillRect(377, 202, 102, 102);
		g.fillRect(504, 202, 102, 102);
		
	}
	
}
