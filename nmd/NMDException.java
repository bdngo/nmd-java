package nmd;

/**
 * Exception for NMD.
 * @author Bryan Ngo
 */
public class NMDException extends RuntimeException {

    /** Constructor. */
    NMDException() {
        super();
    }

    /** Overloaded exception with message. */
    NMDException(String msg) {
        super(msg);
    }
}
