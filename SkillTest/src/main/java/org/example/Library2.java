package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Library class to manage books and provide statistics based on genres.
 */
public class Library2 {

   private final List<Book> bookLlist;

   public Library2(){
         this.bookLlist = new ArrayList<>();
   }

    public void addBook(Book book){
       bookLlist.add(book);
   }

    public void addBooks(List<Book> booksList){
       bookLlist.addAll(booksList);
   }

    public List<Book> getBookLlist(){
       return new ArrayList<>(bookLlist);
   }

  public Map<String, Integer> getBooksCountByGenere(){
       return bookLlist.stream().collect(
               java.util.stream.Collectors.groupingBy(Book::getGenre, java.util.stream.Collectors.summingInt(e -> 1)));
   }

   public Map<String, Double> getTotalPriceByGenre(){
       return bookLlist.stream().collect(
               java.util.stream.Collectors.groupingBy(Book::getGenre, java.util.stream.Collectors.summingDouble(Book::getPrice)));
   }

}

