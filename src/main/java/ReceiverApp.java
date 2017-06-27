import de.unihannover.se.iviewxjava.core.receiver.ETReceiver;
import de.unihannover.se.iviewxjava.core.receiver.response.ETResponse;

/** This app showcases, how you can use a bare receiver to build
 *  an application using a simple main loop. This application uses
 *  a built-in timer of 10 seconds. If you want to interrupt the
 *  loop through user input, you will have to run the eyetracking
 *  part in another process and manipulate it through a volatile
 *  variable, an interrupt, or another thread-communication solution.
 */

public class ReceiverApp {

    public static void main(String[] args) {
        // Create the receiver
        ETReceiver<GazePoint> receiver = new GazeReceiver();

        // Run loop for 10 seconds
        long currentTime = System.currentTimeMillis();
        long endTime = currentTime + 10000;

        while(System.currentTimeMillis() < endTime) {
            // Get a response from the receiver
            ETResponse<GazePoint> response = receiver.getNext();

            // Use a switch statement to easily work with the response type
            switch(response.getType()) {
                case NEW_DATA:
                    // Print the gaze point information
                    GazePoint gazePoint = response.getData();
                    System.out.println(String.format(
                            "%tQ, %.2f, %.2f",
                            gazePoint.getTimestamp(),
                            gazePoint.getX(),
                            gazePoint.getY()
                    ));
                    break;
                case NO_NEW_DATA_AVAILABLE:
                    // Handle no new data available scenario (will not happen with our receiver)
                    System.out.println("No new data available.");
                    break;
                case SOURCE_DEPLETED:
                    // Handle source depleted scenario (will not happen with our receiver)
                    System.out.println("Source depleted. Shutting down...");
                    return;
            }
        }
    }
}
