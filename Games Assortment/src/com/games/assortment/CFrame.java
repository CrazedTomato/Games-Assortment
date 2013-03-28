package com.games.assortment;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class CFrame extends JFrame {
	
	GraphicsDevice device;
	DisplayMode    new_display;
	DisplayMode    old_display;

	public CFrame(String string) {
		new JFrame(string);
		
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		
		device      = env.getDefaultScreenDevice();
		new_display = new DisplayMode(800, 600, 16, DisplayMode.REFRESH_RATE_UNKNOWN);
		old_display = device.getDisplayMode();
		
	}

	public void centerInScreen() {
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int) dim.getWidth()/2-this.getSize().width/2, (int) dim.getHeight()/2-this.getSize().height/2);
		
	}
	
	public void setFullscreen(boolean fill) {
		
		new_display = getBestDisplayMode();
		
		if(device.isFullScreenSupported() && new_display != null) {
			try {
				device.setFullScreenWindow(this);
				//this.setUndecorated(true);
				this.setIgnoreRepaint(true);
				this.setResizable(false);
				if(device.isDisplayChangeSupported()) {device.setDisplayMode(new_display);}
				
			} finally {
				if(!fill) {
					device.setFullScreenWindow(null);
					//this.setUndecorated(false);
					this.setIgnoreRepaint(false);
					this.setResizable(true);
					if(device.isDisplayChangeSupported()) {device.setDisplayMode(old_display);}
				}
			}
		}
		
	}
	
	private DisplayMode getBestDisplayMode() {
		
		DisplayMode[] program_displays = { new DisplayMode(640 , 480, DisplayMode.BIT_DEPTH_MULTI, DisplayMode.REFRESH_RATE_UNKNOWN),
				                           new DisplayMode(800 , 600, DisplayMode.BIT_DEPTH_MULTI, DisplayMode.REFRESH_RATE_UNKNOWN),
				                           new DisplayMode(1024, 768, DisplayMode.BIT_DEPTH_MULTI, DisplayMode.REFRESH_RATE_UNKNOWN),
				                           new DisplayMode(1280, 960, DisplayMode.BIT_DEPTH_MULTI, DisplayMode.REFRESH_RATE_UNKNOWN),
		                                 };
		
		DisplayMode[] device_displays  = device.getDisplayModes();
		DisplayMode   display          = null;
		
		boolean[]     displays         = {false, false, false, false};
		double        dimension_gap    = 99999;
		
		for(int i=0;i<program_displays.length;i++) { //Loop for each standard program display
			for(int n=0;n<device_displays.length;n++) { //Loop for each compatible device display
				
				if(displayModesMatch(device_displays[n], program_displays[i])) { //If the device display is also a standard program display
					displays[i] = true;
					program_displays[i] = device_displays[n];
				}
				
				else {
					if(device_displays[n].getWidth()/4 == device_displays[n].getHeight()/3) { //Check if the device display is in ration 4:3
						for(int t=0;t<program_displays.length;t++) { //Loop for each standard program display
							if(Math.sqrt(Math.pow((double) device_displays[n].getWidth() - program_displays[t].getWidth(), 2.0)) <= dimension_gap) { //Check if the dimension gap < than before
								display = device_displays[n];
								dimension_gap = Math.sqrt(Math.pow((double) device_displays[n].getWidth() - program_displays[t].getWidth(), 2.0));
							}
						}
					}
				}
				
			}
		}
		
		for(int i=0;i<4;i++) {System.out.println(displays[i]);}
		
		for(int i=program_displays.length-1;i>-1;i--) {
			if(displays[i]) {
				System.out.println(program_displays[i].getWidth()+" "+program_displays[i].getHeight());
				return program_displays[i];}
		}
		
		System.out.println(display.getWidth()+", "+display.getHeight());
		return display;
	}
	
	private boolean displayModesMatch(DisplayMode m1, DisplayMode m2) {
		
		if(m1.getWidth() != m2.getWidth() || m1.getHeight() != m2.getHeight()) {return false;}
		
		if(m1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI &&
		   m2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI &&
		   m1.getBitDepth() != m2.getBitDepth()) {return false;}
		
		if(m1.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN &&
		   m2.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN &&
		   m1.getRefreshRate() != m2.getRefreshRate()) {return false;}
		
		return true;
	}
	
}
