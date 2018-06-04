package poker;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author Alexander Frolov
 */
public class PokerController extends JFrame implements ActionListener, MouseListener{
    
    private Bank bank;
    private Timer timeDelay;
    private boolean secondRound = false;
    
    private final MainViewPanel view;
    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenuItem newGame;
    private JMenuItem saveGame;
    private JMenuItem loadGame;
    private JMenuItem exitGame;

    //Game variables
    private List<String> swapList;

    public PokerController(){
        super("Poker");
        this.bank = new Bank();
        this.view = new MainViewPanel(bank.getPlayers());
        initFrame();
        startGame();
    }

    /**
     * Initializes the Frame
     */
    private void initFrame(){
        //Initializes menu bar and its listeners
        initMenuBar();
        //Set screen size
        Toolkit tKit = Toolkit.getDefaultToolkit();
        Dimension screenSize = tKit.getScreenSize();
        this.setSize((screenSize.width * 2 / 3), (screenSize.height * 3 / 4));
        //Set screen position in the center of the screen
        this.setLocation((screenSize.width * 1 / 6), (screenSize.height * 1 / 8));
        
        this.add(view);
        this.setJMenuBar(menuBar);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        
        attachActionListeners();
    }
    
    /**
     * Initializes JMenuBar for this frame
     */
    private void initMenuBar(){
        //Define the menu bar and items
        menuBar = new JMenuBar();
        newGame = new JMenuItem("New Game");
        saveGame = new JMenuItem("Save Game");
        loadGame = new JMenuItem("Load Game");
        exitGame = new JMenuItem("Exit");
                
        //Add actions to the JMenuItems
        newGame.addActionListener(this);
        saveGame.addActionListener(this);
        loadGame.addActionListener(this);
        exitGame.addActionListener(this);
        
        menuFile = new JMenu("File");
        menuFile.setMnemonic(KeyEvent.VK_F);
        
        //Add menu Items
        //New Game, Save Game, Load Game, Exit
        menuFile.add(newGame);
        menuFile.add(saveGame);
        menuFile.add(loadGame);
        menuFile.add(exitGame);
        
        //Add Menu to the MenuBar
        menuBar.add(menuFile);
    }//initMenuBar()
    
    private void attachActionListeners(){
        for(JLabel label: view.getCardLabels()){
            label.addMouseListener(this);
        }
        view.getSkipLabel().addMouseListener(this);
        view.getSwapLabel().addMouseListener(this);
        view.getRiseLabel().addMouseListener(this);
        view.getFoldLabel().addMouseListener(this);
        view.getCallLabel().addMouseListener(this);
    }//attachActionListeners()
    
    
//    ============================ GAME LOGIC ==============================

    private void startGame(){
        swapList = new ArrayList<>();
        gameInitScreen();
    }//startGame()
    
    private void gameInitScreen(){
        this.secondRound = false;
        bank.dealNewRound();
        view.updateView(bank.getPlayers());
        view.setPotSize(0);
        view.resetSlider();
        showHands(false);
            
//      Once game has started it loops around till player quits through menu
//      game logic
//      1 - Dial cards
        showCards();
        showPlayersBallance();
//      getPlayerFeedback
//      2 - First Round of Betting
        showRiseCallFold();
    }
        
    public void showCards(){
        for(Player player: bank.getPlayers()){
            view.updateHand(player);
        }
    }//showHands()
    
    /**
     * Shows players money/Wallet available for betting
     */
    private void showPlayersBallance(){
        for(Player player: bank.getPlayers()){
            view.setPlayerWallet(player);
        }
    }
    
    /**
     * Shows hands face values for comparison at the end of the round
     * @param b 
     */
    public void showHands(boolean b){
        view.showCards(b);
    }
    
    /**
     * Activates only set of controls necessary to bet
     */
    private void showRiseCallFold(){
        //get user input 1, 2 or 3
        view.setEnabledSkipSwap(false);
        view.setEnabledRiseFoldCall(true);
        view.setEnabledSlider(true);
        view.setComments("You can Rise, Call or Fold. How much do you want to bet?");
    }//showFoldCallRise()

