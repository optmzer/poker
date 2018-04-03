/*
 * 
 */
package poker;

import java.io.*;
import java.util.*;
/**
 *  Accept wagers and calculates win also adds wagers to the player's account
 * Bank size number is $500 for everyone.
 * TODO: To determine winners bank sorts players from best hand to worst.
 * when accessing the list of players for accounting players appear
 * in different places after every round.
 * Fix players and only shuffle winners.
 * 
 * 
 * @author Alexander Frolov
 */
public class Bank {
    //Number of players in  the game
    private final int minimumBet = 2;
    private int pot = 0;
    private int num_of_players = 0;
    private final List<Player> players = new ArrayList<>();
    private Deck deck;
    private final List<Player> winning_list = new ArrayList<>();
    
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
    
//    ========  GETTERS/SETTERS ========

    private void initPlayers(){
        players.add(new Player(this.deck.dialHand(), PlayerType.COMPUTER));
        for(int i = 1; i < this.num_of_players; ++i){
            players.add(new Player(this.deck.dialHand(), PlayerType.values()[i]));
        }
    }
    
    public void dealNewRound(){
        //Clear winner_list and Pot
        this.clearWinningList();
        this.setPot(0);
        this.deck = new Deck();
        this.players.forEach( player -> {
            player.check();//Reset bet type to check if there was fold before.
            player.setHand(deck.dialHand());
        });
    }
    
    public Player getPlayer(PlayerType playerType){
        
        for(Player player: this.players){
            if(player.getPlayerType() == playerType)
            return player;
        }
        return null;
    }
    
    public Player getPlayer(int index){
        return this.players.get(index);
    }
    
    public Player getPlayerFromWinningList(int index){
        return this.winning_list.get(index);
    }
    
    public Player getPlayerFromWinningList(PlayerType playerType){
        Player aPlayer = null;
        if(!winning_list.isEmpty()){
            for(Player player: this.winning_list){
                if(player.getPlayerType() == playerType)
                aPlayer = player;
            }
        }else{
            aPlayer = this.getPlayer(playerType);
        }
        return aPlayer;
    }
    
    public Enum getPlayerType(int index){
        return this.players.get(index).getPlayerType();
    }
    
    public List<Player> getPlayers() {
        return this.players;
    }
    
//  ======== POT OPERATIONS =======
    
    public void addToPot(int betAmount) {
        if(betAmount > 0){
            this.pot += betAmount;
        }
    }
    
    public int getPot(){
        return this.pot;
    }
    
    public void setPot(int amount){
        this.pot = amount;
    }
    
    public int splitPot(int numOfPlayers){
        //Do not want to mess around with odd numbers
        int splitPot = this.pot / 2;
        this.setPot(0);
        return splitPot;
    }
    
    public void clearWinningList(){
        this.winning_list.clear();
    }
    
    /**
     * TODO: It works when hands are different
     * need to add evaluation of the same hand types.
     * one pair and one pair. which is higher.
     * @return 
     */
    public List<Player> getWinner(){
        //Move players who did not FOLD to winning_list and Determine hand type
        players.forEach(player -> {
            player.getHand().determineHandType();
            if(player.getBetType() != BetType.FOLD){
                this.winning_list.add(player);
//                System.out.println("L138 Bank winning_list " + player.getPlayerType() + " BetType: " + player.getBetType());
            }
        });
        //Sort players by the highest hand
        Collections.sort(
            this.winning_list, (Player player1, Player player2) -> 
                player1.getHand().getHandType().ordinal() - player2.getHand().getHandType().ordinal()
        );
        
        /**
         * By this time the player with winning hand should be
         * at the beginning of the array.
         */
        
        //if hands are equal then compare them by the highest card.
        if  (winning_list.size() > 1){
            if(this.getPlayerFromWinningList(0).getHand().getHandType() == this.getPlayerFromWinningList(1).getHand().getHandType()){
                int handType = this.getPlayerFromWinningList(0).getHand().getHandType().ordinal();
                /**there are 2 players with the same hand
                * check which hand is higher
                * For straight hands and flashes compare cards one by one
                * For the rest sort by the highest pair and then compare cards one by one.
                */ 
                switch(handType){
                    case 2: //Fall through HandType.FOUR_OF_A_KIND 
    //                    System.out.println("\nL84 Bank - FOUR_OF_A_KIND");
                        for(Player player: winning_list){
                            this.sortHandByPairs(player.getHand(), 4);
                        }
                        break;
                    case 3: //Fall through HandType.FULL_HOUSE
                    case 6: //Fall through HandType.THREE_OF_A_KIND
    //                    System.out.println("\nL137 Bank - FULL_HOUSE or THREE_OF_A_KIND");
                        for(Player player: winning_list){
                            this.sortHandByPairs(player.getHand(), 3);
                        }
                        break;
                    case 7: //Fall through HandType.TWO_PAIRS
                    case 8: //Fall through HandType.ONE_PAIR
                        //Sort Player hand by a group of cards.
    //                    System.out.println("\nL143 Bank - TWO_PAIRS or ONE_PAIR");
                        for(Player player: winning_list){
                            this.sortHandByPairs(player.getHand(), 2);
                        }
                        break;
                    default:
                    //Fall through - HandType.STRIGHT_FLUSH value = 1
                    //Fall through - HandType.FLUSH         value = 4
                    //Fall through - HandType.STRIGHT       value = 5
                    //Fall through - HandType.HIGH_CARD     value = 9
                }//switch(handType)

                //As hands already sorted I only need one method to determine 
                //the highest hand.
                //This function adds entry to the winning_list
                this.compareHandsByCard();

                //if winner list is stil empty = 2 winners
//                if(winning_list.isEmpty()){
//                    System.out.println("L185 Bank - there are 2 winners");
//                    winning_list.add(this.getPlayer(0));
//                    winning_list.add(this.getPlayer(1));    
//                }
            }else {
                //there is one definite winner.
                Player tempPlayer = this.getPlayerFromWinningList(0);
                winning_list.clear();
                winning_list.add(tempPlayer);
            }
        }
        
        //Sort players back into original position for accounting purpuses
        Collections.sort(
            players, (Player player1, Player player2) -> 
                player1.getPlayerType().ordinal() - player2.getPlayerType().ordinal()
        );
        
        return winning_list;
    }//getWinner()
    
