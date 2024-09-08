import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
/**
 * The GravityHandler class manages the gravity effect on the drill machine.
 */
public class GravityManager extends AnimationTimer {
    private Group root;
    private ImageView drillImageView;
    private double lastGravityCheckTime = 0;
    private final double gravityCheckInterval = 1.0; // Gravity check interval in seconds
    private final double gravityAmount = 50; // Amount of gravity to apply
    /**
     * Constructs a new GravityHandler with the specified root group and drill image view.
     *
     * @param root            the root group to apply gravity to
     * @param drillImageView  the image view representing the drill machine
     */
    public GravityManager(Group root, ImageView drillImageView) {
        this.root = root;
        this.drillImageView = drillImageView;
    }
    /**
     * Handles the gravity effect on the drill machine.
     *
     * @param now  the current time in nanoseconds
     */
    @Override
    public void handle(long now) {
        // Calculates elapsed time since last gravity check
        double currentTime = now / 1_000_000_000.0; // Convert nanoseconds to seconds
        double elapsedTime = currentTime - lastGravityCheckTime;

        // Checks if it's time to apply gravity
        if (elapsedTime >= gravityCheckInterval) {
            applyGravity(root, drillImageView);
            lastGravityCheckTime = currentTime;
        }
    }
    /**
     * Applies the gravity effect on the drill machine.
     *
     * @param root            the root group to apply gravity to
     * @param drillImageView  the image view representing the drill machine
     */
    private void applyGravity(Group root, ImageView drillImageView) {
        double newX = drillImageView.getX();
        double newY = drillImageView.getY() + gravityAmount;

        // Check if the new position is within the boundaries
        if ((newY >= 0 && newY <= 800 - drillImageView.getFitHeight()) && (newX >= 0 && newX <= 800 - drillImageView.getFitWidth())) {
            // Calculates the index of the under image
            int indexOfUnder = (int) (16 * (newY - 150) / 50 + newX / 50) + 2;
            // Applies gravity if machine is on the sky or if the bottom of the machine is empty
            if (newY < 150 || (indexOfUnder >= 2 && indexOfUnder < root.getChildren().size() && root.getChildren().get(indexOfUnder).getId().equals("Empty"))) {
                drillImageView.setY(newY);
            }
        }
    }
}
