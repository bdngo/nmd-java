package nmd;

import java.util.ArrayList;
import java.util.HashMap;

import static nmd.Main.*;

/**
 * Player class.
 * @author Bryan Ngo
 */
public class Player {

    /**
     * Constructor for the Player class.
     * @param order Order of the player in the game.
     */
    public Player(int order) {
        _order = order;
        _hand = new ArrayList<>();
        _field = new HashMap<>();
        _bank = new HashMap<>();
    }

    /**
     * Draws a card from MAIN.DECK into this player's hand.
     */
    public void draw() {
        _hand.add(DECK.remove(0));
    }

    /**
     * Plays a card from this player's _HAND.
     * @param index Index of the card to play.
     */
    public void play(int index) {
        Card toPlay = _hand.remove(index);
        toPlay.action(this);
        if (!(toPlay instanceof Placeable)) {
            DISCARDS.add(toPlay);
        }
    }

    // TODO: toString

    /**
     * Pays PAYEE with AMOUNT dollars in the Monopoly Deal fashion.
     * @param payee Player to pay.
     * @param amount Number of Monopoly monies to pay.
     */
    public void pay(Player payee, int amount) {
        // TODO: this
    }

    /**
     * Getter of _FIELD.
     * @return The player's field.
     */
    public HashMap<Color, Integer> getField() {
        return _field;
    }

    /**
     * Adds M to the player's bank.
     * @param m Denomination to add.
     */
    public void addMoney(Money m) {
        if (!_bank.containsKey(m)) {
            _bank.put(m, 0);
        }
        _bank.replace(m, _bank.get(m), _bank.get(m) + 1);
    }

    public void addProp(Color prop) {
        if (!_field.containsKey(prop)) {
            _field.put(prop, 0);
        }
        _field.replace(prop, _field.get(prop), _field.get(prop) + 1);
    }

    /** Order of the player. */
    private final int _order;

    /** Array holding the player's hand. */
    private ArrayList<Card> _hand;

    /** Hashmap holding the player's field of properties. */
    private HashMap<Color, Integer> _field;

    /** Hashmap holding the player's money */
    private HashMap<Money, Integer> _bank;
}
