package com.games.assortment;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

public class CPaintList extends JComponent {
	
	private List<int[]> list = new ArrayList<int[]>();
	private List<String> identity = new ArrayList<String>();
	private int[] attrib;
	
	/**
	 * Adds a new line to paint to the PaintList with bounds(x, y, width, height)
	 * 
	 * @param id     - The identifier of the line (String)
	 * @param x      - The x co-ordinate of the line (Integer)
	 * @param y      - The y co-ordinate of the line (Integer)
	 * @param width  - The width of the line (Integer)
	 * @param height - The height of the line (Integer)
	 */
	public void add(String id, int x, int y, int width, int height) {
		
		
		identity.add(id);
		
		attrib = new int[8];
		attrib[0] = 0;      //Shape
		attrib[1] = x;      //x co-ord
		attrib[2] = y;      //y co-ord
		attrib[3] = width;  //Width
		attrib[4] = height; //Height
		attrib[5] = 0;      //Color red
		attrib[6] = 0;      //Color green
		attrib[7] = 0;      //Color blue
		list.add(attrib);   //Add attributes to list item
		
	}
	
	/**
	 * Adds a new user-defined shape to paint to the PaintList with bounds(x, y, width, height)
	 * 
	 * @param id     - The identifier of the desired shape (String) 
	 * @param x      - The x co-ordinate of the desired shape (Integer)
	 * @param y      - The y co-ordinate of the desired shape (Integer)
	 * @param width  - The width of the desired shape (Integer)
	 * @param height - The height of the desired shape (Integer)
	 * @param shape  - The desired shape (String) -- List of supported shapes: "rectangle", "oval".
	 *                  To draw only an outline use the following format "rectangle -o" (To simply draw a line use a 
	 *                  method that does not require shape)
	 */
	public void add(String id, int x, int y, int width, int height, String shape) {
		
		int shape_no = 0;
		if(shape.equals("rectangle"))    {shape_no = 1;}
		if(shape.equals("rectangle -o")) {shape_no = 2;}
		if(shape.equals("oval"))         {shape_no = 3;}
		if(shape.equals("oval -o"))      {shape_no = 4;}
		
		identity.add(id);
		
		attrib = new int[8];
		attrib[0] = shape_no; //Shape
		attrib[1] = x;        //x co-ord
		attrib[2] = y;        //y co-ord
		attrib[3] = width;    //Width
		attrib[4] = height;   //Height
		attrib[5] = 0;        //Color red
		attrib[6] = 0;        //Color green
		attrib[7] = 0;        //Color blue
		list.add(attrib);     //Add attributes to list item
		
	}
	
	/**
	 * Adds a new line to paint to the PaintList with bounds(x, y, width, height)
	 * and with the desired color
	 * 
	 * @param id     - The identifier of the line (String)
	 * @param x      - The x co-ordinate of the line (Integer)
	 * @param y      - The y co-ordinate of the line (Integer)
	 * @param width  - The width of the line (Integer)
	 * @param height - The height of the line (Integer)
	 * @param color  - The desired color of the line (Color)
	 */
	public void add(String id, int x, int y, int width, int height, Color color) {
		
		int red   = color.getRed();
		int green = color.getGreen();
		int blue  = color.getBlue();
		
		identity.add(id);
		
		attrib = new int[8];
		attrib[0] = 0;        //Shape
		attrib[1] = x;        //x co-ord
		attrib[2] = y;        //y co-ord
		attrib[3] = width;    //Width
		attrib[4] = height;   //Height
		attrib[5] = red;      //Color red
		attrib[6] = green;    //Color green
		attrib[7] = blue;     //Color blue
		list.add(attrib);     //Add attributes to list item
		
	}
	
