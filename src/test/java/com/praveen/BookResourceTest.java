package com.praveen;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Date;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class BookResourceTest extends JerseyTest{
private String book1_id;
private String book2_id;

	protected Application configure(){
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		final BookDao dao = new BookDao();
		return new BookApplication(dao);
		
	}
	
	protected Response addBook(String author,String title,Date published,String isbn){
		Book book = new Book();
		book.setAuthor(author);
		book.setTitle(title);
		book.setIsbn(isbn);
		book.setPublished(published);
		Entity<Book> bookEntity= Entity.entity(book, MediaType.APPLICATION_JSON);
		return target("books").request().post(bookEntity);
	}
	
	@Before
	public void setupBooks(){
		book1_id= addBook("author1", "title1", new Date(),"1234").readEntity(Book.class).getId();
		book2_id= addBook("author2", "title2", new Date(),"2345").readEntity(Book.class).getId();
	}
	
	@Test
	public void testAddBook(){
		Date published= new Date();
		Response response = addBook("author", "title", published, "3456");
		assertEquals(200, response.getStatus());
		
		Book responseBook= response.readEntity(Book.class);
		assertEquals("title",responseBook.getTitle());
		assertEquals("author",responseBook.getAuthor());
		assertEquals(published,responseBook.getPublished());
		assertEquals("3456", responseBook.getIsbn());
	}

	@Test
	public void testGetBooks() {
		Collection<Book> response = target("books").request().get(new GenericType<Collection<Book>>(){});
		assertEquals(2,response.size());
	}

	@Test
	public void testGetBook() {
		Book response = target("books").path(book1_id).request().get(Book.class);
		assertNotNull(response);
	}
	
}
