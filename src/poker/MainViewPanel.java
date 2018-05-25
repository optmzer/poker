/**
 * Attache listener to the cards 
 * Make the controller
 * Add File menu with save load
 * Add label to show the bet.
 */
package poker;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Alexander Frolov
 */
public class MainViewPanel extends JPanel implements ActionListener{
    
    private final String imgURL = "img\\";
    private List<Player> players;
    private Hand player0_hand;
    private Hand player1_hand;
    private boolean showCards = false;
   
//Player 1 card Panel
    private JPanel cardPanel;
    private List<JLabel> cardLabels;
    
    
//moneyPanel wallet size of players and pot size.
    private JPanel moneyPanel;
    private JLabel potSize;
    private JLabel p0_wallet;
    private JLabel p1_wallet;
    
//Shows betting slider
    private JPanel bettingPanel;
    private JSlider bettingSlider;
    private JLabel bettingValueLabel;
    private int bettingMax;
    private int bettingValue = 2; //Minimum bet is $2

//Hints about the game
    private JPanel commentsPanel;
    
//    Control panel 
    private JPanel controlsPanel;
    private JLabel swap;
    private JLabel skip;
    private JLabel rise;
    private JLabel call;
    private JLabel fold;
    private JLabel comments;
    
    public MainViewPanel(List<Player> players){
        this.players = players;
        cardLabels = new ArrayList<>();
        initPanel();
    }
    
    private void initPanel(){
        this.setBackground(Color.green);
        this.setLayout(new BorderLayout());
        initMoneyPanel(); //Left
        initCardsPanel(); //Center;
        initComentsPanel(); //Bottom
        //initBettingPanel had to be moved into update hands
        setPlayers(players);//Must be here to init properly.
        initBettingPanel();//Right
    }
    
    private void initCardsPanel(){
        Toolkit tKit = Toolkit.getDefaultToolkit();
        Dimension screenSize = tKit.getScreenSize();
        int y = screenSize.height / 2 - 70;
        cardPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, y));
//        cardPanel.setBackground(Color.GREEN);
        //init JLabel array
        for(int i = 0; i < 5; ++i){
            //Name labels for easier ID
            JLabel label = new JLabel();
            label.setName(""+i);
            cardLabels.add(label);
        }
        
        for(JLabel label: cardLabels){
            cardPanel.add(label);
        }
        cardPanel.setOpaque(false);
        
        this.add(cardPanel, BorderLayout.CENTER);
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
        this.add(moneyPanel, BorderLayout.WEST);

    }//initMoneyPanel()
    
    private void initComentsPanel(){
        commentsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        commentsPanel.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        commentsPanel.setBackground(Color.GREEN);
        
        controlsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        controlsPanel.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        controlsPanel.setBackground(Color.GREEN);
        
        comments = new JLabel();
        skip = new JLabel();
        swap = new JLabel();
        rise = new JLabel();
        call = new JLabel();
        fold = new JLabel();
        //Card swap
        skip.setText("Skip");
        skip.setName("Skip");
        swap.setText("Swap");
        swap.setName("Swap");
        
        //Betting
        rise.setText("Rise");
        rise.setName("Rise");
        call.setText("Call");
        call.setName("Call");
        fold.setText("Fold");
        fold.setName("Fold");
        
        commentsPanel.add(comments);
        controlsPanel.add(skip);
        controlsPanel.add(swap);
        controlsPanel.add(rise);
        controlsPanel.add(call);
        controlsPanel.add(fold);

        this.add(commentsPanel, BorderLayout.NORTH);
        this.add(controlsPanel, BorderLayout.SOUTH);
    }//initCommentsPanel()
    
    private void initBettingPanel(){
        //set slider values from player wallet
        bettingValueLabel = new JLabel(String.format("%s % 4d ", "$", this.bettingValue));
        bettingValueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        bettingPanel = new JPanel();
        bettingPanel.setLayout(new BoxLayout(bettingPanel, BoxLayout.Y_AXIS));
        bettingPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        bettingPanel.setBackground(Color.green);
        
        bettingSlider = new JSlider(JSlider.VERTICAL);
        bettingSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
        bettingSlider.setMinimum(2);
        bettingSlider.setMaximum(bettingMax);
        bettingSlider.setValue(2);
        bettingSlider.setMajorTickSpacing(50);
        bettingSlider.setMinorTickSpacing(2);
        bettingSlider.setPaintTicks(true);
        bettingSlider.setPaintLabels(true);
        bettingSlider.setBackground(Color.green);
        bettingSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setBettingValue(bettingSlider.getValue());
                bettingValueLabel.setText(String.format("%s % 4d ", "$", getBettingValue()));
//                setBettingValue();
            }
        });
        
        bettingPanel.add(bettingValueLabel);
        bettingPanel.add(bettingSlider);

        this.add(bettingPanel, BorderLayout.EAST);
    }//initBettingPanel()
    
