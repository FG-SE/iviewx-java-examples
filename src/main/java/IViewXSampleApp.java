import de.unihannover.se.iviewxjava.core.receiver.ETReceiver;
import de.unihannover.se.iviewxjava.core.receiver.response.ETResponse;
import de.unihannover.se.iviewxjava.iviewx.IViewX;
import de.unihannover.se.iviewxjava.iviewx.data.ETSample;

/** This app showcases, how you can connect to the SMI IView
 *  Eyetracking Server and use the ETSampleReceiver to retrieve
 *  eyetracking samples from the eyetracker.
 */

public class IViewXSampleApp {

    public static void main(String[] args) {
        // Connect to the Eyetracking Server
        // You could also use `IViewX.connectLocal();`
        IViewX.connect("127.0.0.1", 4444, "127.0.0.1", 5555);

        // Calibrate the eyetracker
        IViewX.calibrate();

        // Get the sample receiver
        ETReceiver<ETSample> sampleReceiver = IViewX.getSampleReceiver();

        // Run loop for 10 seconds
        long currentTime = System.currentTimeMillis();
        long endTime = currentTime + 10000;

        while(System.currentTimeMillis() < endTime) {
            // Get a response from the receiver
            ETResponse<ETSample> response = sampleReceiver.getNext();

            // Use a switch statement to easily work with the response type
            switch(response.getType()) {
                case NEW_DATA:
                    // Print the sample information
                    ETSample sample = response.getData();
                    System.out.println(String.format(
                        "%tQ, [%.2f, %.2f, %.2f, %.2f, %.2f, %.2f], [%.2f, %.2f, %.2f, %.2f, %.2f, %.2f]",
                        sample.getTimestamp(),
                            sample.getLeftEye().getDiameter(),
                            sample.getLeftEye().getEyePositionX(),
                            sample.getLeftEye().getEyePositionY(),
                            sample.getLeftEye().getEyePositionZ(),
                            sample.getLeftEye().getGazeX(),
                            sample.getLeftEye().getGazeY(),
                            sample.getRightEye().getDiameter(),
                            sample.getRightEye().getEyePositionX(),
                            sample.getRightEye().getEyePositionY(),
                            sample.getRightEye().getEyePositionZ(),
                            sample.getRightEye().getGazeX(),
                            sample.getRightEye().getGazeY()
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
}
