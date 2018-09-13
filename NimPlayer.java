/*
COMP90041 Project A+B+C NimPlayer.java
This base class is designed to implement an abstract player
Yulun Wu 
Student#. 966159
*/

import java.io.Serializable;
import java.util.Scanner;

public abstract class NimPlayer implements Serializable {

    protected String userName;
    protected String familyName;
    protected String givenName;
    protected int gamePlayed;
    protected int gameWon;
    protected double winRate;
    protected int numberRemoval;
    //SerialVersionUID
    private static final long serialVersionUID = -6095447794993215101L;
    final protected static int MIN_REMOVAL = 1;

    //constructor
    public NimPlayer() {
        gamePlayed = 0;
        gameWon = 0;
        winRate = 0;
    }

    public NimPlayer( String userName, String familyName, String givenName ) {
        this.userName = userName;
        this.familyName = familyName;
        this.givenName = givenName;
        gamePlayed = 0;
        gameWon = 0;
        winRate = 0;
    }

    public abstract String getUserName();

    public abstract String getGivenName();

    public abstract String getFamilyName();

    public abstract int getGamePlayed();

    public abstract int getGameWon();

    public abstract double getWinRate();

    public abstract void calcWinRate();

    public abstract void setFullName( String newFamilyName, String newGivenName );

    public abstract void setGamePlayed();

    public abstract void setGamePlayed( int gamePlayed );

    public abstract void setGameWon();

    public abstract void setGameWon( int gameWon );

    public abstract String toString();

    public abstract int removeStone( Scanner keyboard, NimBasicGame gameInst );

    public abstract String removeStoneAdv( Scanner keyboard, NimAdvancedGame gameInst );

}