//    ============ SETTERS/GETTERS ===============
    
    private void setBettingValue(int value){
        this.bettingValue = value;
    }
    
    public int getBettingValue(){
        return this.bettingValue;
    }
    
    private void setBettingMax(int max){
        this.bettingMax = max;
    }
    //This sets img for Computer hand
    private void setHandImages(Hand hand){
        for (Card aCard : hand) {
            try {
                aCard.setImage(ImageIO.read(new File(imgURL + aCard.toString() + ".png")));
            } catch (IOException ex) {
                System.out.println("File does not exist = " + imgURL + aCard.toString() + ".png");
            }
        }
    }
    
    private void setLabelImages(){
        for(int i = 0; i < player1_hand.size(); ++i){
            try {
                ImageIcon icon = new ImageIcon();
                icon.setImage(ImageIO.read(new File(imgURL + player1_hand.getCard(i) + ".png")));
                cardLabels.get(i).setIcon(icon);
            } catch (IOException ex) {
                System.out.println("File does not exist = " + imgURL + player1_hand.getCard(i).toString() + ".png");
            }
        }
    }
    
    public void setPlayers(List<Player> players){
//       initBettingPanel();
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
            setBettingMax(player.getWallet());
        }
    }
    
    public void setPotSize(int pot){
        this.potSize.setText("     Pot $ " + pot);
    }
    
    public void updateView(){
        updateView(this.players);
    }
    
    public void updateView(List<Player> players){
        setPlayers(players);
        
        for(Player p: players){
            updateHand(p);
        }
        
        repaint();
    }//setPotSize()
    
    public void setComments(String comments){
        this.comments.setText(comments);
        repaint();
    }
    
    public JLabel getSwapLabel(){
        return this.swap;
    }
    
    public JLabel getSkipLabel(){
        return this.skip;
    }
    
    public JLabel getRiseLabel(){
        return this.rise;
    }
    
    public JLabel getCallLabel(){
        return this.call;
    }
    
    public JLabel getFoldLabel(){
        return this.fold;
    }
    
    public void setEnabledRiseFoldCall(boolean b){
        this.rise.setEnabled(b);
        this.call.setEnabled(b);
        this.fold.setEnabled(b);
    }
    
    public void setEnabledSkipSwap(boolean b) {
        this.skip.setEnabled(b);
        this.swap.setEnabled(b);
    }
    
    public void setEnabledSlider(boolean b){
        this.bettingSlider.setEnabled(b);
    }
    
    public void disableControls(){
        this.setEnabledRiseFoldCall(false);
        this.setEnabledSkipSwap(false);
        this.setEnabledSlider(false);
    }
    
    public List<JLabel> getCardLabels(){
        return this.cardLabels;
    }
    
    public void setCardLabelBorder(String labelName){
        for(JLabel cardLabel: this.cardLabels){
            if(labelName.equalsIgnoreCase(cardLabel.getName())){
                cardLabel.setEnabled(false);
            }
        }
    }//setCardLableBorder()
    
    public void removeCardLabelBorder(String labelName){
        for(JLabel cardLabel: this.cardLabels){
            if(labelName.equalsIgnoreCase(cardLabel.getName())){
                cardLabel.setEnabled(true);
            }
        }
    }//setCardLableBorder()
    
    @Override
     public void paintComponent(Graphics g) {
        int halfLengthOfHand = 220;
        Dimension screenSize = this.getBounds().getSize();
        int x = (int)(screenSize.width / 2 - halfLengthOfHand);
        int y = 50;
        super.paintComponent(g);       
            //Player 0 cards shirt up.
            if(showCards){
                drawCards(player0_hand, x, y, g);
            }else{
                drawCardShirts(x, y, g);
            }
            //player 1 cards face up
    }  
     
    private void drawCards(Hand hand,int x, int y, Graphics g){
        for(Card aCard: hand){
            g.drawImage(aCard.getCardImage(), x, y, this);
            x += 93;
        }
    }
    
    private void drawCardShirts(int x, int y, Graphics g){
        
        Image cardShirt = null;
        try {
            cardShirt = ImageIO.read(new File("img\\SHIRT.png"));
        } catch (IOException e) {
            System.err.println("Cannot find SHIRT.png = " + e.getLocalizedMessage());
        }
        for(Card card: player0_hand){
            g.drawImage(cardShirt, x, y, this);
            x += 93;
        }
    } 
    
    public void showCards(boolean show){
        this.showCards = show;
    }
        
    public void updateHand(Player player){
        setHandImages(player.getHand()); //This sets img for Computer hand
        if(player.getPlayerType() == PlayerType.COMPUTER){
            this.player0_hand = player.getHand();
        }else{
            this.player1_hand = player.getHand();
            setLabelImages();
            setBettingMax(player.getWallet());
        }
    }//updatePlayerHand()
     
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("L92 MainViewPanel action registered = " + e.getSource());
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void resetSlider() {
        this.bettingSlider.setValue(2);
        setBettingValue(2);
    }

    void enableCardLabels() {
        this.cardLabels.forEach(card -> card.setEnabled(true));
    }
       
}//class
