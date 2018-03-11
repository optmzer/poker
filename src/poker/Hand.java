/**
 * Hand ranking 
 * Rank = 1 - Highest
 * Rank = 9 - Lowest
 * Hand ranking within the same hand
 * The Highest cards in the same hand win.
 * eg. Four of a kind. 5,5,5,5,2 is lower then 6,6,6,6,2
 */
package poker;

import java.util.*;

/**
 *
 * @author Alexander Frolov
 */
public class Hand {
    
    //compute stright away hand tipe 
    //and the way to compare to another hand which is higher.
    
    private List<Enum> hand; 
    private Enum handType;
    
    public Hand(List<Enum> hand){
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
    
    
    //Replace a card by index
    
    //compare Hand to another hand
    
    //TODO: What to do with the money if hands are equal in Rank?
    
    public final void sortHand(List<Enum> hand){
        
        Collections.sort(hand, new Comparator<Enum>(){
            @Override
            public int compare(Enum o1, Enum o2){
                return o1.ordinal() - o2.ordinal();
            }
        });
        
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
                if(hand.get(i).ordinal() - hand.get(i + 1).ordinal() == 1){
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
        
        Iterator handIter = this.hand.iterator();
//        int value = 1;
//        System.out.println("L135 Hand = " + this.hand);
        
        /**
         * Creates a Map with key = Card Rank, value - number of repetitions
         */
        while(handIter.hasNext()){
            String[] tokens = handIter.next().toString().split("_");
            keyRank = tokens[0];
            keySuit = tokens[1];
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
        }//while
        
        //Prints out set and values of the hand
//        suitMap.keySet().forEach((key) -> {
//            System.out.println("L117 Hand: suitMap = " + key + " " + suitMap.get(key));
//        });
        
        handMapRanks = rankMap.values();
        handMapSuits = suitMap.values();
        
        if(handMapRanks.contains(4)){
            /**
            * Hand Rank = 2, 
            * 4 of a Kind - 4 cards of the same value. 5th can be any
            * 5,5,5,5,7
            */
            this.handType = HandType.FOUR_OF_A_KIND;
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
            }
        }else if(this.isStright()){
            /**
            * Hand Rank = 5
            * - Straight All cards are in descending order. 
            * 10,9,8,7,6
            */
            this.handType = HandType.STRIGHT;
        }else if(Collections.frequency(handMapSuits, 5) == 1){
            /**
            * Hand Rank = 4
            * Flush - All cards are the same suit.
            * How to determine which Flush is the strongest?
            */
            this.handType = HandType.FLUSH;
        }else if(Collections.frequency(handMapSuits, 5) == 1 && this.isStright()){
            /**
            * Hand Rank = 1 - Highest 
            * Straight flush - Ranking is descending and all cards are the same suit
            * A,K,Q,J,10 - all of the same suit.
            */
            this.handType = HandType.STRIGHT_FLUSH;
        }else{
            /**
             * Hand Rank = 9 - High card - DEFAULT VALUE
             * One highest card in the hand
             * do not need this method as any hand that is not hand above is a high hand
             */
            handType = HandType.HIGH_CARD;
        }
    }//determineHandType()
    
    
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
    
}//class
