import java.util.ArrayList;
/**
 * TicketManager is an abstract class that provides methods for managing tickets.
 */
public abstract class TicketManager {
    /**
     * Calculates the total price of tickets based on the bus type and the number of seats.
     *
     * @param bus   The Bus object representing the bus for which tickets are being sold.
     * @param seats An array of integers representing the seat numbers for which tickets are being sold.
     * @return The total price of the tickets.
     */
    public double feeCalculator(Bus bus, int[] seats) {
        if(bus.getType().equals("Standard")) {
            return seats.length * bus.getPrice();
        } else if (bus.getType().equals("Premium")) {
            int premiumSeats = 0;
            int standartSeats = 0;
            for(int i:seats) {
                if(i % 3 ==1) {
                    premiumSeats += 1;
                }else{
                    standartSeats += 1;
                }
            }
            return standartSeats*bus.getPrice() + premiumSeats*(bus.getPrice()*(bus.getPremiumFee() + 100)/100);
        } else if (bus.getType().equals("Minibus")) {
            return bus.getPrice() * seats.length;
        }
        return -1;
    }
    /**
     * Checks if a bus with the given ID exists in the list of bus objects.
     *
     * @param id          The ID of the bus to check.
     * @param busObjects  The list of Bus objects representing available buses.
     * @return true if a bus with the given ID exists, false otherwise.
     */
    public boolean IDControl(int id, ArrayList<Bus> busObjects) {
        for (Bus bus: busObjects) {
            if (bus.getID() == id) {
                return true;
            }
        }
        return false;
    }
    /**
     * Checks if a given string represents a positive integer.
     *
     * @param numberPart The string to be checked.
     * @return True if the string represents a positive integer, false otherwise.
     */
    public boolean numberControl (String numberPart) {
        for (char c : numberPart.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        if (Integer.parseInt(numberPart) < 0) {
            return false;
        }
        return true;
    }
}
