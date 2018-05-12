/**
 * Attache listener to the cards 
 * Make the controller
 * Add File menu with save load
 * 
 */
package poker;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Alexander Frolov
 */
public class MainViewPanel extends JPanel implements ActionListener{
    
    private final String imgURL = "img\\";
    private List<Player> players;
    private Hand player0_hand;
    private Hand player1_hand;
    private final Bank bank;
    private boolean showCards = false;
    
    private JPanel controlPanel;
    
    private JPanel moneyPanel;
    private JLabel potSize;
    private JLabel p0_wallet;
    private JLabel p1_wallet;


    private JLabel comments;
    
    public MainViewPanel(){
        bank = new Bank();
        players = new ArrayList<>();
        initPanel();
    }
    
    private void initPanel(){
        this.setBackground(Color.green);
        this.setLayout(new BorderLayout());
        initMoneyPanel();
        this.add(moneyPanel, BorderLayout.WEST);
    }
    
    private void initMoneyPanel(){
        moneyPanel = new JPanel(new GridLayout(0, 1));
//        moneyPanel.setPreferredSize(new Dimension(150, this.getHeight()));
        moneyPanel.setBackground(Color.GREEN);

        potSize = new JLabel();
        potSize.setText("     Pot $0");
        p0_wallet = new JLabel();
        p1_wallet = new JLabel();

        moneyPanel.add(new JLabel(""));
        moneyPanel.add(p0_wallet);
        moneyPanel.add(new JLabel(""));
        moneyPanel.add(new JLabel(""));
        moneyPanel.add(potSize);
        moneyPanel.add(new JLabel(""));
        moneyPanel.add(new JLabel(""));
        moneyPanel.add(p1_wallet);
        moneyPanel.add(new JLabel(""));

    }
    
//    ============ SETTERS/GETTERS ===============
    
    private void setHandImages(Hand hand){
        for (Card aCard : hand) {
            try {
                aCard.setImage(ImageIO.read(new File(imgURL + aCard.toString() + ".png")));
            } catch (IOException ex) {
                System.out.println("File does not exist = " + imgURL + aCard.toString() + ".png");
            }
        }
    }

    public void setPlayers(List<Player> players){
       this.players = players;
       for(Player player: players){
           setPlayerWallet(player);
       }
    }
    
    public void setPlayerWallet(Player player){
        if(player.getPlayerType() == PlayerType.COMPUTER){
            p0_wallet.setText("     Kelly $ " + player.getWallet());
        }else{
            p1_wallet.setText("     Your $ " + player.getWallet());
        }
    }
    
    public void setPotSize(int pot){
        this.potSize.setText("     Pot $ " + pot);
    }
    
    public void updateView(List<Player> players){
        setPlayers(players);
        
        for(Player p: players){
            updateHand(p);
        }
        
        repaint();
//        System.out.println("L116 MainViewPanel View Updated");
    }
    
    @Override
     public void paintComponent(Graphics g) {
        super.paintComponent(g);       
//        g.drawString("This is my custom Panel!",10,20);
//        g.setColor(Color.GREEN); //Sets color of the font to green
            //Player 0 cards shirt up.
            if(showCards){
                drawPlayerCards(player0_hand, 200, 40, g);
                drawPlayerCards(player1_hand, 200, 350, g);
            }else{
                drawCardShirts(g);
                drawPlayerCards(player1_hand, 200, 350, g);
            }
            //player 1 cards face up
    }  
     
    private void drawPlayerCards(Hand hand,int x, int y, Graphics g){
        System.out.println("Player 1 = " + hand);
        for(Card aCard: hand){
            g.drawImage(aCard.getCardImage(), x, y, this);
            x += 90;
        }
    }
     
    private void drawCardShirts(Graphics g){
        int player0_x = 200;
        int player0_y = 50;
        Image cardShirt = null;
        try {
            cardShirt = ImageIO.read(new File("img\\SHIRT.png"));
        } catch (IOException e) {
            System.err.println("Cannot find SHIRT.png = " + e.getLocalizedMessage());
        }
        for(Card card: player0_hand){
            g.drawImage(cardShirt, player0_x, player0_y, this);
            player0_x += 90;
        }
    } 
    
    public void showCards(boolean show){
        this.showCards = show;
    }
        
    
    public void updateHand(Player player){
        setHandImages(player.getHand());
        if(player.getPlayerType() == PlayerType.COMPUTER){
            this.player0_hand = player.getHand();
        }else{
            this.player1_hand = player.getHand();
        }
        
    }//updatePlayerHand()
     
   
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("L92 MainViewPanel action registered = " + e.getSource());
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}//class
