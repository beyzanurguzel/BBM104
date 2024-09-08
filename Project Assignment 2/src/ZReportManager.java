import java.util.ArrayList;
import java.util.Collections;
/**
 * The ZReportManager class generates and prints Z reports for voyages.
 */
public class ZReportManager extends PrintManager{
    /**
     * Generates and prints a Z report based on the provided command and list of bus objects.
     *
     * @param pathOfOutput The file path for outputting the Z report.
     * @param command      The command string for generating the Z report.
     * @param busObjects   The list of Bus objects representing available buses.
     */
    public void zReport(String pathOfOutput, String command, ArrayList<Bus> busObjects) {
        String[] parts = command.split("\t");
        // Checks if the command has the correct number of parameters
        if(parts.length != 1) {
            FileOutput.writeToFile(pathOfOutput, "ERROR: Erroneous usage of \"Z_REPORT\" command!", true, false);
        }else{
            // Writes the header for the Z Report to the output file
            FileOutput.writeToFile(pathOfOutput, "Z Report:", true, true);
            FileOutput.writeToFile(pathOfOutput, "----------------", true, true);
            // Creates a list to store IDs of all buses
            ArrayList<Integer> IDsOfBuses = new ArrayList<>();
            if (busObjects.size() == 0) {
                // Writes a message for no voyages available
                FileOutput.writeToFile(pathOfOutput, "No Voyages Available!", true, true);
                FileOutput.writeToFile(pathOfOutput, "----------------", true, false);

            }
            // Iterates through bus objects and collect their IDs
            for (Bus bus: busObjects) {
                IDsOfBuses.add(bus.getID());
            }
            // Sorts the IDs of buses
            Collections.sort(IDsOfBuses);
            int count = 0;
            // Iterates through sorted IDs and print voyage details for each ID
            for (int ID: IDsOfBuses) {
                for (Bus bus: busObjects) {
                    if (bus.getID() == ID) {
                        if(count != 0) {
                            // Adds a new line if it's not the first voyage in the report
                            FileOutput.writeToFile(pathOfOutput, "\n", true, false);
                        }
                        count += 1;
                        // Prints details of the voyage to the output file
                        printVoyage(bus.getID(), bus.getType(), bus.getDestination(), bus.getFirstPlace(),
                                bus.getNumberOfRows(), bus.getRevenue(), bus.getFullSeats(), pathOfOutput);
                        FileOutput.writeToFile(pathOfOutput, "----------------", true, false);
                    }
                }
            }
        }

    }
}