/*
COMP90041 Project A+B+C NimBasicGame.java
This class is designed to maintain a basic gaming instance
Yulun Wu 
Student#. 966159
*/

public class NimBasicGame extends NimGame {
    //argument index
    final static int STONE_TOTAL = 1, STONE_BOUND = 2, BSC_PLAYER_1 = 3, BSC_PLAYER_2 = 4;

    //new game instance constructor
    public NimBasicGame() {
        super();
    }

    public NimBasicGame( NimPlayerMap playerMap, String[] para ) {
        super();
        this.numberOfStone = Integer.valueOf( para[STONE_TOTAL] );
        this.upperBound = Integer.valueOf( para[STONE_BOUND] );
        //store 2 player objects in array
        twoPlayers[PLAYER_1] = playerMap.getPlayer( para[BSC_PLAYER_1] );
        twoPlayers[PLAYER_2] = playerMap.getPlayer( para[BSC_PLAYER_2] );
    }
    
    public void removeStone( int minus ) {
        numberOfStone -= minus;
    }

    public void printBeginMessage() {
        System.out.println( "Initial stone count: " + numberOfStone );
        System.out.println( "Maximum stone removal: " + upperBound );
        System.out.println( "Player 1: " + twoPlayers[PLAYER_1].getGivenName() + " " + twoPlayers[PLAYER_1].getFamilyName() );
        System.out.println( "Player 2: " + twoPlayers[PLAYER_2].getGivenName() + " " + twoPlayers[PLAYER_2].getFamilyName() );
    }

    public void printStone() {
        System.out.print( numberOfStone + " stones left:" );
        for ( int i = 0; i < numberOfStone; System.out.print( " *" ), i++ );
        System.out.println();
    }

    public void printNextTurn() {
        System.out.println();
        printStone();
        System.out.println( twoPlayers[currentPlayer].getGivenName() + "'s turn - remove how many?" );
    }

}