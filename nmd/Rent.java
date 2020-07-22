package nmd;

import java.util.HashMap;
import java.util.Scanner;

import static nmd.Color.*;

/**
 * Class for all rent cards.
 * @author Bryan Ngo
 */
public class Rent extends Card implements Payable {

    public static HashMap<Color, int[]> COLOR2RENT = new HashMap<>();
    static {
        COLOR2RENT.put(BROWN, new int[] {1, 2});
        COLOR2RENT.put(L_BLUE, new int[] {2, 4, 7});
        COLOR2RENT.put(PURPLE, new int[] {1, 2, 4});
        COLOR2RENT.put(ORANGE, new int[] {1, 3, 5});
        COLOR2RENT.put(RED, new int[] {2, 3, 6});
        COLOR2RENT.put(YELLOW, new int[] {2, 4, 6});
        COLOR2RENT.put(GREEN, new int[] {2, 4, 7});
        COLOR2RENT.put(BLUE, new int[] {3, 8});
        COLOR2RENT.put(RAILROAD, new int[] {1, 2, 3, 4});
        COLOR2RENT.put(UTILITY, new int[] {1, 2});
    }

    public Rent(Color c1, Color c2) {
        _colors[0] = c1;
        _colors[1] = c2;
    }

    /**
     * Overrides the `Card` abstract method.
     * Forces every player to pay rent according to the table.
     * @param p Player to enact the action.
     */
    @Override
    public void action(Player p) {
        Scanner s = new Scanner(System.in);
        Color color2Rent;
        do {
            System.out.printf("Choose a color to rent on (%s/%s): ",
                    _colors[0], _colors[1]);
            color2Rent = str2Color(s.nextLine());
        } while ((_colors[0] != color2Rent && _colors[1] != color2Rent)
                || !p.getField().containsKey(color2Rent));
        // TODO: housing rent
        int maxRent = COLOR2RENT.get(color2Rent).length;
        int amt2Rent = COLOR2RENT.get(color2Rent)[
                Math.min(p.getField().get(color2Rent), maxRent) - 1];
        for (Player payer : Main.PLAYERS) {
            if (payer.getOrder() != p.getOrder()) {
                payer.pay(p, amt2Rent);
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Rent (%s/%s) (%dM)",
                _colors[0], _colors[1], getValue());
    }

    @Override
    public int getValue() {
        return 1;
    }

    /** Color of this rent card. */
    private final Color[] _colors = new Color[2];
}
