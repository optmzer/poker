/*
 * 
 */
package poker;

import java.util.*;
import java.util.Scanner;

/**
 * Player 0 is a Computer
 * Player 1 is Player 1 ... and so on
 * @author Alexander Frolov
 */
public class Poker {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int user_input = 0;
        
        Bank bank = new Bank();
        Scanner scan;
        
       
        
        do{
        showMainMenu();
            bank.dealNewRound();
            scan = new Scanner(System.in);
            //this type of try block auto closes resource.
            try {

                    user_input = Integer.parseInt(scan.nextLine());

                    switch(user_input){
                        case 1:
                            //game logic
//                          1 - Dial cards
                            showHands(bank);
                            showPlayersBallance(bank);
                            
//                          2 - First Round of Betting
                            showMakeBet(bank, scan);
                            
//                          3 - trade cards
                            showOfferCardSwap(bank, scan);
                            
//                          4 - Second round of Betting
                            showHands(bank);
                            showPlayersBallance(bank);
                            
                            showMakeBet(bank, scan);

//                          5 - When betting is over check hands.

                            //rise, call, fold
                            showWinners(bank);
                            showPlayersBallance(bank);
                            break;
                        case 2:
                            System.out.println("Continue Saved Game");
                            break;
                        case 3:
                            System.out.println("Program Exit");
                            break;
                        default:
                            System.out.println("No such number. Try again.\n");
                    }
            } catch (NumberFormatException e) {
                System.out.println("Please type an option 1, 2 or 3 and try again.");
            }
            //Save game or Play again
//            showPlayAgain();
        }while(user_input != 3);
        
