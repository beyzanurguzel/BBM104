/**
 * The Decoration class represents a decoration item.
 */
public class Decoration {
    private String name;        // The name of the decoration.
    private String type;        // The type of the decoration.
    private double price;       // The price of the decoration.
    private double unitArea;    // The unit area covered by the decoration.


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getUnitArea() {
        return unitArea;
    }

    public void setUnitArea(double unitArea) {
        this.unitArea = unitArea;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
