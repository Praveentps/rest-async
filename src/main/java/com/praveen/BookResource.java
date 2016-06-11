package com.praveen;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Created by vagrant on 09/06/16.
 */
@Path("/books")
public class BookResource {
BookDao dao = new BookDao();
//http://localhost:8080/myapp/books
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Book> getBooks(){
        return(dao.getbooks());
    }
    //http://localhost:8080/myapp/books/1
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Book getBook(@PathParam("id")String id){
        return dao.getBook(id);
    }
}
