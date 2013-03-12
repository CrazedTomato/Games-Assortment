package com.games.assortment;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.Timer;

public class RubiksCube extends JPanel {
	
	private String     dir              = "";
	private String     difficulty[]     = {"Solved", "Easy", "Medium", "Hard", "Superflip"};
	
	private int        diff_no          = 2;
	private int        grid[][]         = {{-1, -1},{-1, -1},{-1, -1}};
	private int        grid_lines[][][] = { { {1,1 , 2,1 , 2,2},             {1,1 , 1,2 , 2,2 , 2,3 , 2,4},                   {1,2 , 2,4 , 2,5} },             //First Row
											{ {2,1 , 2,2 , 3,1 , 4,1 , 4,2}, {2,2 , 2,3 , 2,4 , 3,1 , 3,2 , 4,2 , 4,3 , 4,4}, {2,4 , 2,5 , 3,2 , 4,4 , 4,5} }, //Second Row
											{ {4,1 , 4,2 , 5,1},             {4,2 , 4,3 , 4,4 , 5,1 , 5,2},                   {4,4 , 4,5 , 5,2} } };           //Third Row
	
	private Color      bg_color         = new Color(51 , 51 , 51 );
	private Color      selected_color   = new Color(0  , 130, 255);
	private Color      solved_color     = new Color(0  , 130, 25 );
	private Color      red_color        = new Color(170, 0  , 0  );
	private Color      green_color      = new Color(0  , 170, 0  );
	private Color      blue_color       = new Color(70 , 100, 150);
	private Color      yellow_color     = new Color(250, 230, 0  );
	private Color      orange_color     = new Color(250, 130, 50 );
	private Color      white_color      = new Color(235, 235, 235);
	
	private CButton    new_cube;
	private CButton    diff;
	
	private CPaintList main_grid;
	private CPaintList sub_grids;
	private CPaintList rubiks;

	public RubiksCube() {

		createGrids();
		createRubiks();
		showButtons();
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int xPos = e.getX();
				int yPos = e.getY();
				
				//Main grid
				if(xPos >= 152 && xPos < 216 && yPos >= 152 && yPos < 216) {gridPressed(1, 1);}
				if(xPos >= 221 && xPos < 285 && yPos >= 152 && yPos < 216) {gridPressed(1, 2);}
				if(xPos >= 290 && xPos < 354 && yPos >= 152 && yPos < 216) {gridPressed(1, 3);}
				if(xPos >= 152 && xPos < 216 && yPos >= 221 && yPos < 285) {gridPressed(2, 1);}
				if(xPos >= 216 && xPos < 285 && yPos >= 221 && yPos < 285) {gridPressed(2, 2);}
				if(xPos >= 290 && xPos < 354 && yPos >= 221 && yPos < 285) {gridPressed(2, 3);}
				if(xPos >= 152 && xPos < 216 && yPos >= 290 && yPos < 354) {gridPressed(3, 1);}
				if(xPos >= 221 && xPos < 285 && yPos >= 290 && yPos < 354) {gridPressed(3, 2);}
				if(xPos >= 290 && xPos < 354 && yPos >= 290 && yPos < 354) {gridPressed(3, 3);}
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
		
		//Sub grids
		sub_grids = new CPaintList();
		
		//Right face
		sub_grids.add("R:hor1", 379, 234, 102, 3, "rectangle", bg_color);
		sub_grids.add("R:hor2", 379, 269, 102, 3, "rectangle", bg_color);
		sub_grids.add("R:ver1", 411, 202, 3, 102, "rectangle", bg_color);
		sub_grids.add("R:ver2", 446, 202, 3, 102, "rectangle", bg_color);
		
		//Back face
		sub_grids.add("B:hor1", 506, 234, 102, 3, "rectangle", bg_color);
		sub_grids.add("B:hor2", 506, 269, 102, 3, "rectangle", bg_color);
		sub_grids.add("B:ver1", 538, 202, 3, 102, "rectangle", bg_color);
		sub_grids.add("B:ver2", 573, 202, 3, 102, "rectangle", bg_color);
		
		//Left face
		sub_grids.add("L:hor1", 25, 234, 102, 3, "rectangle", bg_color);
		sub_grids.add("L:hor2", 25, 269, 102, 3, "rectangle", bg_color);
		sub_grids.add("L:ver1", 57, 202, 3, 102, "rectangle", bg_color);
		sub_grids.add("L:ver2", 92, 202, 3, 102, "rectangle", bg_color);
		
		//Up face
		sub_grids.add("U:hor1", 202, 57, 102, 3, "rectangle", bg_color);
		sub_grids.add("U:hor2", 202, 92, 102, 3, "rectangle", bg_color);
		sub_grids.add("U:ver1", 234, 25, 3, 102, "rectangle", bg_color);
		sub_grids.add("U:ver2", 269, 25, 3, 102, "rectangle", bg_color);
		
		//Down face
		sub_grids.add("U:hor1", 202, 411, 102, 3, "rectangle", bg_color);
		sub_grids.add("U:hor2", 202, 446, 102, 3, "rectangle", bg_color);
		sub_grids.add("U:ver1", 234, 379, 3, 102, "rectangle", bg_color);
		sub_grids.add("U:ver2", 269, 379, 3, 102, "rectangle", bg_color);
		
		
	}
	
