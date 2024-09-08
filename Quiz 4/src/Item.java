/**
 * The {@code Item} class represents an item in an inventory system.
 * It contains properties such as type, name, barcode, and price.
 */
public class Item {
    private String type;
    private String name;
    private int barcode;
    private Double price;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBarcode() {
        return barcode;
    }

    public void setBarcode(int barcode) {
        this.barcode = barcode;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
