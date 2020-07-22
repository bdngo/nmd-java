package nmd;

import java.util.Arrays;
import java.util.List;

import static nmd.Utils.*;

/**
 * Class for all money.
 * @author Bryan Ngo
 */
public class Money extends Card implements Payable {

    /** List of denominations. */
    public static final List<Integer> DENOMINATIONS = Arrays.asList(
            1, 2, 3, 4, 5, 10);

    /**
     * Constructor for the `Money` class.
     * @param value The value of the currency. Must be in `DENOMINATIONS`.
     */
    public Money(int value) {
        if (!DENOMINATIONS.contains(value)) {
            throw error("Not a valid denomination.");
        }
        _value = value;
    }

    /**
     * Overrider for Card abstract class.
     * @param p Player to enact the action.
     */
    @Override
    public void action(Player p) {
        p.addMoney(getValue());
    }

    /**
     * Overrider for Payable interface.
     * @return This money's value.
     */
    @Override
    public int getValue() {
        return _value;
    }

    /**
     * `toString` override.
     * @return String formatting of this money.
     */
    @Override
    public String toString() {
        return getValue() + "M";
    }

    /** Worth of the money. */
    private final int _value;
}
