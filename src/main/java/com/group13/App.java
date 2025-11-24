package com.group13;

import com.group13.Singelton.Game;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Game game = Game.getInstance();
		game.startUp();
        
    }
}
