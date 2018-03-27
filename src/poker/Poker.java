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
        
       
        showMainMenu();
        
        do{
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
                            
//                          2 - First Round of Betting
                            showPlayersBallance(bank);
                            showMakeBet(bank, scan);
                            
//                          3 - trade cards
                            showOfferCardSwap(bank, scan);
                            
//                          4 - Second round of Betting

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
                System.err.println("Please type an integer and try again.");
            }
            //Save game or Play again
            showPlayAgain();
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
        System.out.println("\nL41 Poker: "+ bank.getPlayer(0).getPlayerType() + " " + bank.getPlayer(0).getHand());
        System.out.println("\nL42 Poker: "+ bank.getPlayer(1).getPlayerType() + " " + bank.getPlayer(1).getHand());
    }//showHands()

    private static void showMakeBet(Bank bank, Scanner scan){
        int betAmount = 0;
        boolean bettingAllowed = false; 
        Player computer = bank.getPlayer(PlayerType.COMPUTER);
        Player player1 = bank.getPlayer(PlayerType.PLAYER_1);
        System.out.println("How much do you want to bet? You got: $" + player1.getWallet());

        //Repeat input untill get the amount
        do{
                System.out.print("bet $ > ");//Prompt.
            try{
                betAmount = Integer.parseInt(scan.nextLine());
                bettingAllowed = player1.ableToBet(betAmount);
                if(bettingAllowed && betAmount > 0){
                    //bank record the potSize.
                    bank.addToPot(player1.rise(betAmount));
                    //Make computer bet same as player for now.
                    bank.addToPot(computer.rise(betAmount));
                }else if(betAmount < 0){
                    System.out.println("\n" + player1.getPlayerType() + " bet has to be positive number. Bet amount = " + betAmount);
                }else{
                    System.out.println("\n" + player1.getPlayerType() + " has not enough funds to bet $" + betAmount);
                }
            }catch(NumberFormatException e){
                System.out.println("Please type in bet amount");
//                System.out.print("bet $ > ");//Prompt.
            }
        }while(!bettingAllowed && betAmount < 0);
        
        System.out.println("Pot size $" + bank.getPot());
        
        System.out.println("You have $" + player1.getWallet() + " left");
        System.out.println("Computer $" + computer.getWallet() + " left");
    }//showMakeBet()
    
    public static void showPlayersBallance(Bank bank){
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
        int swapLimit = 3;
        String tokens[];
        System.out.print("What cards do you want to swap? ");
        //Up to 3 cards. If has an Ace can swap up untill 4.
        //make a while loop for swapping and evaluating.
        for(Player aPlayer : bank.getPlayers()){
            if(aPlayer.getPlayerType() == PlayerType.PLAYER_1){
                for (Card aCard : aPlayer.getHand()) {
                    if(aCard.getRank() == CardRank.ACE){
                        swapLimit = 4;
                    }
                }
            }
        }//for
        
        System.out.println("You can swap up to " + swapLimit + " cards");
        System.out.println("Enter numbers separated by comma [1, 3, ...] ");
        System.out.print(">");
        
        //TODO:
        //Create while loop inhere
        //Get input from the Scanner parse it and make an ArrayList.
        do{
            try{
                String userInput = scan.nextLine();
                tokens = userInput.split(",", swapLimit);
                System.out.println("L167 Poker userInput = " + userInput);

                if(tokens.length != 0){
                    for(String token : tokens){
                        cardIndexes.add(Integer.parseInt(token));
                        System.out.println("L168 Poker tokens = " + token);
                    }
                }

            }catch(NoSuchElementException e){
                System.err.println("You need to enter number from 1 to 5 like so > 1, 2, 3");
            }catch( IllegalStateException e){
                System.err.println("You need to enter number from 1 to 5 like so > 1, 2, 3");
            }
            
        }while(cardIndexes.isEmpty());
        
        if(!cardIndexes.isEmpty()){
            //Get deck to deal you cards
            //make a Map from it
            //pass it to player1 to swap.
        }
        
    }//showOfferCardSwap()
    
    private static void showWinners(Bank bank){
        List<Player> winners = bank.getWinner();
                                
        if(winners.size() > 1){
            //there are 2 winners
            System.out.println("L45 It is split :");
            System.out.println("\nL46 Poker: The winner is: " + winners.get(0).getPlayerType() + " with " + winners.get(0).getHand().getHandType());          
            System.out.println("\nL47 Poker: The winner is: " + winners.get(1).getPlayerType() + " with " + winners.get(1).getHand().getHandType());
            //Add split to computer and player1
            int split = bank.splitPot(winners.size());
            winners.get(0).addToWallet(split);
            winners.get(1).addToWallet(split);
        }else{
            System.out.println("\nL49 Poker: The winner is: " + winners.get(0).getPlayerType() + " with " + winners.get(0).getHand().getHandType());          
            //Add split to computer and player1
            winners.get(0).addToWallet(bank.getPot());
        }
    }//showTwoWinners()
    
    
        
}//class
