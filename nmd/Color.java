package nmd;

public enum Color {
    BROWN, L_BLUE, PURPLE, ORANGE, RED,
    YELLOW, GREEN, BLUE, RAILROAD, UTILITY;

    @Override
    public String toString() {
        switch (this) {
            case BROWN:
                return "Brown";
            case L_BLUE:
                return "Light Blue";
            case PURPLE:
                return "Purple";
            case ORANGE:
                return "Orange";
            case RED:
                return "Red";
            case YELLOW:
                return "Yellow";
            case GREEN:
                return "Green";
            case BLUE:
                return "Blue";
            case RAILROAD:
                return "Railroad";
            case UTILITY:
                return "Utility";
            default:
                return "Uncolored";
        }
    }
}
