package com.games.assortment;

import java.util.List;
import java.util.ArrayList;

import javax.swing.JButton;

public class CButton extends JButton {
	
	public List<Integer> intVal = new ArrayList<Integer>();
	public List<String>  intVar = new ArrayList<String>();
	
	public void setInt(String var, int val) {
		intVar.add(var);
		intVal.add(val);
	}
	
	public int getInt(String var) {
		int valNo = intVar.indexOf(var);
		int val   = intVal.get(valNo);
		return val;
	}

}
