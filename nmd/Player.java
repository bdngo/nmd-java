package nmd;

import java.util.ArrayList;
import java.util.HashMap;

import static nmd.Main.*;
import static nmd.Utils.*;

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
        if (index < 0 || index >= _hand.size()) {
            throw error("Index out of bounds");
        }
        Card toPlay = _hand.remove(index);
        toPlay.action(this);
        if (!(toPlay instanceof Placeable)) {
            DISCARDS.add(toPlay);
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("=== Player ").append(_order).append(" ===\n");
        s.append("Current field:\n");
        for (Color color : _field.keySet()) {
            s.append(String.format(
                    "%s: %d\n", color.toString(), _field.get(color)
            ));
        }
        s.append("\nCurrent bank:\n");
        for (Money money : _bank.keySet()) {
            s.append(money.getValue()).append("\n");
        }
        s.append("\nCurrent hand:\n");
        for (int i = 0; i < _hand.size(); i += 1) {
            s.append(i).append(": ");
            s.append(_hand.get(i).toString()).append("\n");
        }
        return s.toString();
    }

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
     * Getter of _HAND.
     * @return The player's hand.
     */
    public ArrayList<Card> getHand() {
        return _hand;
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

    /** Hashmap holding the player's money. */
    private HashMap<Money, Integer> _bank;
}
