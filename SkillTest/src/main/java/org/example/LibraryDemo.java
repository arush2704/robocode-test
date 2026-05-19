package org.example;

import java.util.*;

/**
 * Demo application showing how to use the Library and Book classes.
 */
public class LibraryDemo {

    public static void main(String[] args) {
        // Create a library
        Library library = new Library();

        // Add books to the library
        library.addBook(new Book("The Great Gatsby", 10.99, "fiction"));
        library.addBook(new Book("To Kill a Mockingbird", 12.99, "fiction"));
        library.addBook(new Book("1984", 13.99, "fiction"));

        library.addBook(new Book("Murder on the Orient Express", 9.99, "mystery"));
        library.addBook(new Book("The Da Vinci Code", 14.99, "mystery"));
        library.addBook(new Book("Sherlock Holmes", 8.99, "mystery"));

        library.addBook(new Book("The Hunger Games", 11.99, "thriller"));
        library.addBook(new Book("Gone Girl", 13.99, "thriller"));

        library.addBook(new Book("The Shining", 12.99, "horror"));
        library.addBook(new Book("It", 15.99, "horror"));
        library.addBook(new Book("Dracula", 9.99, "horror"));

        // Display all books
        System.out.println("========== ALL BOOKS IN LIBRARY ==========");
        for (Book book : library.getBooks()) {
            System.out.println(book);
        }

        // Display statistics by genre
        library.displayGenreStatistics();

        // Get specific genre information
        System.out.println("========== DETAILED GENRE BREAKDOWN ==========");
        Map<String, Integer> counts = library.countBooksByGenre();
        for (String genre : counts.keySet()) {
            System.out.println("\n" + genre.toUpperCase() + ":");
            System.out.println("  Books count: " + library.getCountByGenre(genre));
            System.out.println("  Total price: $" + String.format("%.2f", library.getTotalPriceByGenre(genre)));
            System.out.println("  Books:");
            for (Book book : library.getBooksByGenre(genre)) {
                System.out.println("    - " + book.getName() + " ($" + book.getPrice() + ")");
            }
        }
    }
}

