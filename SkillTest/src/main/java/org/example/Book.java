package org.example;

/**
 * Represents a book in the library with name, price, and genre.
 */
public class Book {
    private String name;
    private double price;
    private String genre;

    /**
     * Constructor to create a book.
     *
     * @param name   the name of the book
     * @param price  the price of the book
     * @param genre  the genre of the book (fiction, mystery, thriller, horror)
     */
    public Book(String name, double price, String genre) {
        this.name = name;
        this.price = price;
        this.genre = genre;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getGenre() {
        return genre;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", genre='" + genre + '\'' +
                '}';
    }
}

