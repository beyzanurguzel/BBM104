/**
 * The {@code Stationery} class represents a stationery item, which is a type of {@link Item}.
 * It contains additional property specific to stationery items, such as the kind.
 */
public class Stationery extends Item{
    private String kind;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}
