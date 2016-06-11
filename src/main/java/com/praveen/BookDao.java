package com.praveen;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by vagrant on 09/06/16.
 */
public class BookDao {

private Map<String,Book> books;
    BookDao(){
    	books= new ConcurrentHashMap<String , Book>();
    }
Collection<Book> getbooks(){
    return books.values();
}
    Book getBook(String id){
        return(books.get(id));
    }
    
   Book addBook(Book book){
	   book.setId(UUID.randomUUID().toString());
	   books.put(book.getId(), book);
	   return book;
   }
}
