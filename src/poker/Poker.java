/*
 * @author Alexander Frolov
 */
package poker;

import javax.swing.SwingUtilities;

public class Poker {

    private static PokerController controller;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    controller = new PokerController();
                }
            }
        ); 
    }//main()
}//class
