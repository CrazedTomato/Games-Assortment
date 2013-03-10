package com.games.assortment;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class RubiksCube extends JPanel {
	
	private String difficulty[] = {"Solved", "Easy", "Medium", "Hard", "Superflip"};
	private int    diff_no      = 2;
	private CButton new_cube;
	private CButton diff;

	public RubiksCube() {

		showButtons();
		
	}
	
	public void showButtons() {
		
		setLayout(null);
		
		new_cube = new CButton("New Cube");
		new_cube.setBounds(377, 398, 128, 32);
		
		diff = new CButton(difficulty[diff_no]);
		diff.setBounds(377, 430, 128, 32);
		
		add(new_cube);
		add(diff);
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//g.setColor(Color.BLACK);
		g.fillRect(0, 0, 631, 506);
		
		g.setColor(Color.WHITE);
		g.fillRect(25, 202, 102, 102);
		g.fillRect(152, 152, 202, 202);
		g.fillRect(202, 25, 102, 102);
		g.fillRect(202, 379, 102, 102);
		g.fillRect(377, 202, 102, 102);
		g.fillRect(504, 202, 102, 102);
		
		g.fillRect(377, 398, 128, 64);
		g.fillRect(508, 381, 98, 98);
		
	}
	
}
