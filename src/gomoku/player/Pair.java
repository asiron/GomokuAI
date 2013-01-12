/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gomoku.player;

/**
 * Implementation of pair in Java
 * 
 * @author asiron
 */
class Pair<T0, T1> implements Cloneable{
    
    /**
     * First element of pair
     */
    public T0 first;
    
    /**
     * Second element of pair
     */
    public T1 second;
    
    public Pair(T0 t0, T1 t1){
        first = t0;
        second = t1;
    }
    
    
}
