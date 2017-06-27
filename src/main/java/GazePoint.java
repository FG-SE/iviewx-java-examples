import de.unihannover.se.iviewxjava.core.chronologic.ChronologicComparable;

/** This class showcases, how you can implement your own chronologic
 *  data type. You will need to implement your own data type, if you
 *  want to use your own data source.
 *
 *  In order to make your data type chronologic, you will have to
 *  implement the ChronologicComparable interfaces `chrCompareTo`
 *  method. If you do this, your data type will become chronologically
 *  comparable, meaning you can sort instances of your data type
 *  on a timeline.
 */

// Implement the ChronologicComparable<E> interface with itself (chronologically comparable to itself)
public class GazePoint implements ChronologicComparable<GazePoint> {

    // We are using a timestamp to compare our data type
    private long timestamp;

    private double posX;
    private double posY;

    public GazePoint(long timestamp, double posX, double posY) {
        this.timestamp = timestamp;
        this.posX = posX;
        this.posY = posY;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public double getX() {
        return posX;
    }

    public double getY() {
        return posY;
    }

    // Override the `chrCompareTo` method to return a negative value if this data sample
    // was recorded before the other one, zero if they occurred at the same time, and
    // a positive value if this data sample was recorded after the other one.
    // Long.compare(a, b) does exactly that for long values
    @Override
    public int chrCompareTo(GazePoint other) {
        return Long.compare(timestamp, other.timestamp);
    }
}
