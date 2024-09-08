import java.io.File;
/**
 * The Valuable class represents an object with a name, worth, weight, and image.
 */
public class Valuable {
    private String name;
    private int worth;
    private int weight;
    private File image;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getWorth() {
        return worth;
    }
    public void setWorth(int worth) {this.worth = worth;}
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public File getImage() {
        return image;
    }
    public void setImage(File image) {
        this.image = image;
    }
}