	public void createRubiks() {
		
		rubiks = new CPaintList();
		
		//Front face
		rubiks.add("F:1:1", 152, 152, 64, 64, "rectangle", red_color);
		rubiks.add("F:1:2", 221, 152, 64, 64, "rectangle", red_color);
		rubiks.add("F:1:3", 290, 152, 64, 64, "rectangle", red_color);
		rubiks.add("F:2:1", 152, 221, 64, 64, "rectangle", red_color);
		rubiks.add("F:2:2", 221, 221, 64, 64, "rectangle", red_color);
		rubiks.add("F:2:3", 290, 221, 64, 64, "rectangle", red_color);
		rubiks.add("F:3:1", 152, 290, 64, 64, "rectangle", red_color);
		rubiks.add("F:3:2", 221, 290, 64, 64, "rectangle", red_color);
		rubiks.add("F:3:3", 290, 290, 64, 64, "rectangle", red_color);
		
		//Right face
		rubiks.add("R:1:1", 379, 202, 32, 32, "rectangle", green_color);
		rubiks.add("R:1:2", 414, 202, 32, 32, "rectangle", green_color);
		rubiks.add("R:1:3", 449, 202, 32, 32, "rectangle", green_color);
		rubiks.add("R:2:1", 379, 237, 32, 32, "rectangle", green_color);
		rubiks.add("R:2:2", 414, 237, 32, 32, "rectangle", green_color);
		rubiks.add("R:2:3", 449, 237, 32, 32, "rectangle", green_color);
		rubiks.add("R:3:1", 379, 272, 32, 32, "rectangle", green_color);
		rubiks.add("R:3:2", 414, 272, 32, 32, "rectangle", green_color);
		rubiks.add("R:3:3", 449, 272, 32, 32, "rectangle", green_color);
		                              
		//Back face                   
		rubiks.add("B:1:1", 506, 202, 32, 32, "rectangle", orange_color);
		rubiks.add("B:1:2", 541, 202, 32, 32, "rectangle", orange_color);
		rubiks.add("B:1:3", 576, 202, 32, 32, "rectangle", orange_color);
		rubiks.add("B:2:1", 506, 237, 32, 32, "rectangle", orange_color);
		rubiks.add("B:2:2", 541, 237, 32, 32, "rectangle", orange_color);
		rubiks.add("B:2:3", 576, 237, 32, 32, "rectangle", orange_color);
		rubiks.add("B:3:1", 506, 272, 32, 32, "rectangle", orange_color);
		rubiks.add("B:3:2", 541, 272, 32, 32, "rectangle", orange_color);
		rubiks.add("B:3:3", 576, 272, 32, 32, "rectangle", orange_color);
		                            
		//Left face                 
		rubiks.add("L:1:1", 25, 202, 32, 32, "rectangle", blue_color);
		rubiks.add("L:1:2", 60, 202, 32, 32, "rectangle", blue_color);
		rubiks.add("L:1:3", 95, 202, 32, 32, "rectangle", blue_color);
		rubiks.add("L:2:1", 25, 237, 32, 32, "rectangle", blue_color);
		rubiks.add("L:2:2", 60, 237, 32, 32, "rectangle", blue_color);
		rubiks.add("L:2:3", 95, 237, 32, 32, "rectangle", blue_color);
		rubiks.add("L:3:1", 25, 272, 32, 32, "rectangle", blue_color);
		rubiks.add("L:3:2", 60, 272, 32, 32, "rectangle", blue_color);
		rubiks.add("L:3:3", 95, 272, 32, 32, "rectangle", blue_color);
		                             
		//Up face                    
		rubiks.add("U:1:1", 202, 25, 32, 32, "rectangle", yellow_color);
		rubiks.add("U:1:2", 237, 25, 32, 32, "rectangle", yellow_color);
		rubiks.add("U:1:3", 272, 25, 32, 32, "rectangle", yellow_color);
		rubiks.add("U:2:1", 202, 60, 32, 32, "rectangle", yellow_color);
		rubiks.add("U:2:2", 237, 60, 32, 32, "rectangle", yellow_color);
		rubiks.add("U:2:3", 272, 60, 32, 32, "rectangle", yellow_color);
		rubiks.add("U:3:1", 202, 95, 32, 32, "rectangle", yellow_color);
		rubiks.add("U:3:2", 237, 95, 32, 32, "rectangle", yellow_color);
		rubiks.add("U:3:3", 272, 95, 32, 32, "rectangle", yellow_color);
		                              
		//Down face                   
		rubiks.add("D:1:1", 202, 379, 32, 32, "rectangle", white_color);
		rubiks.add("D:1:2", 237, 379, 32, 32, "rectangle", white_color);
		rubiks.add("D:1:3", 272, 379, 32, 32, "rectangle", white_color);
		rubiks.add("D:2:1", 202, 414, 32, 32, "rectangle", white_color);
		rubiks.add("D:2:2", 237, 414, 32, 32, "rectangle", white_color);
		rubiks.add("D:2:3", 272, 414, 32, 32, "rectangle", white_color);
		rubiks.add("D:3:1", 202, 449, 32, 32, "rectangle", white_color);
		rubiks.add("D:3:2", 237, 449, 32, 32, "rectangle", white_color);
		rubiks.add("D:3:3", 272, 449, 32, 32, "rectangle", white_color);
		
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
		
		setBackground(bg_color);
		
		main_grid.paintList(g);
		sub_grids.paintList(g);
		rubiks.paintList(g);
		
	}
	
