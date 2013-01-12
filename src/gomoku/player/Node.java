/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gomoku.player;

import gomoku.GomokuBoardState;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author asiron
 */
public class Node {
    
    
    /**
     * move that was made in this node
     */
    private Point lastMove;
    
    /**
     * Map of children, due to undefined number of 
     * children at given point in time
     */
    public Map<Integer,Node> children;
    
    
    /**
     * List of all points that are adjacent to current gamestate points
     */
    private List<Point> adjacencyList;
    
    /**
     * Map of current Points on board and corresponding owners
     */
    private Map<Point,GomokuBoardState> boardState;
    
    /**
     * Creates new node and initializes its values
     * @param oldBoardState is a board from previous node
     * @param newMove is a Point selected as a move for this node
     * @param player is a player type ( min or max ) selected for this node
     * @param makePlies specifies number of plies to make with one invocation of this constructor
     */
    public Node(Map<Point,GomokuBoardState> oldBoardState, Point newMove, GomokuBoardState player, int makePlies){
        
        //Initialize lastMove - subject to change
        lastMove = new Point(newMove);
        
        //Initialize list for adjacent points to currently occupied points
        adjacencyList = new ArrayList<>(); 
        
        //Copies old list of occupied points and adds new point that was selected
        //for this node
        boardState = new HashMap<>(oldBoardState);
        boardState.put(newMove, player);      
        
        // Depending on how many plies we want to make, we initialize adjacencyList
        // and children's Map, then we make list of all possible moves considering
        // only adjacent points and we create children map according to that list
        if(makePlies < 1){
            
            //Get list of only adjacent points
            for(Map.Entry<Point,GomokuBoardState> entry : boardState.entrySet()){
                for(int x=-1; x<=1; ++x){
                    for(int y=-1; y<=1; ++y){
                        if(!boardState.containsKey(new Point(entry.getKey().x+x, entry.getKey().y+y))) {
                            adjacencyList.add(new Point(entry.getKey().x+x, entry.getKey().y+y));
                        }
                    }
                }
            }
            
            children = new HashMap<>();
            // Creates children based on adjacencyList
            for(Point p : adjacencyList){
                int i=0;
                children.put(i, new Node(boardState, p, player == GomokuBoardState.A ? GomokuBoardState.B : GomokuBoardState.A, makePlies-1));
                i++;
            }
        }
    } 
    
    
    /**
     * Evaluation function for leaves 
     * 
     * @return integer which is an evaluation of leaves 
     */
    public Integer evaluate(){
        return 5;
    }
    
    /**
     * Returns last move
     * @return lastMove as in move made in this node
     */
    public Point getLastMove(){
        return lastMove;
    }
}
