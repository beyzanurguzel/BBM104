import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 * The ImageManager class manages the loading and extraction of images.
 */
public class ImageManager {
    /**
     * Manages the loading and extraction of images.
     *
     * @param drillImages       the list to store drill images
     * @param imageMap          the map to store image names and corresponding files
     * @param valuableImages    the list to store valuable images
     * @param undergroundObjects the map to store underground objects
     */
    public void manageImages(ArrayList<File> drillImages, HashMap<String, File> imageMap, ArrayList<File> valuableImages, Map<String, Valuable> undergroundObjects) {
        // Calls extract methods for extracting the images which are needed
        extractUnderGroundImages(imageMap, valuableImages);
        extractDrillImages(drillImages);
        // Calls this method for creating valuable underground objects
        createUndergroundObjects(undergroundObjects, valuableImages);
    }
    /**
     * Extracts underground images, valuable images, and populates the image map.
     *
     * @param imageMap          the map to store image names and corresponding files
     * @param valuableImages    the list to store valuable images
     */
    public void extractUnderGroundImages(HashMap<String, File> imageMap, ArrayList<File> valuableImages) {
        File emptyImage = new File("assets\\underground\\empty_15.png");
        File lavaImage = new File("assets\\underground\\lava_01.png");
        File obstacleImage = new File("assets\\underground\\obstacle_01.png");
        File soiliImage = new File("assets\\underground\\soil_01.png");
        File topImage = new File("assets\\underground\\top_01.png");
        File amazonite = new File("assets\\underground\\valuable_amazonite.png");
        File bronzium = new File("assets\\underground\\valuable_bronzium.png");
        File diamond = new File("assets\\underground\\valuable_diamond.png");
        File einsteinium = new File("assets\\underground\\valuable_einsteinium.png");
        File emerald = new File("assets\\underground\\valuable_emerald.png");
        File goldium = new File("assets\\underground\\valuable_goldium.png");
        File ironium = new File("assets\\underground\\valuable_ironium.png");
        File platinum = new File("assets\\underground\\valuable_platinum.png");
        File ruby = new File("assets\\underground\\valuable_ruby.png");
        File silverium = new File("assets\\underground\\valuable_silverium.png");

        imageMap.put("Empty", emptyImage);
        imageMap.put("Lava", lavaImage);
        imageMap.put("Obstacle", obstacleImage);
        imageMap.put("Soil", soiliImage);
        imageMap.put("Top", topImage);

        valuableImages.add(amazonite);
        valuableImages.add(bronzium);
        valuableImages.add(diamond);
        valuableImages.add(einsteinium);
        valuableImages.add(emerald);
        valuableImages.add(goldium);
        valuableImages.add(ironium);
        valuableImages.add(platinum);
        valuableImages.add(ruby);
        valuableImages.add(silverium);
    }
    /**
     * Extracts drill images from the given list and keeps only the necessary ones.
     *
     * @param drillImages the list of drill images
     */
    public void extractDrillImages(ArrayList<File> drillImages) {
        File leftImage = new File("assets\\drill\\drill_01.png");
        File upImage = new File("assets\\drill\\drill_25.png");
        File downImage = new File("assets\\drill\\drill_36.png");
        File rightImage = new File("assets\\drill\\drill_55.png");
        drillImages.add(leftImage); // sees left
        drillImages.add(upImage); // sees up
        drillImages.add(downImage); // sees down
        drillImages.add(rightImage); // sees right
    }
    /**
     * Creates valuable underground objects and populates the underground objects map.
     *
     * @param undergroundObjects the map to store underground objects
     * @param valuableImages     the list of valuable images
     */
    public void createUndergroundObjects(Map<String, Valuable> undergroundObjects, ArrayList<File> valuableImages) {

        Integer[] worths = {500000, 60, 100000, 2000, 5000, 250, 30, 750, 20000, 100};
        Integer[] weights = {120, 10, 100, 40, 60, 20, 10, 30, 80, 10};
        String[] names = {"Amazonite", "Bronzium", "Diamond", "Einsteinium", "Emerald", "Goldium", "Ironium", "Platinum", "Ruby", "Silverium"};
        for (int i = 0; i < names.length; i++) {
            Valuable valuable = new Valuable();
            valuable.setName(names[i]);
            valuable.setWorth(worths[i]);
            valuable.setWeight(weights[i]);
            valuable.setImage(valuableImages.get(i));
            undergroundObjects.put(valuable.getName(), valuable);
        }
    }
}
