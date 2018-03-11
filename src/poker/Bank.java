/*
 * 
 */
package poker;

import java.util.*;

/**
 *  Accept wagers and calculates win also adds wagers to the player's account
 * Bank size number is $500 for everyone.
 * @author Alexander Frolov
 */
public class Bank {
    //Number of players in  the game
    private int num_of_players = 0;
    private final List<Player> players = new ArrayList<>();
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
        players.add(new Player(this.deck.dialHand(), PlayerType.COMPUTER));
        for(int i = 1; i < this.num_of_players; ++i){
            players.add(new Player(this.deck.dialHand(), PlayerType.values()[i]));
        }
    }
    
    public Player getPlayer(int index){
        return this.players.get(index);
    }
    
    public Enum getPlayerType(int index){
        return this.players.get(index).getType();
    }
    
    
    /**
     * TODO: It works when hands are different
     * need to add evaluation of the same hand types.
     * one pair and one pair. which is higher.
     * @return 
     */
    public Player getWinner(){
        
        Collections.sort(players, new Comparator<Player>(){
            @Override
            public int compare(Player player1, Player player2){
                return player1.getType().ordinal() - player2.getType().ordinal();
            }
        });
        
        Collections.reverse(players);


        return this.getPlayer(0);
    }
    
}//class
