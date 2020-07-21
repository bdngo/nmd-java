package nmd;

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

    @Override
    public String toString() {
        return "Property";
    }

    /** Color of this property. */
    private final Color _color;
}
