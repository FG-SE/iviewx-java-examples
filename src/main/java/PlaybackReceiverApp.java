import de.unihannover.se.iviewxjava.core.chronologic.ETChronologicCollection;
import de.unihannover.se.iviewxjava.core.receiver.ETPlaybackReceiver;
import de.unihannover.se.iviewxjava.core.receiver.response.ETResponse;
import de.unihannover.se.iviewxjava.core.receiver.response.ETResponseType;

/** This app showcases, how you can use a ETPlaybackReceiver
 *  to play back data from a ETChronologicCollection in your
 *  application. You will usually use this for analyzing
 *  pre-recorded data, or for mocking a receiver in tests.
 *  This application plays back 100 dummy data samples and
 *  then finishes automatically by terminating the receiving
 *  loop because of the SOURCE_DEPLETED response type.
 */

public class PlaybackReceiverApp {

    public static void main(String[] args) {
        // Create playback receiver
        ETPlaybackReceiver<GazePoint> playbackReceiver = new ETPlaybackReceiver<>();

        // Populate playback receiver with dummy data
        ETChronologicCollection<GazePoint> playbackData = new ETChronologicCollection<>();

        for(int i = 0; i < 100; i++) {
            playbackData.add(new GazePoint(i, i, i));
        }

        playbackReceiver.setElements(playbackData);

        // Use the playback receiver in a loop
        for(ETResponse<GazePoint> response = playbackReceiver.getNext();
            response.getType() != ETResponseType.SOURCE_DEPLETED;
            response = playbackReceiver.getNext())
        {
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
            }
        }

        // Source depleted scenario cancels our loop
        System.out.println("Source depleted. Shutting down.");
    }
}
