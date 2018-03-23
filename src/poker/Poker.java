/*
 * 
 */
package poker;

import java.util.InputMismatchException;
import java.util.List;
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
            } catch (InputMismatchException e) {
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
        Player computer = bank.getPlayer(0);
        Player player1 = bank.getPlayer(1);
        System.out.println("How much do you want to bet? You got: $" + player1.getWallet());

        System.out.print("> ");//Prompt.
        int betAmount = Integer.parseInt(scan.nextLine());
        if( player1.getPlayerType() == PlayerType.PLAYER_1 && player1.ableToBet(betAmount)){
            //bank record the potSize.
            bank.addToPot(player1.rise(betAmount));
            //Make computer bet same as player for now.
            bank.addToPot(computer.rise(betAmount));
        }
        
        System.out.println("Pot size $" + bank.getPot());
        
        System.out.println("You have $" + player1.getWallet() + " left");
        System.out.println("Computer $" + computer.getWallet() + " left");
    }//showMakeBet()
    
    public static void showPlayersBallance(Bank bank){
        System.out.println("   ================== Balans: ================= ");
        bank.getPlayers().forEach(player -> {
            System.out.print("     " + player.getPlayerType() + ": $" + player.getWallet());
        });
        System.out.println("\n   ============================================ ");
    }
    
    private static void showOfferCardSwap(){
        
    }
    
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
