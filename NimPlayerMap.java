/*
COMP90041 Project A+B+C NimPlayerMap.java
This class is designed to maintain a corresponding data structure storing all players accessed in a Nimsys (main) instance.
Yulun Wu 
Student#. 966159
*/

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class NimPlayerMap {
    //define flag constant
    final private Comparator<NimPlayer> ASC_COMPARATOR = Comparator.comparing( NimPlayer :: getWinRate )
                                                                    .thenComparing( NimPlayer :: getUserName );
    final private Comparator<NimPlayer> DESC_COMPARATOR = Comparator.comparing( NimPlayer :: getWinRate ).reversed()
                                                                    .thenComparing( NimPlayer :: getUserName );
    final private DecimalFormat TWO_DIGITS = new DecimalFormat("00");
    
    //implement a TreeMap to store usernames and corresponding Nimplayer instances
    private TreeMap<String, NimPlayer> playerMap = new TreeMap<String, NimPlayer>();

    public boolean hasPlayer( String player ) {
        return playerMap.containsKey( player );
    }

    public NimPlayer getPlayer( String player ) {
        NimPlayer playerCopy = playerMap.get( player );
        return playerCopy;
    }

    public boolean addPlayer( NimPlayer player ) {
        String userName = player.getUserName();
        if ( !hasPlayer( userName ) ) {
            playerMap.put( userName, player );
            return true;
        }
        return false;
    }

    public boolean removePlayer( String player ) {
        if ( hasPlayer( player ) ) {
            playerMap.remove( player );
            return true;
        }
        return false;
    }
    
    //reload removePlayer() with no argument to implement removing all players
    public void removePlayer() {
        playerMap.clear();
    }

    public boolean resetStats( String player ) {
        if ( hasPlayer( player ) ) {
            getPlayer( player ).setGamePlayed(0);
            getPlayer( player ).setGameWon(0);
            return true;
        }
        return false;
    }

    //reload resetStats() with no argument to implement resetting all players
    public void resetStats() {
        for ( Map.Entry<String, NimPlayer> entry : playerMap.entrySet() ) {
            NimPlayer player = entry.getValue();
            player.setGamePlayed(0);
            player.setGameWon(0);
        }
    }

    //print out player info
    public boolean display( String player ) {
        if ( hasPlayer( player ) ) {
            System.out.println( getPlayer( player ).toString() );
            return true;
        }
        return false;
    }

    public void display() {
        for ( Map.Entry<String, NimPlayer> entry : playerMap.entrySet() ) {
            NimPlayer player = entry.getValue();
            System.out.println( player.toString() );
        }
    }

    public void getRanking( boolean flag ) {
        //convert the value of the TreeMap into a list of players in order to sort using custom comparator 
        List<NimPlayer> sortedList = new ArrayList<>( playerMap.values() );
        sortedList.sort( flag ? DESC_COMPARATOR : ASC_COMPARATOR );
        //print out a table with max 10 players
        int itemMax = ( sortedList.size() > 10 ) ? 10 : sortedList.size();
        for( int i = 0; i < itemMax; i++ ) {
            NimPlayer player = sortedList.get(i);
            System.out.printf( "%-5s| %s games | %s %s\n", ( Math.round( player.getWinRate() ) + "%" ),
                                TWO_DIGITS.format( player.getGamePlayed() ), player.getGivenName(), player.getFamilyName() );
        }
    }

    @SuppressWarnings( "unchecked" )
    public void loadGame( File dataFile ) {
        ObjectInputStream playerDataLoad = null;
        try {
            playerDataLoad = new ObjectInputStream( new FileInputStream( dataFile ) );
            try {
                //de-serialize Treemap from a non-empty file
                if ( dataFile.length() != 0 ) {
                    playerMap = ( TreeMap<String, NimPlayer> ) playerDataLoad.readObject();
                }
            }
            catch ( EOFException e ) {
                //dismiss the End-Of-File exception
            }
            catch ( IOException e ) {
                //System.err.println( e.getMessage() );
                System.err.println( "Failed to load player data file." );
            }
            catch ( ClassNotFoundException e ) {
                e.printStackTrace();
            }
            playerDataLoad.close();
            
        }
        catch ( FileNotFoundException e ) {
            System.err.println( "Player data file not found." );
        }
        catch ( IOException e ) {
            //dismiss exception
        }
    }

    public void saveGame( File dataFile ) {
        ObjectOutputStream playerDataSave = null;
        try {
            playerDataSave = new ObjectOutputStream( new FileOutputStream( dataFile ) );
            try {
                //serialize Treemap into file
                playerDataSave.writeObject( playerMap );
            }
            catch ( Exception e ) {
                System.err.println( "Failed to save player data file." );
            }
            playerDataSave.close();
        } 
        catch ( FileNotFoundException e ) {
            System.err.println( "Player data file not found." );
        }
        catch ( IOException e ) {
            System.err.println( "Failed to open player data file." );
        }
    }
}