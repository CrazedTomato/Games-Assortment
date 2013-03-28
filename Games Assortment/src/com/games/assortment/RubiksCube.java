package com.games.assortment;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

public class RubiksCube extends JPanel {
	
	private String        dir              = "";
	private String        grid_lines[][][] = { { {"1:1" , "2:1" , "2:2"},                 {"1:1" , "1:2" , "2:2" , "2:3" , "2:4"},                         {"1:2" , "2:4" , "2:5"} },                 //First Row
			                                   { {"2:1" , "2:2" , "3:1" , "4:1" , "4:2"}, {"2:2" , "2:3" , "2:4" , "3:1" , "3:2" , "4:2" , "4:3" , "4:4"}, {"2:4" , "2:5" , "3:2" , "4:4" , "4:5"} }, //Second Row
			                                   { {"4:1" , "4:2" , "5:1"},                 {"4:2" , "4:3" , "4:4" , "5:1" , "5:2"},                         {"4:4" , "4:5" , "5:2"} }                  //Third Row
			                                 };
	
	private List<Integer> tofront          = new ArrayList<Integer>(); //U=1|D=2|L=3|R=4
	private List<Integer> moves            = new ArrayList<Integer>(); //F=0|F'=1|R=2|R'=3|B=4|B'=5|L=6|L'=7|U=8|U'=9|D=10|D'=11
	
	private int           move             = 0;
	private int           locate_prev_move = -1;
	private int           same_move[]      = {-1, 0};
	
	private int           grid[][]         = { {-1, -1}, {-1, -1}, {-1, -1} };
	                      
	private int           front[][]        = {//F  R  B  L  U  D 
						   					   {0, 1, 2, 3, 4, 5}, //Layout
						   				      //U  L  R  D	  <- Faces relative to grid
						   				       {4, 3, 1, 5}, //Front
						   				       {4, 0, 2, 5}, //Right
						   				       {4, 1, 3, 5}, //Back
						   				       {4, 2, 0, 5}, //Left
						   				       {2, 3, 1, 0}, //Up
						   				       {0, 3, 1, 2}  //Down
						   				     };
	                      
	private int           last_layout[]    = {0, 1, 2, 3, 4, 5};
	
	private int           valid_moves[][]  = { {1,1 , 1,2 , 1,3}, {2,1 , 2,2 , 2,3}, {3,1 , 3,2 , 3,3}, //Right
						   				       {1,3 , 1,2 , 1,1}, {2,3 , 2,2 , 2,1}, {3,3 , 3,2 , 3,1}, //Left
						   				                              
						   				       {1,1 , 2,1 , 3,1}, {1,2 , 2,2 , 3,2}, {1,3 , 2,3 , 3,3}, //Down
						   				       {3,1 , 2,1 , 1,1}, {3,2 , 2,2 , 1,2}, {3,3 , 2,3 , 1,3}, //Up
						   				       
						   				       {1,2 , 1,3 , 2,3}, {2,3 , 3,3 , 3,2}, {3,2 , 3,1 , 2,1}, {2,1 , 1,1 , 1,2}, //Rotate Clockwise
						   				       {1,2 , 1,1 , 2,1}, {2,1 , 3,1 , 3,2}, {3,2 , 3,3 , 2,3}, {2,3 , 1,3 , 1,2}  //Rotate Anti-Clockwise
						   				     };
	                      
	private boolean       user_move        = false;
	
	private Color         color_bg         = new Color(51 , 51 , 51 );
	private Color         color_selected   = new Color(0  , 130, 255);
	private Color         color_correct    = new Color(0  , 130, 25 );
	private Color         color_incorrect  = new Color(240, 20 , 20 );
	private Color         color_red        = new Color(150, 0  , 0  );
	private Color         color_green      = new Color(0  , 170, 0  );
	private Color         color_blue       = new Color(70 , 100, 150);
	private Color         color_yellow     = new Color(250, 230, 0  );
	private Color         color_orange     = new Color(250, 130, 50 );
	private Color         color_white      = new Color(235, 235, 235);
	
	private CTextField    moves_box;
	                      
	private CButton       btn_solve;
	private CButton       btn_moves;
	private CButton       btn_shuffle;
	private CButton       btn_superflip;
	private CButton       btn_front;
	private CButton       btn_gofront;
	private CButton       btn_undo;
	private CButton       btn_redo;
	                      
	private CPaintList    main_grid;
	private CPaintList    sub_grids;
	private CPaintList    rubiks;

