package com.example.warehouse.service;
import com.example.warehouse.Exception.NullFieldException;
import com.example.warehouse.dto.BookDto;
import com.example.warehouse.manager.BookManager;
import com.example.warehouse.manager.ItBooksProxy;
import com.example.warehouse.mapper.BookMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
@Path("/book")
@Slf4j
public class BookService {
    @Autowired
    BookMapper bookmapper;
    @Autowired
    BookManager bookmanager;

    @POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveBook(BookDto bookDto) {
        try {
            bookmanager.save(bookmapper.mapper(bookDto));
        } catch (NullFieldException e) {
           return Response.status(400,e.getMessage()).build();
        }catch (IllegalArgumentException e){
            return Response.status(400,e.getMessage()).build();
        }catch (Exception e){
            return Response.status(422,e.getMessage()).build();
        }
        return Response.status(201,"Book saved ✅").build();
    }

ItBooksProxy itBooksProxy=new ItBooksProxy();
    @GET
    @Path("loadBook/{isbn13}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loadBook(@PathParam("isbn13") String isbn13) {
        return Response.ok(bookmapper.toDto(bookmanager.loadBook(isbn13))).build();
    }
}