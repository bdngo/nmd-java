package nmd;

public class Utils {

    static NMDException error(String msg, Object... args) {
        return new NMDException(String.format(msg, args));
    }
}
