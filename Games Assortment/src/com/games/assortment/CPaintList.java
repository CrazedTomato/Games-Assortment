package com.games.assortment;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

public class CPaintList extends JComponent {
	
	public  final static int HOLLOW = 0;
	public  final static int FILLED = 1;
	
	private final int        LINE  = 0;
	private final int        RECT  = 1;
	                         
	private List<int[]>      bounds         = new ArrayList<int[]>();
	private List<Color[]>    colors         = new ArrayList<Color[]>();
	private List<String>     texts          = new ArrayList<String>();
	private List<Font>       fonts          = new ArrayList<Font>();
	private List<String>     identity       = new ArrayList<String>();
	private int[]            attrib_bounds;
	private Color[]          attrib_colors;
	                         
	private Font             default_font   = new Font("Arial", Font.PLAIN, 12);
	
	/**
	 * Adds a new line to paint to the PaintList with bounds(x, y, width, height) and Color black
	 * 
	 * @param id     - The identifier of the line (String)
	 * @param x      - The x1 co-ordinate of the line (Integer)
	 * @param y      - The y1 co-ordinate of the line (Integer)
	 * @param x2     - The x2 co-ordinate of the line (Integer)
	 * @param y2     - The y2 co-ordinate of the line (Integer)
	 */
	public void addLine(String id, int x1, int y1, int x2, int y2) {
		
		identity.add(id);
		
		attrib_bounds = new int[5];
		attrib_bounds[0] = LINE;        //Shape ID
		attrib_bounds[1] = x1;          //X co-ord 1
		attrib_bounds[2] = y1;          //Y co-ord 1
		attrib_bounds[3] = x2;          //X co-ord 2
		attrib_bounds[4] = y2;          //Y co-ord 2
		attrib_bounds[5] = 1;           //Filled or Hollow
		bounds.add(attrib_bounds);
		
		attrib_colors = new Color[2];
		attrib_colors[0] = Color.BLACK; //Color
		attrib_colors[1] = Color.WHITE; //Textcolor
		colors.add(attrib_colors);
		
		texts.add("");                  //Text
		fonts.add(default_font);        //Font
		
	}
	
	/**
	 * Adds a new line to paint to the PaintList with bounds(x, y, width, height) and Color color
	 * 
	 * @param id     - The identifier of the line (String)
	 * @param x      - The x1 co-ordinate of the line (Integer)
	 * @param y      - The y1 co-ordinate of the line (Integer)
	 * @param x2     - The x2 co-ordinate of the line (Integer)
	 * @param y2     - The y2 co-ordinate of the line (Integer)
	 * @param color  - The color of the line (Color)
	 */
	public void addLine(String id, int x1, int y1, int x2, int y2, Color color) {
		
		identity.add(id);
		
		attrib_bounds = new int[5];
		attrib_bounds[0] = LINE;        //Shape ID
		attrib_bounds[1] = x1;          //X co-ord 1
		attrib_bounds[2] = y1;          //Y co-ord 1
		attrib_bounds[3] = x2;          //X co-ord 2
		attrib_bounds[4] = y2;          //Y co-ord 2
		attrib_bounds[5] = 1;           //Filled or Hollow
		bounds.add(attrib_bounds);
		
		attrib_colors = new Color[2];
		attrib_colors[0] = color;       //Color
		attrib_colors[1] = Color.WHITE; //Textcolor
		colors.add(attrib_colors);
		
		texts.add("");                  //Text
		fonts.add(default_font);        //Font
		
	}	
	