	public RubiksCube() {
		
		createGrids();
		createRubiks();
		createMovesBox();
		createButtons();
		
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
		main_grid.addRectangle("1:1", 216, 152, 5, 64, CPaintList.FILLED, color_bg); //First Row
		main_grid.addRectangle("1:2", 285, 152, 5, 64, CPaintList.FILLED, color_bg); //First Row
		                 
		main_grid.addRectangle("2:1", 152, 216, 64, 5, CPaintList.FILLED, color_bg); //Second Row
		main_grid.addRectangle("2:2", 216, 216, 5, 5, CPaintList.FILLED, color_bg);  //Second Row
		main_grid.addRectangle("2:3", 221, 216, 64, 5, CPaintList.FILLED, color_bg); //Second Row
		main_grid.addRectangle("2:4", 285, 216, 5, 5, CPaintList.FILLED, color_bg);  //Second Row
		main_grid.addRectangle("2:5", 290, 216, 64, 5, CPaintList.FILLED, color_bg); //Second Row
		                                       
		main_grid.addRectangle("3:1", 216, 221, 5, 64, CPaintList.FILLED, color_bg); //Third Row
		main_grid.addRectangle("3:2", 285, 221, 5, 64, CPaintList.FILLED, color_bg); //Third Row
		                                      
		main_grid.addRectangle("4:1", 152, 285, 64, 5, CPaintList.FILLED, color_bg); //Fourth Row
		main_grid.addRectangle("4:2", 216, 285, 5, 5, CPaintList.FILLED, color_bg);  //Fourth Row
		main_grid.addRectangle("4:3", 221, 285, 64, 5, CPaintList.FILLED, color_bg); //Fourth Row
		main_grid.addRectangle("4:4", 285, 285, 5, 5, CPaintList.FILLED, color_bg);  //Fourth Row
		main_grid.addRectangle("4:5", 290, 285, 64, 5, CPaintList.FILLED, color_bg); //Fourth Row
		                                       
		main_grid.addRectangle("5:1", 216, 290, 5, 64, CPaintList.FILLED, color_bg); //Fifth Row
		main_grid.addRectangle("5:2", 285, 290, 5, 64, CPaintList.FILLED, color_bg); //Fifth Row
		
		//Sub grids
		sub_grids = new CPaintList();
		
		//Right face
		sub_grids.addRectangle("R:hor1", 379, 234, 102, 3, CPaintList.FILLED, color_bg);
		sub_grids.addRectangle("R:hor2", 379, 269, 102, 3, CPaintList.FILLED, color_bg);
		sub_grids.addRectangle("R:ver1", 411, 202, 3, 102, CPaintList.FILLED, color_bg);
		sub_grids.addRectangle("R:ver2", 446, 202, 3, 102, CPaintList.FILLED, color_bg);
		                   
		//Back face        
		sub_grids.addRectangle("B:hor1", 506, 234, 102, 3, CPaintList.FILLED, color_bg);
		sub_grids.addRectangle("B:hor2", 506, 269, 102, 3, CPaintList.FILLED, color_bg);
		sub_grids.addRectangle("B:ver1", 538, 202, 3, 102, CPaintList.FILLED, color_bg);
		sub_grids.addRectangle("B:ver2", 573, 202, 3, 102, CPaintList.FILLED, color_bg);
		                    
		//Left face         
		sub_grids.addRectangle("L:hor1", 25, 234, 102, 3, CPaintList.FILLED, color_bg);
		sub_grids.addRectangle("L:hor2", 25, 269, 102, 3, CPaintList.FILLED, color_bg);
		sub_grids.addRectangle("L:ver1", 57, 202, 3, 102, CPaintList.FILLED, color_bg);
		sub_grids.addRectangle("L:ver2", 92, 202, 3, 102, CPaintList.FILLED, color_bg);
		                   
		//Up face          
		sub_grids.addRectangle("U:hor1", 202, 57, 102, 3, CPaintList.FILLED, color_bg);
		sub_grids.addRectangle("U:hor2", 202, 92, 102, 3, CPaintList.FILLED, color_bg);
		sub_grids.addRectangle("U:ver1", 234, 25, 3, 102, CPaintList.FILLED, color_bg);
		sub_grids.addRectangle("U:ver2", 269, 25, 3, 102, CPaintList.FILLED, color_bg);
		                    
		//Down face         
		sub_grids.addRectangle("D:hor1", 202, 411, 102, 3, CPaintList.FILLED, color_bg);
		sub_grids.addRectangle("D:hor2", 202, 446, 102, 3, CPaintList.FILLED, color_bg);
		sub_grids.addRectangle("D:ver1", 234, 379, 3, 102, CPaintList.FILLED, color_bg);
		sub_grids.addRectangle("D:ver2", 269, 379, 3, 102, CPaintList.FILLED, color_bg);
		
		
	}
	
