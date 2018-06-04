package poker;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alexander Frolov
 * Class contains all method related to Save, Load the game into a file
 * as well as Read, Write Data Base
 * Saves Player 1 into file and DB at the same time
 * Loads Player1 from DB first if fails from File
 */
public class Services {
    
    String url = "jdbc:derby:PlayersDB;create=true"; 

    private final String dbUsername = "poker";
    private final String dbPassword = "poker";
    private final String tableName = "PLAYER";
    Connection conn;
    
    
    /**
     * Establishes connection to JDB
     */
    private void connectDB(){
        
        try {
            conn = DriverManager.getConnection(url, dbUsername, dbPassword);
            System.out.println(url + " connected to PlayersDB");
                    
        } catch (SQLException ex) {
            Logger.getLogger(Services.class.getName()).log(Level.SEVERE, "L43 connectDB() = ", ex);
        }
    }
    
    /**
     * Updates DB with player 1 entry.
     * If table does not exists it creates one.
     * @param player 
     */
    private void updateTableDB(Player player){
            DatabaseMetaData dbm;
            Statement statement;
            
            connectDB();
            
        try {
            dbm = conn.getMetaData();
            // check if "PLAYER" table is there
            ResultSet tables = dbm.getTables(null, "POKER", "PLAYER", null);
            statement = conn.createStatement();

            if (tables.next()) {
                // Table exists
                String updateTable = "UPDATE POKER." + tableName + " SET WALLET = " + player.getWallet()
                    + " WHERE ID = 1 AND TYPE = '" +  player.getPlayerType() + "'  ";
                System.out.println("updateTable = " +  updateTable);
                        
                statement.executeUpdate(updateTable);
            }
            else {
                System.out.println(" Dropping table ");
                String sqlCreate = "CREATE TABLE POKER." + tableName + " (ID int NOT NULL,"
                        + "TYPE varchar(20),"
                        + "WALLET int)";
                System.out.println("sqlCreate = " +  sqlCreate);
                statement.executeUpdate(sqlCreate);

                String sqlInsert="INSERT INTO POKER." + tableName 
                    + "(ID, TYPE, WALLET)"
                    + " VALUES(1, '" + player.getPlayerType() + "', " //this casued exception if ' not used
                    + player.getWallet()+ ")";

                statement.executeUpdate(sqlInsert);
            }
                
            closeConnection();
        } //createTableDB()
        catch (SQLException ex) {
            Logger.getLogger(Services.class.getName()).log(Level.SEVERE, "L91 updateTableDB() = ", ex);
        }
                System.out.println("Table created");
    }//updateTableDB()
    
    private int queryDB(Player player){
        ResultSet query = null;
        DatabaseMetaData dbm;
        Statement statement;
        int wallet = -1;//DB has no entries
        connectDB();
        try {
            dbm = conn.getMetaData();
            // check if "PLAYER" table is there
            ResultSet tables = dbm.getTables(null, "POKER", "PLAYER", null);

            if (tables.next()) {
                // Table exists
                statement = conn.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE, 
                        ResultSet.CONCUR_READ_ONLY);
                //Get player 1 from the DB
                String getQuery = "SELECT TYPE, WALLET FROM POKER.PLAYER "
                        + "WHERE ID = 1";
                System.out.println("getQuery = " + getQuery);
                query = statement.executeQuery(getQuery);
                query.beforeFirst();

                while(query.next())
                {
                    String type = query.getString("TYPE"); // 1
                    wallet = query.getInt("WALLET");                
                    System.out.println(type + ":  $ " + wallet);            
                }

            }
            //IF table does not exists tell user that they need to save file first
            closeConnection();

        } catch (SQLException ex) {
            Logger.getLogger(Services.class.getName()).log(Level.SEVERE, "L138 queryDB =", ex);
        }
        System.out.println(" Exited queryDB() ");
        return wallet;
    }//queryDB()
    
    private void closeConnection()
    {
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Services.class.getName()).log(Level.SEVERE, "L141 closeConnection()", ex);
            }
        }
    }//closeConnection()
    
    /**
     * Only saves player1.
     * @param players
     * @param path
     */
    public void saveGame(List<Player> players, String path) {
        
        Writer bWriter;
        try{
            bWriter = new BufferedWriter(new FileWriter(path));
            for(Player aPlayer: players){
                if(aPlayer.getPlayerType().equals(PlayerType.PLAYER_1)){
                    bWriter.write(aPlayer.getPlayerType() + " $" + aPlayer.getWallet() + "\n");
//                    ================ DB ===================
                    updateTableDB(aPlayer);
                }
            }
            bWriter.close();
        }catch(IOException e){
            System.err.println("L298 bank Error writing file => " + e);
        }
        System.out.println("L305 bank Game Saved");
    }//saveGame()
    
    /**
     * Loads player1 to the game.
     * @param player
     * @param path
     */
    public void loadGame(Player  player, String path){
        Scanner scan;
        String[] playerEntry;
        int money;
        try{
            //                    ================ DB ===================
            money = queryDB(player);
            //If DB has no entries of player then use file.
            if(money == -1){
                System.out.println("File data used to load");
                //load data from a file
                scan = new Scanner(new FileReader("SavedGame.txt"));

                //get player ballance
                if(scan.hasNext()){
                    playerEntry = scan.nextLine().split("\\$");
                    money = Integer.parseInt(playerEntry[1]);
                }

                scan.close();
            }
            //bank getPlayer1
            player.setWallet(money);
            //show player
            
        }catch(IOException | NoSuchElementException e){
            System.out.println("File not found. Cannot find 'SavedGames.txt' at specified location.");
            System.out.println("Either file does not exist or was relocated.");
            System.out.println("Try Save Game first then Load.");
        }
        
    }//loadGame()
    
    
}//Services
