/*
 * 
 */
package poker;

import java.util.ArrayList;
import java.util.List;

/**
 *  Accept wagers and calculates win also adds wagers to the player's account
 * Bank size number is $500 for everyone.
 * @author Alexander Frolov
 */
public class Bank {
    //Number of players in  the game
    private int num_of_players = 0;
    private List<Player> players = new ArrayList<>();
    private Deck deck;
    
//    ========  CONSTRUCTORS 
    
    public Bank(int players){
        this.deck = new Deck();
        this.num_of_players = players;
        this.initPlayers();
    }
    
    public Bank(){
        //Standard game of 2 players Comp vs Human
        this(2);
    }
    
//    ========  METHODS

    private void initPlayers(){
        for(int i = 0; i < this.num_of_players; ++i){
            players.add(new Player(this.deck.dialHand()));
        }
    }
    
    public Player getPlayer(int index){
        return this.players.get(index);
    }
    
}//class