	public void createRubiks() {
		
		rubiks = new CPaintList();
		
		//Front face
		rubiks.addRectangle("F:1:1", 152, 152, 64, 64, CPaintList.FILLED, color_red);
		rubiks.addRectangle("F:1:2", 221, 152, 64, 64, CPaintList.FILLED, color_red);
		rubiks.addRectangle("F:1:3", 290, 152, 64, 64, CPaintList.FILLED, color_red);
		rubiks.addRectangle("F:2:1", 152, 221, 64, 64, CPaintList.FILLED, color_red);
		rubiks.addRectangle("F:2:2", 221, 221, 64, 64, CPaintList.FILLED, color_red, "F", new Font("Arial", Font.BOLD, 48), color_bg);
		rubiks.addRectangle("F:2:3", 290, 221, 64, 64, CPaintList.FILLED, color_red);
		rubiks.addRectangle("F:3:1", 152, 290, 64, 64, CPaintList.FILLED, color_red);
		rubiks.addRectangle("F:3:2", 221, 290, 64, 64, CPaintList.FILLED, color_red);
		rubiks.addRectangle("F:3:3", 290, 290, 64, 64, CPaintList.FILLED, color_red);
		
		//Right face
		rubiks.addRectangle("R:1:1", 379, 202, 32, 32, CPaintList.FILLED, color_green);
		rubiks.addRectangle("R:1:2", 414, 202, 32, 32, CPaintList.FILLED, color_green);
		rubiks.addRectangle("R:1:3", 449, 202, 32, 32, CPaintList.FILLED, color_green);
		rubiks.addRectangle("R:2:1", 379, 237, 32, 32, CPaintList.FILLED, color_green);
		rubiks.addRectangle("R:2:2", 414, 237, 32, 32, CPaintList.FILLED, color_green, "R", new Font("Arial", Font.BOLD, 24), color_bg);
		rubiks.addRectangle("R:2:3", 449, 237, 32, 32, CPaintList.FILLED, color_green);
		rubiks.addRectangle("R:3:1", 379, 272, 32, 32, CPaintList.FILLED, color_green);
		rubiks.addRectangle("R:3:2", 414, 272, 32, 32, CPaintList.FILLED, color_green);
		rubiks.addRectangle("R:3:3", 449, 272, 32, 32, CPaintList.FILLED, color_green);
		                              
		//Back face                   
		rubiks.addRectangle("B:1:1", 506, 202, 32, 32, CPaintList.FILLED, color_orange);
		rubiks.addRectangle("B:1:2", 541, 202, 32, 32, CPaintList.FILLED, color_orange);
		rubiks.addRectangle("B:1:3", 576, 202, 32, 32, CPaintList.FILLED, color_orange);
		rubiks.addRectangle("B:2:1", 506, 237, 32, 32, CPaintList.FILLED, color_orange);
		rubiks.addRectangle("B:2:2", 541, 237, 32, 32, CPaintList.FILLED, color_orange, "B", new Font("Arial", Font.BOLD, 24), color_bg);
		rubiks.addRectangle("B:2:3", 576, 237, 32, 32, CPaintList.FILLED, color_orange);
		rubiks.addRectangle("B:3:1", 506, 272, 32, 32, CPaintList.FILLED, color_orange);
		rubiks.addRectangle("B:3:2", 541, 272, 32, 32, CPaintList.FILLED, color_orange);
		rubiks.addRectangle("B:3:3", 576, 272, 32, 32, CPaintList.FILLED, color_orange);
		                            
		//Left face                 
		rubiks.addRectangle("L:1:1", 25, 202, 32, 32, CPaintList.FILLED, color_blue);
		rubiks.addRectangle("L:1:2", 60, 202, 32, 32, CPaintList.FILLED, color_blue);
		rubiks.addRectangle("L:1:3", 95, 202, 32, 32, CPaintList.FILLED, color_blue);
		rubiks.addRectangle("L:2:1", 25, 237, 32, 32, CPaintList.FILLED, color_blue);
		rubiks.addRectangle("L:2:2", 60, 237, 32, 32, CPaintList.FILLED, color_blue, "L", new Font("Arial", Font.BOLD, 24), color_bg);
		rubiks.addRectangle("L:2:3", 95, 237, 32, 32, CPaintList.FILLED, color_blue);
		rubiks.addRectangle("L:3:1", 25, 272, 32, 32, CPaintList.FILLED, color_blue);
		rubiks.addRectangle("L:3:2", 60, 272, 32, 32, CPaintList.FILLED, color_blue);
		rubiks.addRectangle("L:3:3", 95, 272, 32, 32, CPaintList.FILLED, color_blue);
		                             
		//Up face                    
		rubiks.addRectangle("U:1:1", 202, 25, 32, 32, CPaintList.FILLED, color_yellow);
		rubiks.addRectangle("U:1:2", 237, 25, 32, 32, CPaintList.FILLED, color_yellow);
		rubiks.addRectangle("U:1:3", 272, 25, 32, 32, CPaintList.FILLED, color_yellow);
		rubiks.addRectangle("U:2:1", 202, 60, 32, 32, CPaintList.FILLED, color_yellow);
		rubiks.addRectangle("U:2:2", 237, 60, 32, 32, CPaintList.FILLED, color_yellow, "U", new Font("Arial", Font.BOLD, 24), color_bg);
		rubiks.addRectangle("U:2:3", 272, 60, 32, 32, CPaintList.FILLED, color_yellow);
		rubiks.addRectangle("U:3:1", 202, 95, 32, 32, CPaintList.FILLED, color_yellow);
		rubiks.addRectangle("U:3:2", 237, 95, 32, 32, CPaintList.FILLED, color_yellow);
		rubiks.addRectangle("U:3:3", 272, 95, 32, 32, CPaintList.FILLED, color_yellow);
		                              
		//Down face                   
		rubiks.addRectangle("D:1:1", 202, 379, 32, 32, CPaintList.FILLED, color_white);
		rubiks.addRectangle("D:1:2", 237, 379, 32, 32, CPaintList.FILLED, color_white);
		rubiks.addRectangle("D:1:3", 272, 379, 32, 32, CPaintList.FILLED, color_white);
		rubiks.addRectangle("D:2:1", 202, 414, 32, 32, CPaintList.FILLED, color_white);
		rubiks.addRectangle("D:2:2", 237, 414, 32, 32, CPaintList.FILLED, color_white, "D", new Font("Arial", Font.BOLD, 24), color_bg);
		rubiks.addRectangle("D:2:3", 272, 414, 32, 32, CPaintList.FILLED, color_white);
		rubiks.addRectangle("D:3:1", 202, 449, 32, 32, CPaintList.FILLED, color_white);
		rubiks.addRectangle("D:3:2", 237, 449, 32, 32, CPaintList.FILLED, color_white);
		rubiks.addRectangle("D:3:3", 272, 449, 32, 32, CPaintList.FILLED, color_white);
		
	}
	
