package nmd;

/**
 * Card superclass.
 * @author Bryan Ngo
 */
public abstract class Card {

    /**
     * Abstract method for card actions.
     * @param p Player to enact the action.
     */
    public abstract void action(Player p);

    /**
     * `toString` override.
     * @return String format of the card.
     */
    @Override
    public abstract String toString();
}
