package nmd;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import static nmd.Utils.*;
import static nmd.Game.*;
import static nmd.Color.*;
import static nmd.Money.DENOMINATIONS;

/**
 * Simulation of the game Monopoly Deal in Java.
 * @author Bryan Ngo
 */
public class Main {

    /** Mapping containing all the colors and their amounts. */
    public static final HashMap<Color, Integer> COLOR2AMT = new HashMap<>();
    static {
        COLOR2AMT.put(BROWN, 2);
        COLOR2AMT.put(L_BLUE, 3);
        COLOR2AMT.put(PURPLE, 3);
        COLOR2AMT.put(ORANGE, 3);
        COLOR2AMT.put(RED, 3);
        COLOR2AMT.put(YELLOW, 3);
        COLOR2AMT.put(GREEN, 3);
        COLOR2AMT.put(BLUE, 2);
        COLOR2AMT.put(RAILROAD, 4);
        COLOR2AMT.put(UTILITY, 2);
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

    /** The CWD of this project. */
    public static File CWD = new File(".");

    /** The directory storing all game files. */
    public static File GAMES_DIR = join(CWD, ".games");

    /**
     * Main method.
     * @param args Commands to issue.
     */
    public static void main(String[] args) {
        switch (args[0]) {
            case "start" -> initGame(args);
            case "load" -> loadFromFile(args);
            case "delete" -> deleteGame(args);
            default -> throw error("Not a valid command.");
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
        {
            DECK = new ArrayList<>();
            DECK.addAll(constructMoney());
            DECK.addAll(constructProps());
        }
        Collections.shuffle(DECK);
        DISCARDS = new ArrayList<>();
        PLAYERS = new Player[numPlayers];
        for (int i = 0; i < PLAYERS.length; i += 1) {
            drawCards(PLAYERS[i] = new Player(i), 5);
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
        if (DECK.isEmpty()) {
            DECK.addAll(DISCARDS);
            DISCARDS.clear();
            Collections.shuffle(DECK);
        }
        drawCards(p, 2);
        Scanner s;
        String cmd;
        int toPlay;
        for (int i = 3; i > 0; i -= 1) {
            do {
                System.out.println(p);
                System.out.printf(
                        "Play a card "
                        + "(%d action(s) left, 'end' to end turn, "
                        + "'sell' to sell a card):\n", i);
                s = new Scanner(System.in);
                cmd = s.nextLine();
                if (cmd.equals("end")) {
                    return;
                } else if (cmd.equals("sell")) {
                    System.out.println("Choose an index to sell:");
                    int toSell;
                    do {
                        toSell = Integer.parseInt(s.nextLine());
                        p.sell(toSell);
                    } while (toSell < 0 || toSell >= p.getHand().size());
                } else {
                    toPlay = Integer.parseInt(cmd);
                    p.playCard(toPlay);
                }
            } while (cmd.isEmpty());
        }
    }

    /* DECK CONSTRUCTION */

    // TODO: deck builder

    /**
     * Constructs the game's money.
     * @return An `ArrayList` containing this game's money.
     */
    public static ArrayList<Card> constructMoney() {
        ArrayList<Card> money = new ArrayList<>();
        for (int i = 0; i < 6; i += 1) {
            money.add(new Money(DENOMINATIONS.get(0)));
        }
        for (int i = 0; i < 5; i += 1) {
            money.add(new Money(DENOMINATIONS.get(1)));
        }
        for (int i = 0; i < 3; i += 1) {
            money.add(new Money(DENOMINATIONS.get(2)));
            money.add(new Money(DENOMINATIONS.get(3)));
        }
        for (int i = 0; i < 2; i += 1) {
            money.add(new Money(DENOMINATIONS.get(4)));
        }
        money.add(new Money(DENOMINATIONS.get(5)));
        return money;
    }

    /**
     * Constructor of the game's properties.
     * @return An `ArrayList` containing this game's properties.
     */
    public static ArrayList<Card> constructProps() {
        ArrayList<Card> props = new ArrayList<>();
        for (Color c : COLOR2AMT.keySet()) {
            for (int i = 0; i < COLOR2AMT.get(c); i += 1) {
                props.add(new Property(c));
            }
        }
        return props;
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
