import de.unihannover.se.iviewxjava.core.receiver.ETReceiver;
import de.unihannover.se.iviewxjava.core.receiver.response.ETResponse;
import de.unihannover.se.iviewxjava.iviewx.IViewX;
import de.unihannover.se.iviewxjava.iviewx.data.ETEvent;
import de.unihannover.se.iviewxjava.iviewx.data.ETEye;

/** This app showcases, how you can connect to the SMI IView
 *  Eyetracking Server and use the ETEventReceiver to retrieve
 *  eyetracking events from the eyetracker.
 */

public class IViewXEventApp {

    public static void main(String[] args) {
        // Connect to the Eyetracking Server
        // You could also use `IViewX.connectLocal();`
        IViewX.connect("127.0.0.1", 4444, "127.0.0.1", 5555);

        // Calibrate the eyetracker
        IViewX.calibrate();

        // Get the event receiver
        ETReceiver<ETEvent> eventReceiver = IViewX.getEventReceiver();

        // Run loop for 10 seconds
        long currentTime = System.currentTimeMillis();
        long endTime = currentTime + 10000;

        while(System.currentTimeMillis() < endTime) {
            // Get a response from the receiver
            ETResponse<ETEvent> response = eventReceiver.getNext();

            // Use a switch statement to easily work with the response type
            switch(response.getType()) {
                case NEW_DATA:
                    // Print the event information
                    ETEvent event = response.getData();
                    System.out.println(String.format(
                            "%tQ, %tQ, %s, %.f2, %.2f",
                            event.getStartTime(),
                            event.getEndTime(),
                            eyeToString(event.getEye()),
                            event.getPositionX(),
                            event.getPositionY()
                    ));
                    break;
                case NO_NEW_DATA_AVAILABLE:
                    // Handle no new data available scenario
                    System.out.println("No new data available.");
                    break;
                case SOURCE_DEPLETED:
                    // Handle source depleted scenario
                    System.out.println("Source depleted.");
                    break;
            }
        }

        // Disconnect from the Eyetracking Server
        IViewX.disconnect();
    }

    // Utility method to print which eye the event occurred for
    public static String eyeToString(ETEye eye) {
        switch(eye) {
            case LEFT:
                return "left";
            case RIGHT:
                return "right";
            default:
                return "error";
        }
    }
}
