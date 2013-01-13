package gomoku.player;

import gomoku.*;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
    
    public final int MAXDEPTH;
    
    public Bot(){
        super();
        System.out.println("Alpha-beta bot created!");
        MAXDEPTH = 3;
        System.out.printf("Setting MAXDEPTH to %d\n", MAXDEPTH);
    }
    
    
    @Override
    public void run(){
    
        while(!Thread.interrupted()){
            ab = new AlphaBeta(new Node(board, board.lastMove(), board.get(board.lastMove())), board.get(board.lastMove()));
            for(int i=2; i<=MAXDEPTH; ++i)  
                position = ab.algorithm(i) ;
           
            System.out.print("Finished calculating, yielding cpu..");
            Gomoku.game.playerDone();
        }
    }
    
    
    /**
     * Transforms GomokuBoard which is obsolete for search-like algorithms into
     * a Map of Points and GomokuBoardState enums, that are effectively players
     * @return mapping of Points to Players
     */
    private Map<Point,GomokuBoardState> transformBoard(){
        Map<Point,GomokuBoardState> trBoard = new HashMap<>();
        for(Point p : board.with(GomokuBoardState.A))
            trBoard.put(p, GomokuBoardState.A);
        for(Point p : board.with(GomokuBoardState.B))
            trBoard.put(p, GomokuBoardState.B);
        
        return trBoard;
    }
}
