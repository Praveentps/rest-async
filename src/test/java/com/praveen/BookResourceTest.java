package com.praveen;

import static org.junit.Assert.*;

import java.util.Collection;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.AfterClass;
import org.junit.Test;

public class BookResourceTest extends JerseyTest{

	protected Application configure(){
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		final BookDao dao = new BookDao();
		return new ResourceConfig()
		.packages("com.praveen")
		.register(new AbstractBinder() {			
			protected void configure() {
				bind(dao).to(BookDao.class);
				
			}
		});
		
	}

	@Test
	public void testGetBooks() {
		Collection<Book> response = target("books").request().get(new GenericType<Collection<Book>>(){});
		assertEquals(2,response.size());
	}

	@Test
	public void testGetBook() {
		Book response = target("books").path("1").request().get(Book.class);
		assertNotNull(response);
	}
	
	@Test
	public void testDAO(){
		Book response1 = target("books").path("1").request().get(Book.class);
		Book response2 = target("books").path("1").request().get(Book.class);
		assertEquals(response1.getPublished().getTime(), response2.getPublished().getTime());
	}

}