	/**
	 * Adds a new user-defined shape to paint to the PaintList with bounds(x, y, width, height)
	 * and with the desired color
	 * 
	 * @param id     - The identifier of the desired shape (String)
	 * @param x      - The x co-ordinate of the desired shape (Integer)
	 * @param y      - The y co-ordinate of the desired shape (Integer)
	 * @param width  - The width of the desired shape (Integer)
	 * @param height - The height of the desired shape (Integer)
	 * @param shape  - The desired shape (String) -- List of supported shapes: "rectangle", "oval".
	 *                  To draw only an outline use the following format "rectangle -o" (To simply draw a line use a 
	 *                  method that does not require shape)
	 * @param color  - The desired color of the drawing (Color)
	 */
	public void add(String id, int x, int y, int width, int height, String shape, Color color) {
		
		int shape_no = 0;
		if(shape.equals("rectangle"))    {shape_no = 1;}
		if(shape.equals("rectangle -o")) {shape_no = 2;}
		if(shape.equals("oval"))         {shape_no = 3;}
		if(shape.equals("oval -o"))      {shape_no = 4;}
		
		int red   = color.getRed();
		int green = color.getGreen();
		int blue  = color.getBlue();
		
		identity.add(id);
		
		attrib = new int[8];
		attrib[0] = shape_no; //Shape
		attrib[1] = x;        //x co-ord
		attrib[2] = y;        //y co-ord
		attrib[3] = width;    //Width
		attrib[4] = height;   //Height
		attrib[5] = red;      //Color red
		attrib[6] = green;    //Color green
		attrib[7] = blue;     //Color blue
		list.add(attrib);             //Add new attributes array to list
		
	}
	
	/**
	 * Edit the bounds of a specific item within an already created PaintList.
	 * 
	 * @param id     - The identifier of the item (String)
	 * @param x      - The new x co-ordinate of the item (Integer)
	 * @param y      - The new y co-ordinate of the item (Integer)
	 * @param width  - The new width of the item (Integer)
	 * @param height - The new height of the item (Integer)
	 */
	public void editBounds(String id, int x, int y, int width, int height) {
		
		for(int i=0;i<list.size();i++) {
			if(identity.get(i).equals(id)) {
				
				attrib = new int[8];
				attrib[0] = x;
				attrib[1] = y;
				attrib[2] = width;
				attrib[3] = height;
				attrib[4] = list.get(i)[4];
				attrib[5] = list.get(i)[5];
				attrib[6] = list.get(i)[6];
				attrib[7] = list.get(i)[7];
				list.set(i, attrib);
				
			}
		}
		
	}
	
	/**
	 * Edit the shape of a specific item within an already created PaintList.
	 * 
	 * @param id     - The identifier of the item (String)
	 * @param shape  - The new shape of the item (String)
	 */
	public void editShape(String id, String shape) {
		
		int shape_no = 0;
		if(shape.equals("rectangle"))    {shape_no = 1;}
		if(shape.equals("rectangle -o")) {shape_no = 2;}
		if(shape.equals("oval"))         {shape_no = 3;}
		if(shape.equals("oval -o"))      {shape_no = 4;}
		
		for(int i=0;i<list.size();i++) {
			if(identity.get(i).equals(id)) {
				
				attrib = new int[8];
				attrib[0] = list.get(i)[0];
				attrib[1] = list.get(i)[1];
				attrib[2] = list.get(i)[2];
				attrib[3] = list.get(i)[3];
				attrib[4] = shape_no;
				attrib[5] = list.get(i)[5];
				attrib[6] = list.get(i)[6];
				attrib[7] = list.get(i)[7];
				list.set(i, attrib);
				
			}
		}
		
	}
	
	public int getIdAmount(String id) {
		
		int amount = 0;
		
		for(int i=0;i<list.size();i++) {
			if(identity.get(i).contains(id)) {
				amount++;
			}
		}
		
		return amount;
		
	}
	
	public Color getColor(String id) {
		
		int   red   = 0;
		int   green = 0;
		int   blue  = 0;
		Color color;
		
		for(int i=0;i<list.size();i++) {
			if(identity.get(i).equals(id)) {
				
				red   = list.get(i)[5];
				green = list.get(i)[6];
				blue  = list.get(i)[7];
				
			}
		}
		
		color = new Color(red, green, blue);
		return color;
		
	}
	
	public Color[] getColorArray(String id) {
		
		List<Color> color_list  = new ArrayList<Color>();
		Color[]     color_array;
		
		int   red   = 0;
		int   green = 0;
		int   blue  = 0;
		Color color;
		
		for(int i=0;i<list.size();i++) {
			if(identity.get(i).contains(id)) {
				
				red   = list.get(i)[5];
				green = list.get(i)[6];
				blue  = list.get(i)[7];
				
				color = new Color(red, green, blue);
				color_list.add(color);
				
			}
		}
		
		color_array = color_list.toArray(new Color[color_list.size()]);		
		return color_array;
		
	}
	
