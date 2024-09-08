import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import javafx.scene.text.Text;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * The DrillManager class manages the drill machine and its operations.
 */
public class DrillManager {
    int haul = 0;
    int money = 0;
    Double fuel = 10000.000;
    Text fuelText = new Text("fuel:" + String.format("%.3f", fuel));
    Text haulText = new Text("haul:" + haul);
    Text moneyText = new Text("money:" + money);
    /**
     * Sets up the drill machine and starts the game.
     *
     * @param drillImages       the list of drill machine images
     * @param root              the root group to add elements to
     * @param imageMap          a map containing image file references
     * @param undergroundObjects a map containing information about underground objects
     */
    public void setDrillMachine(ArrayList<File> drillImages, Group root, HashMap<String, File> imageMap, Map<String, Valuable> undergroundObjects) {
        // Timeline for fuel decrease
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> fuelDecreases(root, false, new Timeline())));
        timeline.setCycleCount(Animation.INDEFINITE);
        if (fuel == 0) {
            timeline.stop();
        }
        ArrayList<Text> texts = new ArrayList<>();
        texts.add(fuelText);
        texts.add(haulText);
        texts.add(moneyText);

        int initialY = 40;
        for (Text text : texts) {
            text.setFont(Font.font("Arial", FontWeight.BOLD, 25));
            text.setX(5);
            text.setY(initialY);
            text.setFill(Color.BISQUE);
            initialY += 50;
        }

        root.getChildren().addAll(fuelText, haulText, moneyText);
        timeline.play();
        // I created copy list of root.getChildren, because I remove the image when machine drills the image. It can cause index errors
        // because I used index to understand the image. By creating this copy list I got rid of getting errors with creating this list
        List<Node> copyList = new ArrayList<>(root.getChildren());
        // Places the drill machine
        ImageView imageView = new ImageView(drillImages.get(0).toURI().toString());
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        imageView.setX(400);
        imageView.setY(0);

        root.getChildren().add(imageView);
        final double moveAmount = 50; // Move amount for each key press
        // After placing the drill machine, creates gravity
        GravityManager gravityManager = new GravityManager(root, imageView);
        gravityManager.start();

        // Sets key event listener to handle drill movement
        EventHandler<KeyEvent> keyPressHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                fuelDecreases(root, true, timeline);
                KeyCode keyCode = event.getCode();
                switch (keyCode) {
                    case LEFT:
                        moveDrill(-moveAmount, 0, imageView, root, copyList, imageMap, undergroundObjects, timeline); // Move left
                        imageView.setImage(new Image(drillImages.get(0).toURI().toString()));
                        break;
                    case RIGHT:
                        moveDrill(moveAmount, 0, imageView, root, copyList, imageMap, undergroundObjects, timeline); // Move right
                        imageView.setImage(new Image(drillImages.get(3).toURI().toString()));
                        break;
                    case UP:
                        moveDrill(0, -moveAmount, imageView, root, copyList, imageMap, undergroundObjects, timeline); // Move up
                        imageView.setImage(new Image(drillImages.get(1).toURI().toString()));
                        break;
                    case DOWN:
                        moveDrill(0, moveAmount, imageView, root, copyList, imageMap, undergroundObjects, timeline); // Move down
                        imageView.setImage(new Image(drillImages.get(2).toURI().toString()));
                        break;
                    default:
                        break;
                }
            }
        };
        root.setOnKeyPressed(keyPressHandler);
        root.requestFocus();
    }
    /**
     * Moves the drill machine according to the given directions.
     *
     * @param dx                the change in x-coordinate
     * @param dy                the change in y-coordinate
     * @param drillImageView    the image view of the drill machine
     * @param root              the root group to add elements to
     * @param copyList          the copy list of root children
     * @param imageMap          a map containing image file references
     * @param undergroundObjects a map containing information about underground objects
     * @param timeline          the timeline for fuel decrease
     */
    private void moveDrill(double dx, double dy, ImageView drillImageView, Group root, List<Node> copyList, HashMap<String, File> imageMap, Map<String, Valuable> undergroundObjects, Timeline timeline) {
        double newX = drillImageView.getX() + dx;
        double newY = drillImageView.getY() + dy;
        // Check this to avoid exceeding screen limits
        if ((newY >= 0 && newY <= 800 - drillImageView.getFitHeight()) && (newX >= 0 && newX <= 800 - drillImageView.getFitWidth())) {
            // If newY is smaller than 150, it means drill is on the sky, so only changes the drill's place
            if (newY < 150) {
                drillImageView.setX(newX);
                drillImageView.setY(newY);
            } else {
                // I use root.getChildren list, so I created this formula for understanding the index of the drill according to its coordinates
                int index = (int) (16 * (newY - 150) / 50 + newX / 50) + 2; // Does this because copyList first two elements are the rectangles that we use for covering the screen
                // Ends the game, if machine drills lava
                if (copyList.get(index).getId().equals("Lava") && dy != -50) {
                    gameOver(true, root, timeline);
                } else if (copyList.get(index).getId().equals("Obstacle")) {
                    // Don't move
                } else {
                    if (root.getChildren().get(index).getId().equals("Empty")) {
                        drillImageView.setX(newX);
                        drillImageView.setY(newY);
                    }//Checks if dy is -50 or not, because machine cannot drill upward
                    else if (dy != -50) {
                        // Understands the image with copyList and removes image from the root.getChildren list
                        haul += undergroundObjects.get(copyList.get(index).getId()).getWeight();
                        haulText.setText("haul:" + haul);
                        money += undergroundObjects.get(copyList.get(index).getId()).getWorth();
                        moneyText.setText("money:" + money);
                        //Creates empty image for the place which is drilled
                        ImageView newImageView = new ImageView(imageMap.get("Empty").toURI().toString());
                        newImageView.setX(newX);
                        newImageView.setY(newY);
                        root.getChildren().set(index, newImageView);
                        root.getChildren().get(index).setId("Empty");
                        drillImageView.setX(newX);
                        drillImageView.setY(newY);
                    }
                }
            }
        }
    }
    /**
     * Ends the game and displays the game over message.
     *
     * @param isLava   a flag indicating if the game ended due to lava
     * @param root     the root group to add elements to
     * @param timeline the timeline for fuel decrease
     */
    public void gameOver(boolean isLava, Group root, Timeline timeline) {
        Text gameOverText = new Text("GAME OVER");
        gameOverText.setFont(Font.font("Arial", FontWeight.BOLD, 80));
        if (isLava) {
            Rectangle gameOver = new Rectangle(0, 0, 800, 800);
            gameOver.setFill(Color.RED);
            timeline.stop();
            root.getChildren().add(gameOver);
            gameOverText.setX(150);
            gameOverText.setY(400);
            root.getChildren().add(gameOverText);
        } else {
            Text collectedMoneyText = new Text("Collected Money: " + money);
            collectedMoneyText.setFont(Font.font("Arial", FontWeight.BOLD, 50));
            collectedMoneyText.setX(100);
            collectedMoneyText.setY(400);
            Rectangle gameOver = new Rectangle(0, 0, 800, 800);
            gameOver.setFill(Color.GREEN);
            gameOverText.setX(150);
            gameOverText.setY(300);
            root.getChildren().add(gameOver);
            root.getChildren().add(gameOverText);
            root.getChildren().add(collectedMoneyText);
        }
        root.setDisable(true);
    }
    /**
     * Decreases the fuel based on time.
     *
     * @param root     the root group to add elements to
     * @param wasKeyPressed a flag indicating if a key was pressed
     * @param timeline the timeline for fuel decrease
     */
    private void fuelDecreases(Group root, boolean wasKeyPressed, Timeline timeline) {
        // Decreases the fuel by time. If key was pressed, fuel decreases more
        if (fuel > 0) {
            if (wasKeyPressed) {
                fuel -= 100;
                fuelText.setText("fuel: " + String.format("%.3f", fuel));
            } else {
                fuel -= 0.001;
                fuelText.setText("fuel: " + String.format("%.3f", fuel));
            }
        }
        // Ends the game , if fuel is finished
        if (fuel <= 0) {
            timeline.stop();
            gameOver(false, root, timeline);
        }
    }
}