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
        return this.players.get(index).getPlayerType();
    }
    
    
    /**
     * TODO: It works when hands are different
     * need to add evaluation of the same hand types.
     * one pair and one pair. which is higher.
     * @return 
     */
    public List<Player> getWinner(){
        List<Player> winning_list = new ArrayList<>();
        Collections.sort(
            players, (Player player1, Player player2) -> 
                player1.getHand().getHandType().ordinal() - player2.getHand().getHandType().ordinal()
        );
        
        /**
         * By this time the player with winning hand should be
         * at the beginning of the array.
         */
        
        if(this.getPlayer(0).getHand().getHandType() == this.getPlayer(1).getHand().getHandType()){
            int handType = this.getPlayer(0).getHand().getHandType().ordinal();
            //there are 2 players with the same hand
            //check which hand is higher
            switch(handType){
                default:
                //Fall through - HandType.STRIGHT_FLUSH value = 1
                //Fall through - HandType.FLUSH         value = 4
                //Fall through - HandType.STRIGHT       value = 5
                //Fall through - HandType.HIGH_CARD     value = 9
                    
            }
            //hands are equal = 2 winners
            winning_list.add(this.getPlayer(0));
            winning_list.add(this.getPlayer(1));
//            Collections.sort(winning_list, new Comparator<Player>(){
//            @Override
//            public int compare(Player player1, Player player2){
//                return player1.getHand(). - player2.getType().ordinal();
//            }
//        });
            System.out.println("L74 Bank - there are 2 winners");
        }else {
            //there is one definite winner.
            winning_list.add(this.getPlayer(0));
            System.out.println("L78 Bank - there is 1 winner" + winning_list.get(0));

        }
        return winning_list;
    }
    
}//class
