/**
 * The {@code Main} class contains the entry point of the inventory management system.
 * It reads input data from a file, executes commands, and writes output to another file.
 */
public class Main {
    /**
     * The entry point of the program.
     *
     * @param args Command line arguments. Expected arguments are input file path and output file path.
     *             The first argument is the path to the input file containing commands.
     *             The second argument is the path to the output file where the results will be written.
     */
    public static void main(String[] args) {
        // Read input file path and output file path from command line arguments
        String inputPath = args[0];
        String outputPath = args[1];
        // Read input data from the input file
        String[] inputDatas = FileInput.readTxt(inputPath);
        // Create a command manager to execute commands
        CommandManager commandManager = new CommandManager();
        // Execute commands based on the input data and write output to the output file
        commandManager.manageCommands(inputDatas, outputPath);
    }
}