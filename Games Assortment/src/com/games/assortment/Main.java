package com.games.assortment;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	
	private static CFrame menu;
	public  static CFrame game;
	
	private static String[] game_type = {"Sudoku"    , "Rubik's Cube"  , "Space Invaders"   , "New Game",
										 "New Game"  , "New Game"      , "New Game"         , "New Game",
										 "New Game"  , "New Game"      , "New Game"         , "New Game"};
	
	private static JPanel game_class;

	private static int [][] dims      = { {382, 489} , {639, 534}      , {800, 600}                 };
	
	public static void main(String[] args) {
		
		menu = new CFrame("Games Assortment");
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.setSize(518, 124); //518-6=512 && 124-28=96
		menu.centerInScreen();
		menu.setLayout(null);
		menu.setResizable(false);
		menu.setVisible(true);
		
		showHome();
		
	}
	
	public static void showHome() {
		
		CButton menu_item[][] = new CButton[3][4];
		for(int i=0;i<3;i++) {
			for(int n=0;n<4;n++) {
				menu_item[i][n] = new CButton();
				menu_item[i][n].setInt("i", i);
				menu_item[i][n].setInt("n", n);
				menu_item[i][n].setText(game_type[n+4*i]);
				menu_item[i][n].setBounds(n*128, i*32, 128, 32);
				menu_item[i][n].setVisible(true);
				menu_item[i][n].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						int i = ((CButton) e.getComponent()).getInt("i");
						int n = ((CButton) e.getComponent()).getInt("n");
						
						switch(i) {
						case 0: 
							if(n == 0) {showGame(0);}
							if(n == 1) {showGame(1);}
							if(n == 2) {showGame(2);}
							if(n == 3) {showGame(3);}
							break;
						case 1:
							if(n == 0) {showGame(4);}
							if(n == 1) {showGame(5);}
							if(n == 2) {showGame(6);}
							if(n == 3) {showGame(7);}
							break;
						case 2:
							if(n == 0) {showGame(8);}
							if(n == 1) {showGame(9);}
							if(n == 2) {showGame(10);}
							if(n == 3) {showGame(11);}
							break;
						default: break;
						}
					}
				});
				
				menu.add(menu_item[i][n]);
			}
		}
		
	}
	
	public static void showGame(int game_no) {
		
		switch(game_no) {
		
		case 0: game_class = new Sudoku();        break;
		case 1: game_class = new RubiksCube();    break;
		case 2: game_class = new SpaceInvaders(); break;
		default: System.out.println("Unknown Game");
		
		}
		
		if(game == null || !game.isVisible()) {
			game = new CFrame(game_type[game_no]);
			game.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			game.setResizable(false);
			game.setVisible(true);
			game.setSize(dims[game_no][0], dims[game_no][1]);
			game.centerInScreen();
			game.add(game_class);
			
		} else {
			game.setVisible(true);
		}
		
	}
	
}
