package com.dubyniak.bohdan.exception.mapper;

import io.dropwizard.jersey.errors.ErrorMessage;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

public class EntityNotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    @Override
    public Response toResponse(NotFoundException e) {
        return Response.status(NOT_FOUND).entity(new ErrorMessage(NOT_FOUND.getStatusCode(), e.getMessage())).build();
    }
}
