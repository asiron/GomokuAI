
package gomoku.player;

import gomoku.GomokuBoardState;
import java.awt.Point;
import java.util.Map;

/**
 * Implementation of Alpha-Beta pruning algorithm for Gomoku
 * 
 * @author asiron
 */
public class AlphaBeta {

    /**
     * Enum value for our bot
     */
    GomokuBoardState me;    
    
    /**
     * Root of a search tree
     */
    public Node root;
    
    
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
     * @return 
     */
    public Point algorithm(int depth){
        
        
        return new Point();
    }
    
    private Pair<Integer, Point> alphaBeta(Node node, Integer depth, Pair<Integer, Point> alpha, Pair<Integer, Point> beta, GomokuBoardState player){
        
        // if depth is equal to 0, then we return eval function of current node
        // *we assume we can't reach terminal nodes, so we dont check for them*
        if(depth == 0){
            return new Pair(node.evaluate(), node.getLastMove());
        }
        
        if(player == me){
            for(Map.Entry<Integer,Node> child : node.children.entrySet()){
                
                player = (player == GomokuBoardState.A ) ? GomokuBoardState.B : GomokuBoardState.A;
                Pair<Integer,Point> temp = alphaBeta(child.getValue(), depth-1, alpha, beta, player);

                if(alpha.first <= temp.first){
                    alpha.first = temp.first;
                    alpha.second = temp.second;
                }
 
                if( beta.first <= alpha.first )
                    break;
            }
            return alpha;
  
        }else{
            for(Map.Entry<Integer,Node> child : node.children.entrySet()){
                
                player = (player == GomokuBoardState.A ) ? GomokuBoardState.B : GomokuBoardState.A;
                Pair<Integer,Point> temp = alphaBeta(child.getValue(), depth-1, alpha, beta, player);

                if(beta.first >= temp.first){
                    beta.first = temp.first;
                    beta.second = temp.second;
                }
 
                if( beta.first <= alpha.first )
                    break;
            }
            return beta;
        }

    }
}
