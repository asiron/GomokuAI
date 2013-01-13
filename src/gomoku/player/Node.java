/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gomoku.player;

import gomoku.GomokuBoard;
import gomoku.GomokuBoardState;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Node class for each *gamestate*, consists of current game board and 
 * undefined number of children, which are further *gamestates*
 * 
 * @author asiron
 */
public class Node {
    
    
    /**
     * move that was made in this node
     */
    private Point lastMove;
    
    /**
     * player that placed last pawn
     */
    private GomokuBoardState lastPlayer;
    
    /**
     * Map of children, due to undefined number of 
     * children at given point in time
     */
    public Map<Integer,Node> children;
    
    /**
     * Board with current gamestates
     */
    private GomokuBoard board;
    
    /**
     * List of all points that are adjacent to current gamestate points
     */
    private List<Point> adjacencyList;
    
    /**
     * Creates new node and initializes its values
     * @param oldBoard is a board from previous node
     * @param newMove is a Point selected as a move for this node
     * @param player is a player type ( min or max ) selected for this node
     */
    public Node(GomokuBoard oldBoard, Point newMove, GomokuBoardState player){
        
        //Initializes lastMove - subject to change
        lastMove = new Point(newMove);
        
        //Initializes lastPlayer
        lastPlayer = player;
        
        //Initializes list for adjacent points to currently occupied points
        adjacencyList = new ArrayList<>(); 
        
        //Copies old board of points and adds new point that was selected
        //for this node
        board = new GomokuBoard(oldBoard);
        board.set(newMove, player);
        System.out.println("Creating node..");
    } 
    
    /**
     * Creates children for a given node and assigns moves to them
     */
    public void createChildren(){
        
        for(int i = 0; i<board.getSize().width; ++i){
            for(int j = 0; j<board.getSize().height; ++j){
                for(int x=-1; x<=1; ++x){
                    for(int y=-1; y<=1; ++y){
                        if(board.get(new Point(i+x, j+y)) == GomokuBoardState.EMPTY) {
                            adjacencyList.add(new Point(i+x,j+y));
                        }
                    }
                }
            }
        }
        

        children = new HashMap<>();
        // Creates children based on adjacencyList
        for(Point p : adjacencyList){
            int i=0;
            children.put(i, new Node(board, p, lastPlayer == GomokuBoardState.A ? GomokuBoardState.B : GomokuBoardState.A));
            i++;
        }
    }
    
    
    /**
     * Evaluation function for leaves 
     * 
     * @return integer which is an evaluation of leaves 
     */
    public Integer evaluate(){
        
        Integer result = 0;
        
        //Look for doubles, triples, and so on
        int patterns[] = {2,3,4,5};
        
        for(int i : patterns){
            
            //Analyze columns
            for(int x=0; x < board.getSize().width; ++x){
                for(int y=0; y < board.getSize().height - i + 1; ++y){
                    boolean flag = true;
                    for(int k=0; k < i-1; ++k){
                        if(board.getState(x, y+k) != lastPlayer){
                            flag = false;
                            break;
                        }
                    }
                    if(flag)
                        result += i*10;
                }
            }
            
            //Analyze rows
            for(int y=0; y < board.getSize().height; ++y){
                for(int x=0; x < board.getSize().width - x + 1; ++y){
                    boolean flag = true;
                    for(int k=0; k < i-1; ++k){
                        if(board.getState(x+k, y) != lastPlayer){
                            flag = false;
                            break;
                        }
                    }
                    if(flag)
                        result += i*10;
                }
            }
        
            //Analyze right diagonals
            for(int x=0; x < board.getSize().width - i + 1; ++x){
                for(int y=0; y < board.getSize().height - i + 1; ++y){
                    boolean flag = true;
                    for(int k=0; k < i-1; ++k){
                        if(board.getState(x+k, y+k) != lastPlayer){
                            flag = false;
                            break;
                        }
                    }
                    if(flag)
                        result += i*10;
                }
            }
            
            //Analyze left diagonals
            for(int x=i-1; x < board.getSize().width; ++x){
                for(int y=0; y < board.getSize().height - i + 1; ++y){
                    boolean flag = true;
                    for(int k=0; k < i-1; ++k){
                        if(board.getState(x-k, y+k) != lastPlayer){
                            flag = false;
                            break;
                        }
                    }
                    if(flag)
                        result += i*10;
                }
            }
        }

        return result;
    }
    
    /**
     * Returns last move
     * @return lastMove as in move made in this node
     */
    public Point getLastMove(){
        return lastMove;
    }
}
