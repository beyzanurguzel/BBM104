import java.util.ArrayList;
/**
 * The Main class represents the entry point of the Gym Meal Machine program.
 * It reads input files, initializes the Gym Meal Machine, processes purchases,
 * and outputs the final state of the machine.
 */

public class Main {
    /**
     * The main method is the entry point of the program.
     * It reads input files, initializes the Gym Meal Machine, processes purchases,
     * and outputs the final state of the machine.
     *
     * @param args Command-line arguments containing file paths:
     *             args[0]: Path of the file containing product data.
     *             args[1]: Path of the file containing purchase data.
     *             args[2]: Path of the output file.
     */
    public static void main(String[] args) {

        String pathOfProducts =args[0];
        String pathOfPurchases = args[1];
        String pathOfGMM = args[2];

        String[] productDatas = FileInput.readTxt(pathOfProducts);
        GMM gmm = new GMM();
        ArrayList<Product> products = gmm.fill(productDatas, pathOfGMM);

        FileOutput.writeToFile(pathOfGMM, "-----Gym Meal Machine-----", true, true);
        int index = 0;
        for ( int i = 0; i < 6 ; i++) {
            for (int j = 0; j < 4 ; j ++) {
                String name = products.get(index).getName();
                int calorie = products.get(index).getCalorie();
                int stock = products.get(index).getStock();
                FileOutput.writeToFile(pathOfGMM, name + "(" + calorie + ", " + stock + ")___" , true, false);
                index += 1;
            }
            FileOutput.writeToFile(pathOfGMM, "\n", true, false);
        }
        FileOutput.writeToFile(pathOfGMM, "----------", true, true);

        // After filling the GMM and writing it to the output txt, we call our PurchaseManager class to take orders from user.

        PurchaseManager purchaseManager = new PurchaseManager();
        purchaseManager.purchaseManager(products, pathOfPurchases, pathOfGMM);

        // Finally we write the final look of the machine to the output txt

        FileOutput.writeToFile(pathOfGMM, "-----Gym Meal Machine-----", true, true);
        int index2 = 0;
        for ( int i = 0; i < 6 ; i++) {
            for (int j = 0; j < 4 ; j ++) {
                String name = products.get(index2).getName();
                int calorie = products.get(index2).getCalorie();
                int stock = products.get(index2).getStock();
                FileOutput.writeToFile(pathOfGMM, name + "(" + calorie + ", " + stock + ")___" , true, false);
                index2 += 1;
            }
            FileOutput.writeToFile(pathOfGMM, "\n", true, false);
        }
        FileOutput.writeToFile(pathOfGMM, "----------", true, true);
    }
}