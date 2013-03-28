package com.games.assortment;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;

import javax.swing.JPanel;

public class CTextField extends JPanel {

	String[] string;
	Color [] color;
	Font     font;
	float    nextX = 4;
	float    nextY = 14;
	int      lines = 0;
		
	public void setText(String[] string, Color[] color, Font font) {
		
		this.string = string;
		this.color  = color;
		this.font   = font;
		
	}

	public String getText(int index) {
		String s = string[index];
		return s;
	}
	
	public Color getColor(int index) {
		Color c = color[index];
		return c;
	}
	
	public String[] getTextArray()  {return string;}
	public Color [] getColorArray() {return color;}
	public Font     getFont()       {return font;}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(string != null) {	
			
			for(int i=0;i<string.length;i++) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				FontRenderContext frc = g2.getFontRenderContext();
				LineMetrics       lm  = font.getLineMetrics(string[i], frc);
				
				float width  = (float) font.getStringBounds(string[i], frc).getWidth();
				float height = lm.getAscent() + lm.getDescent();
					  nextX += width;
					  nextY = height*lines + 14;
					  
				float x;
				float y;
				      
				if(getX()+nextX > getX()+getWidth()-4) {
					lines++;
					x     = 4;
					y     = nextY + height;
					nextX = width + 4;
					nextY = height*lines + 14;
				} else {
					x = nextX - width;
					y = nextY;
				}
				
				g2.setColor(color[i]);
				g2.drawString(string[i], x, y);
			}
			
			nextX = 4;
			nextY = 4;
			lines = 0;
			
		}
		
	}
	
}