	public void gridPressed(int row, int col) {
		
		int[]   settings = {row, col};
		int[]   nulled   = {-1, -1};
		boolean shift    = false;
		boolean reset    = false;
		
		if(grid[0][0] == -1) {grid[0] = settings;} //Is anything selected?
		
		else if(grid[1][0] == -1) { //A grid is selected so is a second grid selected?
			 	if(!(grid[0][0] == row && grid[0][1] == col)) { //Only one grid is selected so check if this grid is selected
			 		
			 		if(grid[0][0] == row) { //This grid is not selected so check if it is on the same row as the one that is
			 			
			 			//It's on the same row so check if it's beside it else set reset to true
			 			if(col+1 == grid[0][1]) {dir = "Left";}
			 			else if(col-1 == grid[0][1]) {dir = "Right";}
			 			else {reset = true;} 
			 			
			 		} else if (grid[0][1] == col) { //Not on the same row so check if it is on the same column
			 			
			 			//It's on the same column so check if it's beside it else set reset to true
			 			if(row+1 == grid[0][0]) {dir = "Up";}
			 			else if(row-1 == grid[0][0]) {dir = "Down";}
			 			else {reset = true;}
			 			
			 		} else {reset = true;} //This grid is not beside any selected grids so reset
			 		grid[1] = settings;
				} else {reset = true;} //This grid is already selected so reset
			}
		
		else if(!(grid[0][0] == row && grid[0][1] == col) && !(grid[1][0] == row && grid[1][1] == col)) { //Check if grid is already selected
				
				if(dir.equals("Up")) {
					if(row+1 == grid[1][0]) {shift = true;}
					else {reset = true;}
				} 
				
				else if(dir.equals("Down")) {
					if(row-1 == grid[1][0]) {shift = true;}
					else {reset = true;}
				}
				
				else if(dir.equals("Left")) {
					if(col+1 == grid[1][1]) {shift = true;}
					else reset = true;
				}
				
				else if(dir.equals("Right")) {
					if(col-1 == grid[1][1]) {shift = true;}
					else {reset = true;}
				}
				
				else {reset = true;}
				
			} else {reset = true;} //This grid is already selected so reset
		
		
		if(reset) {
			
			grid[0] = settings;
			grid[1] = nulled;
			grid[2] = nulled;
			main_grid.setColor(bg_color);
			
			dir = "";
			
		} 
		
		int total_lines = grid_lines[row-1][col-1].length;
		
		for(int i=0;i<total_lines;i+=2) {
			
			final String row_id = String.valueOf(grid_lines[row-1][col-1][i]);
			final String col_id = String.valueOf(grid_lines[row-1][col-1][i+1]);
			
			main_grid.editColor(row_id + ":" + col_id, selected_color);
			
		}
		
		repaint();
		
		if(shift) {
			
			shiftRubiks();
			dir = "";
			
			grid[0] = nulled;
			grid[1] = nulled;
			grid[2] = nulled;
			
			Timer timer = new Timer(200, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					main_grid.setColor(bg_color);
					repaint();
				}
				
			});
			