	/**
	 * Adds a new user-defined shape to paint to the PaintList with bounds(x, y, width, height)
	 * 
	 * @param id     - The identifier of the desired shape (String) 
	 * @param x      - The x co-ordinate of the desired shape (Integer)
	 * @param y      - The y co-ordinate of the desired shape (Integer)
	 * @param width  - The width of the desired shape (Integer)
	 * @param height - The height of the desired shape (Integer)
	 * @param fill   - Whether the rectangle should be filled or just an outline (Integer - CPaintList.FILLED or CPaintList.Filled)
	 */
	public void addRectangle(String id, int x, int y, int width, int height, int fill) {
		
		identity.add(id);
		
		attrib_bounds = new int[6];
		attrib_bounds[0] = RECT;        //Shape ID
		attrib_bounds[1] = x;           //X co-ord
		attrib_bounds[2] = y;           //Y co-ord
		attrib_bounds[3] = width;       //Width
		attrib_bounds[4] = height;      //Height
		attrib_bounds[5] = fill;        //Fill or Hollow
		bounds.add(attrib_bounds);
		
		attrib_colors = new Color[2];
		attrib_colors[0] = Color.BLACK; //Color
		attrib_colors[1] = Color.WHITE; //Textcolor
		colors.add(attrib_colors);
		
		texts.add("");                  //Text
		fonts.add(default_font);        //Font
		
	}
	
	/**
	 * Adds a new user-defined shape to paint to the PaintList with bounds(x, y, width, height)
	 * 
	 * @param id     - The identifier of the desired shape (String) 
	 * @param x      - The x co-ordinate of the desired shape (Integer)
	 * @param y      - The y co-ordinate of the desired shape (Integer)
	 * @param width  - The width of the desired shape (Integer)
	 * @param height - The height of the desired shape (Integer)
	 * @param fill   - Whether the rectangle should be filled or just an outline (Integer - CPaintList.FILLED or CPaintList.Filled)
	 * @param color  - The color of the rectangle (Color)
	 */
	public void addRectangle(String id, int x, int y, int width, int height, int fill, Color color) {
		
		identity.add(id);
		
		attrib_bounds = new int[6];
		attrib_bounds[0] = RECT;        //Shape ID
		attrib_bounds[1] = x;           //X co-ord
		attrib_bounds[2] = y;           //Y co-ord
		attrib_bounds[3] = width;       //Width
		attrib_bounds[4] = height;      //Height
		attrib_bounds[5] = fill;        //Fill or Hollow
		bounds.add(attrib_bounds);
		
		attrib_colors = new Color[2];
		attrib_colors[0] = color;       //Color
		attrib_colors[1] = Color.WHITE; //Textcolor
		colors.add(attrib_colors);
		
		texts.add("");                  //Text
		fonts.add(default_font);        //Font
		
	}
	
	/**
	 * Adds a new user-defined shape to paint to the PaintList with bounds(x, y, width, height)
	 * 
	 * @param id       - The identifier of the desired shape (String) 
	 * @param x        - The x co-ordinate of the desired shape (Integer)
	 * @param y        - The y co-ordinate of the desired shape (Integer)
	 * @param width    - The width of the desired shape (Integer)
	 * @param height   - The height of the desired shape (Integer)
	 * @param fill     - Whether the rectangle should be filled or just an outline (Integer - CPaintList.FILLED or CPaintList.Filled)
	 * @param text     - The text display in the center of the rectangle (String)
	 * @param textsize - The size of the text (Integer)
	 */
	public void addRectangle(String id, int x, int y, int width, int height, int fill, String text, Font font) {
		
		identity.add(id);
		
		attrib_bounds = new int[6];
		attrib_bounds[0] = RECT;        //Shape ID
		attrib_bounds[1] = x;           //X co-ord
		attrib_bounds[2] = y;           //Y co-ord
		attrib_bounds[3] = width;       //Width
		attrib_bounds[4] = height;      //Height
		attrib_bounds[5] = fill;        //Fill or Hollow
		bounds.add(attrib_bounds);
		
		attrib_colors = new Color[2];
		attrib_colors[0] = Color.BLACK; //Color
		attrib_colors[1] = Color.WHITE; //Textcolor
		colors.add(attrib_colors);
		
		texts.add(text);                //Text
		fonts.add(font);                //Font
		
	}
	
