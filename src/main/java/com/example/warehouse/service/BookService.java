package com.example.warehouse.service;


import com.example.warehouse.dto.BookDto;
import com.example.warehouse.manager.BookManager;
import com.example.warehouse.mapper.BookMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/book")
@Slf4j
public class BookService {
    @Autowired
    BookMapper bookmapper;
    @Autowired
    BookManager bookmanager;
//    @POST
//    @CONSUMES(MEDIATYPE.APPLICATION_JSON)
//    PUBLIC RESPONSE METHODNAME(BOOKDTO BOOKDTO) {
//        BOOKDTO.BUILDER().TITLE("MY TITLE").ISBN10("123213134").BUILD();
//        LOG.INFO(BOOKDTO.GETTITLE());
//        RETURN RESPONSE.STATUS(204).BUILD();
//    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response METHODNAME(BookDto bookDto) {
        bookmanager.save(bookmapper.mapper(bookDto));
//        RETURN Response.status(201).BUILD();
        return null;
    }
}