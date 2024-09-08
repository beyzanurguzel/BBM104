import java.util.ArrayList;
/**
 * PrintManager class manages the printing of voyage information and seating arrangements.
 */
public class PrintManager {
    /**
     * Manages the printing of voyage information and seating arrangements.
     *
     * @param command       Command string containing the PRINT_VOYAGE command and voyage ID.
     * @param busObjects    List of Bus objects representing available buses.
     * @param ticketManager TicketManager object for ticket-related operations.
     * @param voyageManager VoyageManager object for voyage-related operations.
     * @param pathOfOutput  Path to the output file.
     */
    public void managePrint(String command, ArrayList<Bus> busObjects, TicketManager ticketManager, VoyageManager voyageManager, String pathOfOutput) {

        String[] parts = command.split("\t");

        if (printLineControl(parts, busObjects, ticketManager, pathOfOutput)) {
            int ID = Integer.parseInt(parts[1]);
            String type = "";
            String destination = "";
            String firstPlace = "";
            int numberOfRows = 0;
            Double revenue = 0.00;
            ArrayList<Integer> fullSeats = new ArrayList();
            for (Bus bus : voyageManager.getBusObjects()) {
                if (bus.getID() == ID) {
                    type = bus.getType();
                    destination = bus.getDestination();
                    firstPlace = bus.getFirstPlace();
                    numberOfRows = bus.getNumberOfRows();
                    revenue = bus.getRevenue();
                    for (int fullSeat : bus.getFullSeats()) {
                        fullSeats.add(fullSeat);
                    }
                }
            }
            printVoyage(ID, type, destination, firstPlace, numberOfRows, revenue, fullSeats, pathOfOutput);
        }
    }
    /**
     * Prints the seating arrangement and revenue information for a voyage.
     *
     * @param ID             Voyage ID.
     * @param type           Type of the bus.
     * @param destination    Destination of the voyage.
     * @param firstPlace     First place of the voyage.
     * @param numberOfRows   Number of rows in the bus.
     * @param revenue        Revenue of the voyage.
     * @param fullSeats      List of full seat numbers.
     * @param pathOfOutput   Path to the output file.
     */
    public void printVoyage(int ID, String type, String destination, String firstPlace, int numberOfRows,
                            double revenue, ArrayList<Integer> fullSeats, String pathOfOutput) {

        String formattedRevenue = String.format("%.2f", revenue).replace(",", ".");
        FileOutput.writeToFile(pathOfOutput, "Voyage " + ID, true, true);
        FileOutput.writeToFile(pathOfOutput,firstPlace + "-" + destination, true, true);
        if (type.equals("Standard")) {
            int seat = 1;
            for (int i = 0; i < numberOfRows; i++) { // for every row of the bus
                for (int j = 0; j < 4; j++) {  // for every seat of the row
                    if ((seat % 2 == 0) && (seat % 4 != 0)) {  // it means the seat is the second seat before corridor line
                        if(fullSeats.contains(seat)) { // Checks if the seat is full
                            FileOutput.writeToFile(pathOfOutput, "X |", true, false);
                        }else{
                            FileOutput.writeToFile(pathOfOutput, "* |", true, false);
                        }
                    } else {
                        if(fullSeats.contains(seat)) {
                            FileOutput.writeToFile(pathOfOutput, "X", true, false);
                        }else{
                            FileOutput.writeToFile(pathOfOutput, "*", true, false);
                        }
                    }
                    seat += 1;
                    if (j != 3) { // Prevents adding space to the end of the seats of the row
                        FileOutput.writeToFile(pathOfOutput, " ", true, false);
                    }
                }
                FileOutput.writeToFile(pathOfOutput, "\n", true, false);
            }
            FileOutput.writeToFile(pathOfOutput, "Revenue: " + formattedRevenue, true, true);
        } else if (type.equals("Premium")) {
            int seat = 1;
            for (int i = 0; i < numberOfRows; i++) { // for every row of the bus
                for (int j = 0; j < 3; j++) { // for every seat of the bus
                    if ((seat % 3 == 1)) { // this means the seat is the first seat right before the corridor line
                        if (fullSeats.contains(seat)) {
                            FileOutput.writeToFile(pathOfOutput, "X |", true, false);
                        }else{
                            FileOutput.writeToFile(pathOfOutput, "* |", true, false);
                        }
                        if(j != 2) { // Prevents addig space to the end of the seats of the row
                            FileOutput.writeToFile(pathOfOutput, " ", true, false);
                        }
                    } else {
                        if(fullSeats.contains(seat)) {
                            FileOutput.writeToFile(pathOfOutput, "X", true, false);
                        }else{
                            FileOutput.writeToFile(pathOfOutput, "*", true, false);
                        }
                        if(j != 2) {
                            FileOutput.writeToFile(pathOfOutput, " ", true, false);
                        }
                    }
                    seat += 1;
                }
                FileOutput.writeToFile(pathOfOutput, "\n", true, false);
            }
            FileOutput.writeToFile(pathOfOutput, "Revenue: " + formattedRevenue, true, true);
        } else if (type.equals("Minibus")) {
            int seat = 1;
            for (int i = 0; i < numberOfRows; i++) { // for every row of the bus
                for(int j = 0; j < 2; j++) { // for every seat of the bus
                    if (j % 2 == 0) {
                        if (fullSeats.contains(seat)) {
                            FileOutput.writeToFile(pathOfOutput, "X ", true, false);
                        }else{
                            FileOutput.writeToFile(pathOfOutput, "* ", true, false);
                        }
                    }else {
                        if (fullSeats.contains(seat)) {
                            FileOutput.writeToFile(pathOfOutput, "X", true, false);
                        }else{
                            FileOutput.writeToFile(pathOfOutput, "*", true, false);
                        }
                    }
                    if (j == 1) {
                        FileOutput.writeToFile(pathOfOutput, "\n", true, false);
                    }
                    seat += 1;
                }
            }
            FileOutput.writeToFile(pathOfOutput, "Revenue: " + formattedRevenue, true, true);
        }
    }
    /**
     * Checks the validity of the PRINT_VOYAGE command line.
     *
     * @param parts         Array containing command parts.
     * @param busObjects    List of Bus objects representing available buses.
     * @param ticketManager TicketManager object for ticket-related operations.
     * @param pathOfOutput  Path to the output file.
     * @return true if the command line is valid, false otherwise.
     */
    public boolean printLineControl (String[]parts, ArrayList < Bus > busObjects, TicketManager ticketManager, String pathOfOutput){

        if (parts.length != 2) { // Checks length of the command line parts
            FileOutput.writeToFile(pathOfOutput, "ERROR: Erroneous usage of \"PRINT_VOYAGE\" command!", true, true);
            return false;
        }
        if (!numberControl(parts[1])) { // Checks if the id of the voyage number is valid
            FileOutput.writeToFile(pathOfOutput,"ERROR: " + parts[1] + " is not a positive integer, ID of a voyage must be a positive integer!", true, true);
            return false;
        }
        if (!ticketManager.IDControl(Integer.parseInt(parts[1]), busObjects)) {
            FileOutput.writeToFile(pathOfOutput, "ERROR: There is no voyage with ID of " + parts[1] + "!", true, true);
            return false;
        }
        return true;
    }
    /**
     * Checks whether the given string consists only of digits and can be converted to a positive integer.
     *
     * @param numberPart the string to be checked
     * @return true if the string consists only of digits and can be converted to a positive integer, otherwise false.
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
