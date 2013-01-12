/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gomoku.player;

import gomoku.GomokuBoard;
import gomoku.GomokuBoardState;
import java.awt.Point;
import java.awt.Rectangle;
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
     * Map of children, due to undefined number of 
     * children at given point in time
     */
    public Map<Integer,Node> children;
    
    
    /**
     * List of all points that are adjacent to current gamestate points
     */
    public List<Point> adjacencyList;
    
    /**
     * Map of current Points on board and corresponding owners
     */
    public Map<Point,GomokuBoardState> boardState;
    
    /**
     * Creates new node and initializes its values
     * @param rect is rectangle from game rules to specify size of board
     * @param oldBoardState is a board from previous node
     * @param newMove is a Point selected as a move for this node
     * @param player is a player type ( min or max ) selected for this node
     * @param 
     */
    public Node(Rectangle rect, Map<Point,GomokuBoardState> oldBoardState, Point newMove, GomokuBoardState player){
        
        //Initialize Map for children of this node
        children = new HashMap<>(rect.height*rect.width);
        
        //Initialize list for adjacent points to currently occupied points
        adjacencyList = new ArrayList<>(); 
        
        //Copies old list of occupied points and adds new point that was selected
        //for this node
        boardState = new HashMap<>(oldBoardState);
        boardState.put(newMove, player);
        
        //Get list of only adjacent points
        for(Map.Entry<Point,GomokuBoardState> entry : boardState.entrySet()){
            for(int x=-1; x<=1; ++x){
                for(int y=-1; y<=1; y++){
                    if(!boardState.containsKey(new Point(entry.getKey().x+x, entry.getKey().y+y))) {
                        adjacencyList.add(new Point(entry.getKey().x+x, entry.getKey().y+y));
                    }
                }
            }
        }
        
        // Creates children based on adjacencyList if
        for(Point p : adjacencyList){
            int i=0;
            children.put(i, new Node(rect, boardState, p, player == GomokuBoardState.A ? GomokuBoardState.A : GomokuBoardState.B));
            i++;
        }
         
        
    }    

    
}
