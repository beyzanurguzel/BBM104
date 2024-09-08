/**
 * RouteObject represents an object used in route calculation,
 * containing information about the total distance traveled,
 * the road taken, the endpoint of the road, and the previous point.
 */
public class RouteObject implements Comparable<RouteObject> {
    int totalDistance; // Total distance traveled to reach this point.
    Road road; // The road taken to reach this point
    String endPoint; // The endpoint of the road.
    String previousPoint; // The previous point before reaching this one

    public RouteObject(int totalDistance, Road road, String endPoint, String previousPoint) {
        this.totalDistance = totalDistance;
        this.road = road;
        this.endPoint = endPoint;
        this.previousPoint = previousPoint;
    }
    /**
     * Compares this RouteObject with another RouteObject based on total distance
     * and, if total distances are equal, based on the road ID.
     *
     * @param o The RouteObject to be compared.
     * @return A negative integer, zero, or a positive integer as this object is
     *         less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(RouteObject o) {
        if (this.totalDistance != o.totalDistance) {
            return Integer.compare(this.totalDistance, o.totalDistance);
        } else {
            return Integer.compare(this.road.roadID, o.road.roadID);
        }
    }
}