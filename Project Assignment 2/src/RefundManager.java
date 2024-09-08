import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
/**
 * RefundManager class manages the refunding of tickets for voyages.
 */
public class RefundManager extends TicketManager {
    /**
     * Refunds the specified ticket(s) for a voyage and writes the refund information to the output file.
     *
     * @param command      Command string containing the REFUND_TICKET command, voyage ID, and seat numbers.
     * @param busObjects   List of Bus objects representing available buses.
     * @param pathOfOutput Path to the output file.
     */
    public void refundTicket(String command, ArrayList<Bus> busObjects, String pathOfOutput) {
        if (lineControl(command, busObjects, pathOfOutput)) {
            String[] parts = command.split("\t");
            // Extracts seat numbers from the command
            String[] seatNumbers = parts[2].split("_");
            int[] seatNumbers2 = new int[seatNumbers.length];
            // Converts seat numbers from string to integers
            for (int i = 0; i < seatNumbers.length; i++) {
                seatNumbers2[i] = Integer.parseInt(seatNumbers[i]);
            }
            int ID = Integer.parseInt(parts[1]);
            String firstPlace = "";
            String destination = "";
            String formattedPrice = "";
            // Iterates through bus objects to find the corresponding bus
            for (Bus bus: busObjects){
                if (bus.getID() == ID) {
                    for (int seat: seatNumbers2) {
                        bus.getEmptySeats().add(seat);
                        bus.getFullSeats().remove(Integer.valueOf(seat));
                    }
                    firstPlace = bus.getFirstPlace();
                    destination = bus.getDestination();
                    // Calculates the refund amount based on ticket price and refund cut percentage
                    Double totalPrice = feeCalculator(bus, seatNumbers2) * (100 - bus.getRefundCut()) / 100 ;
                    formattedPrice = String.format("%.2f", totalPrice).replace(",", ".");
                    bus.setRevenue(bus.getRevenue() - totalPrice);
                    break;
                }
            }
            // Generates a refund confirmation message
            String message = "Seat " + parts[2].replace("_", "-") + " of the Voyage " + parts[1] + " from " + firstPlace +
                    " to " + destination + " was successfully refunded for " + formattedPrice + " TL.";
            FileOutput.writeToFile(pathOfOutput, message, true, true);
        }

    }
    /**
     * Checks the validity of the REFUND_TICKET command line and performs necessary checks before refunding.
     *
     * @param line         Command line string containing the REFUND_TICKET command, voyage ID, and seat numbers.
     * @param busObjects   List of Bus objects representing available buses.
     * @param pathOfOutput Path to the output file.
     * @return true if the command line is valid and refund can be performed, false otherwise.
     */
    public boolean lineControl(String line, ArrayList<Bus> busObjects, String pathOfOutput) {
        String[] parts = line.split("\t");
        // Checks if the command has the correct number of parameters
        if (parts.length != 3) {
            FileOutput.writeToFile(pathOfOutput, "ERROR: Erroneous usage of \"REFUND_TICKET\" command!", true, true);
            return false;
        }
        // Checks if the id of the voyage number is valid
        if(!numberControl(parts[1])) {
            FileOutput.writeToFile(pathOfOutput, "ERROR: " + parts[1] + " is not a positive integer, ID of a voyage must be a positive integer!", true, true);
            return false;
        }
        // Checks if the specified voyage ID exists
        if (!IDControl(Integer.parseInt(parts[1]), busObjects)) {
            FileOutput.writeToFile(pathOfOutput, "ERROR: There is no voyage with ID of " + parts[1] + "!", true, true);
            return false;
        }
        int ID = Integer.parseInt(parts[1]);
        for (Bus bus: busObjects) {
            if(bus.getID() == ID) {
                // Checks if the voyage is a Minibus type, as Minibus tickets are not refundable
                if(bus.getType().equals("Minibus")) {
                    FileOutput.writeToFile(pathOfOutput, "ERROR: Minibus tickets are not refundable!", true, true);
                    return false;
                }
            }
        }
        // Extracts seat numbers from the command and validates them
        String[] seatNumbers = parts[2].split("_");
        for (String part : seatNumbers) {
            if(!numberControl(part)) {
                FileOutput.writeToFile(pathOfOutput, "ERROR: " + part + " is not a positive integer, seat number must be a positive integer!", true, true);
                return false;
            }
        }
        int[] seatNumbers2 = new int[seatNumbers.length];
        for (int i = 0; i < seatNumbers.length; i++) {
            seatNumbers2[i] = Integer.parseInt(seatNumbers[i]);
        }
        // Checks if the seat numbers are valid
        return seatControl(seatNumbers2, Integer.parseInt(parts[1]), busObjects, pathOfOutput);

    }
    /**
     * Checks the validity of the seat numbers and performs necessary checks before refunding.
     *
     * @param seatNumbers  Array of seat numbers to be refunded.
     * @param ID           Voyage ID.
     * @param busObjects   List of Bus objects representing available buses.
     * @param pathOfOutput Path to the output file.
     * @return true if the seat numbers are valid and refund can be performed, false otherwise.
     */
    public boolean seatControl(int[] seatNumbers, int ID, ArrayList<Bus> busObjects, String pathOfOutput) {
        for(int seatNumber: seatNumbers) {
            for (Bus bus: busObjects) {
                if (bus.getID() == ID) {
                    int numberOfAllSeats = bus.getEmptySeats().size() + bus.getFullSeats().size();
                    // Checks if the seat number exceeds the total number of seats on the bus
                    if(numberOfAllSeats < seatNumber) {
                        FileOutput.writeToFile(pathOfOutput, "ERROR: There is no such a seat!", true, true);
                        return false;
                    }
                    // Checks if the seat is already empty
                    for(int emptySeat: bus.getEmptySeats()) {
                        if (seatNumber == emptySeat) {
                            FileOutput.writeToFile(pathOfOutput, "ERROR: One or more seats are already empty!", true, true);
                            return false;
                        }
                    }
                }
            }
        }
        // All seat numbers are valid and available
        return true;
    }
}