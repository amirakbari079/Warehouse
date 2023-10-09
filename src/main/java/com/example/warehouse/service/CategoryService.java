package com.example.warehouse.service;

import com.example.warehouse.Exception.CategoryNotFoundException;
import com.example.warehouse.Exception.NullSubjectException;
import com.example.warehouse.dto.CategoryDto;
import com.example.warehouse.dto.CategoryDtoPage;
import com.example.warehouse.dto.CategorySearchParamsDto;
import com.example.warehouse.entity.CategoryEntity;
import com.example.warehouse.manager.CategoryManager;
import com.example.warehouse.mapper.CategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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
    public Response CategoryGetRequest(@PathParam("code") String code) {
        CategoryEntity category = null;
        try {
            category = categoryManager.loadByCode(code);
            return Response.ok(categoryMapper.toDto(category)).build();
        } catch (CategoryNotFoundException e) {
            return Response.status(404, e.getMessage()).build();
        }

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

    @PUT
    @Path("update/{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("code") String code, CategoryDto categoryDto){
        try {
            if (categoryDto.getSubject() == null) {
                throw new NullSubjectException();
            }
            CategoryEntity updatedCategory = categoryManager.updateCategory(code, categoryDto.getSubject());
           return Response.ok(categoryMapper.toDto(updatedCategory)).build();
        } catch (NullSubjectException e) {
            return Response.status(422, e.getMessage()).build();
        } catch (CategoryNotFoundException e) {
            return Response.status(404, e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{code}")
    public Response delete(@PathParam("code") String code) {
        try {
            categoryManager.deleteCategory(code);
        } catch (Exception e) {
            return Response.status(404, "There is no Category by this Code").build();
        }
        return Response.status(200, "Successful").build();
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public CategoryDtoPage search(@QueryParam("subject") String subject,
                                  @QueryParam("code") String code,
                                  @QueryParam("pageSize") Integer pageSize,
                                  @QueryParam("pageNumber") Integer pageNumber,
                                  @QueryParam("orderBy") String orderBy,
                                  @QueryParam("sortDirection") String sortDirection
    ) {
        CategorySearchParamsDto searchParamsDto = new CategorySearchParamsDto(subject, code, pageSize, pageNumber, orderBy, sortDirection);
        return categoryMapper.categoryListToDto(categoryManager.searchCategory(searchParamsDto));
    }


}
