package nmd;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import static nmd.Utils.*;
import static nmd.Game.*;

/**
 * Simulation of the game Monopoly Deal in Java.
 * @author Bryan Ngo
 */
public class Main {

    /** Mapping containing all the colors and their amounts. */
    public static final HashMap<Color, Integer> COLOR2AMT = new HashMap<>();
    static {
        COLOR2AMT.put(Color.BROWN, 2);
        COLOR2AMT.put(Color.L_BLUE, 3);
        COLOR2AMT.put(Color.PURPLE, 3);
        COLOR2AMT.put(Color.ORANGE, 3);
        COLOR2AMT.put(Color.RED, 3);
        COLOR2AMT.put(Color.YELLOW, 3);
        COLOR2AMT.put(Color.GREEN, 3);
        COLOR2AMT.put(Color.BLUE, 2);
        COLOR2AMT.put(Color.RAILROAD, 4);
        COLOR2AMT.put(Color.UTILITY, 2);
    }

    /** All the playable/discarded cards in the game. */
    public static ArrayList<Card> DECK, DISCARDS;

    /** All the players in the game. */
    public static Player[] PLAYERS;

    /**
     * Starting turn of the game. Usually zero when initializing,
     * but may be different when loading a previous save.
     */
    private static int INIT_TURN = 0;

    public static File CWD = new File(".");

    public static File GAMES_DIR = join(CWD, ".games");

    /**
     * Main method.
     * @param args Commands to issue.
     */
    public static void main(String[] args) {
        switch (args[0]) {
            case "start":
                initGame(args);
                break;
            case "load":
                loadFromFile(args);
                break;
            case "delete":
                deleteGame(args);
                break;
            default:
                throw error("Not a valid command.");
        }
        play();
    }

    /* UTILITY METHODS */

    /**
     * Draws AMT cards into P's hand.
     * @param p Player to draw.
     * @param amt Amount to draw.
     */
    public static void drawCards(Player p, int amt) {
        while (amt > 0) {
            p.draw();
            amt -= 1;
        }
    }

    /**
     * Generates a fail-safe input.
     * @param query Question to ask for input.
     * @param error Error message to print.
     * @return The answer to standard input.
     */
    public static String fsInput(String query, String error) {
        Scanner s;
        while (true) {
            try {
                System.out.println(query);
                s = new Scanner(System.in);
                if (s.toString().isEmpty()) {
                    throw error("Input is empty.");
                }
                break;
            } catch (NMDException e) {
                System.out.println(error);
                continue;
            }
        }
        return s.toString();
    }

    /* GAME FUNCTIONALITY */

    /**
     * Initializes a new game.
     * @param args Number of players to start with.
     */
    public static void initGame(String[] args) {
        validateNumArgs(args, 2);
        int numPlayers = Integer.parseInt(args[1]);
        if (numPlayers <= 2 || numPlayers >= 5) {
            throw error("Too little/too many players.");
        }
        GAMES_DIR.mkdir();
        DECK = constructMoney();
        DISCARDS = new ArrayList<>();
        PLAYERS = new Player[numPlayers];
        for (int i = 0; i < PLAYERS.length; i += 1) {
            PLAYERS[i] = new Player(i);
        }
    }

    /**
     * Loads a file from ARGS.
     * @param args File path to load file from.
     */
    public static void loadFromFile(String[] args) {
        validateNumArgs(args, 2);
        File path = join(GAMES_DIR, args[1]);
        if (!path.exists()) {
            throw error("Game file does not exist.");
        }
        Game toPlay = loadGame(args[1]);
        DECK = toPlay.getDeck();
        DISCARDS = toPlay.getDiscards();
        PLAYERS = toPlay.getPlayerList();
        INIT_TURN = toPlay.getTurn();
    }

    /**
     * Deletes a game from ARGS.
     * @param args File path to delete file from.
     */
    public static void deleteGame(String[] args) {
        validateNumArgs(args, 2);
        File path = join(GAMES_DIR, args[1]);
        if (!path.exists()) {
            throw error("Game file does not exist.");
        }
        path.delete();
    }

    /**
     * Starts a game of Not Monopoly Deal.
     */
    public static void play() {
        for (int i = INIT_TURN; i < PLAYERS.length;
             i = (i + 1) % PLAYERS.length) {
            turn(PLAYERS[i]);
            if (isWinner(PLAYERS[i])) {
                System.out.printf("Player %d has won!%n", i + 1);
                System.exit(0);
            }
        }
    }

    /**
     * Counts the number of full sets P has.
     * @param p Player to count.
     * @return Number of full sets.
     */
    public static int fullSets(Player p) {
        int numSets = 0;
        for (Color i : p.getField().keySet()) {
            if (p.getField().get(i) >= COLOR2AMT.get(i)) {
                numSets += 1;
            }
        }
        return numSets;
    }

    /**
     * Checks whether P is a winner.
     * @param p Player to check.
     * @return If P is a winner.
     */
    public static boolean isWinner(Player p) {
        return fullSets(p) >= 3;
    }

    /**
     * Enacts a turn.
     * @param p Player to enact a turn on.
     */
    public static void turn(Player p) {
        // TODO: this
        if (DECK.isEmpty()) {
            DECK.addAll(DISCARDS);
            DISCARDS.clear();
            Collections.shuffle(DECK);
        }
        drawCards(p, 2);
        Scanner s;
        int toPlay;
        do {
            System.out.println("Play a card:\n");
            System.out.println(p);
            s = new Scanner(System.in);
            toPlay = Integer.parseInt(s.toString());
        } while (!(0 <= toPlay && toPlay < p.getHand().size()));
        p.play(toPlay);
    }

    /* DECK CONSTRUCTION */

    // TODO: deck builder
    public static ArrayList<Card> constructMoney() {
        ArrayList<Card> money = new ArrayList<>();
        for (int i = 0; i < 6; i += 1) {
            money.add(new Money(Money.DENOMINATIONS.get(0)));
        }
        for (int i = 0; i < 5; i += 1) {
            money.add(new Money(Money.DENOMINATIONS.get(1)));
        }
        for (int i = 0; i < 3; i += 1) {
            money.add(new Money(Money.DENOMINATIONS.get(2)));
            money.add(new Money(Money.DENOMINATIONS.get(3)));
        }
        for (int i = 0; i < 2; i += 1) {
            money.add(new Money(Money.DENOMINATIONS.get(4)));
        }
        money.add(new Money(Money.DENOMINATIONS.get(5)));
        return money;
    }

    /**
     * Checks the number of arguments versus the expected number,
     *
     * @param args Argument array from command line
     * @param n Number of expected arguments
     */
    public static void validateNumArgs(String[] args, int n) {
        if (args.length != n) {
            throw error("Incorrect operands.");
        }
    }
}
