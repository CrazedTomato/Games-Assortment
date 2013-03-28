package com.games.assortment;

import java.awt.event.ActionListener;

import javax.swing.Timer;

public class CTimer extends Timer {

	public CTimer(int delay, Cluster   var, ActionListener listener)   {super(delay, listener);}
	public CTimer(int delay, Cluster[] var, ActionListener listener) {super(delay, listener);}

}
