import java.util.ArrayList;
/**
 * InputManager class manages the input data and delegates tasks to various managers based on the input commands.
 */
public class InputManager {


    VoyageManager voyageManager = new VoyageManager();
    ArrayList<Bus> busObjects = voyageManager.getBusObjects();
    SellManager sellManager = new SellManager();
    RefundManager refundManager = new RefundManager();
    PrintManager printManager = new PrintManager();
    ZReportManager zReportManager = new ZReportManager();
    /**
     * Manages the input data and delegates tasks to various managers based on the input commands.
     *
     * @param inputDatas    Array containing input data lines.
     * @param pathOfOutput  Path to the output file.
     */
    public void manageInput(String[] inputDatas, String pathOfOutput) {
        // Does this controls for checking if the last z-report is given
        int lastCommandRow = inputDatas.length - 1;
        int index = 0;
        int emptyFileCounter = 0;
        // Does this process to managing commands
        for (String line: inputDatas) {
            String line1 = line.trim();
            if(!line1.isEmpty()) {
                if (index == 0) {
                    FileOutput.writeToFile(pathOfOutput, "COMMAND: " + line1, false, true);
                }else{
                    FileOutput.writeToFile(pathOfOutput, "COMMAND: " + line1, true, true);
                }
                String[] parts = line1.split("\t");
                if (parts[0].equals("INIT_VOYAGE")) {
                    voyageManager.initVoyage(line1, pathOfOutput);
                } else if (parts[0].equals("SELL_TICKET")) {
                    sellManager.sellTicket(line1, busObjects, pathOfOutput);
                } else if (parts[0].equals("REFUND_TICKET")) {
                    refundManager.refundTicket(line1, busObjects, pathOfOutput);
                } else if (parts[0].equals("PRINT_VOYAGE")) {
                    printManager.managePrint(line1, busObjects, sellManager, voyageManager, pathOfOutput);
                } else if (parts[0].equals("Z_REPORT")) {
                    zReportManager.zReport(pathOfOutput, line, busObjects);
                    //Does this controlled new line adding to prevent being the last line of the output file is empty
                    if (index != lastCommandRow) {
                        FileOutput.writeToFile(pathOfOutput, "\n", true, false);
                    }
                } else if(parts[0].equals("CANCEL_VOYAGE")) {
                    voyageManager.cancelVoyage(line1, pathOfOutput, busObjects);
                }else {
                    FileOutput.writeToFile(pathOfOutput, "ERROR: There is no command namely " + parts[0] + "!", true, true);
                }
            }else{
                emptyFileCounter += 1;
            }
            if (index == lastCommandRow) {
                if(!inputDatas[index].equals("Z_REPORT")) {
                    zReportManager.zReport(pathOfOutput, "Z_REPORT", busObjects);
                }
            }
            index += 1;
        }
        //Checks if the input file is empty. If it is, writes z-report to the output file
        if (emptyFileCounter == inputDatas.length) {
            zReportManager.zReport(pathOfOutput, "Z_REPORT", busObjects);
        }
    }
}

