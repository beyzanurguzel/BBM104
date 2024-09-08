import java.util.ArrayList;
import java.util.Arrays;

/**
 * The PurchaseManager class manages purchases of products.
 * It handles user requests, validates inputs, and processes purchases.
 */
public class PurchaseManager {
    /**
     * Manages the purchase process based on user inputs.
     *
     * @param productObjects An ArrayList of Product objects.
     * @param pathPurchase   The path to the file containing purchase data.
     * @param pathGMM        The path to the file where output messages will be written.
     */
    public void purchaseManager(ArrayList<Product> productObjects, String pathPurchase, String pathGMM) {

        String[] purchaseDatas = FileInput.readTxt(pathPurchase);
        for (String line : purchaseDatas) {

            String[] parts = line.split("\t");
            String[] moneys = parts[1].split(" ");
            int value = Integer.parseInt(parts[3]);

            FileOutput.writeToFile(pathGMM,"INPUT: " + line, true, true);

            // we make a list of valid moneys in case the user put wrong moneys to the machine.
            // If so, we write an info for the situation with ismoneyTrue method.
            ArrayList<Integer> validMoneys = isMoneyTrue(moneys, pathGMM);

            int change = isMoneyEnough(validMoneys, 0); // we calculate the total valid moneys that user put, in case exceptions occured.
            // First, we check if user wants a product according to nutrition or just a product from its slot number.
            if (parts[2].equals("NUMBER")) {
                // If the value that user enterred for slot number is out of slot indexes, we warn the user.
                if(value > 23) {
                    FileOutput.writeToFile(pathGMM,"INFO: Number cannot be accepted. Please try again with another number.", true, true);
                    FileOutput.writeToFile(pathGMM,"RETURN: Returning your change: " + change + " TL", true, true);
                }// If the slot that user chose is empty, we warn the user.
                else if(productObjects.get(value).getStock() == 0) {
                    FileOutput.writeToFile(pathGMM, "INFO: This slot is empty, your money will be returned.", true, true);
                    FileOutput.writeToFile(pathGMM,"RETURN: Returning your change: " + change + " TL", true, true);
                }// If the user's money is not enough for the chosen product, we return user's money by warning.
                else if(isMoneyEnough(validMoneys,  productObjects.get(value).getPrice()) == -1 ){
                    FileOutput.writeToFile(pathGMM, "INFO: Insufficient money, try again with more money.", true, true);
                    FileOutput.writeToFile(pathGMM, "RETURN: Returning your change: " + change + " TL", true, true);
                }// If no exception occur, we give the product and refund the change.
                else{
                    // First we calculated all the money that user put for exceptions, now we calculate the change.
                    int realChange = isMoneyEnough(validMoneys, productObjects.get(value).getPrice());
                    FileOutput.writeToFile(pathGMM, "PURCHASE: You have bought one " + productObjects.get(value).getName(), true, true);
                    FileOutput.writeToFile(pathGMM, "RETURN: Returning your change: " + realChange + " TL", true, true);
                    // Now, we decrease product's stock, and we reset the slot in case it is empty.
                    productObjects.get(value).setStock(productObjects.get(value).getStock() - 1);
                    if (productObjects.get(value).getStock() == 0) {
                        productObjects.get(value).setStock(0);
                        productObjects.get(value).setCalorie(0);
                        productObjects.get(value).setName("___");
                    }
                }
            }else{
                // we find a product according to user's request.
                // If there is no product according to the request, findProduct method returns -1. In case that it returns -1, we write an info.
                int indexOfFoundedObject = findProduct(productObjects, parts[2], value);
                if (indexOfFoundedObject == -1) {
                    FileOutput.writeToFile(pathGMM, "INFO: Product not found, your money will be returned.", true, true);
                    FileOutput.writeToFile(pathGMM, "RETURN: Returning your change: " + change + " TL", true, true);
                }// isMoneyEnough method returns -1, in case the moneys aren't enough. So, we write an info about it.
                else if (isMoneyEnough(validMoneys, productObjects.get(indexOfFoundedObject).getPrice()) == -1) {
                    FileOutput.writeToFile(pathGMM, "INFO: Insufficient money, try again with more money.", true, true);
                    FileOutput.writeToFile(pathGMM, "RETURN: Returning your change: " + change + " TL", true, true);
                }else{
                    int realChange = isMoneyEnough(validMoneys, productObjects.get(indexOfFoundedObject).getPrice());
                    FileOutput.writeToFile(pathGMM, "PURCHASE: You have bought one " + productObjects.get(indexOfFoundedObject).getName(), true, true);
                    FileOutput.writeToFile(pathGMM, "RETURN: Returning your change: " + realChange + " TL", true, true);
                    productObjects.get(indexOfFoundedObject).setStock(productObjects.get(indexOfFoundedObject).getStock() - 1);
                    if (productObjects.get(indexOfFoundedObject).getStock() == 0) {
                        productObjects.get(indexOfFoundedObject).setStock(0);
                        productObjects.get(indexOfFoundedObject).setCalorie(0);
                        productObjects.get(indexOfFoundedObject).setName("___");
                    }
                }
            }
        }
    }

