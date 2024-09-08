/**
 * The {@code Toy} class represents a toy item, which is a type of {@link Item}.
 * It contains additional property specific to toy items, such as the color.
 */
public class Toy extends Item{
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
