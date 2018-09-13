import java.util.ArrayList;

/*
COMP90041 Project A+B+C NimAdvancedGame.java
This class is designed to maintain an advanced gaming instance
Yulun Wu 
Student#. 966159
*/

public class NimAdvancedGame extends NimGame {
    //argument index
    final static int STONE_TOTAL = 1, ADV_PLAYER_1 = 2, ADV_PLAYER_2 = 3;
    final static int POSITION = 0, QUANTITY = 1;
    private ArrayList<Character> stoneArray = new ArrayList<Character>();
    private String lastMove;

    public NimAdvancedGame() {
        super();
        upperBound = 2;
        lastMove = null;
    }

    public NimAdvancedGame( NimPlayerMap playerMap, String[] para ) {
        super();
        upperBound = 2;
        numberOfStone = Integer.valueOf( para[STONE_TOTAL] );
        lastMove = null;
        
        //initialize stone array list with all stones exist.
        for ( int i = 0; i < numberOfStone; stoneArray.add( new Character( '*' ) ), i++ );

        //store 2 player objects in array
        twoPlayers[PLAYER_1] = playerMap.getPlayer( para[ADV_PLAYER_1] );
        twoPlayers[PLAYER_2] = playerMap.getPlayer( para[ADV_PLAYER_2] );
    }

    public boolean getStatus() {
        for ( Character var : stoneArray ) {
            if ( var.charValue() == '*' ) {
                return true;
            }
        }
        return false;
    }

    public String getLastMove() {
        return lastMove;
    }

    public boolean[] getAvailable() {
        boolean[] available = new boolean[numberOfStone];
        for (int i = 0; i < numberOfStone; available[i] = ( stoneArray.get(i).charValue() == '*' ), i++ );
        return available;
    }

    public boolean getAvailable( int positionMoved ) {
        boolean available = ( stoneArray.get( positionMoved - 1 ).charValue() == '*' );
        return available;
    }

    public void removeStone( String movedParameter ) {
        int positionMoved = Integer.valueOf( movedParameter.split(" ")[POSITION] );
        int numberMoved = Integer.valueOf( movedParameter.split(" ")[QUANTITY] );
        for (int i = 0; i < numberMoved; i++) {
            if ( stoneArray.get( positionMoved + i - 1 ).charValue() == '*' ) {
                stoneArray.set( positionMoved + i - 1, 'x' );
            }
        }
        lastMove = movedParameter;
    }

    public void printBeginMessage() {
        System.out.println( "Initial stone count: " + numberOfStone );
        System.out.print("Stones display:");
        printStone();
        System.out.println( "Player 1: " + twoPlayers[PLAYER_1].getGivenName() + " " + twoPlayers[PLAYER_1].getFamilyName() );
        System.out.println( "Player 2: " + twoPlayers[PLAYER_2].getGivenName() + " " + twoPlayers[PLAYER_2].getFamilyName() );
    }

    public void printStone() {
        for ( int i = 0; i < numberOfStone; System.out.printf( " <%d,%c>", i+1, stoneArray.get(i) ), i++ );
        System.out.println();
    }

    public void printNextTurn() {
        System.out.println();
        System.out.print( numberOfStone + " stones left:" );
        printStone();
        System.out.println( twoPlayers[currentPlayer].getGivenName() + "'s turn - remove how many?" );
    }

}