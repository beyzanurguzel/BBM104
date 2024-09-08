import java.util.ArrayList;
/**
 * Represents a bus entity with its properties and functionalities.
 */
public class Bus {

    private String type;
    private int ID;
    private String firstPlace;
    private String destination;
    private int numberOfRows;
    private double price;
    private double refundCut;
    private double premiumFee;
    private ArrayList<Integer> emptySeats = new ArrayList<>();
    private ArrayList<Integer> fullSeats = new ArrayList<>();
    private double revenue;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstPlace() {
        return firstPlace;
    }

    public void setFirstPlace(String firstPlace) {
        this.firstPlace = firstPlace;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRefundCut() {
        return refundCut;
    }

    public void setRefundCut(double refundCut) {
        this.refundCut = refundCut;
    }

    public double getPremiumFee() {
        return premiumFee;
    }

    public void setPremiumFee(double premiumFee) {
        this.premiumFee = premiumFee;
    }


    public ArrayList<Integer> getEmptySeats() {
        return emptySeats;
    }

    public void setEmptySeats(ArrayList<Integer> emptySeats) {
        this.emptySeats = emptySeats;
    }

    public ArrayList<Integer> getFullSeats() {
        return fullSeats;
    }

    public void setFullSeats(ArrayList<Integer> fullSeats) {
        this.fullSeats = fullSeats;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
}
