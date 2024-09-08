import java.io.File;

/**
 * Main class to execute the program.
 * Reads input data from a text file, processes it, and writes the output to another text file.
 */
public class BookingSystem {
    /**
     * Main method to start the program.
     * Reads input data from a text file, processes it, and writes the output to another text file.
     *
     * @param args Command line arguments. The first argument should be the path to the input file,
     *             and the second argument should be the path to the output file.
     */

    public static void main(String[] args) {

        if(args.length != 2) {
            System.out.println("ERROR: This program works exactly with two command line arguments, the first one is the path to the" +
                    " input file whereas the second one is the path to the output file." +
                    " Sample usage can be as follows: \"java8 BookingSystem input.txt output.txt\". Program is going to terminate!");
            System.exit(1);
        }
        String pathOfInput = args[0];    // Path to the input file
        String pathOfOutput = args[1];   // Path to the output file
        File inputFile = new File(pathOfInput);
        if (!inputFile.exists()) {
            System.out.println("ERROR: This program cannot read from the \"" +pathOfInput + "\"," +
                    " either this program does not have read permission to read that file or file does not exist. Program is going to terminate!");
            System.exit(1); // Terminates program on error
        }

        String[] inputDatas = FileInput.readTxt(pathOfInput);
        // Creates an InputManager object to process the input data, if there is no erroneous situation
        InputManager inputManager = new InputManager();
        inputManager.manageInput(inputDatas, pathOfOutput);
    }
}