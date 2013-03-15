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
	private String     grid_lines[][][] = { { {"1:1" , "2:1" , "2:2"},                 {"1:1" , "1:2" , "2:2" , "2:3" , "2:4"},                         {"1:2" , "2:4" , "2:5"} },                 //First Row
			                              {   {"2:1" , "2:2" , "3:1" , "4:1" , "4:2"}, {"2:2" , "2:3" , "2:4" , "3:1" , "3:2" , "4:2" , "4:3" , "4:4"}, {"2:4" , "2:5" , "3:2" , "4:4" , "4:5"} }, //Second Row
			                              {   {"4:1" , "4:2" , "5:1"},                 {"4:2" , "4:3" , "4:4" , "5:1" , "5:2"},                         {"4:4" , "4:5" , "5:2"} } };               //Third Row
	                                                                                                                                                     
	private int        move             = 0;
	private int        front            = 0;
	private int        grid[][]         = { {-1, -1}, {-1, -1}, {-1, -1} };
	
	private int        valid_moves[][]  = { {1,1 , 1,2 , 1,3}, {2,1 , 2,2 , 2,3}, {3,1 , 3,2 , 3,3}, //Right
											{1,3 , 1,2 , 1,1}, {2,3 , 2,2 , 2,1}, {3,3 , 3,2 , 3,1}, //Left
											                                                         
											{1,1 , 2,1 , 3,1}, {1,2 , 2,2 , 3,2}, {1,3 , 2,3 , 3,3}, //Down
											{3,1 , 2,1 , 1,1}, {3,2 , 2,2 , 1,2}, {3,3 , 2,3 , 1,3}, //Up
											
											{1,2 , 1,3 , 2,3}, {2,3 , 3,3 , 3,2}, {3,2 , 3,1 , 2,1}, {2,1 , 1,1 , 1,2},   //Rotate Clockwise
											{1,2 , 1,1 , 2,1}, {2,1 , 3,1 , 3,2}, {3,2 , 3,3 , 2,3}, {2,3 , 1,3 , 1,2} }; //Rotate Anti-Clockwise
	
	private int        superflip[][]    = { {1, 5, 1, 4, 1, 2, 1, 6, 1}, //Front
											{2, 5, 2, 1, 2, 3, 2, 6, 2}, //Right
											{3, 5, 3, 2, 3, 4, 3, 6, 3}, //Back
											{4, 5, 4, 3, 4, 1, 4, 6, 4}, //Left
											{5, 3, 5, 4, 5, 2, 5, 1, 5}, //Up
											{6, 1, 6, 4, 6, 2, 6, 3, 6}  /*Down*/ };
	
	private Color      color_bg         = new Color(51 , 51 , 51 );
	private Color      color_selected   = new Color(0  , 130, 255);
	private Color      color_correct    = new Color(0  , 130, 25 );
	private Color      color_incorrect  = new Color(240, 20 , 20 );
	private Color      color_red        = new Color(170, 0  , 0  );
	private Color      color_green      = new Color(0  , 170, 0  );
	private Color      color_blue       = new Color(70 , 100, 150);
	private Color      color_yellow     = new Color(250, 230, 0  );
	private Color      color_orange     = new Color(250, 130, 50 );
	private Color      color_white      = new Color(235, 235, 235);
	
	private CButton    btn_solve;
	private CButton    btn_tutorial;
	private CButton    btn_shuffle;
	private CButton    btn_superflip;
	private CButton    btn_front;
	private CButton    btn_gofront;
	
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
				
				//Sub grids
				if(xPos >= 25  && xPos < 127 && yPos >= 202 && yPos < 304) {subgridPressed("L");} //L
				if(xPos >= 379 && xPos < 481 && yPos >= 202 && yPos < 304) {subgridPressed("R");} //R
				
				if(xPos >= 506 && xPos < 608 && yPos >= 202 && yPos < 304) {subgridPressed("B");} //B
				
				if(xPos >= 202 && xPos < 304 && yPos >= 25  && yPos < 127) {subgridPressed("U");} //U
				if(xPos >= 202 && xPos < 304 && yPos >= 379 && yPos < 481) {subgridPressed("D");} //D
				
			}
		});
		
	}
	
	public void createGrids() {
		
		main_grid = new CPaintList();
		main_grid.add("1:1", 216, 152, 5, 64, "rectangle", color_bg); //First Row
		main_grid.add("1:2", 285, 152, 5, 64, "rectangle", color_bg); //First Row
		                                                  
		main_grid.add("2:1", 152, 216, 64, 5, "rectangle", color_bg); //Second Row
		main_grid.add("2:2", 216, 216, 5, 5, "rectangle", color_bg);  //Second Row
		main_grid.add("2:3", 221, 216, 64, 5, "rectangle", color_bg); //Second Row
		main_grid.add("2:4", 285, 216, 5, 5, "rectangle", color_bg);  //Second Row
		main_grid.add("2:5", 290, 216, 64, 5, "rectangle", color_bg); //Second Row
		                                                   
		main_grid.add("3:1", 216, 221, 5, 64, "rectangle", color_bg); //Third Row
		main_grid.add("3:2", 285, 221, 5, 64, "rectangle", color_bg); //Third Row
		                                                   
		main_grid.add("4:1", 152, 285, 64, 5, "rectangle", color_bg); //Fourth Row
		main_grid.add("4:2", 216, 285, 5, 5, "rectangle", color_bg);  //Fourth Row
		main_grid.add("4:3", 221, 285, 64, 5, "rectangle", color_bg); //Fourth Row
		main_grid.add("4:4", 285, 285, 5, 5, "rectangle", color_bg);  //Fourth Row
		main_grid.add("4:5", 290, 285, 64, 5, "rectangle", color_bg); //Fourth Row
		                                                   
		main_grid.add("5:1", 216, 290, 5, 64, "rectangle", color_bg); //Fifth Row
		main_grid.add("5:2", 285, 290, 5, 64, "rectangle", color_bg); //Fifth Row
		
		//Sub grids
		sub_grids = new CPaintList();
		
		//Right face
		sub_grids.add("R:hor1", 379, 234, 102, 3, "rectangle", color_bg);
		sub_grids.add("R:hor2", 379, 269, 102, 3, "rectangle", color_bg);
		sub_grids.add("R:ver1", 411, 202, 3, 102, "rectangle", color_bg);
		sub_grids.add("R:ver2", 446, 202, 3, 102, "rectangle", color_bg);
		                                                       
		//Back face                                            
		sub_grids.add("B:hor1", 506, 234, 102, 3, "rectangle", color_bg);
		sub_grids.add("B:hor2", 506, 269, 102, 3, "rectangle", color_bg);
		sub_grids.add("B:ver1", 538, 202, 3, 102, "rectangle", color_bg);
		sub_grids.add("B:ver2", 573, 202, 3, 102, "rectangle", color_bg);
		
		//Left face
		sub_grids.add("L:hor1", 25, 234, 102, 3, "rectangle", color_bg);
		sub_grids.add("L:hor2", 25, 269, 102, 3, "rectangle", color_bg);
		sub_grids.add("L:ver1", 57, 202, 3, 102, "rectangle", color_bg);
		sub_grids.add("L:ver2", 92, 202, 3, 102, "rectangle", color_bg);
		                                                     
		//Up face                                            
		sub_grids.add("U:hor1", 202, 57, 102, 3, "rectangle", color_bg);
		sub_grids.add("U:hor2", 202, 92, 102, 3, "rectangle", color_bg);
		sub_grids.add("U:ver1", 234, 25, 3, 102, "rectangle", color_bg);
		sub_grids.add("U:ver2", 269, 25, 3, 102, "rectangle", color_bg);
		
		//Down face
		sub_grids.add("D:hor1", 202, 411, 102, 3, "rectangle", color_bg);
		sub_grids.add("D:hor2", 202, 446, 102, 3, "rectangle", color_bg);
		sub_grids.add("D:ver1", 234, 379, 3, 102, "rectangle", color_bg);
		sub_grids.add("D:ver2", 269, 379, 3, 102, "rectangle", color_bg);
		
		
	}
	
	public void createRubiks() {
		
		rubiks = new CPaintList();
		
		//Front face
		rubiks.add("F:1:1", 152, 152, 64, 64, "rectangle", color_red);
		rubiks.add("F:1:2", 221, 152, 64, 64, "rectangle", color_red);
		rubiks.add("F:1:3", 290, 152, 64, 64, "rectangle", color_red);
		rubiks.add("F:2:1", 152, 221, 64, 64, "rectangle", color_red);
		rubiks.add("F:2:2", 221, 221, 64, 64, "rectangle", color_red);
		rubiks.add("F:2:3", 290, 221, 64, 64, "rectangle", color_red);
		rubiks.add("F:3:1", 152, 290, 64, 64, "rectangle", color_red);
		rubiks.add("F:3:2", 221, 290, 64, 64, "rectangle", color_red);
		rubiks.add("F:3:3", 290, 290, 64, 64, "rectangle", color_red);
		
		//Right face
		rubiks.add("R:1:1", 379, 202, 32, 32, "rectangle", color_green);
		rubiks.add("R:1:2", 414, 202, 32, 32, "rectangle", color_green);
		rubiks.add("R:1:3", 449, 202, 32, 32, "rectangle", color_green);
		rubiks.add("R:2:1", 379, 237, 32, 32, "rectangle", color_green);
		rubiks.add("R:2:2", 414, 237, 32, 32, "rectangle", color_green);
		rubiks.add("R:2:3", 449, 237, 32, 32, "rectangle", color_green);
		rubiks.add("R:3:1", 379, 272, 32, 32, "rectangle", color_green);
		rubiks.add("R:3:2", 414, 272, 32, 32, "rectangle", color_green);
		rubiks.add("R:3:3", 449, 272, 32, 32, "rectangle", color_green);
		                              
		//Back face                   
		rubiks.add("B:1:1", 506, 202, 32, 32, "rectangle", color_orange);
		rubiks.add("B:1:2", 541, 202, 32, 32, "rectangle", color_orange);
		rubiks.add("B:1:3", 576, 202, 32, 32, "rectangle", color_orange);
		rubiks.add("B:2:1", 506, 237, 32, 32, "rectangle", color_orange);
		rubiks.add("B:2:2", 541, 237, 32, 32, "rectangle", color_orange);
		rubiks.add("B:2:3", 576, 237, 32, 32, "rectangle", color_orange);
		rubiks.add("B:3:1", 506, 272, 32, 32, "rectangle", color_orange);
		rubiks.add("B:3:2", 541, 272, 32, 32, "rectangle", color_orange);
		rubiks.add("B:3:3", 576, 272, 32, 32, "rectangle", color_orange);
		                            
		//Left face                 
		rubiks.add("L:1:1", 25, 202, 32, 32, "rectangle", color_blue);
		rubiks.add("L:1:2", 60, 202, 32, 32, "rectangle", color_blue);
		rubiks.add("L:1:3", 95, 202, 32, 32, "rectangle", color_blue);
		rubiks.add("L:2:1", 25, 237, 32, 32, "rectangle", color_blue);
		rubiks.add("L:2:2", 60, 237, 32, 32, "rectangle", color_blue);
		rubiks.add("L:2:3", 95, 237, 32, 32, "rectangle", color_blue);
		rubiks.add("L:3:1", 25, 272, 32, 32, "rectangle", color_blue);
		rubiks.add("L:3:2", 60, 272, 32, 32, "rectangle", color_blue);
		rubiks.add("L:3:3", 95, 272, 32, 32, "rectangle", color_blue);
		                             
		//Up face                    
		rubiks.add("U:1:1", 202, 25, 32, 32, "rectangle", color_yellow);
		rubiks.add("U:1:2", 237, 25, 32, 32, "rectangle", color_yellow);
		rubiks.add("U:1:3", 272, 25, 32, 32, "rectangle", color_yellow);
		rubiks.add("U:2:1", 202, 60, 32, 32, "rectangle", color_yellow);
		rubiks.add("U:2:2", 237, 60, 32, 32, "rectangle", color_yellow);
		rubiks.add("U:2:3", 272, 60, 32, 32, "rectangle", color_yellow);
		rubiks.add("U:3:1", 202, 95, 32, 32, "rectangle", color_yellow);
		rubiks.add("U:3:2", 237, 95, 32, 32, "rectangle", color_yellow);
		rubiks.add("U:3:3", 272, 95, 32, 32, "rectangle", color_yellow);
		                              
		//Down face                   
		rubiks.add("D:1:1", 202, 379, 32, 32, "rectangle", color_white);
		rubiks.add("D:1:2", 237, 379, 32, 32, "rectangle", color_white);
		rubiks.add("D:1:3", 272, 379, 32, 32, "rectangle", color_white);
		rubiks.add("D:2:1", 202, 414, 32, 32, "rectangle", color_white);
		rubiks.add("D:2:2", 237, 414, 32, 32, "rectangle", color_white);
		rubiks.add("D:2:3", 272, 414, 32, 32, "rectangle", color_white);
		rubiks.add("D:3:1", 202, 449, 32, 32, "rectangle", color_white);
		rubiks.add("D:3:2", 237, 449, 32, 32, "rectangle", color_white);
		rubiks.add("D:3:3", 272, 449, 32, 32, "rectangle", color_white);
		
	}
	
	public void showButtons() {
		
		setLayout(null);
		
		btn_solve = new CButton("Solve");
		btn_solve.setBounds(377, 377, 114, 34);
		
		btn_tutorial = new CButton("Feature");
		btn_tutorial.setBounds(492, 377, 114, 34);
		
		btn_shuffle = new CButton("Shuffle");
		btn_shuffle.setBounds(377, 411, 114, 34);
		
		btn_superflip = new CButton("Superflip");
		btn_superflip.setBounds(492, 411, 114, 34);
		btn_superflip.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				setSuperflippedState();
			}
			
		});
		
		btn_front = new CButton("Set Front");
		btn_front.setBounds(377, 445, 114, 34);
		btn_front.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				setAsFront();
			}
			
		});
		
		btn_gofront = new CButton("Go To Front");
		btn_gofront.setBounds(492, 445, 114, 34);
		
		add(btn_solve);
		add(btn_tutorial);
		add(btn_shuffle);
		add(btn_superflip);
		add(btn_front);
		add(btn_gofront);
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		setBackground(color_bg);
		
		main_grid.paintList(g);
		sub_grids.paintList(g);
		rubiks.paintList(g);
		
	}
	
	public void gridPressed(int row, int col) {
		
		int[]   settings = {row, col};
		int[]   nulled   = {-1, -1};
		boolean shift    = false;
		boolean reset    = false;
		
		if(grid[0][0] == -1) {
			
			grid[0] = settings;
			for(int i=0;i<valid_moves.length;i++) {
				if(!(grid[0][0] == valid_moves[i][0] && grid[0][1] == valid_moves[i][1])) {
					reset = true;
				} else {
					main_grid.setColor(grid_lines[row-1][col-1], color_selected);
					reset = false;
					break;
				}
			}
			
		}
		
		else if(grid[1][0] == -1) {
				if(!(grid[0][0] == row && grid[0][1] == col)) {
					
					grid[1] = settings;
					for(int i=0;i<valid_moves.length;i++) {
						if(!(grid[1][0] == valid_moves[i][2] && grid[1][1] == valid_moves[i][3]) ||
						   !(grid[0][0] == valid_moves[i][0] && grid[0][1] == valid_moves[i][1])) {
							reset = true;
						} else {
							move = i+1;
							main_grid.setColor(grid_lines[row-1][col-1], color_selected);
							reset = false;
							break;
						}
					}
					
				} else {reset = true;}
		}
		
		else if(grid[2][0] == -1) {
				if(!(grid[0][0] == row && grid[0][1] == col) &&
				   !(grid[1][0] == row && grid[1][1] == col)) {
			
					grid[2] = settings;
					if(!(grid[2][0] == valid_moves[move-1][4] && grid[2][1] == valid_moves[move-1][5])) {
						reset = true;
					} else {
						main_grid.setColor(grid_lines[row-1][col-1], color_selected);
					}
				
				} else {reset = true;}
			
		}
		
		if(reset) {
			
			if(grid[0][0] != -1) {main_grid.setColor(grid_lines[grid[0][0]-1][grid[0][1]-1], color_incorrect);}
			if(grid[1][0] != -1) {main_grid.setColor(grid_lines[grid[1][0]-1][grid[1][1]-1], color_incorrect);}
			if(grid[2][0] != -1) {main_grid.setColor(grid_lines[grid[2][0]-1][grid[2][1]-1], color_incorrect);}
			
			grid[0] = nulled;
			grid[1] = nulled;
			grid[2] = nulled;
			
			repaint();
			
			Timer timer = new Timer(200, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					main_grid.setColor(color_bg);
					repaint();
				}
				
			});
			
			timer.setRepeats(false);
			timer.start();
			
		} else if(grid[2][0] != -1) {
			
			main_grid.setColor(grid_lines[grid[0][0]-1][grid[0][1]-1], color_correct);
			main_grid.setColor(grid_lines[grid[1][0]-1][grid[1][1]-1], color_correct);
			main_grid.setColor(grid_lines[grid[2][0]-1][grid[2][1]-1], color_correct);
			repaint();
			
			final int[] nulled_array = {-1, -1};
			CTimer timer = new CTimer(200, nulled_array, new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {

					switch(move) {
					case 1 : dir = "Right";    break;
					case 2 : dir = "Right";    break;
					case 3 : dir = "Right";    break;
					case 4 : dir = "Left";     break;
				    case 5 : dir = "Left";     break;
					case 6 : dir = "Left";     break;
					case 7 : dir = "Down";     break;
					case 8 : dir = "Down";     break;
					case 9 : dir = "Down";     break;
					case 10: dir = "Up";       break;
					case 11: dir = "Up";       break;
					case 12: dir = "Up";       break;
					case 13: dir = "RotateC";  break;
					case 14: dir = "RotateC";  break;
					case 15: dir = "RotateC";  break;
					case 16: dir = "RotateC";  break;
					case 17: dir = "RotateAC"; break;
					case 18: dir = "RotateAC"; break;
					case 19: dir = "RotateAC"; break;
					case 20: dir = "RotateAC"; break;
					default:                   break;
					}
					
					shiftRubiks("F");
					main_grid.setColor(color_bg);
					
					grid[0] = nulled_array;
					grid[1] = nulled_array;
					grid[2] = nulled_array;
					
					repaint();
					
				}
				
			});
			
			timer.setRepeats(false);
			timer.start();
			
		} else {repaint();}
		
	}

	public void subgridPressed(final String face) {
		
		int[]   nulled  = {-1, -1};
		boolean correct = false;
		
		if(face.equals("L") || face.equals("R")) {
			dir = "Hor";
			sub_grids.setColor(face, color_correct);
			repaint();
			
		} else if(face.equals("U") || face.equals("D")) {
			dir = "Ver";
			sub_grids.setColor(face, color_correct);
			repaint();
			
			
		} else {
			correct = true;
			sub_grids.setColor(face, color_incorrect);
			repaint();
		}
		
		grid[0] = nulled;
		grid[1] = nulled;
		grid[2] = nulled;
		
		final boolean[] correct_array = {correct};
		CTimer timer = new CTimer(200, correct_array, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!correct_array[0]) {shiftRubiks(face);}
				sub_grids.setColor(color_bg);
				repaint();
			}
			
		});
		
		timer.setRepeats(false);
		timer.start();
		
	}
	
	public void setSolvedState() {
		
		
		
	}
	
	public void setShuffledState() {
		
	}
	
	public void setSuperflippedState() {
		
		String[] face   = {"F", "R", "B", "L", "U", "D"};
		Color[]  colors = {color_red, color_green, color_orange, color_blue, color_yellow, color_white};
		int      revert = -1;
		
		for(int i=0;i<6;i++) {
			for(int n=0;n<3;n++) {
				for(int t=0;t<3;t++) {
					if(front < 6-i) {rubiks.setColor(face[i+front]+":"+String.valueOf(n+1)+":"+String.valueOf(t+1), colors[(superflip[i][(n*3)+t])-1]);}
					else {
						if(revert == -1) {revert = i;}
						rubiks.setColor(face[(5-front)+(i-revert)]+":"+String.valueOf(n+1)+":"+String.valueOf(t+1), colors[(superflip[i][(n*3)+t])-1]);
					}
				}
			}
		}
		
		repaint();
		
	}
	
	public void setAsFront() {
		
		String[] face   = {"F", "R", "B", "L", "U", "D"};
		Color[]  colors = {color_red, color_green, color_orange, color_blue, color_yellow, color_white};
		Color[]  color  = new Color[6];
		int[]    attrib = new int[3];
		
		for(int i=0;i<6;i++) {
			color[i] = rubiks.getColor(face[i]+":1:1");
		}
		
		int[][] color_attrib = new int[6][3];
		for(int i=0;i<6;i++) {
			color_attrib[i][0] = color[i].getRed();
			color_attrib[i][1] = color[i].getGreen();
			color_attrib[i][2] = color[i].getBlue();
		}
		
		for(int i=0;i<6;i++) {
			
			attrib[0] = colors[i].getRed();
			attrib[1] = colors[i].getGreen();
			attrib[2] = colors[i].getBlue();
			
			if(color_attrib[i][0] == attrib[0] && color_attrib[i][1] == attrib[1] && color_attrib[i][2] == attrib[2]) {
				front = i;
				return;
			}
			
		}
		
	}
	

	
	//---------------------------------------------------------------------------------------//
	//                                      Shift Methods                                    //
	//---------------------------------------------------------------------------------------//
	
	public void shiftRubiks(String face) {
		
		switch(dir) {
		case "Up"       : shiftUp(grid[0][1]);    break;
		case "Down"     : shiftDown(grid[0][1]);  break;
		case "Left"     : shiftLeft(grid[0][0]);  break;
		case "Right"    : shiftRight(grid[0][0]); break;
		case "RotateC"  : shiftRotate("F", 1);    break;
		case "RotateAC" : shiftRotate("F", 3);    break;
		case "Hor"      : shiftMainHor(face);     break;
		case "Ver"      : shiftMainVer(face);     break;
		default         : break;
		}
		
	}
	
	public void shiftUp(int col) {
		
		Color[]  color = new Color[12];
		String[] face  = {"F", "D", "B", "U"};
		String grid_space;
		
		for(int i=0;i<12;i++) {
			int face_no = (int) Math.floor(((double)i + Math.floor(((double)i)/4))+1)/4;
			int row     = i - face_no*3 + 1;
			
			if(face_no == 2) {grid_space = face[face_no] + ":" + String.valueOf(row) + ":" + String.valueOf(4-col);}
			else             {grid_space = face[face_no] + ":" + String.valueOf(row) + ":" + String.valueOf(col);}
			
			color[i] = rubiks.getColor(grid_space);
		}
		
		for(int i=0;i<12;i++) {
			int face_no = (int) Math.floor(((double)i + Math.floor(((double)i)/4))+1)/4;
			int row     = i - face_no*3 + 1;
			
			if(face_no == 2) {grid_space = face[face_no] + ":" + String.valueOf(row) + ":" + String.valueOf(4-col);}
			else             {grid_space = face[face_no] + ":" + String.valueOf(row) + ":" + String.valueOf(col);}
			
			if(i<9) {rubiks.setColor(grid_space, color[i+3]);}
			else    {rubiks.setColor(grid_space, color[i-9]);}
		}
		
		if(col == 1) {shiftRotate("L", 3);}
		if(col == 3) {shiftRotate("R", 1);}
		
		shiftFlip(col, "D");
		
	}
	
	public void shiftDown(int col) {
		
		Color[]  color = new Color[12];
		String[] face  = {"F", "D", "B", "U"};
		String grid_space;
		
		for(int i=0;i<12;i++) {
			int face_no = (int) Math.floor(((double)i + Math.floor(((double)i)/4))+1)/4;
			int row     = i - face_no*3 + 1;
			
			if(face_no == 2) {grid_space = face[face_no] + ":" + String.valueOf(row) + ":" + String.valueOf(4-col);}
			else             {grid_space = face[face_no] + ":" + String.valueOf(row) + ":" + String.valueOf(col);}
			
			color[i] = rubiks.getColor(grid_space);
		}
		
		for(int i=0;i<12;i++) {
			int face_no = (int) Math.floor(((double)i + Math.floor(((double)i)/4))+1)/4;
			int row     = i - face_no*3 + 1;
			
			if(face_no == 2) {grid_space = face[face_no] + ":" + String.valueOf(row) + ":" + String.valueOf(4-col);}
			else             {grid_space = face[face_no] + ":" + String.valueOf(row) + ":" + String.valueOf(col);}
			
			if(i<3) {rubiks.setColor(grid_space, color[i+9]);}
			else    {rubiks.setColor(grid_space, color[i-3]);}
		}
		
		if(col == 1) {shiftRotate("L", 1);}
		if(col == 3) {shiftRotate("R", 3);}
		
		shiftFlip(col, "U");
		
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
			if(i<9) {rubiks.setColor(grid_space, color[i+3]);}
			else    {rubiks.setColor(grid_space, color[i-9]);}
		}
		
		if(row == 1) {shiftRotate("U", 1);}
		if(row == 3) {shiftRotate("D", 3);}
		
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
			if(i<3) {rubiks.setColor(grid_space, color[i+9]);}
			else    {rubiks.setColor(grid_space, color[i-3]);}
		}
		
		if(row == 1) {shiftRotate("U", 3);}
		if(row == 3) {shiftRotate("D", 1);}
		
	}
	
	public void shiftRotate(String face, int repeats) {
		
		Color[] color;
		int count = 0;
		
		for(int i=0;i<repeats;i++) { //Rotate 3 times
			color = new Color[9];
			
			for(int n=1;n<4;n++) { //Row
				for(int t=1;t<4;t++) { //Column
					color[((n-1)*3)+(t-1)] = rubiks.getColor(face + ":" + String.valueOf(n) + ":" + String.valueOf(t));
				}
			}
		
			for(int n=3;n>0;n--) { //Row
				for(int t=3;t>0;t--) { //Column
					rubiks.setColor(face + ":" + String.valueOf(n) + ":" + String.valueOf(t), color[(n+5)-((t-1)*3)]);
				}
			}
			
		}
		
		while(count < repeats && face.equals("F")) {
			color = new Color[12];
			
			//Get colors for frontface edges of subfaces
			for(int i=0;i<3;i++) {color[i]   = rubiks.getColor("R:"+String.valueOf(i+1)+":1");} //Right face
			for(int i=0;i<3;i++) {color[i+3] = rubiks.getColor("D:1:"+String.valueOf(i+1));}    //Down face
			for(int i=0;i<3;i++) {color[i+6] = rubiks.getColor("L:"+String.valueOf(i+1)+":3");} //Left face
			for(int i=0;i<3;i++) {color[i+9] = rubiks.getColor("U:3:"+String.valueOf(i+1));}    //Up face
			
			Color[] temp_color = new Color[12];
			for(int i=0;i<12;i++) {temp_color[i] = color[i];}
			
			for(int i=0;i<12;i++) {
				if(i<9) {color[i+3] = temp_color[i];}
				else    {color[i-9] = temp_color[i];}				
			}
			
			//Set rotated colors for frontface edges of subfaces
			for(int i=0;i<3;i++) {rubiks.setColor("R:"+String.valueOf(i+1)+":1", color[i]);}   //Right face
			for(int i=0;i<3;i++) {rubiks.setColor("D:1:"+String.valueOf(i+1), color[5-i]);}    //Down face
			for(int i=0;i<3;i++) {rubiks.setColor("L:"+String.valueOf(i+1)+":3", color[i+6]);} //Left face
			for(int i=0;i<3;i++) {rubiks.setColor("U:3:"+String.valueOf(i+1), color[11-i]);}    //Up face
			
			count++;
				
		}
		
	}
	
	public void shiftMainHor(String face) {
		
		main_grid.setColor(color_bg);
		
		if(face.equals("R")) {
			
			shiftLeft(1);
			shiftLeft(2);
			shiftLeft(3);
			
		} else {
			
			shiftRight(1);
			shiftRight(2);
			shiftRight(3);
			
		}
		
	}
	
	public void shiftMainVer(String face) {
		
		main_grid.setColor(color_bg);
		
		if(face.equals("D")) {
			
			shiftUp(1);
			shiftUp(2);
			shiftUp(3);
			
		} else {
			
			shiftDown(1);
			shiftDown(2);
			shiftDown(3);
			
		}
		
	}
	
	public void shiftFlip(int col, String face) {
		
		Color color1 = rubiks.getColor("B:1:"+String.valueOf(4-col));
		Color color2 = rubiks.getColor("B:3:"+String.valueOf(4-col));
		rubiks.setColor("B:1:"+String.valueOf(4-col), color2);
		rubiks.setColor("B:3:"+String.valueOf(4-col), color1);
		
		if(face.equals("D")) {
			
			Color color3 = rubiks.getColor("D:1:"+String.valueOf(col));
			Color color4 = rubiks.getColor("D:3:"+String.valueOf(col));
			rubiks.setColor("D:1:"+String.valueOf(col), color4);
			rubiks.setColor("D:3:"+String.valueOf(col), color3);
			
		} else {
			
			Color color3 = rubiks.getColor("U:1:"+String.valueOf(col));
			Color color4 = rubiks.getColor("U:3:"+String.valueOf(col));
			rubiks.setColor("U:1:"+String.valueOf(col), color4);
			rubiks.setColor("U:3:"+String.valueOf(col), color3);
			
		}
		
	}
	
}
