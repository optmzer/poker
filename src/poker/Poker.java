/*
 * 
 */
package poker;

import javax.swing.SwingUtilities;

/**
 * Player 0 is a Computer
 * Player 1 is Player 1 ... and so on
 * @author Alexander Frolov
 */
public class Poker {

    private static PokerView pView;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            pView = new PokerView();
        });
        
//        new Game().startGame();
        
    }//main()
    
}//class