    /**
     * 
     */
    private void compareHandsByCard (){
        Card card, other_card;
        Hand hand = this.getPlayerFromWinningList(0).getUndeterminedHand();
        Hand otherHand = this.getPlayerFromWinningList(1).getUndeterminedHand();
        for(int index = 0; index < hand.size(); ++index ){
            card = hand.getCard(index);
            other_card = otherHand.getCard(index);
            
            if(card.compareTo(other_card) < 0){
                //player 1 Wins
                Player tempPlayer = this.getPlayerFromWinningList(1);
                winning_list.clear();
                winning_list.add(tempPlayer);
                return;
            }
            
            if(card.compareTo(other_card) > 0){
                //player 2 wins
                Player tempPlayer = this.getPlayerFromWinningList(0);
                winning_list.clear();
                winning_list.add(tempPlayer);
                return;
            }
        }//
    }//compareHandsByCard()
    
    private void sortHandByPairs(Hand hand, int first_pair){
        //TODO:
        /**
         * for 2 pairs and one pair need to check the highest pair within the
         * hand.
         */
        if(first_pair == 2 && hand.getHandType() == HandType.TWO_PAIRS){
            Card temp_card;
            //Determin which pair is higher
            /**
             * Because by this time the hand is already sorted, the 
             * highest pair will be the first in the handMap().
             */
            //Sort by highest pair
            Set handKeySet = hand.getHandMap().keySet();
            List<String> keys = new ArrayList<>(handKeySet);
                //sort hand in reverse
                for(int reverse_index = handKeySet.size() - 1; reverse_index >= 0; --reverse_index){
                    if ((int) hand.getHandMap().get(keys.get(reverse_index)) == first_pair) {
                    //put cards making up a hand with this key first
                    for(int i = 0; i < 5; ++i){
                        if(hand.getCard(i).getRank().toString().equalsIgnoreCase(keys.get(reverse_index))){
                            temp_card = hand.getCard(i);
                            hand.removeCard(i);
                            hand.addCard(0, temp_card);
                        }
                    }
                } 
            }
        }
        //This will work for 4 of a kind and 3 of a kind, as well as full house.
        //Should work for OnePair as well.
        hand.getHandMap().keySet().forEach((Object key) -> {
            Card temp_card;
            //Determine which rank makes a hand
            if ((int) hand.getHandMap().get(key) == first_pair) {
                //put cards making up a hand with this key to the left most position
                for(int i = 0; i < hand.size(); ++i){
                    if(hand.getCard(i).getRank().toString() == key){
                        temp_card = hand.getCard(i);
                        hand.removeCard(i);
                        hand.addCard(0, temp_card);
                    }
                }
                System.out.println(hand);
            } 
        });
    }//sortHandByPairs()

    public void swopCards(PlayerType playerType, List<Integer> listOfIndexes){
        //search for the player
        
        Map cardsToSwap = new HashMap();
        Player aPlayer = this.getPlayer(playerType);
        
        //Deck get cards for each index
        listOfIndexes.forEach((Integer number) -> {
            Card aCard = deck.dealCard();
            cardsToSwap.put(number, aCard);
        });
        
        //replace cards
        aPlayer.swapCards(cardsToSwap);
    }//swopCards()

    /**
     * Only saves player1.
     */
    public void saveGame() {
        
        Writer bWriter;
        try{
            bWriter = new BufferedWriter(new FileWriter("SavedGame.txt"));
            for(Player aPlayer: this.players){
                if(aPlayer.getPlayerType().equals(PlayerType.PLAYER_1)){
                    bWriter.write(aPlayer.getPlayerType() + " $" + aPlayer.getWallet() + "\n");
                }
            }
            bWriter.close();
        }catch(IOException e){
            System.err.println("L298 bank Error writing file => " + e);
        }
        System.out.println("L305 bank Game Saved");
    }//saveGame()
    
    /**
     * Loads player1 to the game.
     */
    public void loadGame(){
        Scanner scan;
        String[] playerEntry;
        int money = 0;
        try{
            //load data from a file
            scan = new Scanner(new FileReader("SavedGame.txt"));

            //get player ballance
            if(scan.hasNext()){
                playerEntry = scan.nextLine().split("\\$");
                money = Integer.parseInt(playerEntry[1]);
            }
            
            scan.close();
            //bank getPlayer1
            this.getPlayer(PlayerType.PLAYER_1).setWallet(money);
            //show player
            System.out.println("Player 1 has $" + this.getPlayer(PlayerType.PLAYER_1).getWallet());
            
        }catch(IOException | NoSuchElementException e){
            System.out.println("File not found. Cannot find 'SavedGames.txt' at specified location.");
            System.out.println("Either file does not exist or was relocated.");
            System.out.println("Try Save Game first then Load.");
        }
        
    }//loadGame()

    void playerFold(PlayerType playerType) {
        this.getPlayerFromWinningList(PlayerType.PLAYER_1).fold();
    }
}//class
