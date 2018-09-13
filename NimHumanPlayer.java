/*
COMP90041 Project A+B+C NimHumanPlayer.java
This class is designed to implement a human player
Yulun Wu 
Student#. 966159
*/

import java.util.InputMismatchException;
import java.util.Scanner;

public class NimHumanPlayer extends NimPlayer {
    //constructor
    final static int POSITION = 0, QUANTITY = 1;

    public NimHumanPlayer() {
        super();
    }

    public NimHumanPlayer( String userName, String familyName, String givenName ) {
        super(userName, familyName, givenName);
    }

    public String getUserName() {
        return userName;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public int getGamePlayed() {
        return gamePlayed;
    }

    public int getGameWon() {
        return gameWon;
    }

    public double getWinRate() {
        return winRate;
    }

    public void calcWinRate() {
        winRate = ( gamePlayed == 0 ) ? 0 : ( (double) gameWon / gamePlayed * 100 ) ;
    }

    public void setFullName( String newFamilyName, String newGivenName ) {
        familyName = newFamilyName;
        givenName = newGivenName;
    }

    public void setGamePlayed() {
        gamePlayed += 1;
    }

    public void setGamePlayed( int gamePlayed ) {
        this.gamePlayed = gamePlayed;
    }

    public void setGameWon() {
        gameWon += 1;
    }

    public void setGameWon( int gameWon ) {
        this.gameWon = gameWon;
    }

    public String toString() {
        return userName + "," + givenName + "," + familyName + ","
                + gamePlayed + " games," + gameWon + " wins";
    }

    public int removeStone( Scanner keyboard, NimBasicGame gameInst ) {
        int upperBound = gameInst.getUpperBound();
        int currentStone = gameInst.getCurrentStone();
        gameInst = ( NimBasicGame ) gameInst;

        do { //check validity of the number of stone removed
            numberRemoval = 0; //reset numberRemoval
            try {
                numberRemoval = keyboard.nextInt();
            } 
            catch (InputMismatchException e) {
                //dismiss the exception. Try-Catch shouldn't be used in flow coontrol.
            }
            finally {
                keyboard.nextLine();
            }
            
            if ( numberRemoval > upperBound
              || numberRemoval > currentStone
              || numberRemoval < MIN_REMOVAL ) {
                System.out.print( "\nInvalid move. You must remove between 1 and ");
                if ( currentStone < upperBound ) {
                    System.out.print( currentStone );
                }
                else {
                    System.out.print( upperBound );
                }
                System.out.println( " stones." );
                gameInst.printNextTurn();
                continue;
            }

            break;
        } while ( true );
        return numberRemoval;
    }

    public String removeStoneAdv( Scanner keyboard, NimAdvancedGame gameInst ) {
        int numberMoved;
        int positionMoved;
        String movedParameter = null;
        
        do { //check validity of the number of stone removed
            numberMoved = 0; //initialize numberRemoval
            positionMoved = 0;
            try {
                movedParameter = keyboard.nextLine();
                String[] cmd = movedParameter.split(" ");
                positionMoved = Integer.valueOf( cmd[POSITION] );
                numberMoved = Integer.valueOf( cmd[QUANTITY] );
            } 
            catch ( Exception e ) {
                //dismiss the exception. Try-Catch shouldn't be used in flow coontrol.
            }

            if ( positionMoved + numberMoved - 1 <= gameInst.getCurrentStone() ) {
                if ( numberMoved == 1 && gameInst.getAvailable( positionMoved ) ) {
                    break;
                }
                else if ( numberMoved == 2 
                    && gameInst.getAvailable( positionMoved ) 
                    && gameInst.getAvailable( positionMoved + 1 ) ) {
                    break;
                }
            } 
            System.out.println( "\nInvalid move.");
            gameInst.printNextTurn();
        } while ( true );
        return movedParameter;
    }
	
}
