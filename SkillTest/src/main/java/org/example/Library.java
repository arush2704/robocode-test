package org.example;

import java.util.*;

/**
 * Library class to manage books and provide statistics based on genres.
 */
public class Library {
    private List<Book> books;

    public Library() {
        this.books = new ArrayList<>();
    }

    /**
     * Add a book to the library.
     *
     * @param book the book to add
     */
    public void addBook(Book book) {
        books.add(book);
    }

    /**
     * Add multiple books to the library.
     *
     * @param booksList the list of books to add
     */
    public void addBooks(List<Book> booksList) {
        books.addAll(booksList);
    }

    /**
     * Get all books in the library.
     *
     * @return list of all books
     */
    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }

    /**
     * Count the number of books by genre.
     *
     * @return a map with genre as key and count as value
     */
    public Map<String, Integer> countBooksByGenre() {
        Map<String, Integer> genreCount = new HashMap<>();
        for (Book book : books) {
            genreCount.put(book.getGenre(), genreCount.getOrDefault(book.getGenre(), 0) + 1);
        }
        return genreCount;
    }

    /**
     * Calculate the total price of books by genre.
     *
     * @return a map with genre as key and total price as value
     */
    public Map<String, Double> getTotalPriceByGenre() {
        Map<String, Double> genrePrice = new HashMap<>();
        for (Book book : books) {
            genrePrice.put(book.getGenre(),
                    genrePrice.getOrDefault(book.getGenre(), 0.0) + book.getPrice());
        }
        return genrePrice;
    }

    /**
     * Get count of books for a specific genre.
     *
     * @param genre the genre to look for
     * @return count of books in that genre
     */
    public int getCountByGenre(String genre) {
        return (int) books.stream()
                .filter(book -> book.getGenre().equalsIgnoreCase(genre))
                .count();
    }

    /**
     * Get total price of books for a specific genre.
     *
     * @param genre the genre to look for
     * @return total price of books in that genre
     */
    public double getTotalPriceByGenre(String genre) {
        return books.stream()
                .filter(book -> book.getGenre().equalsIgnoreCase(genre))
                .mapToDouble(Book::getPrice)
                .sum();
    }

    /**
     * Get books by a specific genre.
     *
     * @param genre the genre to filter by
     * @return list of books in that genre
     */
    public List<Book> getBooksByGenre(String genre) {
        List<Book> filteredBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getGenre().equalsIgnoreCase(genre)) {
                filteredBooks.add(book);
            }
        }
        return filteredBooks;
    }

    /**
     * Display summary statistics for all genres.
     */
    public void displayGenreStatistics() {
        Map<String, Integer> counts = countBooksByGenre();
        Map<String, Double> prices = getTotalPriceByGenre();

        System.out.println("\n========== LIBRARY STATISTICS ==========");
        for (String genre : counts.keySet()) {
            System.out.println(genre + ":");
            System.out.println("  Count: " + counts.get(genre));
            System.out.println("  Total Price: $" + String.format("%.2f", prices.get(genre)));
        }
        System.out.println("========================================\n");
    }
}

