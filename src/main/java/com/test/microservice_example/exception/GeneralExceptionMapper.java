package com.test.microservice_example.exception;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GeneralExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {

        if (exception instanceof IllegalArgumentException || exception instanceof BadRequestException) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Invalid request: " + exception.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
        
        if (exception instanceof NotFoundException) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Resource not found: " + exception.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    
        
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Internal server error: " + exception.getMessage())
                .type(MediaType.TEXT_PLAIN)
                .build();
        

    }
}