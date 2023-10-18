package com.example.warehouse.service;
import com.example.warehouse.Exception.CustomException;
import com.example.warehouse.Exception.NotFoundException;
import com.example.warehouse.Exception.NullFieldException;
import com.example.warehouse.dto.BookDto;
import com.example.warehouse.dto.CategoryDto;
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
        } catch (NullFieldException e) {
            return Response.status(422, e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(422, e.getMessage()).build();
        } catch (NotFoundException e) {
            return Response.status(404, e.getMessage()).build();
        } catch (CustomException e) {
            return Response.status(422, e.getMessage()).build();
        }
        return Response.status(201, "Book saved ✅").build();
    }


    @GET
    @Path("loadBook/{isbn13}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loadBook(@PathParam("isbn13") String isbn13) throws Exception {
        return Response.ok(bookmapper.toDto(bookmanager.loadBook(isbn13))).build();
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
                           @QueryParam("price") Integer price
//                           @QueryParam("categories")List<CategoryDto> categoryDtoList
                           ) {
        return null;
    }
}