	/**
	 * Adds a new user-defined shape to paint to the PaintList with bounds(x, y, width, height)
	 * 
	 * @param id        - The identifier of the desired shape (String) 
	 * @param x         - The x co-ordinate of the desired shape (Integer)
	 * @param y         - The y co-ordinate of the desired shape (Integer)
	 * @param width     - The width of the desired shape (Integer)
	 * @param height    - The height of the desired shape (Integer)
	 * @param fill      - Whether the rectangle should be filled or just an outline (Integer - CPaintList.FILLED or CPaintList.Filled)
	 * @param text      - The text display in the center of the rectangle (String)
	 * @param textsize  - The size of the text (Integer)
	 * @param textcolor - The color of the text (Color)
	 */
	public void addRectangle(String id, int x, int y, int width, int height, int fill, String text, Font font, Color textcolor) {
		
		identity.add(id);
		
		attrib_bounds = new int[6];
		attrib_bounds[0] = RECT;        //Shape ID
		attrib_bounds[1] = x;           //X co-ord
		attrib_bounds[2] = y;           //Y co-ord
		attrib_bounds[3] = width;       //Width
		attrib_bounds[4] = height;      //Height
		attrib_bounds[5] = fill;        //Fill or Hollow
		bounds.add(attrib_bounds);
		
		attrib_colors = new Color[2];
		attrib_colors[0] = Color.BLACK; //Color
		attrib_colors[1] = textcolor;   //Textcolor
		colors.add(attrib_colors);
		
		texts.add(text);                //Text
		fonts.add(font);                //Font
		
	}
	
	/**
	 * Adds a new user-defined shape to paint to the PaintList with bounds(x, y, width, height)
	 * 
	 * @param id       - The identifier of the desired shape (String) 
	 * @param x        - The x co-ordinate of the desired shape (Integer)
	 * @param y        - The y co-ordinate of the desired shape (Integer)
	 * @param width    - The width of the desired shape (Integer)
	 * @param height   - The height of the desired shape (Integer)
	 * @param fill     - Whether the rectangle should be filled or just an outline (Integer - CPaintList.FILLED or CPaintList.Filled)
	 * @param color    - The color of the rectangle (Color)
	 * @param text     - The text display in the center of the rectangle (String)
	 * @param textsize - The size of the text (Integer)
	 */
	public void addRectangle(String id, int x, int y, int width, int height, int fill, Color color, String text, Font font) {
		
		identity.add(id);
		
		attrib_bounds = new int[6];
		attrib_bounds[0] = RECT;        //Shape ID
		attrib_bounds[1] = x;           //X co-ord
		attrib_bounds[2] = y;           //Y co-ord
		attrib_bounds[3] = width;       //Width
		attrib_bounds[4] = height;      //Height
		attrib_bounds[5] = fill;        //Fill or Hollow
		bounds.add(attrib_bounds);
		
		attrib_colors = new Color[2];
		attrib_colors[0] = color;       //Color
		attrib_colors[1] = Color.WHITE; //Textcolor
		colors.add(attrib_colors);
		
		texts.add(text);                //Text
		fonts.add(font);                //Font
		
	}
	
	/**
	 * Adds a new user-defined shape to paint to the PaintList with bounds(x, y, width, height)
	 * 
	 * @param id       - The identifier of the desired shape (String) 
	 * @param x        - The x co-ordinate of the desired shape (Integer)
	 * @param y        - The y co-ordinate of the desired shape (Integer)
	 * @param width    - The width of the desired shape (Integer)
	 * @param height   - The height of the desired shape (Integer)
	 * @param fill     - Whether the rectangle should be filled or just an outline (Integer - CPaintList.FILLED or CPaintList.Filled)
	 * @param color    - The color of the rectangle (Color)
	 * @param text     - The text display in the center of the rectangle (String)
	 * @param textsize - The size of the text (Integer)
	 * @param textcolor - The color of the text (Color)
	 */
	public void addRectangle(String id, int x, int y, int width, int height, int fill, Color color, String text, Font font, Color textcolor) {
		
		identity.add(id);
		
		attrib_bounds = new int[6];
		attrib_bounds[0] = RECT;      //Shape ID
		attrib_bounds[1] = x;         //X co-ord
		attrib_bounds[2] = y;         //Y co-ord
		attrib_bounds[3] = width;     //Width
		attrib_bounds[4] = height;    //Height
		attrib_bounds[5] = fill;      //Fill or Hollow
		bounds.add(attrib_bounds);
		
		attrib_colors = new Color[2];
		attrib_colors[0] = color;     //Color
		attrib_colors[1] = textcolor; //Textcolor
		colors.add(attrib_colors);
		
		texts.add(text);              //Text
		fonts.add(font);              //Font
		
	}
	
	
	
