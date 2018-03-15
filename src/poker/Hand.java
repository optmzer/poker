/**
 * Hand ranking 
 * Rank = 1 - Highest
 * Rank = 9 - Lowest
 * Hand ranking within the same hand
 * The Highest cards in the same hand win.
 * eg. Four of a kind. 5,5,5,5,2 is lower then 6,6,6,6,2
 */
package poker;

    //TODO:
    //Replace a card by index
    
    //compare Hand to another hand
    
    //TODO: What to do with the money if hands are equal in Rank?
    //Move the organized cards to the left 

import java.util.*;

/**
 *
 * @author Alexander Frolov
 */
public class Hand implements Comparable<Hand>{
    
    //compute stright away hand tipe 
    //and the way to compare to another hand which is higher.
    
    private List<Card> hand; 
    private Enum handType;
    
    public Hand(List<Card> hand){
        if(hand.size() == 5){
            this.sortHand(hand);
            this.hand = hand ;
            this.determineHandType();
        }
    }
    
    //Getters - Setters
    public Enum getHandType(){
        return this.handType;
    }
    
    
    
    /**
     * Sorts given Hand by card Rank.
     * @param hand 
     */
    public final void sortHand(List<Card> hand){
        
//        Collections.sort(hand, new Comparator<Card>(){
//            @Override
//            public int compare(Card card1, Card card2){
//                    return card1.compareTo(card2);
//            }
//        });

//      Collections.sort(hand, Card::compareTo); //Using member reference

        Collections.sort(hand, (Card card1, Card card2) -> card1.compareTo(card2)); //Lambda expression

        Collections.reverse(hand);
    }
    
    /**
     * Hand Rank = 5
     * - Straight All cards are in descending order. Suits are different
     * if suits are the same it is Straight Flush (Straight + Flush)
     * 10,9,8,7,6
     * If difference between i and i+1 == 1 it is straight.
     * @return 
     */
    public boolean isStright(){
        int counter = 0;
        if(hand.size() == 5){
            for(int i = 0; i < hand.size() - 1; ++i){
                if(hand.get(i).getRank().ordinal() - hand.get(i + 1).getRank().ordinal() == 1){
                    ++counter;
                }
            }
        }
        return counter == 4;
    }
    
    private void determineHandType(){
        Map rankMap = new HashMap();
        Map suitMap = new HashMap();
        String keyRank;
        String keySuit;
        Collection handMapRanks;
        Collection handMapSuits;
       
        /**
         * Creates a Map with key = Card Rank, value - number of repetitions
         * keyRank = rank of the card
         * keySuit = suit of the card
         */
        for (Card nextCard : this.hand) {
            keyRank = nextCard.getRank().toString();
            keySuit = nextCard.getSuit().toString();
            //Fill in Rank Map
            if(rankMap.containsKey(keyRank)){
                int value = 1 + (int)rankMap.get(keyRank);
                rankMap.put(keyRank, value);
            }else{
                rankMap.put(keyRank, 1);
            }
            //Fill in Suit Map
            if(suitMap.containsKey(keySuit)){
                int value = 1 + (int)suitMap.get(keySuit);
                suitMap.put(keySuit, value);
            }else{
                suitMap.put(keySuit, 1);
            }
        }
        
        handMapRanks = rankMap.values();
        handMapSuits = suitMap.values();
// =============== SORT BY HIGHEST PAIR =================

        if(handMapRanks.contains(4)){
            /**
            * Hand Rank = 2, 
            * 4 of a Kind - 4 cards of the same value. 5th can be any
            * 5,5,5,5,7
            */
            this.handType = HandType.FOUR_OF_A_KIND;
            
            rankMap.keySet().forEach( (Object key) -> {
                //Determine which rank makes 4 of a kind
                if((int)rankMap.get(key) == 4){    
                    //put cards making up a hand with this key first
                    
                }else{
                    //use next key to organize cards
                    
                }
            });
            
            
        }else if(handMapRanks.contains(3)){
            //check if there is 3 and 2
            if(handMapRanks.contains(3) && handMapRanks.contains(2)){
                /**
                * Hand Rank = 3
                * Full house - 3 of the same kind plus 1 pair of the same kind
                * 6,6,6,K,K = (3 + 2)
                */
                this.handType = HandType.FULL_HOUSE;
            }else{//only 3
                /**
                * Hand Rank = 6
                * - 3 of a kind. 3 cards of the same Rank. Others do not match
                * Q,Q,Q,4,7
                */
                this.handType = HandType.THREE_OF_A_KIND;
            }
        }else if(handMapRanks.contains(2)){
            //check if there is another 2
            if(Collections.frequency(handMapRanks, 2) == 2){
                /**
                * Hand Rank = 7
                * - 2 pair. Suit is different for all
                * K,K,7,7,3 (2 + 2 + 1)
                */
                this.handType = HandType.TWO_PAIRS;
            }else{//only one pair
                /**
                * Hand Rank = 8
                * - 1 pair. Suits can be any
                * J,J,8,7,2 (2 + 1 + 1 + 1)
                */
                this.handType = HandType.ONE_PAIR;
// =============== SORT BY HIGHEST PAIR =================
            }
        }else if(this.isStright()){
            /**
            * Hand Rank = 5
            * - Straight All cards are in descending order. 
            * 10,9,8,7,6
            */
            this.handType = HandType.STRIGHT;
            //sort by highest card

        }else if(Collections.frequency(handMapSuits, 5) == 1){
            /**
            * Hand Rank = 4
            * Flush - All cards are the same suit.
            * How to determine which Flush is the strongest?
            */
            this.handType = HandType.FLUSH;
            //sort by highest card

        }else if(Collections.frequency(handMapSuits, 5) == 1 && this.isStright()){
            /**
            * Hand Rank = 1 - Highest 
            * Straight flush - Ranking is descending and all cards are the same suit
            * A,K,Q,J,10 - all of the same suit.
            */
            this.handType = HandType.STRIGHT_FLUSH;
            //sort by highest card

        }else{
            /**
             * Hand Rank = 9 - High card - DEFAULT VALUE
             * One highest card in the hand
             * do not need this method as any hand that is not hand above is a high hand
             */
            handType = HandType.HIGH_CARD;
            //sort by highest card
        }
    }//determineHandType()
    
    public void compareHandByCardRank(List<Card> hand){
        Collections.sort(hand, Card::compareTo);
    }
     
    /**
     * @return 
     */
    @Override
    public String toString(){
        String output = "" + this.handType + ":\n";
        Iterator handIt = hand.iterator();
        while(handIt.hasNext()){
            output += " " + handIt.next();
        }
        return output;
    }

    @Override
    public int compareTo(Hand o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}//class
