/**
 * Represents a book item that extends the {@link Item} class.
 */
public class Book extends Item{
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}