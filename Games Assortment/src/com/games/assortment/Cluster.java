package com.games.assortment;

import java.awt.Color;
import java.awt.Font;

public class Cluster {
	
	private int       type    =  0; 
	
	public  final int BYTE    =  1;
	public  final int SHORT   =  2;
	public  final int INT     =  3;
	public  final int LONG    =  4;
	public  final int FLOAT   =  5;
	public  final int DOUBLE  =  6;
	public  final int BOOLEAN =  7;
	public  final int CHAR    =  8;
	public  final int STRING  =  9;
	public  final int COLOR   = 10;
	public  final int FONT    = 11;
	                              
	private byte      _BYTE       ;
	private short     _SHORT      ;
	private int       _INT        ;
	private long      _LONG       ;
	private float     _FLOAT      ;
	private double    _DOUBLE     ;
	private boolean   _BOOLEAN    ;
	private char      _CHAR       ;
	private String    _STRING     ;
	private Color     _COLOR      ;
	private Font      _FONT       ;
	
	public Cluster(byte    Byte)        {_BYTE    = Byte   ;}
	public Cluster(short   Short)       {_SHORT   = Short  ;}
	public Cluster(int     Int)         {_INT     = Int    ;}
	public Cluster(long    Long)        {_LONG    = Long   ;}
	public Cluster(float   Float)       {_FLOAT   = Float  ;}
	public Cluster(double  Double)      {_DOUBLE  = Double ;}
	public Cluster(boolean Boolean)     {_BOOLEAN = Boolean;}
	public Cluster(char    Char)        {_CHAR    = Char   ;}
	public Cluster(String  string)      {_STRING  = string ;}
	public Cluster(Color   color)       {_COLOR   = color  ;}
	public Cluster(Font    font)        {_FONT    = font   ;}
	                                    
	public int     getType()            {return type       ;}
	                                    
	public byte    getByte()            {return _BYTE      ;}
	public short   getShort()           {return _SHORT     ;}
	public int     getInt()             {return _INT       ;}
	public long    getLong()            {return _LONG      ;}
	public float   getFloat()           {return _FLOAT     ;}
	public double  getDouble()          {return _DOUBLE    ;}
	public boolean getBoolean()         {return _BOOLEAN   ;}
	public char    getChar()            {return _CHAR      ;}
	public String  getString()          {return _STRING    ;}
	public Color   getColor()           {return _COLOR     ;}
	public Font    getFont()            {return _FONT     ;}
	
	public void    set(byte    Byte)    {
		resetValues();
		type  = BYTE;
		_BYTE = Byte;
	}
	public void    set(short   Short)   {
		resetValues();
		type   = SHORT;
		_SHORT = Short;
	}
	public void    set(int     Int)     {
		resetValues();
		type = INT;
		_INT = Int;
	}
	public void    set(long    Long)    {
		resetValues();
		type  = LONG;
		_LONG = Long;
	}
	public void    set(float   Float)   {
		resetValues();
		type   = FLOAT;
		_FLOAT = Float;
	}
	public void    set(double  Double)  {
		resetValues();
		type  = DOUBLE;
		_DOUBLE = Double;
	}
	public void    set(boolean Boolean) {
		resetValues();
		type     = BOOLEAN;
		_BOOLEAN = Boolean;
	}
	public void    set(char    Char)    {
		resetValues();
		type  = CHAR;
		_CHAR = Char;
	}
	public void    set(String  string)  {
		resetValues();
		type  = STRING;
		_STRING = string;
	}
	public void    set(Color color)     {
		resetValues();
		type   = COLOR;
		_COLOR = color;
	}
	public void    set(Font font)       {
		resetValues();
		type   = FONT;
		_FONT = font;
	}	
	
	public void    resetValues()        {
		
		_BYTE    = 0;
		_SHORT   = 0;
		_INT     = 0;
		_LONG    = 0;
		_FLOAT   = 0;
		_DOUBLE  = 0;
		_BOOLEAN = false;
		_CHAR    = 0;
		_STRING  = "";
		_COLOR   = new Color(0, 0, 0);
		_FONT    = new Font("", 0, 0);
		
	}
	
}
