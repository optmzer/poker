/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.util.*;

/**
 *
 * @author Alexander Frolov
 */
public class Player {
    
    private Enum betType = BetType.CHECK;
    private int wallet = 0;
    private Hand hand = null;
    private Enum player_type = null;
    
    public Player(int wallet, Hand hand, Enum player_type){
        this.wallet = wallet;
        this.hand = hand;
        this.player_type = player_type;
    }
    
    public Player(Hand hand, Enum player_type){
        this(500, hand, player_type);
    }
    
    public void setWallet(int wallet){
        this.wallet = wallet;
    }
    
    public int getWallet(){
        return this.wallet;
    }
    
    public void addToWallet(int pot) {
        this.wallet += pot;
    }
    
    public void setHand(Hand hand){
        this.hand = hand;
    }
    
    public Hand getHand(){
        this.hand.determineHandType();
        return this.hand;
    }
    
    public Hand getUndeterminedHand(){
        return this.hand;
    }
    
    public boolean ableToBet(int betAmount) {
        return (this.getWallet() != 0 && betAmount > 0 && (this.getWallet() - betAmount) > 0);
    }
    
    public Enum getBetType(){
        return this.betType;
    }
    
    /**
     * Rise - to rise the bet
        A player who thinks he has a good hand (or who wants the other players
        to think he has a good hand) may increase the wallet required to continue
        playing.
     * 
     * @param howMuch
     * @return howMuch if enough money or If there is not enough money return 0.
     */
    public int rise(int howMuch){
        if(this.getWallet() != 0 && (this.getWallet() - howMuch) > 0){
            this.betType = BetType.RISE;
            this.setWallet(this.getWallet() - howMuch);
            return howMuch;
        }
        return 0;
    }
    //rise()
    
    /**
     * Fold - to let go of the bet.
     * A player who thinks his hand is not good enough to win and who does
 not want to wallet the increased amount may lay down his cards. He
 cannot win the hand, but he also will not lose any more chips.
     */
    public void fold(){
        this.betType = BetType.FOLD;
    }
    
    /**
     * Call - to equal the amount of wallet
 Once a player has raised the stakes, each player must decide whether
 to raise the stakes again, to give in and fold his hand, or to call,
 which means to equal the amount wagered by the player who raised.
     * @param wager
     * @return wallet if enough money or If there is not enough money return 0.
     */
    public int call(int wager){
        this.betType = BetType.RISE;
        return this.rise(wager);
    }
    
    /**
     * Check - Pass the turn if no one raised the bet.
     * If no one has increased the wallet required to continue, a player may
 stand pat by checking, or passing on his option to bet.
     */
    public void check(){
        this.betType = BetType.CHECK;
    }
    
    /**
     * Bank gives card index to remove and new Card from Deck to add
     * key: card index in the Hand
     * value: Card object.
     * @param cardsToSwap
     */
    public void swapCards(Map cardsToSwap){
        if(cardsToSwap != null){
            //get set of keys
            for(Object key: cardsToSwap.keySet()){
                int index = ((int)key) - 1;
                //replace card in the hand with new one.
                this.hand.removeCard(index);
                this.hand.addCard(index, (Card)cardsToSwap.get(key));
//                System.out.println("L125 Player New Hand = " + this.hand);
            }
        }
    }//swapCards()
    
    @Override
    public String toString(){
        String output = "Hand: " + this.hand + "\n";
        output += this.getPlayerType() + " Wallet: " + this.getWallet() + "\n";
        return output;
    }

    public Enum getPlayerType() {
        return this.player_type;
    }
}//class
