import java.util.*;
public class FindTheFastestRoute {
    public  void findTheFastestRoute(boolean isCallFromBarelyConnectedMap, ArrayList<Road> roads,  String start, String destination, Map<String, RouteObject> shortestPathMap, String outputPath, Integer[] totalKilometers) {
        List<RouteObject> routeObjects = new ArrayList<>();
        List<RouteObject> instantRoutes = new ArrayList<>(); // this list is for sorting newly added roads
        // Find roads accessible from the start point and add to instantRoutes list
        for (Road road : roads) {
            if (road.point1.equals(start) || road.point2.equals(start)) {
                String endPoint = road.point1.equals(start) ? road.point2 : road.point1;
                instantRoutes.add(new RouteObject(road.distance, road, endPoint, start));
            }
        }
        // Sort the new roads by distance and add them to routeObjects list
        Collections.sort(instantRoutes);
        routeObjects.addAll(instantRoutes);
        instantRoutes.clear();
        shortestPathMap.put(routeObjects.get(0).previousPoint, routeObjects.get(0));

        // Loop until there are no more route objects to process
        while (!routeObjects.isEmpty()) {
            RouteObject current = routeObjects.remove(0);
            // If the endpoint is already in the shortestPathMap, check if the new distance is shorter
            if (shortestPathMap.containsKey(current.endPoint)) {
                if (shortestPathMap.get(current.endPoint).totalDistance < current.totalDistance) {
                    continue;
                } else if (shortestPathMap.get(current.endPoint).totalDistance == current.totalDistance) {
                    continue;
                } else {
                    shortestPathMap.remove(current.endPoint);
                    shortestPathMap.put(current.endPoint, current);
                }
            } else {
                shortestPathMap.put(current.endPoint, current);
            }
            // Find roads accessible from the current endpoint and add to instantRoutes list
            for (Road road : roads) {
                if (road.point1.equals(current.endPoint) || road.point2.equals(current.endPoint)) {
                    String endPoint = road.point1.equals(current.endPoint) ? road.point2 : road.point1;
                    if (!shortestPathMap.containsKey(endPoint)) {
                        instantRoutes.add(new RouteObject(current.totalDistance + road.distance, road, endPoint, current.endPoint));
                    }
                }
            }
            // Sort the new roads by distance and add them to routeObjects list
            Collections.sort(instantRoutes);
            routeObjects.addAll(instantRoutes);
            instantRoutes.clear();
        }
        // If the destination is in the shortestPathMap, construct the route and write to file
        if (shortestPathMap.containsKey(destination)) {
            RouteObject destinationRoute = shortestPathMap.get(destination);
            int totalDistance = destinationRoute.totalDistance;
            if (isCallFromBarelyConnectedMap) {
                totalKilometers[1] = totalDistance;
            } else {
                totalKilometers[0] = totalDistance;
            }
            if (isCallFromBarelyConnectedMap) {
                FileOutput.writeToFile(outputPath, "Fastest Route from " + start + " to " + destination + " on Barely Connected Map (" + totalDistance + " KM):", true, true);
            } else {
                FileOutput.writeToFile(outputPath, "Fastest Route from " + start + " to " + destination + " (" + totalDistance + " KM):", true, true);
            }
            List<String> path = new ArrayList<>();
            String currentPoint = destination;
            while (!currentPoint.equals(start)) {
                path.add(shortestPathMap.get(currentPoint).road.line);
                RouteObject route = shortestPathMap.get(currentPoint);
                currentPoint = route.previousPoint;
            }
            while (!path.isEmpty()) {
                FileOutput.writeToFile(outputPath, path.get(path.size() - 1), true, true);
                path.remove(path.size() - 1);
            }
        } else {
            FileOutput.writeToFile(outputPath, "No route found from " + start + " to " + destination, true, true);
        }
    }
}
