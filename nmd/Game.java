package nmd;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import static nmd.Utils.*;

/**
 * Class for saving game state.
 * @author Bryan Ngo
 */
public class Game implements Serializable {

    /**
     * Constructor for a game of NMD.
     * @param ps List of players.
     */
    public Game(Player[] ps, int turn,
                ArrayList<Card> deck, ArrayList<Card> discards) {
        _playerList = ps;
        _turn = turn;
        _deck = deck;
        _discards = discards;
    }

    /**
     * Saves (or creates) a game in `./.games`.
     * @param name Name of the save.
     */
    public void saveGame(String name) {
        writeObject(join(Main.GAMES_DIR, name), this);
    }

    /**
     * Loads a game from `./.games`.
     * @param name Name of save to load.
     * @return Game class containing game state.
     */
    public static Game loadGame(String name) {
        File path = join(Main.GAMES_DIR, name);
        if (!path.exists()) {
            throw error("%s does not exist", name);
        }
        return readObject(path, Game.class);
    }

    public Player[] getPlayerList() {
        return _playerList;
    }

    public int getTurn() {
        return _turn;
    }

    public ArrayList<Card> getDeck() {
        return _deck;
    }

    public ArrayList<Card> getDiscards() {
        return _discards;
    }

    /** List of players, sorted by play order. */
    private final Player[] _playerList;

    /** Current index of `_playerList` indicating turn. */
    private final int _turn;

    /** Current drawing deck of the game. */
    private final ArrayList<Card> _deck;

    private final ArrayList<Card> _discards;
}
