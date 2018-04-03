/*
 * 
 */
package poker;

import java.util.*;

/**
 *
 * @author Alexander Frolov
 */
public class Game {
    
    
    
    public void startGame(){
    
    int user_input = 0;
        
        Bank bank = new Bank();
        Scanner scan;
        
//        showStartMenu();
        
        do{
            bank.dealNewRound();
            scan = new Scanner(System.in);
            //this type of try block auto closes resource.
            try {
                    showPlayAgain();
                    user_input = Integer.parseInt(scan.nextLine());

                    switch(user_input){
                        case 1:
                            //game logic
//                          1 - Dial cards
                            showCards(bank);
                            showPlayersBallance(bank);
                            
//                          2 - First Round of Betting
                            if(BetType.FOLD == showFoldCallRise(bank, scan)){
                                showWinners(bank);
                                showPlayersBallance(bank);
                                break;
                            }
                            
//                          3 - trade cards
                            showOfferCardSwap(bank, scan);
                            
//                          4 - Second round of Betting
                            showCards(bank);
                            showPlayersBallance(bank);
                            
                            showFoldCallRise(bank, scan);
                            //In any case after second round of bet
                            //We need to show winner. No need for if statement
//                          5 - When betting is over check hands.
//                            showHands(bank);
                            showWinners(bank);
                            showPlayersBallance(bank);
                            break;
                        case 2:
                            //open prev game if it exist
                            showLoadGameDialog(bank);
                            //Return message if file does not exist saying 
                            //the slot is empty.
                            //if not offer a message to make one.
//                            user_input = 3; //They might want to carry on playing
                            break;
                        case 3:
                            //open prev game if it exist
                            showSaveGameDialog(bank);
                            //if not offer a message to make one.
//                            user_input = 3; //They might want to carry on playing
                            break;
                        case 4:
                            System.out.println("Program Exit");
                            break;
                        default:
                            System.out.println("No such number. Try again.\n");
                    }
            } catch (NumberFormatException e) {
                System.out.println("Please type an option 1, 2, 3 or 4 and try again.");
            }
            //Save game or Play again
//            showPlayAgain();
        }while(user_input != 4);
        
        scan.close();
    }//startGame()
    
     private void showStartMenu(){
        System.out.println("\n=========== Welcome to the Poker Game ===========");
        System.out.println("=================== Draw Poker ==================");

        System.out.println("Press (1) to Play");
        System.out.println("Press (2) to Load Game");
        System.out.println("Press (3) to Save Game");
        System.out.println("Press (4) to Exit");
        System.out.print("> ");//Prompt.
    }//showStartMenu()
    
    private void showPlayAgain(){
        System.out.println("\n=================== Poker Game ==================");
        System.out.println("=================== Draw Poker ==================");
        System.out.println("Press (1) to Play");
        System.out.println("Press (2) to Load Game");
        System.out.println("Press (3) to Save Game");
        System.out.println("Press (4) to Exit");
        System.out.print("> ");//Prompt.
    }
    
    private void showLoadGameDialog(Bank bank){
        //2 - Load game from the file
        //Or return message to say that file not found or does not exist yet.
        System.out.println("Loading saved game from file.");
        bank.loadGame();
    }
    
    private void showSaveGameDialog(Bank bank){
        // 3 - Save to file
        bank.saveGame();
        System.out.println("Your game has been saved.");
    }
    
    private void showCards(Bank bank){
        Hand compHand = bank.getPlayer(0).getHand();
        String compHandString = "";
        for(Card card: compHand){
            compHandString += "[XXXXXX] ";
        }
        System.out.println("\nL128 Poker: "+ bank.getPlayer(0).getPlayerType() + "\n " + compHandString);
        System.out.println("\nL129 Poker: "+ bank.getPlayer(1).getPlayerType() + " " + bank.getPlayer(1).getHand());
    }//showHands()
    
    private void showHands(Bank bank){
        System.out.println("  ============================================");
        System.out.println(" Poker: "+ bank.getPlayer(0).getPlayerType() + " " + bank.getPlayer(0).getHand());
        System.out.println(" Poker: "+ bank.getPlayer(1).getPlayerType() + " " + bank.getPlayer(1).getHand());
    }
    
    private BetType showFoldCallRise(Bank bank, Scanner scan){
        String line = "";
        int userInput = 0;
        //get user input 1, 2 or 3
        do{
            System.out.println("Press (1) - Rise");
            System.out.println("Press (2) - Call");
            System.out.println("Press (3) - Fold");
            System.out.print(">");
            try{
                line = scan.nextLine();
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
                    showMakeBet(bank, scan, BetType.RISE);
                    return BetType.RISE;
                case 2://Call
                    showMakeBet(bank, scan, BetType.CALL);
                    return BetType.CALL;
                default://Fold
                    bank.playerFold(PlayerType.PLAYER_1);
                    return BetType.FOLD;
            }
    }//showFoldCallRise()

    private void showMakeBet(Bank bank, Scanner scan, BetType betType){
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
                        betAmount = Integer.parseInt(scan.nextLine());
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
    
    private void showPlayersBallance(Bank bank){
        System.out.println("  ================== Balans: ================= ");
        for(Player player: bank.getPlayers()){
            System.out.print("     " + player.getPlayerType() + ": $" + player.getWallet());
        }
        System.out.println("\n  ============================================ ");
    }
    
    /**
     * Get input from the user and pass this input to bank.swapCards()
     * @param bank
     * @param scan
     * @return 
     */
    private void showOfferCardSwap(Bank bank, Scanner scan){
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
        do{
            try{
                userInput = scan.nextLine();
                System.out.println("  ============================================ ");
                tokens = userInput.replaceAll("[^0-5]", "");
                //get integers from String input
                if(!tokens.equals("")){
                    for(int i = 0; i < tokens.length() && i < swapLimit; ++i){
                        String token = "" + tokens.charAt(i);
                        int index = Integer.parseInt(token);
                        if(0 == index){
                            return;
                        }
                        cardIndexes.add(index);
                    }
                }else{
                    System.out.println("Please enter integers 0 to 5.");
                    System.out.print(">");
                }
            }catch(NoSuchElementException | NumberFormatException | IllegalStateException e){
                System.err.println("Error = " + e);
                System.out.println("You need to enter number from 0 to 5 like so > 1, 2, 3");
            }
            
        }while(cardIndexes.isEmpty());
        //pass cardIndexes to the bank for swapping.
        if(!cardIndexes.isEmpty()){
            bank.swopCards(PlayerType.PLAYER_1, cardIndexes);
        }

    }//showOfferCardSwap()
    
    private void showWinners(Bank bank){
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
        this.showHands(bank);
    }//showTwoWinners()
    
}//class Game
