package com.example.warehouse.service;

import com.example.warehouse.dto.CategoryDto;
import com.example.warehouse.manager.CategoryManager;
import com.example.warehouse.mapper.CategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Slf4j
@Path("/category")
public class CategoryService {
    @Autowired
    CategoryManager categoryManager;
    @Autowired
    CategoryMapper categoryMapper;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response CategorySaveRequestDto(CategoryDto categoryDto) {
        categoryManager.save(categoryMapper.mapper(categoryDto));
        return null;
//        return Response.status(201).build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response CategoryGetRequest(String code) {
        categoryManager.loadByCode(code);
        return null;
    }

}
