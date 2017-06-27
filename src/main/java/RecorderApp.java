import de.unihannover.se.iviewxjava.core.chronologic.ETChronologicCollection;
import de.unihannover.se.iviewxjava.core.receiver.ETReceiver;
import de.unihannover.se.iviewxjava.core.recorder.ETRecorder;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/** This app showcases, how you can use a ETRecorder to easily
 *  build applications where you perform post-analysis on the
 *  recorded data.
 *
 *  This application records data until the user hits Enter for
 *  a second time. After that, the recorded data item count is
 *  printed, and the time, the user looked (or had his mouse in
 *  this case) on the left side of the screen.
 */

public class RecorderApp {

    public static void main(String[] args) throws Exception {
        // Create the receiver
        ETReceiver<GazePoint> receiver = new GazeReceiver();
        // Create the recorder
        ETRecorder<GazePoint> recorder = new ETRecorder<>(receiver);

        // We use a BufferedReader that reads console input as our start/stop input
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Press the Enter key to start recording.");
        reader.readLine();

        // Start recording with a pollrate of 100ms (10 Hz)
        recorder.startRecording(100);
        System.out.println("Recording... (Press the Enter key to stop recording)");
        reader.readLine();

        // Stops the recording
        recorder.stopRecording();
        System.out.println("Stopped Recording. Processing results.");

        // Get the recorded data points in a ETChronologicCollection
        ETChronologicCollection<GazePoint> gazePoints = recorder.getRecordedData();
        long gazePointCount = gazePoints.size();
        // Filter the items using Java 8 Streams
        // Note that this is hard-coded for a 1920 pixel wide screen
        long leftScreenCount = gazePoints.stream()
                                         .filter(gp -> gp.getX() < 960)
                                         .count();

        // Print the item count and left screen percent
        double leftPercent = ((float)leftScreenCount / gazePointCount) * 100;
        System.out.println("Processing finished.");
        System.out.println();
        System.out.println("Recorded gaze points: " + gazePointCount);
        System.out.println(String.format(
                "You have looked at the left side of your screen for %.2f%% of the time",
                leftPercent
        ));
    }
}
