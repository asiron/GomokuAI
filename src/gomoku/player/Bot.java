package gomoku.player;

import gomoku.*;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

/**
 * Implementation of AI bot using alpha-beta algorithm.
 * 
 * @see PlayerInterface
 * @author asiron
 */
public class Bot extends AIPlayer{
    
    Random random;
    
    public Bot(){
        super();
        System.out.println("Alpha-beta bot created!");
        
        random = new Random();
    }
 
    
    @Override
    public void run(){
    
        while(!Thread.interrupted()){
        
            
            
            position.x = random.nextInt(19);
            position.y = random.nextInt(19);
            Gomoku.game.playerDone();
        }
    }
}