	/**
	 * Edit the bounds of a specific item within an already created PaintList.
	 * To keep something the same just use the value -1
	 * 
	 * @param id     - The identifier of the item (String)
	 * @param x      - The new x co-ordinate of the item (Integer)
	 * @param y      - The new y co-ordinate of the item (Integer)
	 * @param width  - The new width of the item (Integer)
	 * @param height - The new height of the item (Integer)
	 * @param fill   - Whether the rectangle should be filled or hollow (Integer)
	 */
	public void setRectangle(String id, int x, int y, int width, int height, int fill) {
		
		int newX      = -1;
		int newY      = -1;
		int newWidth  = -1;
		int newHeight = -1;
		int newFill   = -1;
		
		for(int i=0;i<identity.size();i++) {
			if(identity.get(i).contains(id)) {
				
				if(x == -1)      {newX      = bounds.get(i)[1];} else {newX      = x     ;}
				if(y == -1)      {newY      = bounds.get(i)[2];} else {newY      = y     ;}
				if(width == -1)  {newWidth  = bounds.get(i)[3];} else {newWidth  = width ;}
				if(height == -1) {newHeight = bounds.get(i)[4];} else {newHeight = height;}
				if(fill == -1)   {newFill   = bounds.get(i)[5];} else {newFill   = fill  ;}
				
				attrib_bounds = new int[6];
				attrib_bounds[0] = RECT;        //Shape ID
				attrib_bounds[1] = newX;        //X co-ord
				attrib_bounds[2] = newY;        //Y co-ord
				attrib_bounds[3] = newWidth;    //Width
				attrib_bounds[4] = newHeight;   //Height
				attrib_bounds[5] = newFill;     //Fill or Hollow
				bounds.set(i, attrib_bounds);
				
			}
		}
		
	}

	/**
	 * Edit the bounds of a specific item within an already created PaintList.
	 * To keep something the same just use the value -1 - To leave color the same just leave it out
	 * 
	 * @param id     - The identifier of the item (String)
	 * @param x      - The new x co-ordinate of the item (Integer)
	 * @param y      - The new y co-ordinate of the item (Integer)
	 * @param width  - The new width of the item (Integer)
	 * @param height - The new height of the item (Integer)
	 * @param fill   - Whether the rectangle should be filled or hollow (Integer)
	 * @param color  - The new color of the rectangle
	 */
	public void setRectangle(String id, int x, int y, int width, int height, int fill, Color color) {
		
		int   newX      = -1;
		int   newY      = -1;
		int   newWidth  = -1;
		int   newHeight = -1;
		int   newFill   = -1;
		
		for(int i=0;i<identity.size();i++) {
			if(identity.get(i).contains(id)) {
				
				if(x      == -1) {newX      = bounds.get(i)[1];} else {newX      = x     ;}
				if(y      == -1) {newY      = bounds.get(i)[2];} else {newY      = y     ;}
				if(width  == -1) {newWidth  = bounds.get(i)[3];} else {newWidth  = width ;}
				if(height == -1) {newHeight = bounds.get(i)[4];} else {newHeight = height;}
				if(fill   == -1) {newFill   = bounds.get(i)[5];} else {newFill   = fill  ;}
				
				attrib_bounds = new int[6];
				attrib_bounds[0] = RECT;        //Shape ID
				attrib_bounds[1] = newX;        //X co-ord
				attrib_bounds[2] = newY;        //Y co-ord
				attrib_bounds[3] = newWidth;    //Width
				attrib_bounds[4] = newHeight;   //Height
				attrib_bounds[5] = newFill;     //Fill or Hollow
				bounds.set(i, attrib_bounds);
				
				attrib_colors = new Color[2];
				attrib_colors[0] = color;
				attrib_colors[1] = colors.get(i)[1];
				colors.set(i, attrib_colors);
				
			}
		}
		
	}
	