			timer.setRepeats(false);
			timer.start();
			
		}

	}

	public void shiftRubiks() {
		
		switch(dir) {
		case "Up"   : shiftUp(grid[0][1]);    break;
		case "Down" : shiftDown(grid[0][1]);  break;
		case "Left" : shiftLeft(grid[0][0]);  break;
		case "Right": shiftRight(grid[0][0]); break;
		default     : break;
		}
		
	}
	
	public void shiftUp(int col) {
		
		Color[]  color = new Color[12];
		String[] face  = {"F", "D", "B", "U"};
		
		for(int i=0;i<12;i++) {
			int face_no = (int) Math.floor(((double)i + Math.floor(((double)i)/4))+1)/4;
			int row     = i - face_no*3 + 1;
			
			String grid_space = face[face_no] + ":" + String.valueOf(row) + ":" + String.valueOf(col);
			color[i] = rubiks.getColor(grid_space);
		}
		
		for(int i=0;i<12;i++) {
			int face_no = (int) Math.floor(((double)i + Math.floor(((double)i)/4))+1)/4;
			int row     = i - face_no*3 + 1;
			
			String grid_space = face[face_no] + ":" + String.valueOf(row) + ":" + String.valueOf(col);
			if(i<9) {rubiks.editColor(grid_space, color[i+3]);}
			else    {rubiks.editColor(grid_space, color[i-9]);}
		}
		
		if(col == 1) {shiftRotate90("L");}
		if(col == 3) {shiftRotate90("R");}
		
	}
	
	public void shiftDown(int col) {
		
		Color[]  color = new Color[12];
		String[] face  = {"F", "D", "B", "U"};
		
		for(int i=0;i<12;i++) {
			int face_no = (int) Math.floor(((double)i + Math.floor(((double)i)/4))+1)/4;
			int row     = i - face_no*3 + 1;
			
			String grid_space = face[face_no] + ":" + String.valueOf(row) + ":" + String.valueOf(col);
			color[i] = rubiks.getColor(grid_space);
		}
		
		for(int i=0;i<12;i++) {
			int face_no = (int) Math.floor(((double)i + Math.floor(((double)i)/4))+1)/4;
			int row     = i - face_no*3 + 1;
			
			String grid_space = face[face_no] + ":" + String.valueOf(row) + ":" + String.valueOf(col);
			if(i<3) {rubiks.editColor(grid_space, color[i+9]);}
			else    {rubiks.editColor(grid_space, color[i-3]);}
		}
		
		if(col == 1) {shiftRotate270("L");}
		if(col == 3) {shiftRotate270("R");}
		
	}
	
	public void shiftLeft(int row) {
		
		Color[]  color = new Color[12];
		String[] face  = {"F", "R", "B", "L"};
		
		for(int i=0;i<12;i++) {
			int face_no = (int) Math.floor(((double)i + Math.floor(((double)i)/4))+1)/4;
			int col     = i - face_no*3 + 1;
			
			String grid_space = face[face_no] + ":" + String.valueOf(row) + ":" + String.valueOf(col);
			color[i] = rubiks.getColor(grid_space);
		}
		
		for(int i=0;i<12;i++) {
			int face_no = (int) Math.floor(((double)i + Math.floor(((double)i)/4))+1)/4;
			int col     = i - face_no*3 + 1;
			
			String grid_space = face[face_no] + ":" + String.valueOf(row) + ":" + String.valueOf(col);
			if(i<9) {rubiks.editColor(grid_space, color[i+3]);}
			else    {rubiks.editColor(grid_space, color[i-9]);}
		}
		
		if(row == 1) {shiftRotate90("U");}
		if(row == 3) {shiftRotate90("D");}
		
	}
	
	public void shiftRight(int row) {
		
		Color[]  color = new Color[12];
		String[] face  = {"F", "R", "B", "L"};
		
		for(int i=0;i<12;i++) {
			int face_no = (int) Math.floor(((double)i + Math.floor(((double)i)/4))+1)/4;
			int col     = i - face_no*3 + 1;
			
			String grid_space = face[face_no] + ":" + String.valueOf(row) + ":" + String.valueOf(col);
			color[i] = rubiks.getColor(grid_space);
		}
		
		for(int i=0;i<12;i++) {
			int face_no = (int) Math.floor(((double)i + Math.floor(((double)i)/4))+1)/4;
			int col     = i - face_no*3 + 1;
			
			String grid_space = face[face_no] + ":" + String.valueOf(row) + ":" + String.valueOf(col);
			if(i<3) {rubiks.editColor(grid_space, color[i+9]);}
			else    {rubiks.editColor(grid_space, color[i-3]);}
		}
		
		if(row == 1) {shiftRotate270("U");}
		if(row == 3) {shiftRotate270("D");}
		
	}
	
	public void shiftRotate90(String face) {
		
		Color[] color = new Color[9];
		
		for(int i=1;i<4;i++) {
			for(int n=1;n<4;n++) {
				color[((i-1)*3)+(n-1)] = rubiks.getColor(face + ":" + String.valueOf(i) + ":" + String.valueOf(n));
			}
		}
		
		for(int i=3;i>0;i--) {
			for(int n=3;n>0;n--) {
				rubiks.editColor(face + ":" + String.valueOf(i) + ":" + String.valueOf(n), color[(i+5)-((n-1)*3)]);
			}
		}
		
	}
	
	public void shiftRotate270(String face) {
		
		Color[] color = new Color[9];
		
		for(int i=1;i<4;i++) {
			for(int n=1;n<4;n++) {
				color[((i-1)*3)+(n-1)] = rubiks.getColor(face + ":" + String.valueOf(i) + ":" + String.valueOf(n));
			}
		}
		
		for(int i=1;i<4;i++) {
			for(int n=1;n<4;n++) {
				rubiks.editColor(face + ":" + String.valueOf(i) + ":" + String.valueOf(n), color[(i+5)-((n-1)*3)]);
			}
		}
		
	}
	
}
