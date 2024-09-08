import java.util.HashMap;
/**
 * The {@code SearchManager} class is responsible for searching items in the inventory.
 *
 * @param <T> The type of items to be searched, which must extend {@link Item}.
 */
public class SearchManager<T extends Item> {
    /**
     * Searches for an item in the inventory based on its barcode.
     *
     * @param parts       An array containing the command parts.
     * @param barcodeMap  A {@link HashMap} containing items mapped by their barcodes.
     * @param outputPath  The path where the output will be written.
     */
    public  void searchByBarcode(String[] parts, HashMap<Integer, T> barcodeMap, String outputPath) {
        // Write header to the output file
        FileOutput.writeToFile(outputPath, "SEARCH RESULTS:", true, true);
        // Parse the barcode from the command parts
        int barcode = Integer.parseInt(parts[1]);

        if (barcodeMap.containsKey(barcode)) {
            stringMethod(barcodeMap.get(barcode), outputPath);
        }else{
            // Write failure message to the output file
            FileOutput.writeToFile(outputPath, "Item is not found.", true, true);
        }
        // Write footer to the output file
        FileOutput.writeToFile(outputPath,"------------------------------" , true, true);
    }
    /**
     * Searches for an item in the inventory based on its name.
     *
     * @param parts       An array containing the command parts.
     * @param nameMap     A {@link HashMap} containing items mapped by their names.
     * @param outputPath  The path where the output will be written.
     */
    public void searchByName(String[] parts, HashMap<String, T> nameMap, String outputPath) {
        // Write header to the output file
        FileOutput.writeToFile(outputPath, "SEARCH RESULTS:", true, true);
        // Extract the name from the command parts
        String name = parts[1];

        if (nameMap.containsKey(name)) {
            stringMethod(nameMap.get(parts[1]), outputPath);
        }else{
            // Write failure message to the output file
            FileOutput.writeToFile(outputPath, "Item is not found.", true, true);
        }
        // Write footer to the output file
        FileOutput.writeToFile(outputPath,"------------------------------" , true, true);
    }
    /**
     * Writes details of the specified item to the output file.
     *
     * @param item        The item to be described.
     * @param outputPath  The path where the output will be written.
     */
    public void stringMethod(T item, String outputPath) {
        String message = "";
        switch (item.getType()) {
            case "Book":
                message = "Author of the " + item.getName() + " is " + ((Book) item).getAuthor()
                        + ". Its barcode is " + item.getBarcode() + " and its price is " + item.getPrice();
                break;
            case "Toy":
                message = "Color of the " + item.getName() + " is " + ((Toy) item).getColor()
                        + ". Its barcode is " + item.getBarcode() + " and its price is " + item.getPrice();
                break;
            case "Stationery":
                message = "Kind of the " + item.getName() + " is " + ((Stationery) item).getKind()
                        + ". Its barcode is " + item.getBarcode() + " and its price is " + item.getPrice();
                break;
        }
        // Write the item details to the output file
        FileOutput.writeToFile(outputPath, message, true, true);
    }
}