	/**
	 * Edit the shape of a specific item within an already created PaintList.
	 * 
	 * @param id     - The identifier of the item (String)
	 * @param shape  - The new shape of the item (Integer - CPaintList.LINE etc.)
	 */
	public void setShape(String id, int type) {
		
		for(int i=0;i<identity.size();i++) {
			if(identity.get(i).contains(id)) {
				
				attrib_bounds = new int[6];
				attrib_bounds[0] = type;             //Shape ID
				attrib_bounds[1] = bounds.get(i)[1]; //X co-ord
				attrib_bounds[2] = bounds.get(i)[2]; //Y co-ord
				attrib_bounds[3] = bounds.get(i)[3]; //Width
				attrib_bounds[4] = bounds.get(i)[4]; //Height
				attrib_bounds[5] = bounds.get(i)[5]; //Fill or Hollow
				bounds.set(i, attrib_bounds);
				
			}
		}
		
	}
	
	public void setColor(Color color) {
		
		for(int i=0;i<identity.size();i++) {
				
			attrib_colors = new Color[2];
			attrib_colors[0] = color;            //Color
			attrib_colors[1] = colors.get(i)[1]; //Textcolor
			colors.set(i, attrib_colors);
				
		}
		
	}
	
	public void setColor(String id, Color color) {
		
		for(int i=0;i<identity.size();i++) {
			if(identity.get(i).contains(id)) {
				
				attrib_colors = new Color[2];
				attrib_colors[0] = color;            //Color
				attrib_colors[1] = colors.get(i)[1]; //Textcolor
				colors.set(i, attrib_colors);
				
			}				
		}
		
	}

	public void setColor(String[] id, Color color) {
		
		int count = 0;
		
		for(int i=0;i<identity.size();i++) {
			if(count >= id.length) {break;} 
			if(identity.get(i).contains(id[count])) {
			
				attrib_colors = new Color[2];
				attrib_colors[0] = color;            //Color
				attrib_colors[1] = colors.get(i)[1]; //Textcolor
				colors.set(i, attrib_colors);
				count++;
				
			}				
		}
		
	}
	
	public void setColorArray(String id, Color[] color) {
		
		int count = 0;
		
		for(int i=0;i<identity.size();i++) {
			if(identity.get(i).contains(id)) {
			
				attrib_colors = new Color[2];
				attrib_colors[0] = color[count];     //Color
				attrib_colors[1] = colors.get(i)[1]; //Textcolor
				colors.set(i, attrib_colors);
				count++;
				
			}				
		}
		
	}
	
	public void setText(String id, String text) {
		
		for(int i=0;i<identity.size();i++) {
			if(identity.get(i).contains(id)) {
				
				texts.set(i, text); //Text
				
			}				
		}
		
	}

	public void setText(String id, String text, Font font) {
		
		for(int i=0;i<identity.size();i++) {
			if(identity.get(i).contains(id)) {
				
				texts.set(i, text); //Text
				fonts.set(i, font); //Font
				
			}				
		}
		
	}

	public void setText(String id, String text, Color textcolor) {
		
		for(int i=0;i<identity.size();i++) {
			if(identity.get(i).contains(id)) {
				
				attrib_colors = new Color[2];
				attrib_colors[0] = colors.get(i)[0]; //Color
				attrib_colors[1] = textcolor;        //Textcolor
				colors.set(i, attrib_colors);
				
				texts.set(i, text);                  //Text
				
			}				
		}
		
	}
	
