package com.example.warehouse.service;

import com.example.warehouse.Exception.CustomException;
import com.example.warehouse.Exception.NotFoundException;
import com.example.warehouse.Exception.NullFieldException;
import com.example.warehouse.dto.BookDto;
import com.example.warehouse.dto.BookDtoPage;
import com.example.warehouse.entity.BookEntity;
import com.example.warehouse.manager.BookManager;
import com.example.warehouse.mapper.BookMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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
        } catch (NullFieldException | CustomException | IllegalArgumentException e) {
            return Response.status(422, e.getMessage()).build();
        } catch (NotFoundException e) {
            return Response.status(404, e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(422, "There is another book in Database with this Isbn10/Isbn13").build();
        }
        return Response.status(201, "Book saved ✅").build();
    }


    @GET
    @Path("loadBook/{isbn13}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loadBook(@PathParam("isbn13") String isbn13){
        try {
            return Response.ok(bookmapper.toDto(bookmanager.loadBook(isbn13))).build();
        } catch (Exception e) {
            return Response.status(404, "There is no book with this isbn13").build();
        }
    }

    @DELETE
    @Path("/{isbn13}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBook(@PathParam("isbn13") String isbn13) {
        try {
            bookmanager.deleteBook(isbn13);
            return Response.ok("Book Deleted.✅").build();
        } catch (Exception e) {
            return Response.status(404, "There is no book with this isbn13").build();
        }
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@QueryParam("title") String title,
                           @QueryParam("price") String price,
                           @QueryParam("categoryCode") String categoryCode
    ) {
        try {
            BookDtoPage bookDtoPage = bookmanager.searchBook(title, price, categoryCode);
            return Response.ok().entity(bookDtoPage).build();
        } catch (NotFoundException e) {
            return Response.status(404, e.getMessage()).build();
        }
    }

    @PATCH
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@QueryParam("isbn13") String isbn13, @QueryParam("isbn10") String isbn10, @QueryParam("title") String title, @QueryParam("price") String price) throws Exception {
        if (isbn13 == null || isbn10 == null || title == null || price == null) {
            return Response.status(400, "all fields are necessary pleas fill all of them ❗").build();
        }
        try {
            bookmanager.updateBook(isbn13, isbn10, title, "$" + price);
            return Response.ok("Category Updated").build();
        } catch (Exception e) {
            return Response.status(404, "book not found ❗").build();
        }
    }
}