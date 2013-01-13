
package gomoku.player;

import gomoku.GomokuBoardState;
import java.awt.Point;
import java.util.Map;

/**
 * Implementation of Alpha-Beta pruning algorithm for Gomoku
 * Builds up MinMax search tree and traverses through it meanwhile pruning 
 * branches that do not need checking
 * 
 * @author asiron
 */
public class AlphaBeta {

    /**
     * Enum value for our bot
     */
    private GomokuBoardState me;    
    
    /**
     * Root of a search tree
     */
   private Node root;
    
    
    /**
     * Creates AlphaBeta with root of a search tree
     * @param _root 
     */
    public AlphaBeta(Node _root, GomokuBoardState _me){
        root = _root;
        me = _me;
    } 
   
    
    /**
     * Runs alpha-beta algorithm up to given depth
     * 
     * @return Point found in search tree
     */
    public Point algorithm(int depth){
        return new Point(alphaBeta(root, depth, new Pair(-1000000000, new Point(-1,-1)), new Pair(1000000000, new Point(-1,-1)), me).second);
    }
    
    /**
     * Implementation of alpha-beta pruning algorithm in recursive version
     * @param node Current node of search tree that we are on
     * @param depth Desired search depth, used as base case for recursion
     * @param alpha Best already explored option along the path to the root for the maximizer
     * @param beta Best already explored option along the path to the root for the minimizer
     * @param player Current player, maximizer or minimizer
     * @return Pair of 1. value from Eval function 2. found point
     */
    private Pair<Integer, Point> alphaBeta(Node node, Integer depth, Pair<Integer, Point> alpha, Pair<Integer, Point> beta, GomokuBoardState player){
        
        // if depth is equal to 0, then we return eval function of current node
        // *we assume we can't reach terminal nodes, so we dont check for them*
        if(depth == 0){
            Pair<Integer, Point> pair = new Pair(node.evaluate(), node.getLastMove());
            System.out.printf("Printing point (%d,%d)", pair.second.x, pair.second.y);
            return pair;
        }
        
        if(player == me){
            
            node.createChildren();
            for(Map.Entry<Integer,Node> child : node.children.entrySet()){
                
                player = (player == GomokuBoardState.A ) ? GomokuBoardState.B : GomokuBoardState.A;
                Pair<Integer,Point> temp = alphaBeta(child.getValue(), depth-1, alpha, beta, player);

                if(alpha.first <= temp.first){
                    alpha.first = temp.first;
                    alpha.second = temp.second;
                }
 
                if( beta.first <= alpha.first ) {
                    break;
                }
            }
            return alpha;
  
        }else{
            
            node.createChildren();
            for(Map.Entry<Integer,Node> child : node.children.entrySet()){
                
                player = (player == GomokuBoardState.A ) ? GomokuBoardState.B : GomokuBoardState.A;
                Pair<Integer,Point> temp = alphaBeta(child.getValue(), depth-1, alpha, beta, player);

                if(beta.first >= temp.first){
                    beta.first = temp.first;
                    beta.second = temp.second;
                }
 
                if( beta.first <= alpha.first ) {
                    break;
                }
            }
            return beta;
        }

    }
}