        scan.close();
        
    }//main()
    
    private static void showMainMenu(){
        System.out.println("\n=========== Welcome to the Poker Game ===========");
        System.out.println("=================== Draw Poker ==================");

        System.out.println("Press (1) to Play");
        System.out.println("Press (2) to Saved Game");
        System.out.println("Press (3) to Exit");
        System.out.print("> ");//Prompt.
    }//mainMenu()
    
    private static void showPlayAgain(){
        System.out.println("\n=================== Poker Game ==================");
        System.out.println("=================== Draw Poker ==================");
        System.out.println("Press (1) to Play Again");
        System.out.println("Press (2) to Save Game");
        System.out.println("Press (3) to Exit");
        System.out.print("> ");//Prompt.
    }
    
    private static void showHands(Bank bank){
        System.out.println("Play Game");
        System.out.println("\nL102 Poker: "+ bank.getPlayer(0).getPlayerType() + " " + bank.getPlayer(0).getHand());
        System.out.println("\nL103 Poker: "+ bank.getPlayer(1).getPlayerType() + " " + bank.getPlayer(1).getHand());
    }//showHands()

    private static void showMakeBet(Bank bank, Scanner scan){
        int betAmount = 0;
        int minimumBet = 2;
        boolean hasEnoughToBet = false; 
        Player computer = bank.getPlayer(PlayerType.COMPUTER);
        Player player1 = bank.getPlayer(PlayerType.PLAYER_1);
        
        //Fold Call Rise
        
        System.out.println("How much do you want to bet? You got: $" + player1.getWallet());

        //Repeat input untill get the amount
        do{
                System.out.print("bet $ > ");//Prompt.
            try{
                betAmount = Integer.parseInt(scan.nextLine());
                hasEnoughToBet = player1.ableToBet(betAmount);
                if(hasEnoughToBet && betAmount >= minimumBet){
                    //bank record the potSize.
                    bank.addToPot(player1.rise(betAmount));
                    //Make computer bet same as player for now.
                    bank.addToPot(computer.rise(betAmount));
                }else if(betAmount < minimumBet){
                    System.out.println("\n Rules of the house. Bet cannot be lower than 2. Bet amount = " + betAmount);
                }else{
                    System.out.println("\n" + player1.getPlayerType() + " has not enough funds to bet $" + betAmount);
                }
            }catch(NumberFormatException e){
                System.out.println("Please type in bet amount. \nRules of the house. Bet cannot be lower than 2");
            }
        }while(!(hasEnoughToBet && betAmount >= minimumBet));
        
        System.out.println("Pot size $" + bank.getPot());
        
        System.out.println("You have $" + player1.getWallet() + " left");
        System.out.println("Computer $" + computer.getWallet() + " left");
    }//showMakeBet()
    
    private static void showPlayersBallance(Bank bank){
        System.out.println("  ================== Balans: ================= ");
        bank.getPlayers().forEach(player -> {
            System.out.print("     " + player.getPlayerType() + ": $" + player.getWallet());
        });
        System.out.println("\n  ============================================ ");
    }
    
    /**
     * Get input from the user and pass this input to bank.swapCards()
     * @param bank
     * @param scan
     * @return 
     */
    private static void showOfferCardSwap(Bank bank, Scanner scan){
        List<Integer> cardIndexes = new ArrayList<>();
        Hand player1_hand;
        
        int swapLimit = 3;
        String tokens;
        String userInput;
        
        System.out.print("What cards do you want to swap? ");
        //Up to 3 cards. If has an Ace can swap up untill 4.
        //make a while loop for swapping and evaluating.
        for(Player aPlayer : bank.getPlayers()){
            if(aPlayer.getPlayerType() == PlayerType.PLAYER_1){
                //Get player 1 hand while we are at it.
                player1_hand = aPlayer.getHand();
                for (Card aCard : aPlayer.getHand()) {
                    if(aCard.getRank() == CardRank.ACE){
                        swapLimit = 4;
                    }
                }
            }
        }//for
        
        System.out.println("You can swap up to " + swapLimit + " cards");
        System.out.println("Enter card numbers in sequence separated space [1 3 ...]");
        System.out.print(">");
        
        //TODO:
        //Handls negative numbers by converting them to positive.
        //Get input from the Scanner parse it and make an ArrayList.
        do{
            try{
                userInput = scan.nextLine();
//                System.out.println("L188 Poker userInput = " + userInput);
                
                tokens = userInput.replaceAll("[^1-5]", "");

                if(!tokens.equals("")){
//                    System.out.println("L193 Poker tokens = " + tokens);
                    for(int i = 0; i < tokens.length() && i < swapLimit; ++i){
                        String token = "" + tokens.charAt(i);
//                        System.out.println("L196 Poker tokens = " + token);
                        cardIndexes.add(Integer.parseInt(token));
//                        System.out.println("L199 cardIndexes[" + i + "] = " + cardIndexes.get(i));
                    }
                }
            }catch(NoSuchElementException | NumberFormatException | IllegalStateException e){
                System.err.println("Error = " + e);
                System.err.println("You need to enter number from 1 to 5 like so > 1, 2, 3");
            }
            
        }while(cardIndexes.isEmpty());
        //pass cardIndexes to the bank for swapping.
        if(!cardIndexes.isEmpty()){
            bank.swopCards(PlayerType.PLAYER_1, cardIndexes);
        }
        
    }//showOfferCardSwap()
    
    private static void showWinners(Bank bank){
        List<Player> winners = bank.getWinner();
                                
        if(winners.size() > 1){
            //there are 2 winners
            System.out.println("L220 It is split :");
            System.out.println("\nL221 Poker: The winner is: " + winners.get(0).getPlayerType() + " with " + winners.get(0).getHand().getHandType());          
            System.out.println("\nL222 Poker: The winner is: " + winners.get(1).getPlayerType() + " with " + winners.get(1).getHand().getHandType());
            //Add split to computer and player1
            int split = bank.splitPot(winners.size());
            winners.get(0).addToWallet(split);
            winners.get(1).addToWallet(split);
        }else{
            System.out.println("\nL228 Poker: The winner is: " + winners.get(0).getPlayerType() + " with " + winners.get(0).getHand().getHandType());          
            //Add split to computer and player1
            winners.get(0).addToWallet(bank.getPot());
        }
    }//showTwoWinners()
    
    
        
}//class
