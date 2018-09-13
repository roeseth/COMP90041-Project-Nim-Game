/*
	NimAIPlayer.java
	
	This class is provided as a skeleton code for the tasks of 
	Sections 2.4, 2.5 and 2.6 in Project C. Add code (do NOT delete any) to it
	to finish the tasks. 
*/
/*
COMP90041 Project A+B+C NimAIPlayer.java
This class is designed to implement a AI player
Yulun Wu 
Student#. 966159
*/

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;


public class NimAIPlayer extends NimPlayer implements Testable {
	// you may further extend a class or implement an interface
	// to accomplish the tasks.	
	final static int POSITION = 0, QUANTITY = 1;

	public NimAIPlayer() {
        super();
    }

    public NimAIPlayer( String userName, String familyName, String givenName ) {
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
		int remainder = currentStone % (upperBound + 1 );
		
		if ( remainder != 1 ) {
			//win strategy
			numberRemoval = ( remainder - 1 ) % ( upperBound + 1 );
		}
		else {
			Random rand = new Random();
			if ( currentStone < upperBound ) {
				numberRemoval = rand.nextInt(currentStone) + 1;
			}
			else {
				numberRemoval = rand.nextInt(upperBound) + 1;
			}
		}
		return numberRemoval;
	}
	
	public String advancedMove(boolean[] available, String lastMove) {
		// the implementation of the victory
		// guaranteed strategy designed by you
		String move = "";
		int lastIndex = 0;
		int lastQuantity =0;
		int total = available.length;
		if ( lastMove != null ) {
			lastIndex = Integer.valueOf( lastMove.split(" ")[POSITION] ) - 1;
			lastQuantity = Integer.valueOf( lastMove.split(" ")[QUANTITY] );
		}
		int left = 0;
		int firstOne = total - 1;
		for ( int i = total - 1; i >= 0; i-- ) {
			if( available[i] ) {
				left++;
			}
			if( available[i] ) {
				firstOne = i;
			}
		}
		if ( left == 1 ) {
			//left 2 or 1, take all
			move = ( firstOne + 1 ) + " " + 1;
		}
		else if ( left == 2 && available[firstOne] && available[firstOne+1] ) {
			move = ( firstOne + 1 ) + " " + 2;
		}
		else if ( total % 2 == 1 ) {
			int middle = ( total - 1 ) / 2;
			if ( available[middle] ) {
				if ( left % 2 == 1 ) {
					move = ( middle + 1 ) + " " + 1;
				}
				else {
					if( available[middle-1] ) {
						move = ( middle ) + " " + 2;
					}
					else {
						move = ( middle + 1 ) + " " + 2;
					}
				}
			}
			else {
				if ( total - left == 1 ) {
					move = "1 2";
				}
				else if ( total - left == 2 ) {
					move = available[middle-1] ? middle + " " + 1 : ( middle + 2 ) + " " + 1;
				}
				else {
					if ( ( left % 2 == 1 && lastQuantity == 1 )
						|| ( left % 2 == 0 && lastQuantity == 2 ) ) {
							if ( ( lastQuantity == 1 ) && available[total - lastIndex - lastQuantity] ) {
								move = ( total - lastIndex - lastQuantity + 1 ) + " " + lastQuantity;
							}
							else if ( ( lastQuantity == 2 ) 
								&& available[total - lastIndex - lastQuantity] 
								&& available[total - lastIndex - lastQuantity + 1] ) {
								move = ( total - lastIndex - lastQuantity + 1 ) + " " + lastQuantity;
							}
							else {
								move = ( firstOne + 1 ) + " " + 1;
							}
					}
					else {
						move = ( firstOne + 1 ) + " " + 1;
					}
				}
			}
		}
		else {
			int middle = total / 2 - 1;
			if ( available[middle] && available[middle+1] ) {
				move = ( middle + 1 ) + " " + 2;
			}
			else if ( available[middle] || available[middle+1] ) {
				if ( left % 2 == 0 ) {
					if ( available[middle] ) {
						move = ( middle + 2 ) + " " + 2;
					}
					else {
						move = ( middle ) + " " + 2;
					}
				}
				else {
					if ( available[middle] ) {
						move = ( middle + 2 ) + " " + 1;
					} 
					else {
						move = ( middle + 1 ) + " " + 1;
					}
				}
			}
			else {
				if ( ( lastQuantity == 1 ) && available[total - lastIndex - lastQuantity] ) {
					move = ( total - lastIndex - lastQuantity + 1 ) + " " + lastQuantity;
				}
				else if ( ( lastQuantity == 2 ) 
					&& available[total - lastIndex - lastQuantity] 
					&& available[total - lastIndex - lastQuantity + 1] ) {
					move = ( total - lastIndex - lastQuantity + 1 ) + " " + lastQuantity;
				}
				else {
					move = ( firstOne + 1 ) + " " + 1;
				}
			}
		}
		return move;
	}

	public String removeStoneAdv( Scanner keyboard, NimAdvancedGame gameInst ) {
		boolean[] available = gameInst.getAvailable();
		String lastMove = gameInst.getLastMove();
		String movedParameter = advancedMove(available, lastMove);
        return movedParameter;
    }
	
}
