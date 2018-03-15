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
        
        int user_input;
        
        Bank bank = new Bank();
        
        //this type of try block auto closes resource.
        try ( Scanner scan = new Scanner(System.in)) {
            do{
                System.out.println("===== Welcome to the Poker Game =====");
                System.out.println("Press (1) to Play");
                System.out.println("Press (2) to Saved Game");
                System.out.println("Press (3) to Exit");

                System.out.print("> ");//Prompt.

                user_input = scan.nextInt();
                
                switch(user_input){
                    case 1:
                        System.out.println("Play Game");
                        System.out.println("L41 Poker: "+ bank.getPlayer(0).getPlayerType() + " " + bank.getPlayer(0).getHand());
                        System.out.println("L42 Poker: "+ bank.getPlayer(1).getPlayerType() + " " + bank.getPlayer(1).getHand());
                            List<Player> winners = bank.getWinner();
                        if(winners.size() > 1){
                            //there are 2 winners
                            System.out.println("L45 It is split :");
                            System.out.println("L46 Poker: The winner is: " + winners.get(0).getPlayerType() + " with " + winners.get(0).getHand().getHandType());          
                            System.out.println("L47 Poker: The winner is: " + winners.get(1).getPlayerType() + " with " + winners.get(1).getHand().getHandType());          
                        }else{
                            System.out.println("L49 Poker: The winner is: " + winners.get(0).getPlayerType() + " with " + winners.get(0).getHand().getHandType());          
                        }
                        return;
                    case 2:
                        System.out.println("Continue Saved Game");
                        break;
                    case 3:
                        System.out.println("Program Exit");
                        break;
                    default:
                        System.out.println("No such number. Try again.\n");
                }
            }while(user_input != 2);
           
        } catch (InputMismatchException e) {
            System.err.println("Please type an integer and try again.");
        }
    }//main()
}//class
