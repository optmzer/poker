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
        
        Bank bank;
        Scanner scan;
        
        do{
            bank = new Bank();
            scan = new Scanner(System.in);
            //this type of try block auto closes resource.
            try {
                    System.out.println("===== Welcome to the Poker Game =====");
                    System.out.println("Press (1) to Play");
                    System.out.println("Press (2) to Saved Game");
                    System.out.println("Press (3) to Exit");

                    System.out.print("> ");//Prompt.

                    user_input = Integer.parseInt(scan.nextLine());

                    switch(user_input){
                        case 1:
                            System.out.println("Play Game");
                            System.out.println("L41 Poker: "+ bank.getPlayer(0).getPlayerType() + " " + bank.getPlayer(0).getHand());
                            System.out.println("L42 Poker: "+ bank.getPlayer(1).getPlayerType() + " " + bank.getPlayer(1).getHand());
                                List<Player> winners = bank.getWinner();
                            if(winners.size() > 1){
                                //there are 2 winners
                                System.out.println("L45 It is split :");
                                System.out.println("\nL46 Poker: The winner is: " + winners.get(0).getPlayerType() + " with " + winners.get(0).getHand().getHandType());          
                                System.out.println("\nL47 Poker: The winner is: " + winners.get(1).getPlayerType() + " with " + winners.get(1).getHand().getHandType());          
                            }else{
                                System.out.println("\nL49 Poker: The winner is: " + winners.get(0).getPlayerType() + " with " + winners.get(0).getHand().getHandType());          
                            }
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
        }while(user_input != 3);
        
        scan.close();
        
    }//main()
}//class
