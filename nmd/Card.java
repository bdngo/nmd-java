package nmd;

/**
 * Card superclass.
 * @author Bryan Ngo
 */
public abstract class Card {

    public abstract void action(Player p);

    public String toString() {
        return "Placeholder Card";
    }
}
