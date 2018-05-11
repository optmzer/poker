/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

/**
 *
 * @author Alexander Frolov
 */
public class CardView extends JPanel implements ActionListener{
    
    private final String cardData;
    
    public CardView(String cardData){
        this.setSize(250, 350);
        this.cardData = cardData;
        this.setBackground(Color.red);
    }

    @Override
     public void paintComponent(Graphics g) {
        super.paintComponent(g);       
//        g.setColor(Color.GREEN); //Sets color of the font to green
        // Draw Text
//        g.
        g.drawString(cardData,10,20);
    }  
    
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}//class
