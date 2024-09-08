/**
 * The Product class represents a product in the Gym Meal Machine.
 * It encapsulates information such as name, price, nutritional values, calorie, and stock.
 */
public class Product {
    private String name;
    private int price;
    private double protein;
    private double carbohydrate;
    private double fat;
    private int calorie;
    private int stock = 0;

    /**
     * Constructs a Product object with the given name, price, protein, carbohydrate, and fat values.
     * Calorie value is calculated based on protein, carbohydrate, and fat values.
     *
     * @param name         The name of the product.
     * @param price        The price of the product.
     * @param protein      The protein value of the product.
     * @param carbohydrate The carbohydrate value of the product.
     * @param fat          The fat value of the product.
     */
    public Product(String name, int price, double protein, double carbohydrate, double fat ) {
        this.name=name;
        this.price = price;
        this.protein = protein;
        this.carbohydrate = carbohydrate;
        this.fat = fat;
        calorie = (int) Math.round((4*protein + 4*carbohydrate + 9*fat));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }


    public double getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public double getCarbohydrate() {
        return carbohydrate;
    }


    public double getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {this.stock = stock;}
}