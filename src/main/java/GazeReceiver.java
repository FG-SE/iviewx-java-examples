import de.unihannover.se.iviewxjava.core.receiver.ETReceiver;
import de.unihannover.se.iviewxjava.core.receiver.response.ETResponse;
import de.unihannover.se.iviewxjava.core.receiver.response.ETResponseType;

import java.awt.Point;
import java.awt.MouseInfo;

/** This class showcases, how you can implement your own receiver
 *  that can be used with the IView X Java core library. You will
 *  need to write your own receiver, if you want to interact with
 *  a custom data source.
 *
 *  In order to implement your own receiver, you will need to
 *  extend the ETReceiver abstract class and override the
 *  `getNextFromSource` method.
 */

// Extend the ETReceiver<E> abstract class, which returns our custom GazePoint data type
public class GazeReceiver extends ETReceiver<GazePoint> {

    public GazeReceiver() { }

    // Override the `getNextFromSource` method to return a ETResponse<GazePoint>.
    // Since we have no real eyetracker running, we will use our current mouse
    // location as the gaze point returned.
    @Override
    protected ETResponse<GazePoint> getNextFromSource() {
        // Use current system time as timestamp and current mouse location as gaze point
        long timestamp = System.currentTimeMillis();
        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();

        // If your data source has new data at the time of request, you return NEW_DATA
        // If your data source has no new data at the time of request, you return NO_NEW_DATA_AVAILABLE
        // If your data source runs out of items to return, you return SOURCE_DEPLETED (eg for mocked sources)
        return new ETResponse<>(
                ETResponseType.NEW_DATA,
                new GazePoint(timestamp, mouseLocation.getX(), mouseLocation.getY())
        );
    }
}
