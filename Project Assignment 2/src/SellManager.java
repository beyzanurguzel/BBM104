import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
/**
 * SellManager class manages the selling of tickets for voyages.
 */
public class SellManager extends TicketManager {
    /**
     * Sells the specified ticket(s) for a voyage and writes the sale information to the output file.
     *
     * @param command      Command string containing the SELL_TICKET command, voyage ID, and seat numbers.
     * @param busObjects   List of Bus objects representing available buses.
     * @param pathOfOutput Path to the output file.
     */
    public void sellTicket(String command, ArrayList<Bus> busObjects, String pathOfOutput) {

        String[] parts = command.split("\t");
        if(lineControl(command, busObjects, pathOfOutput)) {
            String[] seatNumbers = parts[2].split("_");
            int[] seatNumbers2 = new int[seatNumbers.length];
            for (int i = 0; i < seatNumbers.length; i++) {
                seatNumbers2[i] = Integer.parseInt(seatNumbers[i]);
            }
            // Initializes variables to store details of the voyage
            String firstPlace = "";
            String destination = "";
            int ID = Integer.parseInt(parts[1]);
            String formattedPrice = "";

            for (Bus bus : busObjects) {
                if(bus.getID() == ID) {
                    firstPlace = bus.getFirstPlace();
                    destination = bus.getDestination();
                    Double totalPrice = feeCalculator(bus, seatNumbers2);
                    // Formats the price to two decimal places
                    formattedPrice = String.format("%.2f", totalPrice).replace(",", ".");
                    bus.setRevenue(bus.getRevenue() + totalPrice);
                    // Updates seat status: moves seats from empty to full
                    for (int seat: seatNumbers2) {
                        bus.getFullSeats().add(Integer.valueOf(seat));
                        bus.getEmptySeats().remove(Integer.valueOf(seat));
                    }
                    break;
                }
            }
            String message = "Seat " + parts[2].replace("_", "-") + " of the Voyage " + parts[1] + " from " + firstPlace + " to " +
                    destination + " was successfully sold for " + formattedPrice + " TL.";
            FileOutput.writeToFile(pathOfOutput, message, true, true);
        }

    }
    /**
     * Checks the validity of the SELL_TICKET command line and performs necessary checks before selling.
     *
     * @param line         Command line string containing the SELL_TICKET command, voyage ID, and seat numbers.
     * @param busObjects   List of Bus objects representing available buses.
     * @param pathOfOutput Path to the output file.
     * @return true if the command line is valid and sale can be performed, false otherwise.
     */
    public boolean lineControl(String line, ArrayList<Bus> busObjects, String pathOfOutput) {
        String[] parts = line.split("\t");
        if(parts.length != 3) {
            FileOutput.writeToFile(pathOfOutput, "ERROR: Erroneous usage of \"SELL_TICKET\" command!", true, true);
            return false;
        }
        if(!numberControl(parts[1])) {
            FileOutput.writeToFile(pathOfOutput, "ERROR: " + parts[1] + " is not a positive integer, ID of a voyage must be a positive integer!", true, true);
        }
        if(!IDControl(Integer.parseInt(parts[1]), busObjects)) {
            FileOutput.writeToFile(pathOfOutput, "ERROR: There is no voyage with ID of " + parts[1] + "!", true, true);
            return false;
        }
        return seatControl(line, Integer.parseInt(parts[1]), busObjects, pathOfOutput);
    }
    /**
     * Checks the validity of the seat numbers and performs necessary checks before selling.
     *
     * @param line         Command line string containing the SELL_TICKET command, voyage ID, and seat numbers.
     * @param ID           Voyage ID.
     * @param busObjects   List of Bus objects representing available buses.
     * @param pathOfOutput Path to the output file.
     * @return true if the seat numbers are valid and sale can be performed, false otherwise.
     */
    public boolean seatControl(String line, int ID, ArrayList<Bus> busObjects, String pathOfOutput) {
        String[] parts = line.split("\t");
        String[] seatNumbers = parts[2].split("_");
        int[] seatNumbers2 = new int[seatNumbers.length];
        for (String seat: seatNumbers) {
            if (!numberControl(seat)) {
                FileOutput.writeToFile(pathOfOutput, "ERROR: " + seat + " is not a positive integer, seat number must be a positive integer!", true, true);
                return false;
            }
        }
        for (int i = 0; i < seatNumbers.length; i++) {
            seatNumbers2[i] = Integer.parseInt(seatNumbers[i]);
        }
        for(int seatNumber: seatNumbers2) {
            // Iterates over each bus to find the bus with the given ID
            for (Bus bus: busObjects) {
                if (bus.getID() == ID) {
                    int numberOfAllSeats = bus.getEmptySeats().size() + bus.getFullSeats().size();
                    // Check if the provided seat number is valid
                    if(numberOfAllSeats < seatNumber) {
                        FileOutput.writeToFile(pathOfOutput, "ERROR: There is no such a seat!", true,true);
                        return false;
                    }
                    // Checks if the seat has already been sold
                    for(int fullSeat: bus.getFullSeats()) {
                        if (seatNumber == fullSeat) {
                            FileOutput.writeToFile(pathOfOutput, "ERROR: One or more seats already sold!", true, true);
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

}
