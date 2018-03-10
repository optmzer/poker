/*
 * 
 */
package poker;

import java.util.InputMismatchException;
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
                        System.out.println("L37 Poker: Player 1 = " + bank.getPlayer(1).getHand());
                                        
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