	public void setText(String id, String text, Font font, Color textcolor) {
		
		for(int i=0;i<identity.size();i++) {
			if(identity.get(i).contains(id)) {
				
				attrib_colors = new Color[2];
				attrib_colors[0] = colors.get(i)[0]; //Color
				attrib_colors[1] = textcolor;        //Textcolor
				colors.set(i, attrib_colors);
				
				texts.set(i, text);                  //Text
				fonts.set(i, font);                  //Font
				
			}				
		}
		
	}

	
	
	public int getIdAmount(String id) {
		
		int amount = 0;
		
		for(int i=0;i<identity.size();i++) {
			if(identity.get(i).contains(id)) {
				amount++;
			}
		}
		
		return amount;
		
	}
	
	public Color getColor(String id) {
		
		Color color = Color.BLACK;
		
		for(int i=0;i<identity.size();i++) {
			if(identity.get(i).equals(id)) {
				color = colors.get(i)[0];
			}
		}
		
		return color;
		
	}
	
	public Color[] getColorArray(String id) {
		
		List<Color> color_list  = new ArrayList<Color>();
		Color[]     color_array;
		Color       color;
		
		for(int i=0;i<identity.size();i++) {
			if(identity.get(i).contains(id)) {
								
				color = colors.get(i)[0];
				color_list.add(color);
				
			}
		}
		
		color_array = color_list.toArray(new Color[color_list.size()]);		
		return color_array;
		
	}

	
	public boolean compareColor(String id, Color color) {
		
		int[] id_attrib    = new int[3];
		int[] color_attrib = new int[3];
		
		color_attrib[0] = color.getRed();
		color_attrib[1] = color.getGreen();
		color_attrib[2] = color.getBlue();
		
		for(int i=0;i<identity.size();i++) {
			if(identity.get(i).equals(id)) {
				id_attrib[0] = colors.get(i)[0].getRed();
				id_attrib[1] = colors.get(i)[0].getGreen();
				id_attrib[2] = colors.get(i)[0].getBlue();
			}
		}
			
		if(color_attrib[0] == id_attrib[0] && color_attrib[1] == id_attrib[1] && color_attrib[2] == id_attrib[2]) {
			return true;
		} else return false;
		
	}
	
	/**
	 * Use this method inside your paint method to display the CPaintList.
	 */
	public void paintList(Graphics g) {
		
		if(identity.isEmpty()) {return;} //Exit if no shapes have been added
		
		for(int i=0;i<identity.size();i++) {
				
				int    type      = bounds.get(i)[0];
				int    x1        = bounds.get(i)[1];
				int    y1        = bounds.get(i)[2];
				int    x2        = bounds.get(i)[3];
				int    y2        = bounds.get(i)[4];
				int    fill      = bounds.get(i)[5];
				
				Color  color     = colors.get(i)[0];
				Color  textcolor = colors.get(i)[1];
				
				String text      = texts.get(i);
				Font   font      = fonts.get(i);
				
				
				switch(type) {
				
				case 0: //Line
					g.setColor(color);
					g.drawLine(x1, y1, x2, y2);
					break;
					
				case 1: //Filled Rectangle
					
					//Rectangle
					g.setColor(color);
					if(fill == 0) {g.drawRect(x1, y1, x2, y2);}
					else          {g.fillRect(x1, y1, x2, y2);}
					
					//Text
					if(!text.equals("")) {
						Graphics2D g2 = (Graphics2D) g;
						g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						FontRenderContext frc = g2.getFontRenderContext();
						LineMetrics       lm  = font.getLineMetrics(text, frc);
						
						float width   = (float) font.getStringBounds(text, frc).getWidth();
						float height  = (float) lm.getAscent() + lm.getDescent();
						float stringX = x1 + (x2-width)/2;
						float stringY = y1 + (y2+height)/2 - lm.getDescent();
						
						g2.setColor(textcolor);
						g2.setFont(font);
						g2.drawString(text, stringX-1, stringY);
					}
					
					break;
					
				default:
					System.out.println("CPaintList hit default");
					break;
				}
				
			}
		
		
	}
	
}
