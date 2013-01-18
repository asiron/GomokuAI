
package gomoku.player;

import gomoku.GomokuBoardState;
import java.awt.Point;
import java.util.Map;

/**
 * Implementation of Alpha-Beta pruning algorithm for Gomoku.
 * Builds up MinMax search tree and traverses through it meanwhile pruning 
 * branches that do not need checking
 * 
 * @author asiron
 */
public class AlphaBeta extends Object{

    /**
     * Number of points to put in row to win the game, taken from GameRules
     */
    private int mInRow;
    
    /**
     * Best move after performing alpha-beta pruning
     */
    public Point bestMove;
    
    /**
     * Enum value for our bot
     */
    private GomokuBoardState me;    
    
    /**
     * Enum value for our adversary
     */
    private GomokuBoardState adversary;
    
    /**
     * Root of a search tree
     */
   private Node root;
    
    
    /**
     * Creates AlphaBeta with root of a search tree
     * @param _root GomokuBoard without running the algorithm, first node
     * @param _me GomokuBoardState - yourself
     * @param _adversary GomokuBoardState - opponent
     * @param _mInRow Points in row need to win game
     */
    public AlphaBeta(Node _root, GomokuBoardState _me, GomokuBoardState _adversary, int _mInRow){
        root = _root;
        me = _me;
        adversary = _adversary;
        mInRow = _mInRow;
        bestMove = new Point(0,0);
    } 
   
    
    /**
     * Runs alpha-beta algorithm up to given depth
     * 
     * @return Point found in search tree
     */
    public Point algorithm(int depth){
        alphaBeta(root, depth, -1000000000, 1000000000, me, adversary);
        return bestMove;
    }
    
    /**
     * Implementation of alpha-beta pruning algorithm in recursive version
     * @param node Current node of search tree that we are on
     * @param depth Desired search depth, used as base case for recursion
     * @param alpha Best already explored option along the path to the root for the maximizer
     * @param beta Best already explored option along the path to the root for the minimizer
     * @param player Current player, maximizer or minimizer
     * @param opponent Next player, complementary to player
     * @return Pair of 1. value from Eval function 2. found point
     */
    private int alphaBeta(Node node, int depth, int alpha, int beta, GomokuBoardState player, GomokuBoardState opponent){
        
        // if depth is equal to 0, then we return eval function of current node
        // *we assume we can't reach terminal nodes, so we dont check for them*
        if(depth == 0){
            return node.evaluate(me, mInRow) - node.evaluate(adversary, mInRow);
        }
        
        if( node.isGameOver(me, adversary, mInRow) == me && me == player){
            return 1000000;
        }

        if( node.isGameOver(me, adversary, mInRow) == adversary && adversary == player){
            return -1000000;
        }
        
        if(player == me){
            node.createChildren();
            for(Node child : node.children){
                
                int temp = alphaBeta(child, depth-1, alpha, beta, opponent, player);

                if(alpha < temp){
                    alpha = temp;
                    if (node == root){
                        bestMove = child.getLastMove();
                    }
                }
 
                if( beta <= alpha ) {
                    return alpha;
                }
            }
            return alpha;
  
        }else{
            
            node.createChildren();
            for(Node child : node.children){
                
                int temp = alphaBeta(child, depth-1, alpha, beta, opponent, player);

                if(beta > temp){
                    beta = temp;
                    if ( node == root){
                        bestMove = child.getLastMove();
                    }
                }
                if( beta <= alpha ) {
                    return beta;
                }
            }
            return beta;
        }

    }
    
    public void delete() throws Throwable{
        this.finalize();
    }
}
