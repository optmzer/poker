/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *  Contains an array of 52 playing cards
 * @author Alexander Frolov
 */
public class Deck {
    //Create array of Cards.
    private final List<Card> deck = new ArrayList<>();
    
    public Deck(){

        //Populate the Deck with 52 cards in sequence
        for(Enum suit : CardSuit.values()){
            for(Enum rank : CardRank.values()){
                this.deck.add(new Card(suit, rank));
            }
        }
//      Every day I'm shuffling - Shuffle the Deck
        this.shuffleCards();
    }//constructor
    
    
    //array of cards
    //Randomly populate deck with 4 decks by 52
    //As you exhause deck replase it with new one for new card deal.
    
    //deal cards
    /**
     * Creates a hand of 5 cards.
     * @return 
     */
    public Hand dialHand(){
        ArrayList<Card> hand = new ArrayList<>();
        for(int i = 0; i < 5; ++i){
            hand.add(this.deck.get(0));
            this.deck.remove(0);
//            System.out.println("L55 Hand = " + hand.get(i).getClass());
        }
//        System.out.println("L58 Hand = " + hand);
        return (new Hand(hand));
    }
    
    //mix cards
    public final void shuffleCards(){
        Collections.shuffle(this.deck);
    }//shuffleCars()
    
    @Override
    public String toString(){
        String output = "Print out all deck\n";
        Iterator deckIt = deck.iterator();
        int counter = 0;
        while(deckIt.hasNext()){
            output += ++counter + " - " + deckIt.next() + "\n";
        }
        return output;
    }
}
