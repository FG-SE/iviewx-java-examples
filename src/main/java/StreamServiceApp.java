import de.unihannover.se.iviewxjava.core.reactive.ETStreamService;
import de.unihannover.se.iviewxjava.core.receiver.ETReceiver;
import io.reactivex.schedulers.Schedulers;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/** This app showcases, how you can build an application using
 *  the ETStreamService. ETStreamService provides a RxJava 2.x
 *  Flowable, which is a data stream that supports backpressure.
 *
 *  You will not understand most of what is happening in this
 *  example without learning the basics of RxJava first. As
 *  this is the most powerful tool that the IView X Java library
 *  provides, it is certainly worth a look. You will be surprised,
 *  how much the reactive programming model fits usual eyetracking
 *  application needs.
 */

public class StreamServiceApp {

    public static void main(String[] args) throws Exception {
        // Create receiver
        ETReceiver<GazePoint> receiver = new GazeReceiver();
        // Create stream service
        ETStreamService<GazePoint> streamService = new ETStreamService<>(receiver);

        // Set up our stream pipeline to print every gaze point that is detected
        // in the upper left quadrant of the screen (1920x1080 pixel screen).
        // This pipeline also shows how to apply backpressure to the stream
        // (you will usually use `onBackpressureLatest` for eyetracking applications,
        // as you want to most up-to-date value when requesting) and how to
        // schedule the operators on a computational thread (increases throughput).
        // The operators work pretty much like the Java 8 Stream operators.
        streamService.getStream()
                     .onBackpressureLatest()
                     .observeOn(Schedulers.computation())
                     .doOnComplete(() -> System.out.println("Shut down stream. Shutting down."))
                     .filter(gp -> gp.getX() < 960)
                     .filter(gp -> gp.getY() < 540)
                     .forEach(gp -> {
                         System.out.println(String.format(
                                 "%tQ, %.2f, %.2f",
                                 gp.getTimestamp(),
                                 gp.getX(),
                                 gp.getY()
                         ));
                     });

        // We use a BufferedReader that reads console input as our start/stop input
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Press the Enter key to start the stream service." +
                "(Press Enter key again during streaming to stop the service)");
        reader.readLine();

        // Start the stream with a pollrate of 20ms (50 Hz)
        streamService.start(20);

        reader.readLine();

        // Stop the stream
        streamService.stop();

        // Shut down the stream (emits onComplete)
        streamService.shutdown();
    }
}
