import javafx.scene.Group;
import javafx.scene.image.ImageView;
import java.io.File;
import java.util.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
/**
 * The SceneManager class manages the creation of the default game screen.
 */
public class SceneManager {
    /**
     * Creates the default game screen with sky, earth, underground objects, and valuables.
     *
     * @param root              the root group to add elements to
     * @param undergroundObjects a map containing information about underground objects
     * @param imageMap          a map containing image file references
     */
    public void createDefaultScreen(Group root, Map<String, Valuable> undergroundObjects, HashMap<String, File> imageMap) {
        Rectangle sky = new Rectangle(0, 0, 800, 152);
        sky.setFill(Color.MIDNIGHTBLUE);

        Rectangle earth = new Rectangle(0, 152, 800, 650);
        earth.setFill(Color.CORAL);

        root.getChildren().addAll(sky, earth);
        //This array is used for collecting the images that will be used for the screen
        ArrayList<File> printImages = new ArrayList<>();

        Random random = new Random();
        // This is used for calculating the remaining place for soil
        int total;
        List<String> keyList = new ArrayList<>(undergroundObjects.keySet());
        int lavaCount = random.nextInt(5) + 5; // Make it 5 to 9 lava
        for (int i = 0; i < lavaCount; i++) {
            printImages.add(imageMap.get("Lava"));
        }
        int obstacleCount = random.nextInt(7); //Make it 0 to 6 obstacle
        total = lavaCount + obstacleCount;
        for (int i = 0; i < obstacleCount; i++) {
            printImages.add(imageMap.get("Obstacle"));
        }
        // Creates this chosenIndexes array To avoid selecting the same item
        ArrayList<Integer> chosenIndexes = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            boolean sameIndex = false;
            while (!sameIndex) {
                int index = random.nextInt(undergroundObjects.size());
                if(chosenIndexes.contains(index)) {
                    sameIndex = true;
                }else {
                    chosenIndexes.add(index);
                    int number = random.nextInt(5) + 1; //Make it 1 to 5 the chosen item
                    for (int l = 0; l < number; l++) {
                        printImages.add(undergroundObjects.get(keyList.get(index)).getImage());
                    }
                    total = total + number;
                }
            }
        }
        //After arranging the valuables, adds lava, obstacle and soil to the printImages list.
        Valuable lava = new Valuable();
        lava.setName("Lava");
        lava.setImage(imageMap.get("Lava"));

        undergroundObjects.put(lava.getName(), lava);

        Valuable obstacle = new Valuable();
        obstacle.setName("Obstacle");
        obstacle.setImage(imageMap.get("Obstacle"));
        undergroundObjects.put(obstacle.getName(), obstacle);

        Valuable soil = new Valuable();
        soil.setName("Soil");
        soil.setImage(imageMap.get("Soil"));
        for (int i = 0; i < 154-total; i++) {
            printImages.add(soil.getImage());
        }

        soil.setImage(imageMap.get("Soil"));
        undergroundObjects.put(soil.getName(), soil);

        // Places the top images
        for(int i = 0; i < 16; i++) {
            ImageView imageView = new ImageView(imageMap.get("Top").toURI().toString());
            imageView.setId("Soil");
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
            imageView.setX(i * 50);
            imageView.setY(150);
            root.getChildren().add(imageView);
        }
        // Places rest of the underground
        for(int i = 4; i < 16; i++) {
            for(int j = 0; j < 16; j++) {
                // Places the obstacles tı the bounders
                if(i == 15) {
                    ImageView imageView = new ImageView(imageMap.get("Obstacle").toURI().toString());
                    imageView.setId("Obstacle");
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    imageView.setX(j * 50);
                    imageView.setY(750);
                    root.getChildren().add(imageView);
                }else if (j == 0 || j == 15) {
                    ImageView imageView = new ImageView(imageMap.get("Obstacle").toURI().toString());
                    imageView.setId("Obstacle");
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    imageView.setX(j * 50);
                    imageView.setY(i * 50);
                    root.getChildren().add(imageView);
                }// Places the random pictures to the underground
                else{
                    // This returns random number and places this random picture and removes this random picture from the list
                    int index = random.nextInt(printImages.size());
                    ImageView imageView = new ImageView(printImages.get(index).toURI().toString());
                    // Calls this method to understand object's name
                    imageView.setId(findID(printImages.get(index).getName()));
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    imageView.setX(j * 50);
                    imageView.setY(i * 50);
                    root.getChildren().add(imageView);
                    printImages.remove(index);
                }
            }
        }
    }
    /**
     * Finds and returns the ID corresponding to the given image name.
     *
     * @param imageName the name of the image file
     * @return the ID corresponding to the image name
     */
    public String findID(String imageName) {
        switch (imageName) {
            case "soil_01.png":
                return "Soil";
            case "lava_01.png":
                return "Lava";
            case "obstacle_01.png":
                return "Obstacle";
            case "valuable_diamond.png":
                return "Diamond";
            case "valuable_bronzium.png":
                return "Bronzium";
            case "valuable_goldium.png":
                return "Goldium";
            case "valuable_platinum.png":
                return "Platinum";
            case "valuable_emerald.png":
                return "Emerald";
            case "valuable_ironium.png":
                return "Ironium";
            case "valuable_silverium.png":
                return "Silverium";
            case "valuable_einsteinium.png":
                return "Einsteinium";
            case "valuable_amazonite.png":
                return "Amazonite";
            case "valuable_ruby.png":
                return "Ruby";
            default:
                return "";
        }
    }
}
