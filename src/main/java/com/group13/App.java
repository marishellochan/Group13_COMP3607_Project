package com.group13;

import com.group13.UI.LoadDataScreen;
import com.group13.UI.MainFrame;

import java.awt.EventQueue;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
        
    }
}