	public void setColor(Color color) {
		
		int red   = color.getRed();
		int green = color.getGreen();
		int blue  = color.getBlue();
		
		for(int i=0;i<list.size();i++) {
				
			attrib = new int[8];
			attrib[0] = list.get(i)[0];
			attrib[1] = list.get(i)[1];
			attrib[2] = list.get(i)[2];
			attrib[3] = list.get(i)[3];
			attrib[4] = list.get(i)[4];
			attrib[5] = red;
			attrib[6] = green;
			attrib[7] = blue;
			list.set(i, attrib);
				
		}
		
	}
	
	public void setColor(String id, Color color) {
		
		int red   = color.getRed();
		int green = color.getGreen();
		int blue  = color.getBlue();
		
		for(int i=0;i<list.size();i++) {
			if(identity.get(i).contains(id)) {
			
				attrib = new int[8];
				attrib[0] = list.get(i)[0];
				attrib[1] = list.get(i)[1];
				attrib[2] = list.get(i)[2];
				attrib[3] = list.get(i)[3];
				attrib[4] = list.get(i)[4];
				attrib[5] = red;
				attrib[6] = green;
				attrib[7] = blue;
				list.set(i, attrib);
				
			}				
		}
		
	}

	public void setColor(String[] id, Color color) {
		
		int red   = color.getRed();
		int green = color.getGreen();
		int blue  = color.getBlue();
		int count = 0;
		
		for(int i=0;i<list.size();i++) {
			if(count >= id.length){break;} 
			if(identity.get(i).contains(id[count])) {
			
				attrib = new int[8];
				attrib[0] = list.get(i)[0];
				attrib[1] = list.get(i)[1];
				attrib[2] = list.get(i)[2];
				attrib[3] = list.get(i)[3];
				attrib[4] = list.get(i)[4];
				attrib[5] = red;
				attrib[6] = green;
				attrib[7] = blue;
				
				list.set(i, attrib);
				count++;
				
			}				
		}
		
	}
	
	public void setColorArray(String id, Color[] color) {
		
		int red[]   = new int[color.length];
		int green[] = new int[color.length];
		int blue[]  = new int[color.length];
		int count   = 0;
		
		for(int i=0;i<color.length;i++) {
			
			red[i]   = color[i].getRed();
			green[i] = color[i].getGreen();
			blue[i]  = color[i].getBlue();
			
		}
		
		for(int i=0;i<list.size();i++) {
			if(identity.get(i).contains(id)) {
			
				attrib = new int[8];
				attrib[0] = list.get(i)[0];
				attrib[1] = list.get(i)[1];
				attrib[2] = list.get(i)[2];
				attrib[3] = list.get(i)[3];
				attrib[4] = list.get(i)[4];
				attrib[5] = red[count];
				attrib[6] = green[count];
				attrib[7] = blue[count];
				
				list.set(i, attrib);
				count++;
				
			}				
		}
		
	}
	
	/**
	 * Use this method inside your paint method to display the CPaintList.
	 */
	public void paintList(Graphics g) {
		
		if(list.isEmpty()) {return;} //Exit if no shapes have been added
		
		for(int i=0;i<list.size();i++) {
				
				int shape_no = list.get(i)[0];
				int x        = list.get(i)[1];
				int y        = list.get(i)[2];
				int width    = list.get(i)[3];
				int height   = list.get(i)[4];
				int red      = list.get(i)[5];
				int green    = list.get(i)[6];
				int blue     = list.get(i)[7];
				
				switch(shape_no) {
				
				case 0: //Line
					g.setColor(new Color(red, green, blue));
					g.drawLine(x, y, width, height);
					break;
					
				case 1: //Filled Rectangle
					g.setColor(new Color(red, green, blue));
					g.fillRect(x, y, width, height);
					break;
					
				case 2: //Hollow Rectangle
					g.setColor(new Color(red, green, blue));
					g.drawRect(x, y, width, height);
					break;
					
				case 3: //Filled Oval
					g.setColor(new Color(red, green, blue));
					g.fillOval(x, y, width, height);
					break;
					
				case 4: //Hollow Oval
					g.setColor(new Color(red, green, blue));
					g.drawOval(x, y, width, height);
					break;
					
				default:
					System.out.println("CPaintList hit default");
					break;
				}
				
			}
		
		
	}
	
}
