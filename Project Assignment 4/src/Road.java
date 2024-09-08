/**
 * Road represents a road connecting two points, characterized by its distance,
 * road ID, points it connects, and its line description.
 */
public class Road implements Comparable<Road> {
    int distance; // The distance of the road
    int roadID; // The unique identifier for the road
    String point1; // The first point connected by the road
    String point2; // The second point connected by the road
    String line; // Description of the road line

    public Road(int distance, int roadID, String point1, String point2, String line) {
        this.distance = distance;
        this.roadID = roadID;
        this.point1 = point1;
        this.point2 = point2;
        this.line = line;
    }
    /**
     * Compares this Road with another Road based on distance and road ID.
     *
     * @param o The Road to be compared.
     * @return A negative integer, zero, or a positive integer as this object is
     *         less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Road o) {
        if (this.distance != o.distance) {
            return Integer.compare(this.distance, o.distance);
        } else {
            return Integer.compare(this.roadID, o.roadID);
        }
    }
}