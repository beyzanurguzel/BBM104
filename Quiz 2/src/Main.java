import java.util.ArrayList;

/**
 * Main class for managing items and decorations.
 */
public class Main {
    /**
     * Main method to execute the program.
     *
     * @param args Command line arguments. They should contain paths for items data file,
     *             decoration data file, and output file respectively.
     */

    public static void main(String[] args) {

        String pathOfItems =args[0];
        String pathOfDecorate = args[1];
        String pathOfOutput = args[2];

        String[] itemsDatas = FileInput.readTxt(pathOfItems);
        String[] decorateDatas = FileInput.readTxt(pathOfDecorate);
        ItemsManager itemsManager = new ItemsManager();
        // we returned an array list which contains the lists classObjects and decorateObjects
        // by calling makeItemObjects method of itemsmanager class.
        ArrayList<ClassBuilder> classObjects =  itemsManager.makeItemObjects(itemsDatas).get(0);
        ArrayList<Decoration> decorationObjects = itemsManager.makeItemObjects(itemsDatas).get(1);
        // we called decorateManager class for decorating the classes.
        DecorateManager decorateManager = new DecorateManager();
        decorateManager.decorate(decorateDatas, classObjects, decorationObjects, pathOfOutput);
    }
}