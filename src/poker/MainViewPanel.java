/**
 * How to paint a Card object on the panel.
 */
package poker;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Alexander Frolov
 */
public class MainViewPanel extends JPanel{
    
    private final String imgURL = "img\\";
    private final List<Image> handImages = new ArrayList();
    private Bank bank;
    
    
    public MainViewPanel(){
        bank = new Bank();
        bank.dealNewRound();
        this.initPanel();
    }
    
    private void initPanel(){
        this.setBackground(Color.green);
        this.getHandImages(bank.getPlayer(PlayerType.PLAYER_1).getHand());
    }
    
    @Override
     public void paintComponent(Graphics g) {
        super.paintComponent(g);       
//        g.drawString("This is my custom Panel!",10,20);
//        g.setColor(Color.GREEN); //Sets color of the font to green
        // Draw Text
//        try {
//            //for each card in hand draw image
//            Image testImg = ImageIO.read(new File("img//ACE D.png"));
//            g.drawImage(testImg, WIDTH, HEIGHT, this);
//        } catch (IOException ex) {
//            Logger.getLogger(MainViewPanel.class.getName()).log(Level.SEVERE, null, ex);
//        }
            int x = 200;
            int y = 370;
        for(Image img: this.handImages){
            g.drawImage(img, x, y, this);
            x += 90;
        }
    }  
     
     private void getHandImages(Hand hand){
        
        Image cardImage = null;
         
         for (Card aCard : hand) {
            try {
                
                cardImage = ImageIO.read(new File(imgURL + aCard.toString() + ".png"));
                System.out.println("L62 MainViewPanel ImageIO.read => " + imgURL + aCard.toString() + ".png");
                System.out.println("cardImage != null = " + (cardImage != null));
                        
            } catch (IOException ex) {
                System.out.println("File does not exist = " + imgURL + aCard.toString() + ".png");
                Logger.getLogger(MainViewPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(cardImage != null){
                this.handImages.add(cardImage);
            }
        }
     }
    
}//class
