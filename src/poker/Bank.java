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
    private List<Player> winning_list = new ArrayList<>();
    
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
            Player possible_winner_1 = this.getPlayer(0);
            Player possible_winner_2 = this.getPlayer(1);
            //there are 2 players with the same hand
            //check which hand is higher
            //For Strights and Flashes compare cards one by one
            //For the rest sort by the highest pair and then compare cards one by one.
            switch(handType){
                case 2: //Fall through HandType.FOUR_OF_A_KIND 
                case 3: //Fall through HandType.FULL_HOUSE
                case 6: //Fall through HandType.THREE_OF_A_KIND
                case 7: //Fall through HandType.TWO_PAIRS
                case 8: //Fall through HandType.ONE_PAIR
                    System.out.println("L82 Bank - Pairs case");
                    break;
                default:
                System.out.println("L85 Bank - Entered Default case");
                //Fall through - HandType.STRIGHT_FLUSH value = 1
                //Fall through - HandType.FLUSH         value = 4
                //Fall through - HandType.STRIGHT       value = 5
                //Fall through - HandType.HIGH_CARD     value = 9
                
                compareHandsByCard();
                
                //if winner list is stil empty = 2 winners
            }//switch(handType)
            
            if(winning_list.isEmpty()){
                System.out.println("L99 Bank - there are 2 winners");
                winning_list.add(this.getPlayer(0));
                winning_list.add(this.getPlayer(1));    
            }
            
            //hands are equal = 2 winners
//            Collections.sort(winning_list, new Comparator<Player>(){
//            @Override
//            public int compare(Player player1, Player player2){
//                return player1.getHand(). - player2.getType().ordinal();
//            }
//        });
        }else {
            //there is one definite winner.
            winning_list.add(this.getPlayer(0));
            System.out.println("L78 Bank - there is 1 winner" + winning_list.get(0));

        }
        return winning_list;
    }//getWinner()
    
    private void compareHandsByCard (){
        for(Card card: this.getPlayer(0).getHand()){
            Card other_card = this.getPlayer(1).getHand().next();
            if(card.compareTo(other_card) < 0){
                //player 1 Wins
                System.out.println("L94 Bank - Equal hands there is 1 winners");
                winning_list.add(this.getPlayer(1));
                return;
            }else if(card.compareTo(other_card) > 0){
                //player 2 wins
                System.out.println("L98 Bank - Equal hands there is 1 winners");
                winning_list.add(this.getPlayer(0));
                return;
            }
               
            System.out.println("Comparing cards " + card + " Player 1 = " + other_card + " result " + card.compareTo(other_card));
        }//
    }//compareHandsByCard()
    
}//class