    /**
     * Checks if the provided money values are valid.
     *
     * @param moneyList  An array of strings representing money values.
     * @param pathOfGmm  The path to the file where output messages will be written.
     * @return           An ArrayList containing valid money values that user put.
     */
    public ArrayList<Integer> isMoneyTrue(String[] moneyList, String pathOfGmm) {
        ArrayList<Integer> acceptableMoneys = new ArrayList<>(Arrays.asList(1, 5, 10, 20, 50, 100, 200));
        ArrayList<Integer> intMoneys = new ArrayList<>();
        // we convert string moneys to integer moneys.
        for(int k = 0; k < moneyList.length; k++) {
            intMoneys.add(Integer.parseInt(moneyList[k]));
        }
        // we make a list of valid moneys in cae of the user put invalid moneys.
        ArrayList<Integer> validMoneys = new ArrayList<>();

        for(int money : intMoneys) {
            if (acceptableMoneys.contains(money)) {
                validMoneys.add(money);
            }
        }
        // If validMoneys's size is not equal intMoneys's size, it means that the user put invalid money or moneys.
        // If so, we write an info about it.
        if(validMoneys.size() != intMoneys.size()) {
            FileOutput.writeToFile(pathOfGmm, "INFO: Invalid money", true, true);
        }
        return validMoneys;
    }

    /**
     * Checks if the total money is enough for the given purchase.
     *
     * @param moneyList An ArrayList containing valid money values.
     * @param money     The total amount of money required for the purchase.
     * @return          The remaining change if the money is enough; -1 otherwise.
     */
    public int isMoneyEnough(ArrayList<Integer> moneyList, int money) {

        int total = 0;

        for(int i : moneyList) {
            total += i;
        }
        // After calculating total money, we check if the moneys that user put is enough or not for the product.
        // If it is enough, the method returns change. If it is not enough it returns -1.
        if(total < money) {
            return -1;
        }else{
            return (total - money);
        }
    }

    /**
     * Finds a product based on user's request.
     *
     * @param products   An ArrayList of Product objects.
     * @param nutrition  The type of nutrition to search for (PROTEIN, CARB, FAT, or CALORIE).
     * @param value      The value of the nutrition to search for.
     * @return           The index of the found product; -1 if no product matches the criteria.
     */
    public int findProduct(ArrayList<Product> products, String nutrition, int value) {

        // Also, we first check the stock situation of the product.
        // Because sold out products kept by changing their names to "___" , calories and stock situations to 0.
        // If we don't check first the stock situation before finding the project, there might be mistakes.
        if (nutrition.equals("PROTEIN")) {
            for ( Product product : products) {
                if(product.getStock() != 0) {
                    if ((product.getProtein() >= (value - 5)) && (product.getProtein() <= (value + 5))) {
                        return products.indexOf(product);
                    }
                }
            }
        }else if(nutrition.equals("CARB")) {
            for ( Product product : products) {
                if (product.getStock() != 0) {
                    if ((product.getCarbohydrate() >= (value - 5)) && (product.getCarbohydrate() <= (value + 5))) {
                        return products.indexOf(product);
                    }
                }
            }
        } else if (nutrition.equals("FAT")) {
            for ( Product product : products) {
                if (product.getStock() != 0) {
                    if ((product.getFat() >= (value - 5)) && (product.getFat() <= (value + 5))) {
                        return products.indexOf(product);
                    }
                }
            }
        }else{
            for ( Product product: products) {
                if(product.getStock() != 0) {
                    if((product.getCalorie() >= (value - 5)) && (product.getCalorie() <= (value + 5))) {
                        return products.indexOf(product);
                    }
                }
            }
        }
        return -1;
    }
}