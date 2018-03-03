/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

/**
 *
 * @author Alexander Frolov
 */
public class Card {
    private Suit card_suit = null;
    private Rank card_rank = null;
    
    public Card(Suit card_suit, Rank card_rank){
        this.card_suit = card_suit;
        this.card_rank = card_rank;
    }
    
    public Suit getSuit(){
        return this.card_suit;
    }
    public void setSuit(Suit card_suit){
        this.card_suit = card_suit;
    }
    
    public Rank getRank(){
        return this.card_rank;
    }
    public void setRank(Rank card_rank){
        this.card_rank = card_rank;
    }
    
    @Override
    public String toString(){
        String output = "" + getRank() + "" + getSuit();
        return output;
    }
    
}//class
