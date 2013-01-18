package gomoku.player;

import gomoku.*;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Implementation of AI bot using alpha-beta algorithm.
 * 
 * @see PlayerInterface
 * @author asiron
 */
public class Bot extends AIPlayer{
    
    /**
     * AlphaBeta optimizer
     */
    public AlphaBeta ab;
    
    /**
     * Depth upto which algorithm will run
     */
    public final int MAXDEPTH;
    
    public Bot(){
        super();
        System.out.println("Alpha-beta bot created!");
        MAXDEPTH = 4;
        System.out.printf("Setting MAXDEPTH to %d\n", MAXDEPTH);
    }
    
    
    @Override
    public void run(){
    
        while(!Thread.interrupted()){
            
            if(board.occupiedFields().isEmpty()){
                position = new Point(rules.getFirstMoveRectangle().x+1,rules.getFirstMoveRectangle().y+1);
                System.out.println("Finished calculating, yielding cpu..");
                Gomoku.game.playerDone();  
            }else{
                if(ab != null){
                    try {
                        ab.delete();
                    } catch (Throwable ex) {
                        Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                ab = new AlphaBeta(new Node(new GomokuBoard(board), new Point(board.lastMove()), board.get(board.lastMove())), 
                    board.get(board.lastMove()) == GomokuBoardState.A ? GomokuBoardState.B : GomokuBoardState.A, board.get(board.lastMove()), rules.getInRowToWin());
                System.out.println("Starting Iterative Deepening of Alpha-Beta pruning");
                for(int i=2; i<=MAXDEPTH; ++i) {
                    position = ab.algorithm(i) ;
                    System.out.printf("Iterative Deepening finished! - depth %d, Position found (%d,%d)\n", i, position.x, position.y);            
                }

                System.out.println("Finished calculating, yielding cpu..");
                Gomoku.game.playerDone();
            }
        }
    }
}