    /**
     * Checks if player has enough funds to bet
     * @param betType 
     */
    private void showMakeBet(BetType betType){
        int betAmount;
        int minimumBet = 2;
        boolean hasEnoughToBet = false; 
        Player computer = bank.getPlayer(PlayerType.COMPUTER);
        Player player1 = bank.getPlayer(PlayerType.PLAYER_1);
        
        //Fold Call Rise
        if(null == betType){
            
        }else switch (betType) {
            case RISE:
                betAmount = view.getBettingValue();
                hasEnoughToBet = player1.ableToBet(betAmount);
                if(hasEnoughToBet && betAmount >= minimumBet){
                    //bank record the potSize.
                    bank.addToPot(player1.rise(betAmount));
                    //Make computer bet same as player for now.
                    bank.addToPot(computer.rise(betAmount));
                    
                }else if(betAmount < minimumBet){
                    view.setComments("Rules of the house. Bet cannot be lower than 2. Bet amount = " + betAmount);
                }else{
                    view.setComments(player1.getPlayerType() + " has not enough funds to bet $" + betAmount);
                }
                
                view.setPotSize(bank.getPot());
                view.updateView(bank.getPlayers());
                showOfferCardSwap();

                break;
            case CALL:
                /*
                * If call bet amount == 10 as we are not required to develop AI.
                */
                betAmount = 10;
                hasEnoughToBet = player1.ableToBet(betAmount);
                if(hasEnoughToBet && betAmount >= minimumBet){
                    //bank record the potSize.
                    bank.addToPot(player1.rise(betAmount));
                    //Make computer bet same as player for now.
                    bank.addToPot(computer.rise(betAmount));
                }else if(betAmount < minimumBet){
                    view.setComments("Rules of the house. Bet cannot be lower than 2. Bet amount = " + betAmount);
                }else{
                    view.setComments(player1.getPlayerType() + " has not enough funds to bet $" + betAmount);
                }   
                
                view.setPotSize(bank.getPot());
                view.updateView(bank.getPlayers());

                showOfferCardSwap();
                break;
            default:
                //Fold
                //give up the Pot.
                break;
        }
        view.resetSlider();
        view.disableControls();
        view.setEnabledSkipSwap(true);
    }//showMakeBet()
    
    
    
    /**
     * Displays text and how many cards you can swap max this time
     * 
     */
    private void showOfferCardSwap(){
        view.setComments("What cards do you want to swap? You can swap up to " + bank.getSwapLimit(PlayerType.PLAYER_1) + " cards");
    }//showOfferCardSwap()
    
    /**
     * Gets the winners list from the model - Bank and displays the winner
     * Times out at 5 seconds so that player can check cards of the opponent
     */
    private void showWinners(){
        List<Player> winners = bank.getWinner();
                                
        if(winners.size() > 1){
            //there are 2 winners
            view.setComments("The pot is split.");
            
            //Add split to computer and player1
            int split = bank.splitPot(winners.size());
            winners.get(0).addToWallet(split);
            winners.get(1).addToWallet(split);
        }else{
            view.setComments("The winner is: " + winners.get(0).getPlayerType() + " with " + winners.get(0).getHand().getHandType());
            //Add split to computer and player1
            winners.get(0).addToWallet(bank.getPot());
        }
        view.updateView(bank.getPlayers());
        this.showHands(true);
        view.disableControls();
        
        //resetSlider is in showWinners
        //And starts new game
        timeDelay = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        timeDelay.setRepeats(false);
        timeDelay.start();
    }//showWinners()
    
    
    
//    ============================== HANDLERS ==============================
    //They will be switching the screens.
    /**
     * Handles selection of the player 1 cards for swapping
     * @param e 
     */
    private void handleCardSelect(MouseEvent e){

        String cardName = e.getComponent().getName();
        if(!swapList.isEmpty() && !e.getComponent().isEnabled()){
//          enable cardLabel
            view.removeCardLabelBorder(cardName);
//            remove from swap list
            this.swapList.remove(cardName);
        }else{
//          disable cardLabel
            view.setCardLabelBorder(cardName);
//            add to swap list
            this.swapList.add(cardName);
        }
        //If selected more then allowed remove previouse card
        if(!swapList.isEmpty() && swapList.size() > bank.getSwapLimit(PlayerType.PLAYER_1)){
            int prevCardIndex = bank.getSwapLimit(PlayerType.PLAYER_1) - 1;
            view.removeCardLabelBorder("" + swapList.get(prevCardIndex));
            swapList.remove(bank.getSwapLimit(PlayerType.PLAYER_1) - 1);
        }
    }//handleCardSelect()
    
