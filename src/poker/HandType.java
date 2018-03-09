/*
 * Describes Hands and their values the lower the Enum the higher the Hand.
 */
package poker;

/**
 *
 * @author Alexander Frolov
 */
public enum HandType {
    NOT_HAND,
    /**
     * Hand Rank = 1 - Highest 
     * Straight flush 
     * - Ranking is descending and all cards are the same suit
     * A,K,Q,J,10 - all of the same suit.
     */
    STRIGHT_FLUSH,
    
    /**
     * Hand Rank = 2
     * Four of a Kind 
     * - 4 cards of the same value. 5th can be any
     * 5,5,5,5,7
     */
    FOUR_OF_A_KIND,
    
    /**
     * Hand Rank = 3
     * Full house
     * - 3 of the same kind plus 1 pair of the same kind
     * 6,6,6,K,K = (3 + 2)
     */
    FULL_HOUSE,
    
    /**
     * Hand Rank = 4
     * Flush
     * - All cards are the same suit.
     * How to determine which Flush is the strongest?
     */
    FLUSH,
    
    /**
     * Hand Rank = 5
     * - Straight All cards are in descending order. Suits are different
     * if suits are the same it is Straight Flush (Straight + Flush)
     * 10,9,8,7,6
     */
    STRIGHT,
    
    /**
     * Hand Rank = 6
     * - Three of a kind
     * Three cards of the same Rank. Others do not match
     * If match it is Full House (3 + 2 - one pair)
     * Q,Q,Q,4,7
     */
    THREE_OF_A_KIND,
    
    /**
     * Hand Rank = 7
     * - Two pair. Suit is different for all
     * K,K,7,7,3 (2 + 2 + 1)
     */
    TWO_PAIRS,
    
    /**
     * Hand Rank = 8
     * - One pair. Suits can be any
     * J,J,8,7,2 (2 + 1 + 1 + 1)
     */
    ONE_PAIR,
    
    /**
     * Hand Rank = 9
     * - High card
     * One highest card in the hand
     * 
     */
    HIGH_CARD;
}
