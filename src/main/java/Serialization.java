import de.unihannover.se.iviewxjava.iviewx.data.ETEvent;
import de.unihannover.se.iviewxjava.iviewx.data.ETEye;
import de.unihannover.se.iviewxjava.iviewx.data.ETEyeData;
import de.unihannover.se.iviewxjava.iviewx.data.ETSample;
import de.unihannover.se.iviewxjava.iviewx.serialization.json.ETEventJsonSerializer;
import de.unihannover.se.iviewxjava.iviewx.serialization.json.ETJsonOutputType;
import de.unihannover.se.iviewxjava.iviewx.serialization.json.ETSampleJsonSerializer;

/** This example showcases, how you can serialize and deserialize
 *  an ETSample and an ETEvent to and from JSON.
 */

public class Serialization {

    public static void main(String[] args) {
        // Create dummy sample
        ETSample sample = new ETSample(
                new ETEyeData(1,1,1,1,1,1),
                new ETEyeData(2,2,2,2,2,2),
                10
        );

        // Serialize to JSON
        String sampleJson = ETSampleJsonSerializer.serialize(sample, ETJsonOutputType.PRETTY);
        System.out.println(sampleJson);
        System.out.println();

        // Deserialize from JSON
        ETSample deserializedSample = ETSampleJsonSerializer.deserialize(sampleJson);
        System.out.println(String.format(
                "%tQ, [%.2f, %.2f, %.2f, %.2f, %.2f, %.2f], [%.2f, %.2f, %.2f, %.2f, %.2f, %.2f]",
                deserializedSample.getTimestamp(),
                deserializedSample.getLeftEye().getDiameter(),
                deserializedSample.getLeftEye().getEyePositionX(),
                deserializedSample.getLeftEye().getEyePositionY(),
                deserializedSample.getLeftEye().getEyePositionZ(),
                deserializedSample.getLeftEye().getGazeX(),
                deserializedSample.getLeftEye().getGazeY(),
                deserializedSample.getRightEye().getDiameter(),
                deserializedSample.getRightEye().getEyePositionX(),
                deserializedSample.getRightEye().getEyePositionY(),
                deserializedSample.getRightEye().getEyePositionZ(),
                deserializedSample.getRightEye().getGazeX(),
                deserializedSample.getRightEye().getGazeY()
        ));
        System.out.println();

        // Create dummy event
        ETEvent event = new ETEvent(10, 20, ETEye.LEFT, 1, 1);

        // Serialize to JSON
        String eventJson = ETEventJsonSerializer.serialize(event, ETJsonOutputType.PRETTY);
        System.out.println(eventJson);
        System.out.println();

        // Deserialize from JSON
        ETEvent deserializedEvent = ETEventJsonSerializer.deserialize(eventJson);
        System.out.println(String.format(
                "%tQ, %tQ, %s, %.2f, %.2f",
                deserializedEvent.getStartTime(),
                deserializedEvent.getEndTime(),
                eyeToString(deserializedEvent.getEye()),
                deserializedEvent.getPositionX(),
                deserializedEvent.getPositionY()
        ));
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
