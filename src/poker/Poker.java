/*
 * 
 */
package poker;

import javax.swing.SwingUtilities;

/**
 * DB connection data.
 * DB name:     pokerDB
 * User Name:   zfb1978
 * password:    12345
 * Player 0 is a Computer
 * Player 1 is Player 1 ... and so on
 * @author Alexander Frolov
 */
public class Poker {

    private static PokerController controller;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    controller = new PokerController(new Bank(), new MainViewPanel());
                }
            }
        ); 
//        new Game().startGame();
        
    }//main()
    
}//class
