import java.util.ArrayList;
import java.util.HashMap;
/**
 * The {@code DisplayManager} class is responsible for displaying items from the inventory.
 * It displays items of type {@code T}, which must extend {@link Item}.
 *
 * @param <T> The type of items to be displayed, which must extend {@link Item}.
 */
public class DisplayManager<T extends Item> {
    /**
     * Displays the items stored in the inventory based on their names.
     *
     * @param searchManager   The {@link SearchManager} instance used to search for items.
     * @param nameMap         A {@link HashMap} containing items mapped by their names.
     * @param bookNames       An {@link ArrayList} containing names of books to be displayed.
     * @param toyNames        An {@link ArrayList} containing names of toys to be displayed.
     * @param stationaryNames An {@link ArrayList} containing names of stationary items to be displayed.
     * @param outputPath      The path where the output will be written.
     */
    public void displayItems(SearchManager<T> searchManager, HashMap<String, T> nameMap, ArrayList<String> bookNames,
                              ArrayList<String> toyNames, ArrayList<String> stationaryNames, String outputPath) {
        // Writes header to the output file
        FileOutput.writeToFile(outputPath, "INVENTORY:", true, true);
        // Display books
        for (String name: bookNames) {
            searchManager.stringMethod(nameMap.get(name), outputPath);
        }
        // Display toys
        for (String name: toyNames) {
            searchManager.stringMethod(nameMap.get(name), outputPath);
        }
        // Display stationary items
        for (String name: stationaryNames) {
            searchManager.stringMethod(nameMap.get(name), outputPath);
        }
        // Write footer to the output file
        FileOutput.writeToFile(outputPath, "------------------------------", true, true);
    }
}
