import java.util.ArrayList;
/**
 * The GMM (Gym Meal Machine) class represents a vending machine for gym meals.
 * It is responsible for filling the machine with products and managing the occupancy rate.
 */

public class GMM {

    /**
            * Fills the Gym Meal Machine with products based on the provided data.
            *
            * @param datasOfProducts An array of strings containing product data.
            * @param pathOfOutput    The path to the output file where information will be written.
            * @return An ArrayList of Product objects representing the products in the machine.
     */
    public ArrayList<Product> fill(String[] datasOfProducts, String pathOfOutput) {
        ArrayList<Product> products = new ArrayList<>();

        // We define an int variable to know the machine occupancy rate.
        int totalStock = 0;

        for(String line: datasOfProducts) {
            String[] parts = line.split("\t");
            String[] otherParts = parts[2].split(" ");
            String name = parts[0];
            int price = Integer.parseInt(parts[1]);
            double protein = Double.parseDouble(otherParts[0]);
            double carbohydrate = Double.parseDouble(otherParts[1]);
            double fat = Double.parseDouble(otherParts[2]);
            int indexOfSameObject = sameObject(products, name);
            // If there is already a slot which is the same as the product that we want to place, we should put it to the slot.
            // So, by calling sameObject method, we gain knowledge about the situation.
            if (indexOfSameObject != -1) {
                products.get(indexOfSameObject).setStock(products.get(indexOfSameObject).getStock() + 1);
                totalStock += 1;
            }// We check if all slots are full.
            else if (products.size() < 24){
                Product product = new Product(name, price, protein, carbohydrate, fat);
                product.setStock(product.getStock() + 1);
                products.add(product);
                totalStock += 1;
            }// If all slots are full and totalStock is not equal 240 thus there is no available place to put the product, we write and info about it.
            else if (totalStock < 240){
                FileOutput.writeToFile(pathOfOutput, "INFO: There is no available place to put " + name, true, true);
            }else if(totalStock == 240) {
                FileOutput.writeToFile(pathOfOutput, "INFO: There is no available place to put " + name, true, true);
                FileOutput.writeToFile(pathOfOutput,"INFO: The machine is full!", true, true);
                break;
            }
        }
        // If there are still empty slots after loading the GMM, we make empty products for the appearance of the GMM.
        for (int i = products.size() ; i < 24; i++) {
            Product product = new Product("___", 0, 0, 0, 0);
            products.add(product);
        }
        return products;
    }

    /**
     * Checks if there is an existing slot for the given product name.
     *
     * @param products An ArrayList of Product objects representing the products in the machine.
     * @param name     The name of the product to check.
     * @return         The index of the existing slot if found; -1 otherwise.
     */
    public Integer sameObject(ArrayList<Product> products, String name) {
        int objectIndex;

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getName().equals(name)) {
                if (products.get(i).getStock() < 10) {
                    objectIndex = i;
                    return objectIndex;
                }
            }
        }
        return -1;
    }
}
