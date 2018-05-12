/**
 * TODO: It does not need to implement Listener
 * but it needs to provide access to Frame elements.
 * Make EXIT button/label visible from the panel
 * on Window open start new game.
 */
package poker;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.swing.*;

/**
 *
 * @author Alexander Frolov
 */
public class PokerController extends JFrame implements ActionListener{
    
    private final Bank bank;
    
    private MainViewPanel pokerView;
    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenuItem newGame;
    private JMenuItem saveGame;
    private JMenuItem loadGame;
    private JMenuItem exitGame;


    public PokerController(Bank bank, MainViewPanel view){
        super("Poker");
        this.bank = bank;
        this.pokerView = view;
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
        
        this.add(pokerView);
        this.setJMenuBar(menuBar);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.pack();// this makes it as compact as possible
        this.setVisible(true);
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
    
//    ============================ GAME LOGIC ==============================

    private void startGame(){
    
//        showStartMenu();
            bank.dealNewRound();
            pokerView.setPlayers(bank.getPlayers());
//                    showPlayAgain();
//                Once game has started it loops around till player quits through menu

                            //game logic
//                          1 - Dial cards
                            showCards();
                            showPlayersBallance();
//                            getPlayerFeedback
//                          2 - First Round of Betting
//                            if(BetType.FOLD == showFoldCallRise()){
//                                showWinners();
//                                showPlayersBallance();
////                                break;
//                            }
                            
//                          3 - trade cards
                            showOfferCardSwap();
                            
//                          4 - Second round of Betting
                            showCards();
                            showPlayersBallance();
                            
//                            showFoldCallRise(bank, scan);
                            //In any case after second round of bet
                            //We need to show winner. No need for if statement
//                          5 - When betting is over check hands.
//                            showHands(bank);
                            showWinners();
                            showPlayersBallance();
                            
//                        case 2:
                            //open prev game if it exist
//                            showLoadGameDialog();
                            //Return message if file does not exist saying 
                            //the slot is empty.
                            //if not offer a message to make one.
//                            user_input = 3; //They might want to carry on playing
                            
//                        case 3:
                            //open prev game if it exist
//                            showSaveGameDialog();
                            //if not offer a message to make one.
//                            user_input = 3; //They might want to carry on playing
                             

    }//startGame()
    
        
    
    
    public void showCards(){
        for(Player player: bank.getPlayers()){
            pokerView.updateHand(player);
        }
    }//showHands()
    
    private void showPlayersBallance(){
        for(Player player: bank.getPlayers()){
            pokerView.setPlayerWallet(player);
            System.out.print("     " + player.getPlayerType() + ": $" + player.getWallet());
        }
    }
    
    public void showHands(){
        System.out.println("  ============================================");
        System.out.println(" Poker: "+ bank.getPlayer(0).getPlayerType() + " " + bank.getPlayer(0).getHand());
        System.out.println(" Poker: "+ bank.getPlayer(1).getPlayerType() + " " + bank.getPlayer(1).getHand());
    }
    
    private BetType showFoldCallRise(){
        String line = "";
        int userInput = 0;
        //get user input 1, 2 or 3
        do{
            System.out.println("Press (1) - Rise");
            System.out.println("Press (2) - Call");
            System.out.println("Press (3) - Fold");
            System.out.print(">");
            try{
//                line = scan.nextLine();
                userInput = Integer.parseInt(line);
                if(userInput != 1 && userInput != 2 && userInput != 3){
                    System.out.println("You entered > " + userInput);
                    System.out.println("Please enter a nmber from 1 to 3");
                }
            }catch(NumberFormatException e){
                System.out.println("You entered > " + line);
                System.out.println("Please enter a nmber from 1 to 3");
            }
            
        }while(userInput != 1 && userInput != 2 && userInput != 3);

            System.out.println("  ============================================");
            
            switch(userInput){
                case 1://Rise
                    showMakeBet(BetType.RISE);
                    return BetType.RISE;
                case 2://Call
                    showMakeBet(BetType.CALL);
                    return BetType.CALL;
                default://Fold
                    bank.playerFold(PlayerType.PLAYER_1);
                    return BetType.FOLD;
            }
    }//showFoldCallRise()

    private void showMakeBet(BetType betType){
        int betAmount = 0;
        int minimumBet = 2;
        boolean hasEnoughToBet = false; 
        Player computer = bank.getPlayer(PlayerType.COMPUTER);
        Player player1 = bank.getPlayer(PlayerType.PLAYER_1);
        
        //Fold Call Rise
        
        System.out.println("How much do you want to bet? You got: $" + player1.getWallet());

        if(null == betType){
            
        }else switch (betType) {
            case RISE:
                //Repeat input untill get the amount
                do{
                    System.out.print("bet $ > ");//Prompt.
                    try{
//                        betAmount = Integer.parseInt(scan.nextLine());
                        hasEnoughToBet = player1.ableToBet(betAmount);
                        if(hasEnoughToBet && betAmount >= minimumBet){
                            //bank record the potSize.
                            bank.addToPot(player1.rise(betAmount));
                            //Make computer bet same as player for now.
                            bank.addToPot(computer.rise(betAmount));
                        }else if(betAmount < minimumBet){
                            System.out.println("\n Rules of the house. Bet cannot be lower than 2. Bet amount = " + betAmount);
                        }else{
                            System.out.println("\n" + player1.getPlayerType() + " has not enough funds to bet $" + betAmount);
                        }
                    }catch(NumberFormatException e){
                        System.out.println("Please type in bet amount. \nRules of the house. Bet cannot be lower than 2");
                    }
                }while(!(hasEnoughToBet && betAmount >= minimumBet));
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
                    System.out.println("\n Rules of the house. Bet cannot be lower than 2. Bet amount = " + betAmount);
                }else{
                    System.out.println("\n" + player1.getPlayerType() + " has not enough funds to bet $" + betAmount);
                }   break;
            default:
                //Fold
                //give up the Pot.
                break;
        }

        System.out.println("  ============ POT SIZE $" + bank.getPot() + " ============");
        
        System.out.println(player1.getPlayerType() + " have $" + player1.getWallet() + " left");
        System.out.println(computer.getPlayerType() + " has  $" + computer.getWallet() + " left");
        System.out.println("  ============================================");

    }//showMakeBet()
    
    
    
