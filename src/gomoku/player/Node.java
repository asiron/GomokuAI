
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
 * Node class for each *gamestate*, consists of current game board and 
 * undefined number of children, which are further *gamestates*
 * 
 * @author asiron
 */
public class Node {
    
    /**
     * Move that was made in this node
     */
    private Point lastMove;
    
    /**
     * Player that placed last pawn
     */
    private GomokuBoardState lastPlayer;
    
    /**
     * List of children, due to undefined number of 
     * children at given point in time
     */
    public List<Node> children;
        
    /**
     * Board with current gamestates
     */
    private GomokuBoard board;

    /**
     * Creates new node and initializes its values
     * @param oldBoard is a board from previous node
     * @param newMove is a Point selected as a move for this node
     * @param player is a player type ( min or max ) selected for this node
     */
    public Node(GomokuBoard oldBoard, Point newMove, GomokuBoardState player){
        
        //Initializes lastMove - subject to change
        lastMove = newMove;
        
        //Initializes lastPlayer
        lastPlayer = player;
                
        //Copies old board of points and adds new point that was selected
        //for this node
        this.board = oldBoard;
        this.board.set(lastMove, player);
        
    } 
    
    /**
     * Creates children for a given node and assigns moves to them
     */
    public void createChildren(){
        
        List<Point> adjacencyList = new ArrayList<>();
        
        for(int i = 0; i<board.getSize().width; ++i){
            for(int j = 0; j<board.getSize().height; ++j){
                if((board.getState(i,j) == GomokuBoardState.A )|| (board.getState(i,j) == GomokuBoardState.B)){
                    for(int x=-1; x<=1; ++x){
                        for(int y=-1; y<=1; ++y){
                            if( ( board.getState(i+x, j+y) == GomokuBoardState.EMPTY )  &&  ( !adjacencyList.contains(new Point(i+x,j+y)) ) ) {
                                adjacencyList.add(new Point(i+x,j+y));
                            }
                        }
                    }
                }     
            }
        }
        children = new ArrayList<>();
        // Creates children based on adjacencyList
        for(Point p : adjacencyList){
            children.add(new Node(new GomokuBoard(board), new Point(p), lastPlayer == GomokuBoardState.A ? GomokuBoardState.B : GomokuBoardState.A));
        }
    }
    
    /**
     * Checks if game will end if it comes to this state for given player
     * @param player will check game board for this player
     * @param opponent will check game board fir this player as well
     * @param mInRow points in row to win game, needed for checking
     * @return GomokuBoardState returns player that won game, or null
     */
    public GomokuBoardState isGameOver(GomokuBoardState player, GomokuBoardState opponent, int mInRow){
       
       // Analyze columns
       for(int i=0; i<board.getSize().width; ++i){
           for(int j=0; j<board.getSize().height - mInRow +1; ++j){
               boolean playerFlag = true;
               boolean opponentFlag = true;
               for(int k=0; k<=mInRow-1; ++k){
                   if(board.getState(i, j+k) != player){
                       playerFlag = false;
                   }
                   if(board.getState(i, j+k) != opponent){
                       opponentFlag = false;
                   }    
                   if(!playerFlag && !opponentFlag){
                       break;
                   }
               }
               if ( playerFlag ){
                   return player;
               }
               if ( opponentFlag ){
                   return opponent;
                }    
           }
       }
       
       // Analyze rows
       for(int j=0; j<board.getSize().height; ++j){
           for(int i=0; i<board.getSize().width - mInRow +1; ++i){
               boolean playerFlag = true;
               boolean opponentFlag = true;
               for(int k=0; k<=mInRow-1; ++k){
                   if(board.getState(i+k, j) != player){
                       playerFlag = false;
                   }
                   if(board.getState(i+k, j) != opponent){
                       opponentFlag = false;
                   }    
                   if(!playerFlag && !opponentFlag){
                       break;
                   }
               }
               if ( playerFlag ){
                   return player;
               }
               if ( opponentFlag ){
                   return opponent;
                }    
           }
       }
       
       //Analyze left diagonals
       for(int i=0; i<board.getSize().width - mInRow +1; ++i){
           for(int j=0; j<board.getSize().height - mInRow +1; ++j){
               boolean playerFlag = true;
               boolean opponentFlag = true;
               for(int k=0; k<=mInRow-1; ++k){
                   if(board.getState(i+k, j+k) != player){
                       playerFlag = false;
                   }
                   if(board.getState(i+k, j+k) != opponent){
                       opponentFlag = false;
                   }    
                   if(!playerFlag && !opponentFlag){
                       break;
                   }
               }
               if ( playerFlag ){
                   return player;
               }
               if ( opponentFlag ){
                   return opponent;
                }    
           }
       }
       
       //Analyze right diagonals
       for(int i= mInRow +1; i<board.getSize().width; ++i){
           for(int j=0; j<board.getSize().height - mInRow +1; ++j){
               boolean playerFlag = true;
               boolean opponentFlag = true;
               for(int k=0; k<=mInRow-1; ++k){
                   if(board.getState(i-k, j+k) != player){
                       playerFlag = false;
                   }
                   if(board.getState(i-k, j+k) != opponent){
                       opponentFlag = false;
                   }    
                   if(!playerFlag && !opponentFlag){
                       break;
                   }
               }
               if ( playerFlag ){
                   return player;
               }
               if ( opponentFlag ){
                   return opponent;
                }    
           }
       }
       return null;
    }
    
    /**
     * Evaluation function for leaves 
     * 
     * @param player Evaluates node for given player
     * @param mInRow points in row to win game
     * @return integer which is an evaluation of leaves 
     */
    public int evaluate(GomokuBoardState player, int mInRow){
        
        int result = 0;
        
        //Look for doubles, triples, and so on
        int patterns[] = new int[3];
        for(int i=0; i<3; ++i)
            patterns[i] = mInRow-2+i;
        
        for(int i : patterns){
            
           //Analyze columns
           for(int x=0; x < board.getSize().width; ++x){
                for(int y=0; y < board.getSize().height - i + 1; ++y){
                    boolean flag = true;
                    for(int k=0; k <= i-1; ++k){
                        if(board.getState(x, y+k) != player){
                            flag = false;
                            break;
                        }
                    }
                    if(flag)
                        result +=  Math.pow(10, i);
                }
            }
            
            //Analyze rows
            for(int y=0; y < board.getSize().height; ++y){
                for(int x=0; x < board.getSize().width - x + 1; ++x){
                    boolean flag = true;
                    for(int k=0; k <= i-1; ++k){
                        if(board.getState(x+k, y) != player){
                            flag = false;
                            break;
                        }
                    }
                    if(flag)
                        result +=  Math.pow(10, i);
                }
            }
        
            //Analyze left diagonals
            for(int x=0; x < board.getSize().width - i + 1; ++x){
                for(int y=0; y < board.getSize().height - i + 1; ++y){
                    boolean flag = true;
                    for(int k=0; k <= i-1; ++k){
                        if(board.getState(x+k, y+k) != player){
                            flag = false;
                            break;
                        }
                    }
                    if(flag)
                        result +=  Math.pow(10, i);
                }
            }
            
            //Analyze right diagonals
            for(int x=i-1; x < board.getSize().width; ++x){
                for(int y=0; y < board.getSize().height - i + 1; ++y){
                    boolean flag = true;
                    for(int k=0; k <= i-1; ++k){
                        if(board.getState(x-k, y+k) != player){
                            flag = false;
                            break;
                        }
                    }
                    if(flag)
                        result +=  Math.pow(10, i);
                }
            }
        }

        //System.out.printf("Eval: %d\n", result);
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
