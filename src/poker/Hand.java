/**
 * Hand ranking 
 * Rank = 1 - Highest
 * Rank = 9 - Lowest
 * Hand ranking within the same hand
 * The Highest cards in the same hand win.
 * eg. Four of a kind. 5,5,5,5,2 is lower then 6,6,6,6,2
 */
package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Alexander Frolov
 */
public class Hand {
    
    List<Enum> hand; 
    
    public Hand(List<Enum> hand){
                
        if(hand.size() == 5){
            this.hand = hand ;
        System.out.println("L26 Hand. Hand created = " + hand);
        }
    }
    
    //Getters - Setters
    
    //Replace a card by index
    
    //compare Hand to another hand
    
    //TODO: What to do with the money if hands are equal in Rank?
    
    /**
     * Hand Rank = 1 - Highest 
     * Straight flush 
     * - Ranking is descending and all cards are the same suit
     * A,K,Q,J,10 - all of the same suit.
     * @return 
     */
    public Enum isStrightFlush(){
        return HandType.NOT_HAND;

    }
    /**
     * Hand Rank = 2
     * Four of a Kind 
     * - 4 cards of the same value. 5th can be any
     * 5,5,5,5,7
     * @return 
     */
    public Enum isFourOfKind(){
        //TODO: sprt highest to lowest. Check pattern.
        return HandType.NOT_HAND;
    }
    
    /**
     * Hand Rank = 3
     * Full house
     * - 3 of the same kind plus 1 pair of the same kind
     * 6,6,6,K,K = (3 + 2)
     * @return 
     */
    public Enum isFullHouse(){
        return HandType.NOT_HAND;
    }
    
    /**
     * Hand Rank = 4
     * Flush
     * - All cards are the same suit.
     * How to determine which Flush is the strongest?
     * @return 
     */
    public Enum isFlush(){
        for(int i = 1; i < this.hand.size(); ++i){
            if(this.hand.get(0) != this.hand.get(i)){
                return HandType.FLUSH;
            }
        }
        return HandType.NOT_HAND;
    }
    
    /**
     * Hand Rank = 5
     * - Straight All cards are in descending order. Suits are different
     * if suits are the same it is Straight Flush (Straight + Flush)
     * 10,9,8,7,6
     * @return 
     */
    public Enum isStright(){
        return HandType.NOT_HAND;
    }
    
    /**
     * Hand Rank = 6
     * - Three of a kind
     * Three cards of the same Rank. Others do not match
     * If match it is Full House (3 + 2 - one pair)
     * Q,Q,Q,4,7
     * @return 
     */
    public Enum isThreeOfKind(){
        return HandType.NOT_HAND;
    }
    
    /**
     * Hand Rank = 7
     * - Two pair. Suit is different for all
     * K,K,7,7,3 (2 + 2 + 1)
     * @return 
     */
    public Enum isTwoPair(){
        return HandType.NOT_HAND;
    }
    
    /**
     * Hand Rank = 8
     * - One pair. Suits can be any
     * J,J,8,7,2 (2 + 1 + 1 + 1)
     * @return 
     */
    public Enum isOnePair(){
        
        
        return HandType.NOT_HAND;
    }
    
    /**
     * Hand Rank = 9
     * - High card
     * One highest card in the hand
     * do not need this method as any hand that is not hand above is a high hand
     */
    
    /**
     * 
     */
    
    
    @Override
    public String toString(){
        String output = "Hand = ";
        Iterator handIt = hand.iterator();
        while(handIt.hasNext()){
            output += " " + handIt.next();
        }
        return output;
    }
    
}//class
