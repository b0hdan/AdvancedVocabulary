package com.dubyniak.bohdan.exception.mapper;

import com.dubyniak.bohdan.exception.NoDatabaseColumnsException;
import io.dropwizard.jersey.errors.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

public class NoDatabaseColumnsExceptionMapper implements ExceptionMapper<NoDatabaseColumnsException> {
    @Override
    public Response toResponse(NoDatabaseColumnsException e) {
        return Response.status(BAD_REQUEST).entity(new ErrorMessage(BAD_REQUEST.getStatusCode(), e.getMessage())).build();
    }
}
