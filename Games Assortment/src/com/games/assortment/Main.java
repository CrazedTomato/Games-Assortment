package com.games.assortment;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Main {
	
	private static JFrame menu;
	private static JFrame game;
	private static String[] game_type = {"Sudoku", "Rubik's Cube", "New Game", "New Game",
										 "New Game", "New Game", "New Game", "New Game",
										 "New Game", "New Game", "New Game", "New Game"};

	public static void main(String[] args) {
		
		menu = new JFrame("Games Assortment");
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.setSize(518, 124); //518-6=512 && 124-28=96
		menu.setLocationRelativeTo(null);
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
		
		if(game == null || !game.isVisible()) {
			game = new JFrame(game_type[game_no]);
			game.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			game.setResizable(false);
			game.setVisible(true);
			
			switch(game_no) {
			case 0:
				game.setSize(382, 489);
				game.setLocationRelativeTo(menu);
				game.setLocation((menu.getX()+menu.getWidth()/2)-game.getWidth()/2, (menu.getY()+menu.getHeight()/2)-game.getHeight()/2);
				game.add(new Sudoku());
				break;
			case 1:
				game.setSize(639, 534);
				game.setLocationRelativeTo(menu);
				game.setLocation((menu.getX()+menu.getWidth()/2)-game.getWidth()/2, (menu.getY()+menu.getHeight()/2)-game.getHeight()/2);
				game.add(new RubiksCube());
				break;
			default: break;
			}
		} else {
			game.setVisible(true);
		}
		
	}
	
}
