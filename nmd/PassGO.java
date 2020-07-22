package nmd;

/**
 * Class for Pass Go cards.
 * @author Bryan Ngo
 */
public class PassGO extends Card implements Payable {

    public PassGO() {
        super();
    }

    /**
     * Draws 2 cards into P's hand.
     * @param p Player to enact the action.
     */
    @Override
    public void action(Player p) {
        Main.drawCards(p, 2);
    }

    /**
     * `toString` override.
     * @return String format of the card.
     */
    @Override
    public String toString() {
        return String.format("Pass GO: Draw 2 cards. (%dM)", getValue());
    }

    /**
     * Getter for the value of the card.
     * @return The card's value.
     */
    @Override
    public int getValue() {
        return 1;
    }
}
