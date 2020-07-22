package nmd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static nmd.Main.*;
import static nmd.Utils.*;
import static nmd.Money.DENOMINATIONS;

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
    public void playCard(int index) {
        if (index < 0 || index >= _hand.size()) {
            throw error("Index out of bounds");
        }
        Card toPlay = _hand.remove(index);
        toPlay.action(this);
        if (!(toPlay instanceof Placeable)) {
            DISCARDS.add(toPlay);
        }
    }

    /**
     * `toString` override.
     * @return This player's order, field, bank, and hand.
     */
    @Override
    public String toString() {
        return "=== Player " + _order + " ===\n"
                + printField()
                + printBank()
                + printHand();
    }

    /**
     * Field printer helper method.
     * @return This player's field.
     */
    private String printField() {
        StringBuilder s = new StringBuilder();
        s.append("Current field:\n");
        for (Color color : _field.keySet()) {
            s.append(String.format(
                    "%s: %d\n", color.toString(), _field.get(color)
            ));
        }
        return s.toString();
    }

    /**
     * Bank printer helper method.
     * @return This player's bank.
     */
    private String printBank() {
        StringBuilder s = new StringBuilder();
        s.append("\nCurrent bank:\n");
        for (int money : DENOMINATIONS) {
            if (_bank.containsKey(money)) {
                s.append(money).append("M: ");
                s.append(_bank.get(money)).append("\n");
            }
        }
        return s.toString();
    }

    /**
     * Hand printer helper method.
     * @return This player's hand.
     */
    private String printHand() {
        StringBuilder s = new StringBuilder();
        s.append("\nCurrent hand:\n");
        for (int i = 0; i < _hand.size(); i += 1) {
            s.append(String.format("[%d]", i)).append(": ");
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
        int subtotal = 0;
        Scanner s = new Scanner(System.in);
        while (subtotal < amount) {
            System.out.printf("%d remaining\n", amount - subtotal);
            if (_bank.isEmpty() && _field.isEmpty()) {
                System.out.printf("Player %d has nothing, skipping...\n", _order);
                return;
            } else if (_bank.isEmpty()) {
                Color prop;
                System.out.println(printField());
                System.out.println("No more money! Pick a property to sell:");
                while (true) {
                    try {
                        prop = Color.str2Color(s.nextLine());
                        break;
                    } catch (NMDException e) {
                        System.out.println(e.getMessage());
                    }
                }
                _field.replace(prop, _field.get(prop) - 1);
                payee.addProp(prop);
                subtotal += COLOR2AMT.get(prop);
            } else {
                System.out.println(printBank());
                System.out.println("Pick an amount to withdraw:");
                int prop2Sell;
                do {
                    prop2Sell = Integer.parseInt(s.nextLine());
                } while (!DENOMINATIONS.contains(prop2Sell));
                _bank.replace(prop2Sell, _bank.get(prop2Sell) - 1);
                payee.addMoney(prop2Sell);
                subtotal += prop2Sell;
            }
        }
    }

    /**
     * Sells a card.
     * @param index Index of card to sell.
     */
    public void sell(int index) {
        Card sold = _hand.remove(index);
        if (!(sold instanceof Payable)) {
            throw error("Not a payable card");
        }
        int amt = ((Payable) sold).getValue();
        addMoney(amt);
    }

    /**
     * Discards a card from this player's _HAND.
     * @param index Index of card to discard.
     */
    public void discard(int index) {
        DISCARDS.add(_hand.remove(index));
    }

    /**
     * Getter of _FIELD.
     * @return This player's field.
     */
    public HashMap<Color, Integer> getField() {
        return _field;
    }

    /**
     * Getter of _BANK.
     * @return This player's bank.
     */
    public HashMap<Integer, Integer> getBank() {
        return _bank;
    }

    /**
     * Getter of _HAND.
     * @return This player's hand.
     */
    public ArrayList<Card> getHand() {
        return _hand;
    }

    /**
     * Getter of _ORDER.
     * @return This player's order.
     */
    public int getOrder() {
        return _order;
    }

    /**
     * Adds M to the player's bank.
     * @param m Denomination to add.
     */
    public void addMoney(int m) {
        if (!_bank.containsKey(m)) {
            _bank.put(m, 0);
        }
        _bank.replace(m, _bank.get(m) + 1);
    }

    public void addProp(Color prop) {
        if (!_field.containsKey(prop)) {
            _field.put(prop, 0);
        }
        _field.replace(prop, _field.get(prop) + 1);
    }

    /** Order of the player. */
    private final int _order;

    /** Array holding the player's hand. */
    private final ArrayList<Card> _hand;

    /** Hashmap holding the player's field of properties. */
    private final HashMap<Color, Integer> _field;

    /** Hashmap holding the player's money. */
    private final HashMap<Integer, Integer> _bank;
}
