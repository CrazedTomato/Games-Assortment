package com.games.assortment;

import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
  
public class LimitField extends JTextField  
{  
    // Maximum number of characters allowed in this field  
    private int maximum ;  
    /** 
      *  Constructs a new empty LimitField with the specified number of columns and 
      *  the specified maximum allowable characters. 
      * 
      *  @param columns - The number of columns to use to calculate the preferred width. 
      *                   If columns is set to zero, the preferred width will be whatever 
      *                   naturally results from the component implementation. 
      *  @param max - The maximum number of characters allowed in this field. 
      */  
    public LimitField( int columns, int max )  
    {  
        super( columns );  
        maximum = max ;  
    }  
    /** 
      *  Constructs a new LimitField initialized with the specified text and columns and 
      *  the specified maximum allowable characters. 
      * 
      *  @param text - The text to be displayed, or null. 
      *  @param columns - The number of columns to use to calculate the preferred width. 
      *                   If columns is set to zero, the preferred width will be whatever 
      *                   naturally results from the component implementation. 
      *  @param max - The maximum number of characters allowed in this field. 
      */  
    public LimitField( String text, int columns, int max )  
    {  
        super( text, columns );  
        maximum = max;  
    }  
    /** 
      *  Method creates the default implementation of the model to be used at construction 
      *  if one isn't explicitly given. An instance of LimitDocument is returned. 
      * 
      *  @return The default model implementation. 
      */  
    protected Document createDefaultModel()  
    {  
        return new LimitDocument();  
    }  
    /** 
      *  Class LimitDocument is used to customize a JTextField to allow only a certain number of. 
      *  characters.  It will work even if text is pasted into the field from the clipboard 
      *  or if it is altered via programmatic changes. 
      */  
    private class LimitDocument extends PlainDocument  
    {  
        public void insertString(int offs, String str, AttributeSet a)  
            throws BadLocationException  
        {  
            StringBuffer buffer = new StringBuffer( getText( 0, getLength() ) );  
			if ( ( buffer.length() + str.length() ) <= maximum )  
			{  
			super.insertString(offs, str, a);  
			}  
			else  
			{  
			Toolkit.getDefaultToolkit().beep();  
			}  
        }  
    }  
}  