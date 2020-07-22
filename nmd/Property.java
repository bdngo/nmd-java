package nmd;

import java.util.HashMap;

import static nmd.Color.*;

/**
 * Class for property cards.
 * @author Bryan Ngo
 */
public class Property extends Card implements Placeable, Payable {

    /** Hashmap containing each property color's value. */
    private static final HashMap<Color, Integer> PROP_WORTH = new HashMap<>();
    static {
        PROP_WORTH.put(BROWN, 1);
        PROP_WORTH.put(L_BLUE, 1);
        PROP_WORTH.put(PURPLE, 2);
        PROP_WORTH.put(ORANGE, 2);
        PROP_WORTH.put(RAILROAD, 2);
        PROP_WORTH.put(UTILITY, 2);
        PROP_WORTH.put(RED, 3);
        PROP_WORTH.put(YELLOW, 3);
        PROP_WORTH.put(GREEN, 4);
        PROP_WORTH.put(BLUE, 4);
    }

    /**
     * Constructor for a property.
     * @param c color of the property.
     */
    public Property(Color c) {
        _color = c;
        _value = PROP_WORTH.get(c);
    }

    /**
     * Override of the abstract card action.
     * @param p Player to enact the action.
     */
    @Override
    public void action(Player p) {
        p.addProp(_color);
    }

    /**
     * Override for the Payable interface.
     * @return The property's value.
     */
    @Override
    public int getValue() {
        return _value;
    }

    /**
     * `toString` override.
     * @return String formatting for this card.
     */
    @Override
    public String toString() {
        return String.format("%s Property (%dM)", _color, getValue());
    }

    /** Color of this property. */
    private final Color _color;

    /** Value of this property. */
    private final int _value;
}