    /**
     * Handles button pushes for Rise/Call/Fold
     * Determines BetType
     * @param type 
     */
    private void handleRiseCallFold(BetType type){
        
        if(BetType.FOLD != type){
            showMakeBet(type);
        }else{
            bank.playerFold(PlayerType.PLAYER_1);
            showWinners();
        }
        //Without BetType check if it is second betting this part will 
        //trigger showWinners doubling the amount of win to the Computer.
        if(secondRound && BetType.FOLD != type){
            showWinners();
        }
        
//      Sets flag that the method already run at least once
        secondRound = true;
    }
    
    /**
     * Handles swap and skip card swapping button pushes
     * @param action 
     */
    private void handleSwapSkip(String action){
        List<Integer> cardIndexes = new ArrayList<>();
        
        switch(action){
        //if swap - obtain cards to swap card and second round of betting
            case "Swap":
                //Conert Strings into Integers
                if(!swapList.isEmpty()){
                    for(String name: swapList){
                        //+1 because swopCards does -1.
                        cardIndexes.add(Integer.parseInt(name) + 1);
                    }
                }

                //pass cardIndexes to the bank for swapping.
                if(!cardIndexes.isEmpty()){
                    bank.swopCards(PlayerType.PLAYER_1, cardIndexes);
                    view.updateView();
                    view.enableCardLabels();
                }
                break;
            default://default is skip
        }
        //If skip do show second round of betting
        
        showRiseCallFold();
    }//handleSwapSkip()
    
    
//    ============================== ACTIONS ==============================
        
    /**
     * Actions for file drop down menu
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
//        System.out.println("Event = " + e);
        String cmd = e.getActionCommand();
        
        if(cmd.equals("New Game")){
            bank = new Bank();
            startGame();
            //update view
            view.resetSlider();
            view.updateView(bank.getPlayers());
        }
        if(cmd.equals("Save Game")){
            bank.saveGame();
            //update info label on view
            view.setComments("Your game was saved.");
        }
        if(cmd.equals("Load Game")){
            bank.loadGame();
            //update wallets and the view
            view.resetSlider();
            view.updateView(bank.getPlayers());
        }
        
        if(cmd.equals("Exit")){
            processEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
        
    }//actionPerformed
    
    //Mouse Actions

    /**
     * Mouse click handler of the main view
     * Card selection, button push and slider movements
     * @param e 
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        String card = e.getComponent().getName();
        // ========== Card selection
        //If swap !isEnabled() do not register clicks
        if(!view.getSwapLabel().isEnabled()){
            
        }else switch(card){
            case "0"://cardLabel 0
                handleCardSelect(e);
                break;
            case "1"://cardLabel 1
                handleCardSelect(e);
                break;
            case "2"://cardLabel 2
                handleCardSelect(e);
                break;
            case "3"://cardLabel 3
                handleCardSelect(e);
                break;
            case "4"://cardLabel 4
                handleCardSelect(e);
                break;
            default:
            //leave empty as every other event will pass through here
                break;
        }
        
        // ========== Button Pushes
        String actionLabel = e.getComponent().getName();
        if(actionLabel.equalsIgnoreCase("Skip") && e.getComponent().isEnabled()){
            //Skip cards swap or betting 
            handleSwapSkip("Skip");
        }
        if(actionLabel.equalsIgnoreCase("Swap") && e.getComponent().isEnabled()){
            //Swap cards
            handleSwapSkip("Swap");
        }
        
        if(actionLabel.equalsIgnoreCase("Rise") && e.getComponent().isEnabled()){
            handleRiseCallFold(BetType.RISE);
            //Place bet if enough money
        }
        
        if(actionLabel.equalsIgnoreCase("Call") && e.getComponent().isEnabled()){
            handleRiseCallFold(BetType.CALL);
            //Place bet if enough money
        }
        
        if(actionLabel.equalsIgnoreCase("Fold") && e.getComponent().isEnabled()){
            handleRiseCallFold(BetType.FOLD);
            //Place bet if enough money
        }
    }//mouseClicked()

    /**
     * Reserved for future use
     * @param e 
     */
    @Override
    public void mousePressed(MouseEvent e) {
    }

    /**
     * Reserved for future use
     * @param e 
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Reserved for future use
     * @param e 
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    
    /**
     * Reserved for future use
     * @param e 
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    
}//class PokerController
