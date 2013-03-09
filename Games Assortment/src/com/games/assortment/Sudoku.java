package com.games.assortment;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Sudoku extends JPanel {
	
	private String[]    difficulty   = {"Easy", "Medium", "Hard"};
	private int         diffno       = 0;
	private int[][][][] solution     = new int[3][3][3][3];
	private LimitField  grid[][][][] = new LimitField[3][3][3][3];
	private Random      random       = new Random();
	private JButton     newgame;
	private JButton     check;
	private JButton     diff;
	private JButton     other;
	
	public Sudoku() {
		
		showButtons();
		generateSudoku();
		createTextViews();
		
	}
	
	public void showButtons() {
		
		setLayout(null);
		
		newgame = new JButton("New Game");
		newgame.setBounds(60, 376, 128, 32);
		newgame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				generateSudoku();
				createTextViews();
			}
		});
		
		check = new JButton("Check");
		check.setBounds(188, 376, 128, 32);
		check.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean complete = checkComplete();
				//checkAnswers(complete);
			}
		});
		
		diff = new JButton(difficulty[diffno]);
		diff.setBounds(60, 408, 128, 32);
		diff.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(diffno != 2) {diffno++;}
				else {diffno = 0;}
				
				diff.setText(difficulty[diffno]);
				generateSudoku();
				createTextViews();
			}
		});
		
		other = new JButton("Feature");
		other.setBounds(188, 408, 128, 32);
		
		add(newgame);
		add(check);
		add(diff);
		add(other);
		
	}
	
	public void generateSudoku() {
		
		int number = -1;
		boolean horCopy   = false; //The random number is the same as another horizontally
		boolean verCopy   = false; //The random number is the same as another vertically
		boolean boxCopy   = false; //The random number is the same as another in its box
		boolean denyValue = false; //The random number is the same as another already attempted
		
		//Initialise solutions
		for(int i=0;i<3;i++) {
			for(int n=0;n<3;n++) {
				for(int t=0;t<3;t++) {
					for(int e=0;e<3;e++) {
						solution[i][n][t][e] = -1;
					}
				}
			}
		}
		
		//Calculate solutions
		for(int i=0;i<3;i++) {
			for(int n=0;n<3;n++) {
				for(int t=0;t<3;t++) {
					for(int e=0;e<3;e++) {
						
						String valuesCopied = "";
						
						do {
							
							denyValue = false;
							
							number = random.nextInt(9) + 1;
							if(valuesCopied.contains(String.valueOf(number))) {
								if(valuesCopied.length() >= 9) {
									
									for(int g=0;g<3;g++) {
										for(int r=0;r<3;r++) {
											for(int s=0;s<3;s++) {
												solution[i][g][r][s] = -1;
											}
										}
									}
									
									n = 0;
									t = 0;
									e = 0;
									
								} else {denyValue = true;}
							}
							
							if(!denyValue) {
								
								horCopy = false;
								verCopy = false;
								boxCopy = false;
								
								for(int a=0;a<3;a++) {
									if(horCopy || verCopy || boxCopy) {break;}
									for(int b=0;b<3;b++) {
										if(horCopy || verCopy || boxCopy) {break;}
										
										if(number == solution[i][a][t][b]) {
											horCopy = true;
											valuesCopied += String.valueOf(number);
										}
										if(number == solution[a][n][b][e]) {
											verCopy = true;
											valuesCopied += String.valueOf(number);
										}
										if(number == solution[i][n][a][b]) {
											boxCopy = true;
											valuesCopied += String.valueOf(number);
										}
									}								
								}
								
							}
							
						} while(horCopy || verCopy || boxCopy || denyValue);
						
						solution[i][n][t][e] = number;
						
					}
				}
			}
		}
		
	}
	
	public void createTextViews() {
		
		int count = 1;
		
		for(int i=0;i<3;i++) {
			for(int n=0;n<3;n++) {
				for(int t=0;t<3;t++) {
					for(int e=0;e<3;e++) {
						if(grid[i][n][t][e] != null) {remove(grid[i][n][t][e]);}
						grid[i][n][t][e] = new LimitField(1, 1) {
							public void processKeyEvent(KeyEvent e) {
								char c = e.getKeyChar();
								try {
									if(c > 31 && c < 65535 && c != 127) {
										Integer.parseInt(c + "");
									}
									super.processKeyEvent(e);
								}
								catch(NumberFormatException n) {}
							}							
						};
						
						int x = e*32+e*3+30 + n*107;
						if(n >= 3) {x+=2;}
						if(n >= 6) {x+=2;}
						
						int y = t*32+t*3+30 + i*107;
						if(i >= 3) {y+=2;}
						if(i >= 6) {y+=2;}
						
						grid[i][n][t][e].setBounds(x, y, 32, 32);
						grid[i][n][t][e].setBorder(null);
						grid[i][n][t][e].setFont(new Font("Arial Bold", Font.ROMAN_BASELINE, 24));
						grid[i][n][t][e].setHorizontalAlignment(JTextField.CENTER);
						add(grid[i][n][t][e]);
					}
				}
			}
		}
		
		//Show only clues
		int clueno = 25 - (diffno*4);		
		while(count <= clueno) {
			for(int i=0;i<3;i++) {
				if(count > clueno) {break;}
				for(int n=0;n<3;n++) {
					if(count > clueno) {break;}
					for(int t=0;t<3;t++) {
						if(count > clueno) {break;}
						for(int e=0;e<3;e++) {
							if(count > clueno) {break;}
							if(random.nextInt(81) < clueno) {
								if(grid[i][n][t][e].isEditable()) {
									grid[i][n][t][e].setText(String.valueOf(solution[i][n][t][e]));
									grid[i][n][t][e].setEditable(false);
									count++;
								}
							}
						}
					}
				}
			}
		}
		
		repaint();
		
	}

	public boolean checkComplete() {
		
		int count = 0;
		
		//Check if all answers entered
		for(int i=0;i<3;i++) {
			for(int n=0;n<3;n++) {
				for(int t=0;t<3;t++) {
					for(int e=0;e<3;e++) {
						
						if(grid[i][n][t][e].getText().equals("")) {
							grid[i][n][t][e].setBackground(Color.RED);
							count++;
						}
						
					}
				}
			}
		}
		
		repaint();
		
		if(count > 0) {return false;}
		else {return true;}
		
	}
	
	public void checkAnswers(boolean complete) {
		
		int correct = 0;
		
		if(!complete) {
			try {
				Thread.sleep(2000);
				for(int i=0;i<3;i++) {
					for(int n=0;n<3;n++) {
						for(int t=0;t<3;t++) {
							for(int e=0;e<3;e++) {
								if(grid[i][n][t][e].getBackground() == Color.RED) {
									grid[i][n][t][e].setBackground(Color.WHITE);
								}								
							}
						}
					}
				}
				repaint();
			}
			catch (InterruptedException e) {e.printStackTrace();}
		} else { //Check if answers are correct
			for(int i=0;i<3;i++) {
				for(int n=0;n<3;n++) {
					for(int t=0;t<3;t++) {
						for(int e=0;e<3;e++) {
							if(grid[i][n][t][e].getText().equals(String.valueOf(solution[i][n][t][e]))) {
								correct++;
								if(grid[i][n][t][e].isEditable()) {grid[i][n][t][e].setBackground(Color.GREEN);}
							} else {grid[i][n][t][e].setBackground(Color.RED);}
						}
					}
				}
			}
			if(correct == 81) {
				check.setText("Well Done!");
			} else {
				check.setText("Whoops!");
			}
			
		}
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Set background of soduku box
		g.setColor(Color.WHITE);
		g.fillRect(25, 25, 326, 326);
		g.setColor(Color.BLACK);
			
		//Surrounding box		
		g.fillRect(25, 25, 326, 5);   //Top line
		g.fillRect(346, 25, 5, 326);  //Right line
		g.fillRect(25, 346, 326, 5); //Bottom line
		g.fillRect(25, 25, 5, 326);   //Left line
		
		//Main vertical lines		
		g.fillRect(132, 30, 5, 316); //First line
		g.fillRect(239, 30, 5, 316); //Second line
		
		//Main horizontal lines		
		g.fillRect(30, 132, 316, 5); //First line
		g.fillRect(30, 239, 316, 5); //Second line
		
		//Sub vertical lines	
		g.fillRect(62, 30, 3, 316);  //First line
		g.fillRect(97, 30, 3, 316);  //Second line
		g.fillRect(169, 30, 3, 316); //Third line
		g.fillRect(204, 30, 3, 316); //Fourth line
		g.fillRect(276, 30, 3, 316); //Fifth line
		g.fillRect(311, 30, 3, 316); //Sixth line
		
		//Sub horizontal lines	
		g.fillRect(30, 62, 316, 3);  //First line
		g.fillRect(30, 97, 316, 3);  //Second line
		g.fillRect(30, 169, 316, 3); //Third line
		g.fillRect(30, 204, 316, 3); //Fourth line
		g.fillRect(30, 276, 316, 3); //Fifth line
		g.fillRect(30, 311, 316, 3); //Sixth line
		
	}

}
