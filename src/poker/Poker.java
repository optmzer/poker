/*
 * 
 */
package poker;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Alexander Frolov
 */
public class Poker {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int user_input;
        Deck deck1 = new Deck();

        //this type of try block auto closes resource.
        try ( Scanner scan = new Scanner(System.in)) {
            do{
                System.out.println("===== Welcome to the Poker Game =====");
                System.out.println("Press (1) to Play");
                System.out.println("Press (2) to Exit");
                
                System.out.print("> ");//Prompt.

                user_input = scan.nextInt();
                
                switch(user_input){
                    case 1:
                        System.out.println("Play Game");
                        System.out.println("Deck = " + deck1.dialHand());
                                        
                        return;
                    case 2:
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
