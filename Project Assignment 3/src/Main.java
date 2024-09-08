import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;
import java.util.*;

/**
 * The Main class is the entry point of the application. It extends the JavaFX Application class
 * and provides the start method to initialize and start the JavaFX application.
 */
public class Main extends Application {
    //Creates arrays and objects to manage them
    HashMap<String, File> imageMap = new HashMap<>();
    ArrayList<File> drillImages = new ArrayList<>();
    ArrayList<File> valuableImages = new ArrayList<>();
    Map<String, Valuable> undergroundObjects = new HashMap<>();
    ImageManager imageManager = new ImageManager();
    SceneManager sceneManager = new SceneManager();
    DrillManager drillManager = new DrillManager();
    /**
     * The start method is called when the JavaFX application is started. It initializes the primary stage,
     * creates the scene and root group, manages images, creates the default screen, creates the drill machine,
     * and starts the game.
     *
     * @param primaryStage the primary stage of the application
     */
    @Override
    public void start(Stage primaryStage) {

        // Calls imageManager to create and extract images
        imageManager.manageImages(drillImages, imageMap, valuableImages, undergroundObjects);

        // Create root group and scene
        Group root = new Group();
        Scene scene = new Scene(root, 800, 800 );//50 x 50 olacak resimler
        primaryStage.setResizable(false);

        //Calls sceneManager to create the screen
        sceneManager.createDefaultScreen(root, undergroundObjects,imageMap);

        //After creating screen, creates the drill machine and starts the game
        drillManager.setDrillMachine(drillImages, root, imageMap, undergroundObjects);

        // Set the scene and show the primary stage
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The main method is the entry point of the Java application. It launches the JavaFX application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
