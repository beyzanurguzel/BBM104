import java.util.ArrayList;
import java.util.HashMap;
/**
 * The {@code CommandManager} class manages commands for manipulating items in an inventory system.
 * It delegates commands to specific managers for adding, removing, searching, and displaying items.
 */
public class CommandManager {
    AddManager addManager = new AddManager();
    RemoveManager removeManager = new RemoveManager();
    SearchManager searchManager = new SearchManager();
    DisplayManager displayManager = new DisplayManager();
    HashMap<Integer, Item> barcodeMap = new HashMap<>();
    HashMap<String, Item> nameMap = new HashMap<>();
    ArrayList<String> bookNames = new ArrayList<>();
    ArrayList<String> toyNames = new ArrayList<>();
    ArrayList<String> stationaryNames = new ArrayList<>();
    /**
     * Manages the given array of input data, executing commands based on the data.
     *
     * @param inputDatas  An array of input data containing commands to be executed.
     * @param outputPath  The path where the output will be written.
     */
    public void manageCommands(String[] inputDatas, String outputPath) {
        for (String command : inputDatas) {
            String[] parts = command.trim().split("\t");
            switch (parts[0]) {
                case "ADD":
                    addManager.addItem(parts, barcodeMap, nameMap, bookNames, toyNames, stationaryNames);
                    break;
                case "REMOVE":
                    removeManager.removeItem(parts, barcodeMap, nameMap, bookNames, toyNames, stationaryNames, outputPath);
                    break;
                case "SEARCHBYBARCODE":
                    searchManager.searchByBarcode(parts, barcodeMap, outputPath);
                    break;
                case "SEARCHBYNAME":
                    searchManager.searchByName(parts, nameMap, outputPath);
                    break;
                case "DISPLAY":
                    displayManager.displayItems(searchManager, nameMap, bookNames, toyNames, stationaryNames, outputPath);
                    break;
            }
        }
    }
}