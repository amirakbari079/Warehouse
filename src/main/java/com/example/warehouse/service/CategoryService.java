package com.example.warehouse.service;

import com.example.warehouse.dto.CategoryDto;
import com.example.warehouse.entity.CategoryEntity;
import com.example.warehouse.manager.CategoryManager;
import com.example.warehouse.mapper.CategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Slf4j
@Path("/category")
public class CategoryService {
    @Autowired
    CategoryManager categoryManager;
    @Autowired
    CategoryMapper categoryMapper;

    @GET
//    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{code}")
    public CategoryDto CategoryGetRequest(@PathParam("code") String code) {
        CategoryEntity category = categoryManager.loadByCode(code);
        return categoryMapper.toDto(category);
        //TODO Implementing Not Found Exception
    }

    @POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response CategorySaveRequestDto(CategoryDto categoryDto) {
        categoryManager.save(categoryMapper.toEntity(categoryDto));
        return null;
//        return Response.status(201).build();
    }

    @POST
    @Path("update/{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CategoryDto update(@PathParam("code") String code, @RequestBody CategoryDto categoryDto) {
        CategoryEntity updatedCategory = categoryManager.updateCategory(code, categoryDto.getSubject());
        CategoryDto category = categoryMapper.toDto(updatedCategory);
        return category;
    }

    @DELETE
    @Path("/{code}")
    public void delete(@PathParam("code") String code) {
        categoryManager.deleteCategory(code);
    }


}
