package nmd;

import static nmd.Color.*;

public class Property extends Card implements Placeable, Payable {

    public Property(Color c) {
        _color = c;
    }

    @Override
    public void action(Player p) {
        p.addProp(_color);
    }

    @Override
    public int getValue() {
        return 0;
    }

    /** Color of this property. */
    private Color _color;
}
