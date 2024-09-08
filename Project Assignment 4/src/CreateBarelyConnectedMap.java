import java.math.RoundingMode;
import java.text.Collator;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Locale;
/**
 * The CreateBarelyConnectedMap class creates a barely connected map based on a list of roads
 * and performs analysis on the map, including finding the fastest route and calculating
 * material usage and route ratios compared to the original map.
 */
public class CreateBarelyConnectedMap {
    /**
     * Creates a barely connected map based on the provided roads and performs analysis on it.
     *
     * @param roads           The list of roads from which to create the barely connected map.
     * @param start           The starting point for finding the fastest route on the barely connected map.
     * @param destination     The destination point for finding the fastest route on the barely connected map.
     * @param outputPath      The file path where the output will be written.
     * @param totalKilometers An array containing the total kilometers for both regular and barely connected maps.
     */
    public void createBarelyConnectedMap(ArrayList<Road> roads, String start, String destination, String outputPath, Integer[] totalKilometers) {
        List<String> points = new ArrayList<>(); // List to store unique points
        HashMap<String, Road> barelyConnectedMap = new HashMap<>(); // Map to store roads in the barely connected map
        for (Road road : roads) {
            if (!points.contains(road.point1)) {
                points.add(road.point1);
            }
            if (!points.contains(road.point2)) {
                points.add(road.point2);
            }
        }
        // Sort the points alphabetically
        Collator collator = Collator.getInstance(new Locale("tr", "TR"));
        points.sort(collator::compare);

        List<String> visitedPlaces = new ArrayList<>();
        List<Road> instantRoads = new ArrayList<>();
        String startPoint = points.get(0);
        visitedPlaces.add(startPoint);

        // Find roads connected to the start point
        for (Road road : roads) {
            if (road.point1.equals(startPoint) || road.point2.equals(startPoint)) {
                instantRoads.add(road);
            }
        }
        Collections.sort(instantRoads);
        // Construct the barely connected map
        while (barelyConnectedMap.size() < points.size() - 1) {
            if (instantRoads.isEmpty()) {
                break;
            }
            Road smallestRoad = instantRoads.remove(0);
            String newPoint = null;
            if (visitedPlaces.contains(smallestRoad.point1) && !visitedPlaces.contains(smallestRoad.point2)) {
                newPoint = smallestRoad.point2;
            } else if (!visitedPlaces.contains(smallestRoad.point1) && visitedPlaces.contains(smallestRoad.point2)) {
                newPoint = smallestRoad.point1;
            }

            if (newPoint != null) {
                barelyConnectedMap.put(newPoint, smallestRoad);
                visitedPlaces.add(newPoint);
                // Find roads connected to the new point
                for (Road road : roads) {
                    if (road.point1.equals(newPoint) || road.point2.equals(newPoint)) {
                        if (!visitedPlaces.contains(road.point1) || !visitedPlaces.contains(road.point2)) {
                            instantRoads.add(road);
                        }
                    }
                }
                Collections.sort(instantRoads);
            }
        }
        // Sort roads in the barely connected map and write them to the output file
        ArrayList<Road> valuesList = new ArrayList<>(barelyConnectedMap.values());
        Collections.sort(valuesList);
        FileOutput.writeToFile(outputPath, "Roads of Barely Connected Map is:", true, true);
        for (Road road: valuesList) {
            FileOutput.writeToFile(outputPath, road.line, true, true);
        }
        // Find the fastest route on the barely connected map and calculate material usage ratio and route ratio
        FindTheFastestRoute findTheFastestRoute = new FindTheFastestRoute();
        Map<String, RouteObject> shortestPathMap = new HashMap<>();
        findTheFastestRoute.findTheFastestRoute(true, valuesList, start, destination, shortestPathMap, outputPath, totalKilometers);

        int allRoadMaterial = 0;
        int barelyMapMaterial = 0;
        for (Road road: roads) {
            allRoadMaterial += road.distance;
        }
        for (Road road: valuesList) {
            barelyMapMaterial += road.distance;
        }
        Double ratio = (double) barelyMapMaterial/allRoadMaterial;
        Double ratio2 = (double) totalKilometers[1] / totalKilometers[0];
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        // Write analysis results to the output file
        FileOutput.writeToFile(outputPath, "Analysis:", true, true);
        FileOutput.writeToFile(outputPath, "Ratio of Construction Material Usage Between Barely Connected and Original Map: " + df.format(ratio), true, true);
        FileOutput.writeToFile(outputPath, "Ratio of Fastest Route Between Barely Connected and Original Map: " + df.format(ratio2), true, false);
    }
}
