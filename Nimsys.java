/*
COMP90041 Project A+B+C Nimsys.java
Yulun Wu 
Student#. 966159
*/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Nimsys {
    //enum command set
    enum COMMAND {
        addplayer, addaiplayer, removeplayer, editplayer, resetstats,
        displayplayer, rankings, startgame, startadvancedgame, exit, nosuchcommand };
    //define flag constant and argument index
    final static String DELIMS = "[ ,]";
    final static int INITIAL_FLAG = 0, ARGV = 1;
    final static int SOL_ARGV = 1, SINGLE_CHAR = 1;
    final static boolean DESC = true, ASC = false;
    final static int USER_NAME = 1, FAM_NAME = 2, GIVEN_NAME = 3;
    final static int STONE_TOTAL = 1, STONE_BOUND = 2, PLAYER_1 = 3, PLAYER_2 = 4;
    final static int ADV_PLAYER_1 = 2, ADV_PLAYER_2 = 3;

    public static void main( String[] args ) {

        Scanner keyboard = new Scanner( System.in );

        //find players.dat
        File dataFile = new File( "players.dat" );
        //if not exists, create one
        if ( !dataFile.exists() ) {
            try {
                dataFile.createNewFile();
            } catch ( IOException e ) {
                System.err.println( e.getMessage() );
            }
        }

        //create a corresponding data-storage object to store all players.
        NimPlayerMap playerDB = new NimPlayerMap();
        playerDB.loadGame( dataFile );
        
        System.out.println( "Welcome to Nim" );

        while ( true ) {
            System.out.print( "\n$" );
            String para[] = keyboard.nextLine().split( DELIMS );
            COMMAND command = parseCommand( para );
            
            try {
                switch ( command ) {

                    case addplayer:
                        addPlayer( playerDB, para );
                        break;

                    case addaiplayer:
                        addAIPlayer( playerDB, para );
                        break;
    
                    case removeplayer:
                        removePlayer( playerDB, para, keyboard );
                        break;
    
                    case editplayer:
                        editPlayer( playerDB, para );
                        break;
    
                    case resetstats:
                        resetStats( playerDB, para, keyboard );
                        break;
    
                    case displayplayer:
                        displayerPlayer(playerDB, para);
                        break;
    
                    case rankings:
                        ranking(playerDB, para);
                        break;
    
                    case startgame:
                        startGame(playerDB, para, keyboard);
                        break;

                    case startadvancedgame:
                        startAdvancedGame(playerDB, para, keyboard);
                        break;
    
                    case exit:
                        System.out.println();
                        keyboard.close();
                        playerDB.saveGame( dataFile );
                        System.exit(0);
                        break;
                        
                    case nosuchcommand:
                    default:
                        break;
                }
            }
            catch (ArrayIndexOutOfBoundsException e) {
                System.out.println( "Incorrect number of arguments supplied to command." );
            }

        }
    }

    //defined methods
    public static COMMAND parseCommand( String[] para ) {
        COMMAND command = COMMAND.nosuchcommand;
        try {
            command = COMMAND.valueOf( para[INITIAL_FLAG] );
        }
        catch (IllegalArgumentException e) {
            System.out.printf( "'%s' is not a valid command.\n", para[INITIAL_FLAG] );
        }
        return command;
    }

    public static void addPlayer( NimPlayerMap playerDB, String[] para ) {
        if ( !playerDB.addPlayer( new NimHumanPlayer( para[USER_NAME], para[FAM_NAME], para[GIVEN_NAME] ) ) ) {
            System.out.println( "The player already exists." );
        }
    }

    public static void addAIPlayer( NimPlayerMap playerDB, String[] para ) {
        if ( !playerDB.addPlayer( new NimAIPlayer( para[USER_NAME], para[FAM_NAME], para[GIVEN_NAME] ) ) ) {
            System.out.println( "The player already exists." );
        }
    }

    public static void editPlayer( NimPlayerMap playerDB, String[] para ) {
        if ( playerDB.hasPlayer( para[USER_NAME] ) ) {
            playerDB.getPlayer( para[USER_NAME] ).setFullName( para[FAM_NAME], para[GIVEN_NAME] );
        }
        else {
            System.out.println( "The player does not exist." );
        }
    }

    public static void removePlayer( NimPlayerMap playerDB, String[] para, Scanner keyboard ) {
        //check single argument or multiple
        if ( para.length == SOL_ARGV ) {
            System.out.println( "Are you sure you want to remove all players? (y/n)" );
            String flag = keyboard.next();
            keyboard.nextLine();

            //check if flag is y or n
            while ( flag.length() != SINGLE_CHAR
                    || ( flag.charAt(0) != 'y' && flag.charAt(0) != 'n' ) ) {
                System.out.println( "Invalid input, are you sure you want to remove all playes? (y/n)" );
                flag = keyboard.next();
                keyboard.nextLine();
            }
            if ( flag.charAt(0) == 'y' ) {
                playerDB.removePlayer();
            }
        }
        else if( !playerDB.removePlayer( para[USER_NAME] ) ) {
            System.out.println( "The player does not exist." );
        }
    }

    public static void resetStats( NimPlayerMap playerDB, String[] para, Scanner keyboard ) {
        if ( para.length == SOL_ARGV ) {
            System.out.println( "Are you sure you want to reset all player statistics? (y/n)" );
            String flag = keyboard.next();
            keyboard.nextLine();

            while ( flag.length() != SINGLE_CHAR
                    || ( flag.charAt(0) != 'y' && flag.charAt(0) != 'n' ) ) {
                System.out.println( "Invalid input, are you sure you want to reset all players statistics? (y/n)" );
                flag = keyboard.next();
                keyboard.nextLine();
            }
            if ( flag.charAt(0) == 'y' ) {
                playerDB.resetStats();
            }
        }
        else if( !playerDB.resetStats(para[USER_NAME]) ) {
            System.out.println( "The player does not exist." );
        }
    }

    public static void displayerPlayer( NimPlayerMap playerDB, String[] para ) {
        if ( para.length == SOL_ARGV ) {
            playerDB.display();
        }
        else if( !playerDB.display( para[USER_NAME] ) ) {
            System.out.println( "The player does not exist." );
        }
    }

    public static void ranking( NimPlayerMap playerDB, String[] para ) {
        if ( para.length == SOL_ARGV || para[ARGV].equals( "desc" ) ) {
            playerDB.getRanking( DESC );
        }
        else if( para[ARGV].equals( "asc" ) ) {
            playerDB.getRanking( ASC );
        }
    }

    public static void startGame( NimPlayerMap playerDB, String[] para, Scanner keyboard ) {
        //check player validity
        if ( playerDB.hasPlayer( para[PLAYER_1] )
                && playerDB.hasPlayer( para[PLAYER_2] ) ) {
            NimBasicGame gameInst = new NimBasicGame( playerDB, para );

            System.out.println();
            gameInst.printBeginMessage();
            //main playing loop
            while ( gameInst.getCurrentStone() > 0 ) {
                gameInst.printNextTurn();

                //get number of stone removed
                int numberRemoval = gameInst.getCurrentPlayer().removeStone( keyboard, gameInst );
                //perform remove
                gameInst.removeStone( numberRemoval );
                //switch player
                gameInst.nextPlayer();
            }

            System.out.println( "\nGame Over" );
            //update game records after game finished
            gameInst.printWinner();
            gameInst.recordGame();
        }
        else {
            System.out.println( "One of the players does not exist." );
        }
    }

    public static void startAdvancedGame( NimPlayerMap playerDB, String[] para, Scanner keyboard ) {
        //check player validity
        if ( playerDB.hasPlayer( para[ADV_PLAYER_1] )
                && playerDB.hasPlayer( para[ADV_PLAYER_2] ) ) {
            NimAdvancedGame gameInst = new NimAdvancedGame( playerDB, para );

            System.out.println();
            gameInst.printBeginMessage();
            //main playing loop
            while ( gameInst.getStatus() ) {
                gameInst.printNextTurn();

                //get number of stone removed
                String movedParameter = gameInst.getCurrentPlayer().removeStoneAdv( keyboard, gameInst );
                //perform remove
                gameInst.removeStone( movedParameter );
                //switch player
                gameInst.nextPlayer();
            }

            System.out.println( "\nGame Over" );
            //update game records after game finished
            gameInst.nextPlayer();
            gameInst.printWinner();
            gameInst.recordGame();
        }
        else {
            System.out.println( "One of the players does not exist." );
        }
    }
}