    /**
     * Get input from the user and pass this input to bank.swapCards()
     * @param bank
     * @param scan
     * @return 
     */
    private void showOfferCardSwap(){
        List<Integer> cardIndexes = new ArrayList<>();
        Hand player1_hand;
        
        int swapLimit = 3;
        String tokens;
        String userInput;
        
        System.out.print("What cards do you want to swap? ");
        //Up to 3 cards. If has an Ace can swap up untill 4.
        //make a while loop for swapping and evaluating.
        for(Player aPlayer : bank.getPlayers()){
            if(aPlayer.getPlayerType() == PlayerType.PLAYER_1){
                //Get player 1 hand while we are at it.
                player1_hand = aPlayer.getHand();
                for (Card aCard : aPlayer.getHand()) {
                    if(aCard.getRank() == CardRank.ACE){
                        swapLimit = 4;
                    }
                }
            }
        }//for
        
        System.out.println("You can swap up to " + swapLimit + " cards");
        System.out.println("Enter card numbers you want to swap in sequence [1 2 3 ...]");
        System.out.println("Enter 0 if you do not want to swap.");
        System.out.print(">");

        //Handls negative numbers by converting them to positive.
        //Get input from the Scanner parse it and make an ArrayList.
//        do{
//            try{
////                userInput = scan.nextLine();
////                System.out.println("  ============================================ ");
////                tokens = userInput.replaceAll("[^0-5]", "");
//                //get integers from String input
//                if(!tokens.equals("")){
//                    for(int i = 0; i < tokens.length() && i < swapLimit; ++i){
//                        String token = "" + tokens.charAt(i);
//                        int index = Integer.parseInt(token);
//                        if(0 == index){
//                            return;
//                        }
//                        cardIndexes.add(index);
//                    }
//                }else{
//                    System.out.println("Please enter integers 0 to 5.");
//                    System.out.print(">");
//                }
//            }catch(NoSuchElementException | NumberFormatException | IllegalStateException e){
//                System.err.println("Error = " + e);
//                System.out.println("You need to enter number from 0 to 5 like so > 1, 2, 3");
//            }
//            
//        }while(cardIndexes.isEmpty());
        //pass cardIndexes to the bank for swapping.
        if(!cardIndexes.isEmpty()){
            bank.swopCards(PlayerType.PLAYER_1, cardIndexes);
        }

    }//showOfferCardSwap()
    
    private void showWinners(){
        List<Player> winners = bank.getWinner();
                                
        if(winners.size() > 1){
            //there are 2 winners
            System.out.println("L326 It is split :");
//            System.out.println("\nL327 Poker: The winner is: " + winners.get(0).getPlayerType() + " with " + winners.get(0).getHand().getHandType());          
//            System.out.println("\nL328 Poker: The winner is: " + winners.get(1).getPlayerType() + " with " + winners.get(1).getHand().getHandType());
            //Add split to computer and player1
            
            int split = bank.splitPot(winners.size());
            winners.get(0).addToWallet(split);
            winners.get(1).addToWallet(split);
        }else{
            System.out.println("\nL334 Poker: The winner is: " + winners.get(0).getPlayerType() + " with " + winners.get(0).getHand().getHandType());          
            //Add split to computer and player1
            winners.get(0).addToWallet(bank.getPot());
        }
        this.showHands();
    }//showTwoWinners()
    
//    ============================== HANDLERS ==============================
 
    
    
    
//    ============================== ACTIONS ==============================
    public void showLoadGameDialog(){
        //2 - Load game from the file
        //Or return message to say that file not found or does not exist yet.
       
    }
    
    public void showSaveGameDialog(){
        // 3 - Save to file
        
    }
    @Override
    public void actionPerformed(ActionEvent e) {
//        System.out.println("Event = " + e);
        String cmd = e.getActionCommand();
        
        if(cmd.equals("New Game")){
            startGame();
            //update view
            pokerView.updateView(bank.getPlayers());
        }
        if(cmd.equals("Save Game")){
            bank.saveGame();
            //update info label on view
        }
        if(cmd.equals("Load Game")){
            bank.loadGame();
            //update wallets and the view
            pokerView.updateView(bank.getPlayers());
        }
        
        if(cmd.equals("Exit")){
            processEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
        
    }//actionPerformed
    
    
}//class PokerController
