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
    public static final List<Integer> DENOMINATIONS = Arrays.asList(1, 2, 3, 4, 5, 10);

    public Money(int value) {
        if (!DENOMINATIONS.contains(value)) {
            throw error("Not a valid denomination.");
        }
        _value = value;
    }

    @Override
    public void action(Player p) {
        p.addMoney(this);
    }

    @Override
    public int getValue() {
        return _value;
    }

    @Override
    public String toString() {
        return getValue() + "M";
    }

    /** Worth of the money. */
    private final int _value;
}
