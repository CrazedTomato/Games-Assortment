package com.games.assortment;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

public class RubiksCube extends JPanel {
	
	private String     dir              = "";
	private String     difficulty[]     = {"Solved", "Easy", "Medium", "Hard", "Superflip"};
	private int        diff_no          = 2;
	private int        grid[][]         = {{-1, -1},{-1, -1},{-1, -1}};
	private int        grid_lines[][][] = { { {1,1 , 2,1 , 2,2},             {1,1 , 1,2 , 2,2 , 2,3 , 2,4},                   {1,2 , 2,4 , 2,5} },             //First Row
											{ {2,1 , 2,2 , 3,1 , 4,1 , 4,2}, {2,2 , 2,3 , 2,4 , 3,1 , 3,2 , 4,2 , 4,3 , 4,4}, {2,4 , 2,5 , 3,2 , 4,4 , 4,5} }, //Second Row
											{ {4,1 , 4,2 , 5,1},             {4,1 , 4,2 , 4,3 , 5,1 , 5,2},                   {4,4 , 4,5 , 5,2} } };           //Third Row
	private Color      bg_color         = new Color(51, 51, 51);
	private Color      selected_color   = new Color(0, 130, 255);
	private Color      solved_color     = new Color(0, 130, 25);
	private CButton    new_cube;
	private CButton    diff;
	private CPaintList main_grid;

	public RubiksCube() {

		createGrids();
		showButtons();
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int xPos = e.getX();
				int yPos = e.getY();
				
				//Main grid
				if(xPos >= 152 && xPos <= 216 && yPos >= 152 && yPos <= 216) {gridPressed(1, 1);}
				if(xPos >= 221 && xPos <= 285 && yPos >= 152 && yPos <= 216) {gridPressed(1, 2);}
				if(xPos >= 152 && xPos <= 216 && yPos >= 152 && yPos <= 216) {gridPressed(1, 3);}
				if(xPos >= 221 && xPos <= 285 && yPos >= 152 && yPos <= 216) {gridPressed(2, 1);}
				if(xPos >= 152 && xPos <= 216 && yPos >= 152 && yPos <= 216) {gridPressed(2, 2);}
				if(xPos >= 221 && xPos <= 285 && yPos >= 152 && yPos <= 216) {gridPressed(2, 3);}
				if(xPos >= 152 && xPos <= 216 && yPos >= 152 && yPos <= 216) {gridPressed(3, 1);}
				if(xPos >= 221 && xPos <= 285 && yPos >= 152 && yPos <= 216) {gridPressed(3, 2);}
				if(xPos >= 152 && xPos <= 216 && yPos >= 152 && yPos <= 216) {gridPressed(3, 3);}
			}
		});
		
	}
	
	public void createGrids() {
		
		main_grid = new CPaintList();
		main_grid.add("1:1", 216, 152, 5, 64, "rectangle", bg_color); //First Row
		main_grid.add("1:2", 285, 152, 5, 64, "rectangle", bg_color); //First Row
		                                                 
		main_grid.add("2:1", 152, 216, 64, 5, "rectangle", bg_color); //Second Row
		main_grid.add("2:2", 216, 216, 5, 5, "rectangle", bg_color);  //Second Row
		main_grid.add("2:3", 221, 216, 64, 5, "rectangle", bg_color); //Second Row
		main_grid.add("2:4", 285, 216, 5, 5, "rectangle", bg_color);  //Second Row
		main_grid.add("2:5", 290, 216, 64, 5, "rectangle", bg_color); //Second Row
		                                                 
		main_grid.add("3:1", 216, 221, 5, 64, "rectangle", bg_color); //Third Row
		main_grid.add("3:2", 285, 221, 5, 64, "rectangle", bg_color); //Third Row
		                                                
		main_grid.add("4:1", 152, 285, 64, 5, "rectangle", bg_color); //Fourth Row
		main_grid.add("4:2", 216, 285, 5, 5, "rectangle", bg_color);  //Fourth Row
		main_grid.add("4:3", 221, 285, 64, 5, "rectangle", bg_color); //Fourth Row
		main_grid.add("4:4", 285, 285, 5, 5, "rectangle", bg_color);  //Fourth Row
		main_grid.add("4:5", 290, 285, 64, 5, "rectangle", bg_color); //Fourth Row
		                                                 
		main_grid.add("5:1", 216, 290, 5, 64, "rectangle", bg_color); //Fifth Row
		main_grid.add("5:2", 285, 290, 5, 64, "rectangle", bg_color); //Fifth Row
		
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
	
	@Override
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
		
		main_grid.paintList(g);
		
	}
	
	public void gridPressed(int row, int col) {
		
		int[]   settings = {row, col};
		int[]   nulled   = {-1, -1};
		boolean shift    = false;
		boolean reset    = false;
		
		if(grid[0][0] == -1) {grid[0] = settings;} //Is anything selected?
		
		else if(grid[1][0] == -1) { //A grid is selected so is a second grid selected?
			 	if(grid[0][0] != row && grid[0][1] != col) { //Only one grid is selected so check if this grid is selected
			 		
			 		if(grid[0][0] == row) { //This grid is not selected so check if it is on the same row as the one that is
			 			
			 			//It's on the same row so check if it's beside it else set reset to true
			 			if(col+1 == grid[0][1]) {dir = "Left";}
			 			else if(col-1 == grid[0][1]) {dir = "Right";}
			 			else {reset = true;} 
			 			
			 		} else if (grid[0][1] == col) { //Not on the same row so check if it is on the same column
			 			
			 			//It's on the same column so check if it's beside it else set reset to true
			 			if(row+1 == grid[0][0]) {dir = "Down";}
			 			else if(row-1 == grid[0][0]) {dir = "Up";}
			 			else {reset = true;}
			 			
			 		}			 		
			 		grid[1] = settings;
				}
			}
		
		else if(grid[2][0] == -1) { //Two grids are selected so is a third grid selected?
				if(grid[0][0] != row && grid[0][1] != col && grid[1][0] != row && grid[1][1] != col) {
				
					if(dir.equals("Up")) {
						if(row-1 == grid[1][0]) {shift = true;}
						else {reset = true;}
					} 
					
					else if(dir.equals("Down")) {
						if(row+1 == grid[1][0]) {shift = true;}
						else {reset = true;}
					}
					
					else if(dir.equals("Left")) {
						if(col+1 == grid[1][1]) {shift = true;}
						else reset = true;
					}
					
					else if(dir.equals("Right")) {
						if(col-1 == grid[0][1]) {shift = true;}
						else {reset = true;}
					}
					
					else {reset = true;}
					
				}
			}
		
		
		if(reset) {
			
			grid[0] = settings;
			grid[1] = nulled;
			grid[2] = nulled;
			main_grid.setColor(bg_color);
			
		}
		
		if(shift) {
			System.out.println("Shift");
		} else {
		
			final int total_lines = grid_lines[0][0].length;
			
			for(int i=0;i<total_lines;i+=2) {
				
				String row_id = String.valueOf(grid_lines[row-1][col-1][i]);
				String col_id = String.valueOf(grid_lines[row-1][col-1][i+1]);
				
				System.out.println(total_lines);
				System.out.println(row_id+":"+col_id);
				
				main_grid.editColor( row_id + ":" + col_id, selected_color );
			}
			
		}
		
		repaint();

	}
	
}
