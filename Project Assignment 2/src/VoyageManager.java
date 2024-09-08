import java.util.ArrayList;
import java.util.List;
/**
 * The VoyageManager class manages voyages, including initializing voyages, canceling voyages, and performing line control.
 */
public class VoyageManager {
    PrintManager printManager = new PrintManager();
    private ArrayList<Bus> busObjects = new ArrayList<>();
    /**
     * Initializes a voyage based on the provided command.
     *
     * @param command      The command string containing voyage information.
     * @param pathOfOutput The file path for outputting messages.
     */
    public void initVoyage(String command, String pathOfOutput) {
        String[] parts = command.split("\t");

        if(lineControl(command, pathOfOutput)) {
            if (parts[1].equals("Standard")) {
                Bus standardBus = new Bus();
                standardBus.setType(parts[1]);
                standardBus.setID(Integer.parseInt(parts[2]));
                standardBus.setFirstPlace(parts[3]);
                standardBus.setDestination(parts[4]);
                standardBus.setNumberOfRows(Integer.parseInt(parts[5]));
                standardBus.setPrice(Double.parseDouble(parts[6]));
                standardBus.setRefundCut(Double.parseDouble(parts[7]));
                int numberOfSeats = 4 * standardBus.getNumberOfRows();
                for (int seat = 1; seat <= numberOfSeats; seat++) {
                    standardBus.getEmptySeats().add(seat);
                }
                getBusObjects().add(standardBus);

                String formattedPrice = String.format("%.2f", standardBus.getPrice()).replace(",", "."); // uuuuu
                String message = "Voyage " + parts[2] + " was initialized as a standard (2+2) voyage from " + parts[3] +
                        " to " + parts[4] + " with " + formattedPrice + " TL priced " + 4*standardBus.getNumberOfRows() +
                        " regular seats. Note that refunds will be " + parts[7] + "% less than the paid amount.";
                FileOutput.writeToFile(pathOfOutput, message, true, true);

            } else if (parts[1].equals("Premium")) {
                Bus premiumBus = new Bus();
                premiumBus.setType(parts[1]);
                premiumBus.setID(Integer.parseInt(parts[2]));
                premiumBus.setFirstPlace(parts[3]);
                premiumBus.setDestination(parts[4]);
                premiumBus.setNumberOfRows(Integer.parseInt(parts[5]));
                premiumBus.setPrice(Double.parseDouble(parts[6]));
                premiumBus.setRefundCut(Double.parseDouble(parts[7]));
                premiumBus.setPremiumFee(Double.parseDouble(parts[8]));
                int numberOfSeats2 = 3 * premiumBus.getNumberOfRows();
                for (int seat = 1; seat <= numberOfSeats2; seat++) {
                    premiumBus.getEmptySeats().add(seat);
                }
                getBusObjects().add(premiumBus);

                String formattedPrice = String.format("%.2f", premiumBus.getPrice()).replace(",", ".");
                Double premiumPrice = premiumBus.getPrice()*(100 + premiumBus.getPremiumFee()) /100;
                String formattedPremiumPrice = String.format("%.2f", premiumPrice).replace(",", ".");

                String message = "Voyage " + parts[2] + " was initialized as a premium (1+2) voyage from " + parts[3] +
                        " to " + parts[4] + " with " + formattedPrice +  " TL priced " + 2* premiumBus.getNumberOfRows() +
                        " regular seats and " + formattedPremiumPrice + " TL priced " + parts[5] +
                        " premium seats. Note that refunds will be " + parts[7] + "% less than the paid amount.";
                FileOutput.writeToFile(pathOfOutput, message, true, true);
            } else if (parts[1].equals("Minibus")) {
                Bus minibus = new Bus();
                minibus.setType(parts[1]);
                minibus.setID(Integer.parseInt(parts[2]));
                minibus.setFirstPlace(parts[3]);
                minibus.setDestination(parts[4]);
                minibus.setNumberOfRows(Integer.parseInt(parts[5]));
                minibus.setPrice(Double.parseDouble(parts[6]));
                int numberOfSeats3 = 2 * minibus.getNumberOfRows();
                for (int seat = 1; seat <= numberOfSeats3 ; seat++) {
                    minibus.getEmptySeats().add(seat);
                }
                String formattedPrice = String.format("%.2f", minibus.getPrice()).replace(",", ".");
                getBusObjects().add(minibus);
                String message = "Voyage " + parts[2] + " was initialized as a minibus (2) voyage from " + parts[3] +
                        " to " + parts[4] + " with " + formattedPrice + " TL priced " + numberOfSeats3 + " regular seats. Note that minibus tickets are not refundable.";
                FileOutput.writeToFile(pathOfOutput, message, true, true);
            }
        }
    }
    /**
     * Performs line control for the provided command.
     *
     * @param line         The command string to be checked.
     * @param pathOfOutput The file path for outputting messages.
     * @return true if the command passes the line control, false otherwise.
     */
    public boolean lineControl(String line, String pathOfOutput) {
        String[] parts = line.split("\t");
        List<String> busTypes = new ArrayList<>();
        busTypes.add("Standard");
        busTypes.add("Premium");
        busTypes.add("Minibus");
        // Checks if the bus type is valid
        if(!busTypes.contains(parts[1])) {
            FileOutput.writeToFile(pathOfOutput, "ERROR: Erroneous usage of \"INIT_VOYAGE\" command!", true, true);
            return false;
        }
        // Checks the number of parameters based on bus type
        if(parts[1].equals("Standard")) {
            if(parts.length != 8) {
                FileOutput.writeToFile(pathOfOutput, "ERROR: Erroneous usage of \"INIT_VOYAGE\" command!", true, true);
                return false;
            }
        }
        if (parts[1].equals("Premium")) {
            if (parts.length != 9) {
                FileOutput.writeToFile(pathOfOutput, "ERROR: Erroneous usage of \"INIT_VOYAGE\" command!", true, true);
                return false;
            }
        }
        if(parts[1].equals("Minibus")) {
            if (parts.length != 7) {
                FileOutput.writeToFile(pathOfOutput, "ERROR: Erroneous usage of \"INIT_VOYAGE\" command!", true, true);
                return false;
            }
        }
        // Checks if the ID of the voyage is a positive integer and not already used
        if(!numberControl(parts[2])) {
            FileOutput.writeToFile(pathOfOutput, "ERROR: " + parts[2] + " is not a positive integer, ID of a voyage must be a positive integer!", true, true);
            return false;
        }
        if (isThereAnyBusWithSameID(Integer.parseInt(parts[2]))) {
            FileOutput.writeToFile(pathOfOutput, "ERROR: There is already a voyage with ID of " + parts[2] + "!", true, true);
            return false;
        }
        // Checks if the number of seat rows is a positive integer
        if (!numberControl(parts[5])) {
            FileOutput.writeToFile(pathOfOutput, "ERROR: " + parts[5] +" is not a positive integer, number of seat rows of a voyage must be a positive integer!", true, true);
            return false;
        }
        // Checks if the price is a positive number
        if (!isDouble(parts[6])) {
            FileOutput.writeToFile(pathOfOutput, "ERROR: " + parts[6] + " is not a positive number, price must be a positive number!", true, true);
            return false;
        }
        if(isDouble(parts[6])) {
            if (Double.parseDouble(parts[6]) < 0) {
                FileOutput.writeToFile(pathOfOutput, "ERROR: " + parts[6] + " is not a positive number, price must be a positive number!", true, true);
                return false;
            }
        }
        if (!isDouble(parts[6])) {
            FileOutput.writeToFile(pathOfOutput, "ERROR: " + parts[6] + " is not a positive number, price must be a positive number!", true, true);
            return false;
        }
        if (Double.parseDouble(parts[6]) < 0) {
            FileOutput.writeToFile(pathOfOutput, "ERROR: " + parts[6] + " is not a positive number, price must be a positive number!", true, true);
            return false;
        }
        // Checks if the refund cut is a valid integer percentage
        if (parts.length > 7) {
            if(!numberControl(parts[7])) {
                FileOutput.writeToFile(pathOfOutput, "ERROR: " + parts[7] +
                        " is not an integer that is in range of [0, 100], refund cut must be an integer that is in range of [0, 100]!", true, true );
                return false;
            }
            if ((Integer.parseInt(parts[7]) > 100) || (Integer.parseInt(parts[7]) < 0)) {
                FileOutput.writeToFile(pathOfOutput, "ERROR: " + parts[7] +
                        " is not an integer that is in range of [0, 100], refund cut must be an integer that is in range of [0, 100]!", true, true );
                return false;
            }
        }
        // Check if the premium fee is a non-negative integer (if provided)
        if (parts.length == 9) {
            if (!numberControl(parts[8])) {
                FileOutput.writeToFile(pathOfOutput, "ERROR: " + parts[8] + " is not a non-negative integer, premium fee must be a non-negative integer!", true, true);
                return false;
            }
        }
        // If all checks pass, return true
        return true;
    }
    /**
     * Checks if there is already a bus with the same ID.
     *
     * @param referenceID The ID to be checked.
     * @return true if there is a bus with the same ID, false otherwise.
     */
    public boolean isThereAnyBusWithSameID(int referenceID) {
        for (Bus bus: getBusObjects()) {
            if (bus.getID() == referenceID) {
                return true;
            }
        }
        return false;
    }
    /**
     * Gets the list of Bus objects representing available buses.
     *
     * @return The list of Bus objects.
     */
    public ArrayList<Bus> getBusObjects() {
        return busObjects;
    }
    /**
     * Cancels a voyage based on the provided command.
     *
     * @param command     The command string containing the voyage ID to be canceled.
     * @param pathOfOutput The file path for outputting messages.
     * @param busObjects  The list of Bus objects representing available buses.
     */
    public void cancelVoyage(String command, String pathOfOutput, ArrayList<Bus> busObjects) {
        String[] parts = command.split("\t");
        // Checks if the command passes line control
        if(lineControl2(command, pathOfOutput)) {
            FileOutput.writeToFile(pathOfOutput, "Voyage " + parts[1] + " was successfully cancelled!", true, true);
            FileOutput.writeToFile(pathOfOutput, "Voyage details can be found below:", true, true);
            int index = 0;
            // Iterates through bus objects to find the cancelled voyage
            for (Bus bus: busObjects) {
                if (bus.getID() == Integer.parseInt(parts[1])) {
                    Double remainRevenue = remainRevenueCalculator(bus);
                    printManager.printVoyage(bus.getID(), bus.getType(), bus.getDestination(), bus.getFirstPlace(),
                            bus.getNumberOfRows(), remainRevenue, bus.getFullSeats(), pathOfOutput);
                    break;
                }else{
                    index += 1;
                }
            }
            busObjects.remove(index);
        }

    }
    /**
     * Calculates the remaining revenue for a canceled voyage.
     *
     * @param bus The Bus object representing the canceled voyage.
     * @return The remaining revenue.
     */
    public double remainRevenueCalculator(Bus bus) {
        if (bus.getType().equals("Standard")) {
            return bus.getRevenue() - (bus.getFullSeats().size() * bus.getPrice());
        }
        if (bus.getType().equals("Premium")) {
            int premiumSeats = 0;
            int standardSeats = 0;
            for(int seat: bus.getFullSeats()) {
                if(seat % 3 == 1) {
                    premiumSeats += 1;
                }else{
                    standardSeats += 1;
                }
            }

            Double standartPrice = bus.getPrice() * standardSeats;
            Double premiumPrice = premiumSeats * bus.getPrice()*(100 + bus.getPremiumFee()) / 100;
            return bus.getRevenue() - (standartPrice + premiumPrice);
        }
        if (bus.getType().equals("Minibus")) {
            return 0;
        }
        return -1;
    }
    /**
     * Validates a command line for cancelling a voyage.
     * Checks if the command has the correct number of parameters, if the provided voyage ID is a positive integer,
     * and if there exists a voyage with the provided ID.
     *
     * @param command      the command line containing information about the voyage cancellation
     * @param pathOfOutput the path to the output file for writing error messages
     * @return {@code true} if the command is valid, {@code false} otherwise
     */
    public boolean lineControl2(String command, String pathOfOutput) {
        String[] parts = command.split("\t");
        // Checks if the command has the correct number of parameters
        if (parts.length != 2) {
            FileOutput.writeToFile(pathOfOutput, "ERROR: Erroneous usage of \"CANCEL_VOYAGE\" command!", true, true);
            return false;
        }
        // Checks if the provided voyage ID is a positive integer
        if (!numberControl(parts[1])) {
            FileOutput.writeToFile(pathOfOutput, "ERROR: " + parts[1] + " is not a positive integer, ID of a voyage must be a positive integer!"
                    ,true, true);
            return false;
        }
        if (!isThereAnyBusWithSameID(Integer.parseInt(parts[1]))) {
            FileOutput.writeToFile(pathOfOutput, "ERROR: There is no voyage with ID of " + parts[1] + "!", true, true);
            return false;
        }
        return true;
    }
    public boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
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