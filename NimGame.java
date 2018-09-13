/*
COMP90041 Project A+B+C NimGame.java
This is the base class of a gaming instance
Yulun Wu 
Student#. 966159
*/

public abstract class NimGame {
    protected NimPlayer[] twoPlayers = new NimPlayer[2];
    protected int upperBound;
    protected int numberOfStone;
    protected int currentPlayer;
    final static int PLAYER_1 = 0, PLAYER_2 = 1;

    public NimGame() {
        currentPlayer = PLAYER_1;
    }
    //new game instance constructor

    public int getCurrentStone() {
        return numberOfStone;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public NimPlayer getCurrentPlayer() {
        NimPlayer playerCopy = twoPlayers[currentPlayer];
        return playerCopy;
    }

    public void nextPlayer() {
        currentPlayer = ( currentPlayer == PLAYER_1 ) ? PLAYER_2 : PLAYER_1;
    }

    public abstract void printBeginMessage();

    public abstract void printStone();

    public abstract void printNextTurn();

    public void printWinner() {
        System.out.println( twoPlayers[currentPlayer].getGivenName() + " "
                            + twoPlayers[currentPlayer].getFamilyName() + " wins!" );
    }

    public void recordGame() {
        //total number played ++
        twoPlayers[PLAYER_1].setGamePlayed();
        twoPlayers[PLAYER_2].setGamePlayed();
        //winner game won ++
        twoPlayers[currentPlayer].setGameWon();
        //update win rate
        twoPlayers[PLAYER_1].calcWinRate();
        twoPlayers[PLAYER_2].calcWinRate();
    }
}