	public void createMovesBox() {
		
		moves_box = new CTextField();
		moves_box.setFocusable(false);
		moves_box.setBackground(color_bg.darker());
		moves_box.setBorder(BorderFactory.createLineBorder(rubiks.getColor("F:2:2"), 1, true));
		moves_box.setBounds(379, 25, 229, 67);
		add(moves_box);
		
	}
	
	public void createButtons() {
		
		setLayout(null);
		
		btn_solve = new CButton("Solve");
		btn_solve.setBounds(379, 379, 114, 34);
		btn_solve.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				setSolvedState();
			}
			
		});
		
		btn_moves = new CButton("Reset Moves");
		btn_moves.setBounds(494, 379, 114, 34);
		btn_moves.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				resetMoves();
			}
			
		});
		
		btn_shuffle = new CButton("Shuffle");
		btn_shuffle.setBounds(379, 413, 114, 34);
		btn_shuffle.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				setShuffledState();
			}
			
		});
		
		btn_superflip = new CButton("Superflip");
		btn_superflip.setBounds(494, 413, 114, 34);
		btn_superflip.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				setSuperflippedState();
			}
			
		});
		
		btn_front = new CButton("Set Front");
		btn_front.setBounds(379, 447, 114, 34);
		btn_front.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				setAsFront();
			}
			
		});
		
		btn_gofront = new CButton("Go To Front");
		btn_gofront.setBounds(494, 447, 114, 34);
		btn_gofront.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				goToFront();
			}
			
		});
		
		btn_undo = new CButton("Undo");
		btn_undo.setBounds(379, 93, 114, 34);
		btn_undo.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				undoMove();
			}
			
		});
		
		btn_redo = new CButton("Redo");
		btn_redo.setBounds(494, 93, 114, 34);
		
		add(btn_solve);
		add(btn_moves);
		add(btn_shuffle);
		add(btn_superflip);
		add(btn_front);
		add(btn_gofront);
		add(btn_undo);
		add(btn_redo);
		
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
			
			user_move = true;
			
			main_grid.setColor(grid_lines[grid[0][0]-1][grid[0][1]-1], color_correct);
			main_grid.setColor(grid_lines[grid[1][0]-1][grid[1][1]-1], color_correct);
			main_grid.setColor(grid_lines[grid[2][0]-1][grid[2][1]-1], color_correct);
			repaint();
			
			final Cluster[] nulled_cluster = new Cluster[2];
			nulled_cluster[0] = new Cluster(-1);
			nulled_cluster[1] = new Cluster(-1);
			
			CTimer timer = new CTimer(200, nulled_cluster, new ActionListener() {
				
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
					
					for(int i=0;i<2;i++) {
						grid[0][i] = nulled_cluster[i].getInt();
						grid[1][i] = nulled_cluster[i].getInt();
						grid[2][i] = nulled_cluster[i].getInt();
					}
					
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
		
		user_move = true;
		
		final Cluster Correct = new Cluster(correct);
		CTimer timer = new CTimer(200, Correct, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!Correct.getBoolean()) {shiftRubiks(face);}
				sub_grids.setColor(color_bg);
				repaint();
			}
			
		});
		
		timer.setRepeats(false);
		timer.start();
		
	}
	
	public void setSolvedState() {
		
		String[] face   = {"F", "R", "B", "L", "U", "D"};
		Color[]  colors = {color_red, color_green, color_orange, color_blue, color_yellow, color_white};
		
		for(int i=0;i<6;i++) {
			rubiks.setColor(face[i], colors[front[0][i]]);
		}
		
		for(int i=0;i<6;i++) {rubiks.setText(face[i]+":2:2", face[i]);}
		
		for(int i=0;i<3;i++) {
			grid[i][0] = -1;
			grid[i][1] = -1;
		}
		
		main_grid.setColor(color_bg);
		
		resetMoves();
		repaint();
		
	}
	
	public void setShuffledState() {
		
		Random random = new Random();
		
		for(int i=0;i<20;i++) {
			
			switch(random.nextInt(5)) {
			case 0  : shiftUp(random.nextInt(3));    break;	
			case 1  : shiftDown(random.nextInt(3));  break;
			case 2  : shiftLeft(random.nextInt(3));  break;
			case 3  : shiftRight(random.nextInt(3)); break;
			case 4  : shiftRotate("F", 1);           break;
			case 5  : shiftRotate("F", 3);           break;
			default : break;
			}
			
		}
		
		for(int i=0;i<3;i++) {
			grid[i][0] = -1;
			grid[i][1] = -1;
		}
		
		main_grid.setColor(color_bg);
		
		resetMoves();
		goToFront();
		
	}
	
	public void setSuperflippedState() {
		
		String[] face     = {"F", "R", "B", "L", "U", "D"};
		Color [] color    = new Color[6];
		
		setSolvedState();
		
		for(int i=0;i<6;i++) {
			color[i] = rubiks.getColor(face[i]+":1:1");
		}
		
		for(int i=0;i<6;i++) { //Loop for each face
			
			rubiks.setColor(face[i]+":1:2", color[front[i+1][0]]); //Up
			rubiks.setColor(face[i]+":2:1", color[front[i+1][1]]); //Left
			rubiks.setColor(face[i]+":2:3", color[front[i+1][2]]); //Right
			rubiks.setColor(face[i]+":3:2", color[front[i+1][3]]); //Down
			
		}
		
		resetMoves();
		repaint();
		
	}
	
	public void setAsFront() {
		
		String[] face   = {"F", "R", "B", "L", "U", "D"};		
		if(Arrays.equals(front[0], getCurrentLayout())) {return;}
		
		front[0] = getCurrentLayout();
		resetMoves();
		
		for(int i=0;i<6;i++) {rubiks.setText(face[i]+":2:2", face[i]);}		
		moves_box.setBorder(BorderFactory.createLineBorder(rubiks.getColor("F:2:2"), 1, true));
		
		for(int i=0;i<3;i++) {
			grid[i][0] = -1;
			grid[i][1] = -1;
		}
		
		main_grid.setColor(color_bg);
		
		tofront = new ArrayList<Integer>();
		repaint();
		
	}
	
	public void goToFront() {
		
		int amount = tofront.size();
		
		if(amount == 0) {repaint(); return;} //Already using front layout - repaint incase called by shuffle
		
		for(int i=amount;i>0;i--) {
			
			switch(tofront.get(i-1)) {
			case 1 : shiftMainVer("U"); break;
			case 2 : shiftMainVer("D"); break;
			case 3 : shiftMainHor("L"); break;
			case 4 : shiftMainHor("R"); break;
			default: break;
			}
			
		}
		
		String[] face   = {"F", "R", "B", "L", "U", "D"};
		for(int i=0;i<6;i++) {rubiks.setText(face[i]+":2:2", face[i]);}
		
		for(int i=0;i<3;i++) {
			grid[i][0] = -1;
			grid[i][1] = -1;
		}
		
		main_grid.setColor(color_bg);
		
		tofront = new ArrayList<Integer>();
		repaint();
		
	}
	
	public int[] getCurrentLayout() {
		
		String[] face   = {"F", "R", "B", "L", "U", "D"};
		Color[]  colors = {color_red, color_green, color_orange, color_blue, color_yellow, color_white};
		int[]    layout = new int[6];
		
		//Gets layout of grids on screen
		for(int i=0;i<6;i++) { //Loop for each face
			for(int n=0;n<6;n++) { //Loop for each color
				if(rubiks.compareColor(face[i]+":2:2", colors[front[0][n]])) {
					layout[i] = n;
				}
			}
		}
		
		return layout;
		
	}
	
	public void goToLayout(int[] layout) {
		
		if(getCurrentLayout().equals(last_layout) || tofront.size() == 0) {return;} //Already using last layout
		
		int count = tofront.size()-1;
		while(!getCurrentLayout().equals(last_layout) && count > -1) {
			
			switch(tofront.get(count)) {
			case 1 : shiftMainVer("U"); break;
			case 2 : shiftMainVer("D"); break;
			case 3 : shiftMainHor("L"); break;
			case 4 : shiftMainHor("R"); break;
			default: break;
			}
			
			count--;
			
		}
		
		String[] face   = {"F", "R", "B", "L", "U", "D"};
		for(int i=0;i<6;i++) {rubiks.setText(face[i]+":2:2", face[i]);}
		
		for(int i=0;i<3;i++) {
			grid[i][0] = -1;
			grid[i][1] = -1;
		}
		
		main_grid.setColor(color_bg);
		
		tofront = new ArrayList<Integer>();
		repaint();
		
	}
	
	public void addMove(int int_move) {
		
		boolean  new_move     = true;
		String[] string_moves = {"F"   , "F2"    , "F'"  , "F'2"   , /*NOT USED DIRECTLY*/ "F"   , "F2"    ,
								 "R"   , "R2"    , "R'"  , "R'2"   , /*NOT USED DIRECTLY*/ "R"   , "R2"    ,
								 "B"   , "B2"    , "B'"  , "B'2"   , /*NOT USED DIRECTLY*/ "B"   , "B2"    ,
								 "L"   , "L2"    , "L'"  , "L'2"   , /*NOT USED DIRECTLY*/ "L"   , "L2"    ,
								 "U"   , "U2"    , "U'"  , "U'2"   , /*NOT USED DIRECTLY*/ "U"   , "U2"    ,
								 "D"   , "D2"    , "D'"  , "D'2"   , /*NOT USED DIRECTLY*/ "D"   , "D2"    ,
								 "U D'", "U2 D'2", "U' D", "U'2 D2", /*NOT USED DIRECTLY*/ "U D'", "U2 D'2",
								 "L R'", "L2 R'2", "L' R", "L'2 R2", /*NOT USED DIRECTLY*/ "L R'", "L2 R'2"
								};
		
		//Check if the user undid moves and then made a new move
		if(locate_prev_move < moves.size()-1) {new_move = false;}
		try {if(!moves_box.getColor(moves.size()-1).equals(Color.WHITE)) {new_move = false;}}
		catch(NullPointerException | IndexOutOfBoundsException e) {}
		
		if(new_move) {
			
			//Check if the move is new, inverted or a repeat
			if(int_move != same_move[0] && (int_move+2 != same_move[0] && int_move-2 != same_move[0])) { //New move
				moves.add(int_move);
				locate_prev_move++;
				same_move[0] = int_move;
				same_move[1] = 0;
			}
			
			else if(int_move != same_move[0]) { //Inverted
				if(same_move[1] == 0) { //Original move was not repeated so remove it altogether
					moves.remove(moves.size()-1);
					locate_prev_move--;
					try {same_move[0] = moves.get(moves.size()-1);}
					catch(IndexOutOfBoundsException e) {same_move[0] = -1;}
					same_move[1] = 0;
				} else { //Original move was repeated so remove the repeat just
					moves.set(moves.size()-1, moves.get(moves.size()-1)-1);
				}
			}
			
			else { //Same move
				moves.set(moves.size()-1, moves.get(moves.size()-1)+1);
				switch(same_move[1]) {
				case 0: same_move[1]++; break;
				case 1: same_move[1]++; break;
				case 2:
					moves.remove(moves.size()-1);
					locate_prev_move--;
					try {same_move[0] = moves.get(moves.size()-1);}
					catch(IndexOutOfBoundsException e) {same_move[0] = -1;}
					same_move[1] = 0;
					break;
				default: break;
				}
			}
			
			for(int i=moves.size()-1;i>locate_prev_move;i++) {
				moves.remove(i);
			}
			
			String[] string = new String[moves.size()];
			Color [] color  = new Color [moves.size()];
			Font     font   = new Font("Arial", Font.BOLD, 12);
			
			for(int i=0;i<moves.size();i++) {
				 string[i] = string_moves[moves.get(i)] + " ";
				 color [i] = Color.WHITE;
			}
			
			moves_box.setText(string, color, font);	
			repaint(379, 25, 229, 67);
			
		} else { //The user undid a move and has now repeated that move
			
			if(int_move == moves.get(locate_prev_move+1)) { //Check if the move made is the same as the previously undid move
				locate_prev_move++;
				
				String[] string = new String[moves.size()];
				Color [] color  = new Color [moves.size()];
				Font     font   = new Font("Arial", Font.BOLD, 12);
				
				for(int i=0;i<locate_prev_move+1;i++) { //For moves that have been made
					 string[i] = string_moves[moves.get(i)] + " ";
					 color [i] = Color.WHITE;
				}
				
				for(int i=locate_prev_move+1;i<moves.size();i++) { //For moves that have been undid
					 string[i] = string_moves[moves.get(i)] + " ";
					 color [i] = Color.GRAY.darker();
				}
				
				moves_box.setText(string, color, font);	
				repaint(379, 25, 229, 67);
			}
			
			else if(int_move == moves.get(locate_prev_move+1)+1) { //Check if move made is the same but the prev undid move was made twice
				if(!moves_box.getColor(locate_prev_move+1).equals(Color.WHITE)) { //Check if no part of the double move has been re-made
					String[] string = new String[moves.size()+1];
					Color [] color  = new Color [moves.size()+1];
					Font     font   = new Font("Arial", Font.BOLD, 12);
					
					for(int i=0;i<locate_prev_move+1;i++) { //For moves that have been made
						 string[i] = string_moves[moves.get(i)] + " ";
						 color [i] = Color.WHITE;
					}
					
					string[locate_prev_move+1] = string_moves[moves.get(locate_prev_move+1)].substring(0, string_moves[moves.get(locate_prev_move+1)].length()-1);
					color [locate_prev_move+1] = Color.WHITE;
					
					string[locate_prev_move+2] = "2";
					color [locate_prev_move+2] = Color.GRAY.darker();
					
					for(int i=locate_prev_move+3;i<moves.size()+1;i++) { //For moves that have been undid
						 string[i] = string_moves[moves.get(i)] + " ";
						 color [i] = Color.GRAY.darker();
					}
					
					moves_box.setText(string, color, font);	
					repaint(379, 25, 229, 67);
				
				} else { //The first part of the double move has already been made
					locate_prev_move++;
					
					String[] string = new String[moves.size()];
					Color [] color  = new Color [moves.size()];
					Font     font   = new Font("Arial", Font.BOLD, 12);
					
					for(int i=0;i<locate_prev_move+1;i++) { //For moves that have been made
						 string[i] = string_moves[moves.get(i)] + " ";
						 color [i] = Color.WHITE;
					}
					
					for(int i=locate_prev_move+1;i<moves.size();i++) { //For moves that have been undid
						 string[i] = string_moves[moves.get(i)] + " ";
						 color [i] = Color.GRAY.darker();
					}
					
					moves_box.setText(string, color, font);	
					repaint(379, 25, 229, 67);
				}
			}
			
			else { //The new move is not the same as the previously made move nor is it part of a double move
				for(int i=moves.size()-1;i>locate_prev_move;i--) {
					moves.remove(i);
				}
				
				same_move[0] = -1;
				same_move[1] = -1;
				addMove(int_move);
			}
			
		}
		
	}

	public void resetMoves() {
		
		same_move[0] = -1;
		same_move[1] = 0;
		moves        = new ArrayList<Integer>();
		moves_box.setText(null, null, null);
		repaint(379, 25, 229, 67);
		
	}
	
	public void callMove(int move) {
		switch(move) {
		
		case 0 : shiftRotate("F", 1);           break; //F
		case 2 : shiftRotate("F", 3);           break; //F'
		                                        
		case 6 : shiftUp    (3);                break; //R
		case 8 : shiftDown  (3);                break; //R'
		                                        
		case 14: shiftRotate("F", 1);           break; //B
		case 16: shiftRotate("F", 3);           break; //B'
		
		case 20: shiftDown  (1);                break; //L
		case 22: shiftUp    (1);                break; //L'
		                                        
		case 26: shiftRight (1);                break; //U
		case 28: shiftLeft  (1);                break; //U'
		                                        
		case 32: shiftRight (3);                break; //D
		case 34: shiftLeft  (3);                break; //D'
		
		case 38: shiftLeft  (1); shiftLeft (3); break; //U D'
		case 40: shiftRight (1); shiftRight(3); break; //U' D
		
		case 44: shiftDown  (1); shiftDown (3); break; //L R'
		case 46: shiftUp    (1); shiftUp   (3); break; //L' R
		
		default:                                break;
		
		}
	}
	
	public void undoMove() {
		
		int undo = moves.get(locate_prev_move);
		String[] string_moves = {"F"   , "F2"    , "F'"  , "F'2"   , /*NOT USED DIRECTLY*/ "F"   , "F2"    ,
								 "R"   , "R2"    , "R'"  , "R'2"   , /*NOT USED DIRECTLY*/ "R"   , "R2"    ,
								 "B"   , "B2"    , "B'"  , "B'2"   , /*NOT USED DIRECTLY*/ "B"   , "B2"    ,
								 "L"   , "L2"    , "L'"  , "L'2"   , /*NOT USED DIRECTLY*/ "L"   , "L2"    ,
								 "U"   , "U2"    , "U'"  , "U'2"   , /*NOT USED DIRECTLY*/ "U"   , "U2"    ,
								 "D"   , "D2"    , "D'"  , "D'2"   , /*NOT USED DIRECTLY*/ "D"   , "D2"    ,
								 "U D'", "U2 D'2", "U' D", "U'2 D2", /*NOT USED DIRECTLY*/ "U D'", "U2 D'2",
								 "L R'", "L2 R'2", "L' R", "L'2 R2", /*NOT USED DIRECTLY*/ "L R'", "L2 R'2"
								};
		
		user_move = false;
		goToLayout(last_layout);
		
		if(!string_moves[undo].contains("2")) { //Checks if the move recorded was not a double move
			locate_prev_move--;
			
			//Checks if the move is not inverted
			if(!string_moves[undo].contains("'")) {callMove(undo+2);}
			else                                  {callMove(undo-2);}
			
			String[] string = new String[moves.size()];
			Color [] color  = new Color [moves.size()];
			Font     font   = new Font("Arial", Font.BOLD, 12);
			
			for(int i=0;i<locate_prev_move+1;i++) { //For moves that have been made
				 string[i] = string_moves[moves.get(i)] + " ";
				 color [i] = Color.WHITE;
			}
			
			for(int i=locate_prev_move+1;i<moves.size();i++) { //For moves that have been undid
				 string[i] = string_moves[moves.get(i)] + " ";
				 color [i] = Color.GRAY.darker();
			}
			
			moves_box.setText(string, color, font);	
			repaint();
		
		} else { //The move is a double move
			//Checks if the move is not inverted
			if(!string_moves[undo].contains("'")) {callMove(undo+2);}
			else                                  {callMove(undo-2);}
			
			if(moves_box.getColor(locate_prev_move).equals(Color.WHITE)) { //Check if no part of the double move has been undid
				String[] string = new String[moves.size()+1];
				Color [] color  = new Color [moves.size()+1];
				Font     font   = new Font("Arial", Font.BOLD, 12);
				
				for(int i=0;i<locate_prev_move+1;i++) { //For moves that have been made
					 string[i] = string_moves[moves.get(i)] + " ";
					 color [i] = Color.WHITE;
				}
				
				string[locate_prev_move+1] = string_moves[moves.get(locate_prev_move+1)].substring(0, string_moves[moves.get(locate_prev_move+1)].length()-1);
				color [locate_prev_move+1] = Color.WHITE;
				
				string[locate_prev_move+2] = "2";
				color [locate_prev_move+2] = Color.GRAY.darker();
				
				for(int i=locate_prev_move+3;i<moves.size();i++) { //For moves that have been undid
					 string[i] = string_moves[moves.get(i)] + " ";
					 color [i] = Color.GRAY.darker();
				}
				
				moves_box.setText(string, color, font);	
				repaint();
				
			} else { //The second part of the double has been undid
				locate_prev_move--;
				
				String[] string = new String[moves.size()+1];
				Color [] color  = new Color [moves.size()+1];
				Font     font   = new Font("Arial", Font.BOLD, 12);
				
				for(int i=0;i<locate_prev_move+1;i++) { //For moves that have been made
					 string[i] = string_moves[moves.get(i)] + " ";
					 color [i] = Color.WHITE;
				}
				
				for(int i=locate_prev_move+1;i<moves.size();i++) { //For moves that have been undid
					 string[i] = string_moves[moves.get(i)] + " ";
					 color [i] = Color.GRAY.darker();
				}
				
				moves_box.setText(string, color, font);	
				repaint();
				
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
		
		last_layout = getCurrentLayout();
		
	}
	
	public void shiftUp(int col) {
		
		Color[]  color = new Color[12];
		String[] face  = {"F", "D", "B", "U"};
		String grid_space;
		
		for(int i=0;i<12;i++) { //Loop for each color
			int face_no = (int) Math.floor(((double)i + Math.floor(((double)i)/4))+1)/4;
			int row     = i - face_no*3 + 1;
			
			if(face_no == 2) {grid_space = face[face_no] + ":" + String.valueOf(row) + ":" + String.valueOf(4-col);}
			else             {grid_space = face[face_no] + ":" + String.valueOf(row) + ":" + String.valueOf(col);}
			
			color[i] = rubiks.getColor(grid_space);
		}
		
		for(int i=0;i<12;i++) { //loop for each color
			int face_no = (int) Math.floor(((double)i + Math.floor(((double)i)/4))+1)/4;
			int row     = i - face_no*3 + 1;
			
			if(face_no == 2) {grid_space = face[face_no] + ":" + String.valueOf(row) + ":" + String.valueOf(4-col);}
			else             {grid_space = face[face_no] + ":" + String.valueOf(row) + ":" + String.valueOf(col);}
			
			if(i<9) {rubiks.setColor(grid_space, color[i+3]);}
			else    {rubiks.setColor(grid_space, color[i-9]);}
		}
		
		if(col == 1) {
			shiftRotate("L", 3);
			if(user_move) {addMove(20);}
		}
		
		if(col == 2) {
			tofront.add(1);
			String[] faces  = {"F", "R", "B", "L", "U", "D"};
			int[]    layout = getCurrentLayout();
			for(int i=0;i<6;i++) {
				rubiks.setText(faces[i]+":2:2", faces[layout[i]]);
			}
			
			if(user_move) {addMove(42);}
		}
		
		if(col == 3) {
			shiftRotate("R", 1);
			if(user_move) {addMove(6);}
		}
		
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
		
		if(col == 1) {
			shiftRotate("L", 1);
			if(user_move) {addMove(18);}
		}
		
		if(col == 2) {
			tofront.add(2);
			String[] faces  = {"F", "R", "B", "L", "U", "D"};
			int[]    layout = getCurrentLayout();
			for(int i=0;i<6;i++) {
				rubiks.setText(faces[i]+":2:2", faces[layout[i]]);
			}
			
			if(user_move) {addMove(44);}
		}
		
		if(col == 3) {
			shiftRotate("R", 3);
			if(user_move) {addMove(8);}
		}
		
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
		
		if(row == 1) {
			shiftRotate("U", 1);
			if(user_move) {addMove(24);}
		}
		
		if(row == 2) {
			tofront.add(3);
			String[] faces  = {"F", "R", "B", "L", "U", "D"};
			int[]    layout = getCurrentLayout();
			for(int i=0;i<6;i++) {
				rubiks.setText(faces[i]+":2:2", faces[layout[i]]);
			}
			
			if(user_move) {addMove(38);}
		}
		
		if(row == 3) {
			shiftRotate("D", 3);
			if(user_move) {addMove(32);}
		}
		
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
		
		if(row == 1) {
			shiftRotate("U", 3);
			if(user_move) {addMove(26);}
		}
		
		if(row == 2) {
			tofront.add(4);
			String[] faces  = {"F", "R", "B", "L", "U", "D"};
			int[]    layout = getCurrentLayout();
			for(int i=0;i<6;i++) {
				rubiks.setText(faces[i]+":2:2", faces[layout[i]]);
			}
			
			if(user_move) {addMove(36);}
		}
		
		if(row == 3) {
			shiftRotate("D", 1);
			if(user_move) {addMove(30);}
		}
		
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
			if(user_move) {addMove(getCurrentLayout()[0]*6);}
				
		}
		
	}
	
	public void shiftMainHor(String face) {
		
		main_grid.setColor(color_bg);
		
		if(face.equals("R")) {
			
			shiftLeft(1);
			if(user_move) {addMove(26);} //Cancel out moves as just a view rotation
			
			shiftLeft(2);
			if(user_move) {addMove(36);} //Cancel out moves as just a view rotation
			
			shiftLeft(3);
			if(user_move) {addMove(30);} //Cancel out moves as just a view rotation
			
			
		} else {
			
			shiftRight(1);
			if(user_move) {addMove(24);} //Cancel out moves as just a view rotation
			
			shiftRight(2);
			if(user_move) {addMove(38);} //Cancel out moves as just a view rotation
			
			shiftRight(3);
			if(user_move) {addMove(32);} //Cancel out moves as just a view rotation
			
		}
		
	}
	
	public void shiftMainVer(String face) {
		
		main_grid.setColor(color_bg);
		
		if(face.equals("D")) {
			
			shiftUp(1);
			if(user_move) {addMove(18);} //Cancel out moves as just a view rotation
			
			shiftUp(2);
			if(user_move) {addMove(44);} //Cancel out moves as just a view rotation
			
			shiftUp(3);
			if(user_move) {addMove(8);} //Cancel out moves as just a view rotation
			
		} else {
			
			shiftDown(1);
			if(user_move) {addMove(20);} //Cancel out moves as just a view rotation
			
			shiftDown(2);
			if(user_move) {addMove(42);} //Cancel out moves as just a view rotation
			
			shiftDown(3);
			if(user_move) {addMove(6);} //Cancel out moves as just a view rotation
			
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
