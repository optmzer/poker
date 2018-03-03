/**
 * Hand ranking 
 * Rank = 1 - Highest
 * Rank = 9 - Lowest
 * Hand ranking within the same hand
 * The Highest cards in the same hand win.
 * eg. Four of a kind. 5,5,5,5,2 is lower then 6,6,6,6,2
 */
package poker;

/**
 *
 * @author Alexander Frolov
 */
public class Hand {
    
    Card[] hand; 
    
    public Hand(Card[] hand){
        this.hand = hand;
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
     */
    
    /**
     * Hand Rank = 2
     * Four of a Kind 
     * - 4 cards of the same value. 5th can be any
     * 5,5,5,5,7
     */
    
    /**
     * Hand Rank = 3
     * Full house
     * - 3 of the same kind plus 1 pair of the same kind
     * 6,6,6,K,K = (3 + 2)
     */
    
    /**
     * Hand Rank = 4
     * Flush
     * - All cards are the same suit.
     * How to determine which Flush is the strongest?
     */
    
    /**
     * Hand Rank = 5
     * - Straight All cards are in descending order. Suits are different
     * if suits are the same it is Straight Flush (Straight + Flush)
     * 10,9,8,7,6
     */
    
    /**
     * Hand Rank = 6
     * - Three of a kind
     * Three cards of the same Rank. Others do not match
     * If match it is Full House (3 + 2 - one pair)
     * Q,Q,Q,4,7
     */
    
    /**
     * Hand Rank = 7
     * - Two pair. Suit is different for all
     * K,K,7,7,3 (2 + 2 + 1)
     */
    
    /**
     * Hand Rank = 8
     * - One pair. Suits can be any
     * J,J,8,7,2 (2 + 1 + 1 + 1)
     */
    
    /**
     * Hand Rank = 9
     * - High card
     * One highest card in the hand
     * 
     */
    
}//class
