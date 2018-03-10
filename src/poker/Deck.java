/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 *  Contains an array of 52 playing cards
 * @author Alexander Frolov
 */
public class Deck {
    //Create array of Cards.
    private final List<Enum> deck = new ArrayList<>();
    
    public Deck(){
        //List of Spades
        Enum[] spades = Spades.class.getEnumConstants();
        //List of Diamonds
        Enum[] diamonds = Diamonds.class.getEnumConstants();
        //List of Hearts.
        Enum[] hearts = Hearts.class.getEnumConstants();
        //List of Clubs
        Enum[] clubs = Clubs.class.getEnumConstants();

        this.deck.addAll(Arrays.asList(spades));
        this.deck.addAll(Arrays.asList(diamonds));
        this.deck.addAll(Arrays.asList(hearts));
        this.deck.addAll(Arrays.asList(clubs));
        
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
        ArrayList<Enum> hand = new ArrayList<>();
        for(int i = 0; i < 5; ++i){
            hand.add(this.deck.get(0));
            this.deck.remove(0);
//            System.out.println("L55 Hand = " + hand.get(i).getClass());
        }
//        System.out.println("L58 Hand = " + hand);
        
        Collections.sort(hand, new Comparator<Enum>(){
            @Override
            public int compare(Enum o1, Enum o2){
                return o1.ordinal() - o2.ordinal();
            }
        });
        
        Collections.reverse(hand);
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
