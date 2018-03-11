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
public class Player {
    private int wager = 0;
    private Hand hand = null;
    private Enum player_type = null;
    
    public Player(int wager, Hand hand, Enum player_type){
        this.wager = wager;
        this.hand = hand;
        this.player_type = player_type;
    }
    
    public Player(Hand hand, Enum player_type){
        this(500, hand, player_type);
    }
    
    public void setWager(int wager){
        this.wager = wager;
    }
    
    public int getWager(){
        return this.wager;
    }
    
    public void setHand(Hand hand){
        this.hand = hand;
    }
    
    public Hand getHand(){
        return this.hand;
    }
    
    /**
     * Rise - to rise the bet
     * A player who thinks he has a good hand (or who wants the other players
     * to think he has a good hand) may increase the wager required to continue
     * playing.
     */
    public void rise(){
        
    }
    /**
     * Fold - to let go of the bet.
     * A player who thinks his hand is not good enough to win and who does
     * not want to wager the increased amount may lay down his cards. He
     * cannot win the hand, but he also will not lose any more chips.
     */
    public void fold(){
        
    }
    
    /**
     * Call - to equal the amount of wager
     * Once a player has raised the stakes, each player must decide whether
     * to raise the stakes again, to give in and fold his hand, or to call,
     * which means to equal the amount wagered by the player who raised.
     */
    public void call(){
        
    }
    
    /**
     * Check - Pass the turn if no one raised the bet.
     * If no one has increased the wager required to continue, a player may
     * stand pat by checking, or passing on his option to bet.
     */
    
    public void check(){
        
    }
    
    @Override
    public String toString(){
        String output = "Hand: " + this.hand + "\n";
        output += "Wager: " + this.wager + "\n";
        return output;
    }

    public Enum getType() {
        return this.player_type;
    }
}//class
