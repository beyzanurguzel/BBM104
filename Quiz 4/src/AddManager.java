import java.util.ArrayList;
import java.util.HashMap;
/**
 * The AddManager class handles the addition of new items to the inventory.
 * It supports adding items of type Book, Toy, and Stationery.
 *
 * @param <T> The type of items managed by this AddManager, extending Item.
 */
public class AddManager<T extends Item> {
    /**
     * Adds a new item to the inventory based on the provided input parts.
     * Updates the barcode and name maps, as well as the specific type name lists.
     *
     * @param parts The input data split into parts, representing item attributes.
     * @param barcodeMap A map of item barcodes to items.
     * @param nameMap A map of item names to items.
     * @param bookNames A list of book names.
     * @param toyNames A list of toy names.
     * @param stationaryNames A list of stationery names.
     */
    public void addItem(String[] parts, HashMap<Integer, T> barcodeMap, HashMap<String, T> nameMap,
                        ArrayList<String> bookNames, ArrayList<String> toyNames, ArrayList<String> stationaryNames) {
        T item = null;
        switch (parts[1]) {
            case "Book":
                item = (T) new Book();
                ((Book) item).setAuthor(parts[3]);
                bookNames.add(parts[2]);
                break;
            case "Toy":
                item = (T) new Toy();
                ((Toy) item).setColor(parts[3]);
                toyNames.add(parts[2]);
                break;
            case "Stationery":
                item = (T) new Stationery();
                ((Stationery) item).setKind(parts[3]);
                stationaryNames.add(parts[2]);
                break;
        }
        if (item != null) {
            item.setType(parts[1]);
            item.setName(parts[2]);
            item.setBarcode(Integer.parseInt(parts[4]));
            item.setPrice(Double.parseDouble(parts[5]));
            barcodeMap.put(item.getBarcode(), item);
            nameMap.put(item.getName(), item);
        }
    }
}
