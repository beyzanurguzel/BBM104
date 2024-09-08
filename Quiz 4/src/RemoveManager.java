import java.util.ArrayList;
import java.util.HashMap;
/**
 * The {@code RemoveManager} class is responsible for removing items from the inventory.
 *
 * @param <T> The type of items to be removed, which must extend {@link Item}.
 */
public class RemoveManager<T extends  Item> {
    /**
     * Removes an item from the inventory based on its barcode.
     *
     * @param parts          An array containing the command parts.
     * @param barcodeMap     A {@link HashMap} containing items mapped by their barcodes.
     * @param nameMap        A {@link HashMap} containing items mapped by their names.
     * @param bookNames      An {@link ArrayList} containing names of books in the inventory.
     * @param toyNames       An {@link ArrayList} containing names of toys in the inventory.
     * @param stationaryNames An {@link ArrayList} containing names of stationary items in the inventory.
     * @param outputPath     The path where the output will be written.
     */
    public  void removeItem(String[] parts, HashMap<Integer, T> barcodeMap, HashMap<String, T> nameMap, ArrayList<String> bookNames,
                            ArrayList<String> toyNames, ArrayList<String> stationaryNames, String outputPath) {
        // Write header to the output file
        FileOutput.writeToFile(outputPath, "REMOVE RESULTS:", true, true);
        // Parse the barcode from the command parts
        int barcode = Integer.parseInt(parts[1]);

        if (barcodeMap.containsKey(barcode)) {
            T item = barcodeMap.get(barcode);
            // Remove the item from the appropriate lists
            switch (item.getType()) {
                case "Book":
                    bookNames.remove(item.getName());
                    break;
                case "Toy":
                    toyNames.remove(item.getName());
                    break;
                case "Stationery":
                    stationaryNames.remove(item.getName());
                    break;
            }
            // Remove the item from the maps
            nameMap.remove(item.getName());
            barcodeMap.remove(barcode);
            // Write success message to the output file
            FileOutput.writeToFile(outputPath, "Item is removed.", true, true);
        }else{
            // Write failure message to the output file
            FileOutput.writeToFile(outputPath, "Item is not found.", true, true);
        }
        // Write footer to the output file
        FileOutput.writeToFile(outputPath, "------------------------------", true, true);
    }
}
