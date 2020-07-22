package nmd;

import static nmd.Utils.*;

/**
 * Enumeration for the colors of Not Monopoly Deal.
 * @author Bryan Ngo
 */
public enum Color {
    BROWN, L_BLUE, PURPLE, ORANGE, RED,
    YELLOW, GREEN, BLUE, RAILROAD, UTILITY;

    /**
     * `toString` override.
     * @return String formatting of the colors.
     */
    @Override
    public String toString() {
        return switch (this) {
            case BROWN -> "Brown";
            case L_BLUE -> "Light Blue";
            case PURPLE -> "Purple";
            case ORANGE -> "Orange";
            case RED -> "Red";
            case YELLOW -> "Yellow";
            case GREEN -> "Green";
            case BLUE -> "Blue";
            case RAILROAD -> "Railroad";
            case UTILITY -> "Utility";
        };
    }

    /**
     * Name of color to color object conversion.
     * @param c Name of color, case insensitive.
     * @return Color object.
     */
    public static Color str2Color(String c) {
        return switch (c.toLowerCase()) {
            case "brown" -> BROWN;
            case "light blue" -> L_BLUE;
            case "purple" -> PURPLE;
            case "orange" -> ORANGE;
            case "red" -> RED;
            case "yellow" -> YELLOW;
            case "green" -> GREEN;
            case "blue" -> BLUE;
            case "railroad" -> RAILROAD;
            case "utility" -> UTILITY;
            default -> throw error("Not a color");
        };
    }
}
