import java.util.*;
/**
 * The Main class contains the main method to run the application for finding the fastest route
 * on both regular and barely connected maps.
 */
public class MapAnalyzer {
    /**
     * The main method reads input data, creates Road objects, finds the fastest route on the regular map,
     * creates a barely connected map, and finds the fastest route on it.
     *
     * @param args Command-line arguments containing input and output file paths.
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        // Get input and output file paths from command line arguments
        String inputPath = args[0];
        String outputPath = args[1];

        String[] inputDatas = FileInput.readTxt(inputPath);
        String start = inputDatas[0].split("\t")[0]; // Extract start point from the first line of input
        String destination = inputDatas[0].split("\t")[1]; // Extract destination from the first line of input

        // Parse road data and create Road objects
        ArrayList<Road> roads = new ArrayList<>();
        for (int i = 1; i < inputDatas.length; i++) {
            String[] parts = inputDatas[i].split("\t");
            Road road = new Road(Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), parts[0], parts[1], inputDatas[i]);
            roads.add(road);
        }
        // Initialize an array to store total kilometers for both regular and barely connected maps
        Integer[] totalKilometers = new Integer[2];

        // Find the fastest route on the regular map
        FindTheFastestRoute findTheFastestRoute = new FindTheFastestRoute();
        Map<String, RouteObject> shortestPathMap = new HashMap<>();
        findTheFastestRoute.findTheFastestRoute(false, roads, start, destination, shortestPathMap, outputPath, totalKilometers);

        // Create the barely connected map and find the fastest route on it
        CreateBarelyConnectedMap createBarelyConnectedMap = new CreateBarelyConnectedMap();
        createBarelyConnectedMap.createBarelyConnectedMap(roads, start, destination, outputPath, totalKilometers);

    }
}