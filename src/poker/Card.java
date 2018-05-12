/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.awt.Image;

/**
 *
 * @author Alexander Frolov
 */
public class Card implements Comparable<Card>{
    private Enum card_suit;
    private Enum card_rank;
    private Image img;
    
    public Card(Enum card_suit, Enum card_rank){
        this.card_suit = card_suit;
        this.card_rank = card_rank;
    }
    
    public Enum getSuit(){
        return this.card_suit;
    }
    public void setSuit(Enum card_suit){
        this.card_suit = card_suit;
    }
    
    public Enum getRank(){
        return this.card_rank;
    }
    public void setRank(Enum card_rank){
        this.card_rank = card_rank;
    }
    
    public void setImage(Image img){
        this.img = img;
    }
    
    public Image getCardImage(){
        return this.img;
    }
    
    /**
     * @param other_card
     * @return 
     */
    public boolean isSameSuit(Card other_card){
        boolean output = false;
        
        if(other_card != null && (other_card instanceof Card)){
            output = this.getSuit().equals(other_card.getSuit());
        }
        return output;
    }
    
    /**
     * To suppress warning I need to check type with instanceof
     * other check like Card.class.isInstance(obj) will not
     * get rid of the warning
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj){
        boolean output = false;
        if(obj != null && (obj instanceof Card)){
            final Card other_card = (Card)obj;
            
            if(other_card.getRank() != null && other_card.getSuit() != null){
                if(this.getRank() == other_card.getRank() 
                        && this.getSuit() == other_card.getSuit()){
                    output = true;
                }
            }
        }
        return output;
    }
    
    @Override
    public int hashCode(){
        int hash = 37;
        hash = hash*this.card_suit.hashCode() + this.card_rank.hashCode(); 
        return hash;
    }
    
    /**
     * Compares Ranks of the cards. Ignores suits.
     * @param other_card
     * @return 
     */
    @Override
    public int compareTo(Card other_card) {
        return this.getRank().compareTo(other_card.getRank());
    }
    
    @Override
    public String toString(){
//        String output = "[" + getRank() + " " + getSuit().toString().charAt(0) + "]";
        String output = getRank() + " " + getSuit().toString().charAt(0);

        return output;
    }
}//class
